package test.org.fugerit.java.core.util.tree;

import java.io.InputStream;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.core.util.tree.InheritTreeDecorator;
import org.fugerit.java.core.util.tree.TreeConfigKeyStringXML;

class TestTreeConfig extends TreeConfigKeyStringXML<TestNode, ListMapStringKey<TestNode>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5101890005734322613L;

	public TestTreeConfig() {
		this.decorators.add( new InheritTreeDecorator<TestNode>() );
		this.getGeneralProps().setProperty( ATT_TYPE , TestNode.class.getName() );
		this.getGeneralProps().setProperty( ATT_LIST_TYPE , ListMapStringKey.class.getName() );
	}
	
	public static TestTreeConfig load( String clPath ) throws Exception {
		TestTreeConfig config = new TestTreeConfig();
		InputStream is = ClassHelper.loadFromDefaultClassLoader( clPath );
		config.configureXML( is );
		return config;
	}
	
}
