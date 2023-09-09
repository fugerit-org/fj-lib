package test.org.fugerit.java.core.cfg.provider;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.AbstractConfigurableObject;
import org.fugerit.java.core.cfg.provider.ConfigProviderFacade;
import org.fugerit.java.core.cfg.provider.ConfigProviderWrapper;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConfigProvider extends AbstractConfigurableObject {

	private static final long serialVersionUID = 134123432L;

	@Test
	public void testConfigProvider() throws ConfigException {
		ConfigProviderWrapper wrapper = new ConfigProviderWrapper(); // will wrap around a DefaultConfigProvider
		wrapper.readConfiguration( StreamHelper.MODE_CLASSLOADER , "core/cfg/xml/factory-catalog-test.xml" );
		log.info( "unwrap : {}", wrapper.unwrap() );
		log.info( "unwrapModel : {}", wrapper.unwrapModel() );
		log.info( "toString : {}", wrapper );
		Assert.assertThrows( ConfigException.class , () ->  wrapper.readConfiguration( null , "path/not/exists.xml" ) );
		TestConfigProvider caller = new TestConfigProvider();
		ConfigProviderFacade.getInstance().registerByCaller( caller, wrapper );
		Assert.assertNotNull( ConfigProviderFacade.getInstance().getProviderByCaller(caller) );
		Assert.assertNotNull( ConfigProviderFacade.getInstance().getProviderByName( caller.getClass().getName() ) );
		ConfigProviderFacade.setDefaultProvider(wrapper);
		ConfigProviderFacade.getInstance().findAndSetConfigProvider( caller.getClass().getName() , caller );
	}

	@Override
	public void configure(Properties props) throws ConfigException {
		log.info( "configure() do nothing!" );
	}

	@Override
	public void configure(Element tag) throws ConfigException {
		log.info( "configure() do nothing!" );
	}
	
}
