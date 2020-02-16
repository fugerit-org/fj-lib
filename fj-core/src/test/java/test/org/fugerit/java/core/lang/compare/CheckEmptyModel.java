package test.org.fugerit.java.core.lang.compare;

import java.io.Serializable;

import org.fugerit.java.core.lang.compare.CheckEmpty;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class CheckEmptyModel implements Serializable, CheckEmpty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7042054220652894764L;

	private String prop1;
	
	private String prop2;

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	public CheckEmptyModel(String prop1, String prop2) {
		super();
		this.prop1 = prop1;
		this.prop2 = prop2;
	}

	@Override
	public boolean isEmpty() {
		return StringUtils.isEmpty( this.getProp1() ) && StringUtils.isEmpty( this.getProp2() );
	}
	
	
	
}
