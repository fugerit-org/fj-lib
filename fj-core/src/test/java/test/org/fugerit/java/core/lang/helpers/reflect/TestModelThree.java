package test.org.fugerit.java.core.lang.helpers.reflect;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class TestModelThree {

	private BigDecimal idThree;
	
	private String valueThree;
	
	private Date dateJava;
	
	private XMLGregorianCalendar dateXml;

	public BigDecimal getIdThree() {
		return idThree;
	}

	public void setIdThree(BigDecimal idThree) {
		this.idThree = idThree;
	}

	public String getValueThree() {
		return valueThree;
	}

	public void setValueThree(String valueThree) {
		this.valueThree = valueThree;
	}

	public Date getDateJava() {
		return dateJava;
	}

	public void setDateJava(Date dateJava) {
		this.dateJava = dateJava;
	}

	public XMLGregorianCalendar getDateXml() {
		return dateXml;
	}

	public void setDateXml(XMLGregorianCalendar dateXml) {
		this.dateXml = dateXml;
	}
	
}
