package test.org.fugerit.java.core.cfg.provider;

import java.io.IOException;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.AbstractConfigurableObject;
import org.fugerit.java.core.cfg.provider.ConfigProviderFacade;
import org.fugerit.java.core.cfg.provider.ConfigProviderWrapper;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestConfigProvider extends AbstractConfigurableObject {

	private static final long serialVersionUID = 134123432L;

	@Test
	void testConfigProvider() throws ConfigException, IOException {
		ConfigProviderWrapper wrapper = new ConfigProviderWrapper(); // will wrap around a DefaultConfigProvider
		wrapper.readConfiguration( StreamHelper.MODE_CLASSLOADER , "core/cfg/xml/factory-catalog-test.xml" );
		log.info( "unwrap : {}", wrapper.unwrap() );
		log.info( "unwrapModel : {}", wrapper.unwrapModel() );
		log.info( "toString : {}", wrapper );
		Assertions.assertThrows( ConfigException.class , () ->  wrapper.readConfiguration( null , "path/not/exists.xml" ) );
		TestConfigProvider caller = new TestConfigProvider();
		ConfigProviderFacade.getInstance().registerByCaller( caller, wrapper );
		Assertions.assertNotNull( ConfigProviderFacade.getInstance().getProviderByCaller(caller) );
		Assertions.assertNotNull( ConfigProviderFacade.getInstance().getProviderByName( caller.getClass().getName() ) );
		ConfigProviderFacade.setDefaultProvider(wrapper);
		ConfigProviderFacade.getInstance().findAndSetConfigProvider( caller.getClass().getName() , caller );
		Assertions.assertNotNull( new BasicTest().fullSerializationTest( wrapper ) );
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
