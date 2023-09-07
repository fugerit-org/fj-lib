/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.xml.dom;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Sample utility for looking though a DOM structure
 *
 * @author Fugerit
 *
 */
public class SearchDOM {

	private SearchDOM() {}
	
	/**
	 * Creates a new SearchDOM instance
	 * 
	 * @param trimText		should test be trimmed?
	 * @param ignoreBlank	should blank spaces be ignored?
	 * @return the SearchDOM object
	 */
    public static SearchDOM newInstance(boolean trimText, boolean ignoreBlank) {
        return (new SearchDOM(trimText, ignoreBlank));
    }
    
    /**
     * Return a new SearchDOM object, default <code>true</code> to both trimText and ignoreBlank
     * 
     * @return the SearchDOM object
     */
    public static SearchDOM newInstance() {
    	return newInstance( true, true );
    }
    
    private static List<Element> makeList(Element base) {
        List<Element> list = new ArrayList<Element>();
        list.add(base);
        return list;
    }
    
    private boolean trimText;
    private boolean ignoreBlank;

    private SearchDOM(boolean trimText, boolean ignoreBlank) {
        this.trimText = trimText;
        this.ignoreBlank = ignoreBlank;
    }
    
    /**
     * Finds the text contained in an Element
     * 
     * @param node	the element
     * @return		the text
     */
    public String findText(Element node) {
        String text = null;
        List<String> list = this.findAllText(node);
        if (!list.isEmpty()) {
            text = (String)list.get(0);
        }
        return text;
    }
    
    /**
     * Finds all the texts contained in a Element
     * 
     * @param node	the element
     * @return		list of text
     */
    public List<String> findAllText(Element node) {
        List<String> result = new ArrayList<String>();
        NodeList kids = node.getChildNodes();
        for (int k=0; k<kids.getLength(); k++) {
            String text = this.getText(kids.item(k));
            if (text!=null) {
                result.add(text);
            }
        }
        return result;
    }       
    
    /**
     * Finds all Element child to a given element with a given tag name.
     * 
     * @param node		the element
     * @param name		the name of the tag to be looked
     * @return			the list of element
     */
    public List<Element> findAllTags(Element node, String name) {
        List<Element> result = new ArrayList<Element>();
        List<Element> search = makeList(node);
        Element tag = findTag(search, name);
        while (tag!=null) {
            result.add(tag);
            tag = findTag(search, name);
        }
        return result;
    }    
    
    /**
     * Find next Element with a give Name in a tag
     * 
     * @param node	the Element where to search
     * @param name	the ELement name to look for
     * @return		the Element if found.
     */
    public Element findTag(Element node, String name) {
        return this.findTag(makeList(node), name);
    }
  
    private Element findTag(List<Element> queue, String name) {
        Element tag = null;
        while (tag==null && !queue.isEmpty()) {
            Element current = (Element)queue.remove(0);
            if (current.getTagName().equals(name)) {
                tag = current;
            }
            NodeList kids = current.getChildNodes();
            for (int k=0; k<kids.getLength(); k++) {
                Node child = kids.item(k);
                if (child.getNodeType()==Node.ELEMENT_NODE) {
                    queue.add( (Element)child );
                }
            }
        }
        return tag;
    }
    
    private String getText(Node node) {
        String text = null;
        if (node instanceof CharacterData) {
            text = ((CharacterData)node).getData();
            if (this.trimText) {
                text = text.trim();
            }
            if (this.ignoreBlank && text.length()==0) {
                text = null;
            }
        }
        return text;
    }
	
}
