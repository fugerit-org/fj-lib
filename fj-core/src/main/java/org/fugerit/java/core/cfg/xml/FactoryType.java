package org.fugerit.java.core.cfg.xml;

import java.io.IOException;

import org.w3c.dom.Element;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class FactoryType extends BasicIdConfigType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4156411727576276222L;

	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END	
	
    @Getter @Setter private String type;
	
    @Getter @Setter private String info;
	
    @Getter @Setter private String unsafe;
	
    @Getter @Setter private String unsafeMode;
	
    @Getter @Setter private Element element;

}
