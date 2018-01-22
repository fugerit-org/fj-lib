package org.fugerit.java.core.web.navmap.model;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.core.web.auth.handler.AuthHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * NavMap library configuration reading.
 * 
 * Version 1.0.2 (2016-12-12)
 * 
 * @author Fugerit
 *
 * @see org.fugerit.java.core.web.navmap.model.NavMap
 *
 */
public class NavConfig {

	private static final Logger logger= LoggerFactory.getLogger(NavConfig.class);
	
	private static void recurseEntries( Element element, ListMapStringKey<NavEntry> entryList, NavEntry parent  ) {
		NodeList navEntryTags = element.getChildNodes();
		
		for ( int k=0; k<navEntryTags.getLength(); k++ ) {
			Node currentNode = navEntryTags.item( k );
			
			if ( currentNode.getNodeType() == Node.ELEMENT_NODE ) {
				Element currentEntryTag = (Element) navEntryTags.item( k );
				if ( currentEntryTag.getTagName().equals( "nav-entry" ) ) {
					String url = currentEntryTag.getAttribute( "url" );
					String auth = currentEntryTag.getAttribute( "auth" );
					String label = currentEntryTag.getAttribute( "label" );
					String menu1 = currentEntryTag.getAttribute( "menu1" );
					String menu2 = currentEntryTag.getAttribute( "menu2" );
					String menu3 = currentEntryTag.getAttribute( "menu3" );
					NavEntry entry = new NavEntry(url, label, menu1, menu2, menu3, auth);
					if ( parent != null ) {
						entry.setParent( parent );
						parent.getKids().add( entry );
					}
					entryList.add( entry );
					logger.info( "recurseEntries() - adding entry : "+entry+" (parent:"+parent+")" );
					// handling aliases
					String alias = currentEntryTag.getAttribute( "alias" );
					if ( alias != null && alias.trim().length() > 0 ) {
						String[] aliases = alias.split( ";" );
						for ( int i=0; i<aliases.length; i++ ) {
							String currentAlias = aliases[i];
							NavEntry navAlias = new NavEntry(currentAlias, label, menu1, menu2, menu3, auth);
							entryList.add( navAlias );
							entry.getAlias().add( navAlias );
						}
					}
					// recursion
					recurseEntries( currentEntryTag , entryList, entry );
				}
			}
		}
	}
	
	public static NavMap parseConfig( InputStream is ) throws NavException {
		logger.info( "parseConfig() - start" );
		ListMapStringKey<NavEntry> entryList = new ListMapStringKey<NavEntry>();
		ListMapStringKey<NavMenu> menuList = new ListMapStringKey<NavMenu>();
		AuthHandler authHandler = null;
		try {
			logger.info( "parseConfig() - parsing input stream start" );
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = dbf.newDocumentBuilder();
			Document document = parser.parse( is );
			logger.info( "parseConfig() - parsing input stream end" );
			Element root = document.getDocumentElement();
			String authHandlerType = root.getAttribute( "auth-handler" );
			
			if ( authHandlerType != null && authHandlerType.trim().length() > 0 ) {
				authHandler = (AuthHandler)ClassHelper.newInstance( authHandlerType );
			}
			// parsing entry
			NodeList nle = root.getElementsByTagName( "nav-entry-list" );
			if ( nle.getLength() == 1 ) {
				Element navEntryListTag = (Element)nle.item( 0 );
				recurseEntries( navEntryListTag, entryList, null );	
			} else {
				throw new Exception( "Configuration error, nav-entry-list tag must be provided" );
			}
			logger.info( "parseConfig() - total entries : "+entryList.size() );
			// parsing menu
			NodeList navMenuTags = root.getElementsByTagName( "nav-menu" );
			for ( int k=0; k<navMenuTags.getLength(); k++ ) {
				Element currentTag = (Element) navMenuTags.item( k );
				String id = currentTag.getAttribute( "id" );
				NavMenu menu = new NavMenu( id );
				menuList.add( menu );
				NodeList menuItemTags = currentTag.getElementsByTagName( "menu-item" );
				for ( int i=0; i<menuItemTags.getLength(); i++ ) {
					Element currentItem = (Element) menuItemTags.item( i );
					String url = currentItem.getAttribute( "url" );
					NavEntry item = entryList.get( url );
					String useLabel = currentItem.getAttribute( "use-label" );
					if ( StringUtils.isNotEmpty( useLabel ) ) {
						menu.getEntries().add( item.copyWithLabel( useLabel ) );
					} else {
						menu.getEntries().add( item );	
					}
					logger.debug( "parseConfig() - adding menu item : "+item+" to menu "+id );
				}
				logger.debug( "parseConfig() - adding menu : "+menu );
			}
			
			logger.info( "parseConfig() - total menu : "+menuList.size() );
		} catch (Exception e) {
			logger.error( "NavMap config error", e );
		} finally {
			logger.info( "parseConfig() - end" );
		}
		NavMap navMap = new NavMap( entryList, menuList );
		if ( authHandler != null ) {
			logger.info( "parseConfig() - override auth handler -> "+authHandler );
			navMap.setAuthHandler( authHandler );
		}
		return navMap;
	}
	
}
