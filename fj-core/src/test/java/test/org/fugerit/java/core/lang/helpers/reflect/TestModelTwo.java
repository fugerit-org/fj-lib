package test.org.fugerit.java.core.lang.helpers.reflect;

import java.math.BigDecimal;

public class TestModelTwo {

	public TestModelTwo() {
		this( null, null );
	}
	
	public TestModelTwo(BigDecimal idTwo, String valueTwo) {
		super();
		this.idTwo = idTwo;
		this.valueTwo = valueTwo;
	}

	private BigDecimal idTwo;
	
	private String valueTwo;

	public BigDecimal getIdTwo() {
		return idTwo;
	}

	public void setIdTwo(BigDecimal idTwo) {
		this.idTwo = idTwo;
	}

	public String getValueTwo() {
		return valueTwo;
	}

	public void setValueTwo(String valueTwo) {
		this.valueTwo = valueTwo;
	}
	
	public String toString() {
		return this.getClass().getSimpleName()+"[idTwo:"+this.getIdTwo()+",valueTwo:"+this.getValueTwo()+"]";
	}
	
}
