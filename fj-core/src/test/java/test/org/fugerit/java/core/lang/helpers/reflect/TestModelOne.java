package test.org.fugerit.java.core.lang.helpers.reflect;

import java.math.BigDecimal;

public class TestModelOne {

	public TestModelOne() {
		this( null, null, null );
	}
	
	public TestModelOne(BigDecimal idOne, String valueOne, TestModelTwo kid) {
		super();
		this.idOne = idOne;
		this.valueOne = valueOne;
		this.kid = kid;
	}

	private BigDecimal idOne;
	
	private String valueOne;

	public BigDecimal getIdOne() {
		return idOne;
	}

	public void setIdOne(BigDecimal idOne) {
		this.idOne = idOne;
	}

	public String getValueOne() {
		return valueOne;
	}

	public void setValueOne(String valueOne) {
		this.valueOne = valueOne;
	}

	private TestModelTwo kid;

	public TestModelTwo getKid() {
		return kid;
	}

	public void setKid(TestModelTwo kid) {
		this.kid = kid;
	}
	
	public String toString() {
		return this.getClass().getSimpleName()+"[idOne:"+this.getIdOne()+",valueOne:"+this.getValueOne()+",kid:"+this.getKid()+"]";
	}
	
	
}
