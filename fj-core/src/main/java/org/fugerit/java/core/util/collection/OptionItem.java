/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		http://www.fugerit.org/java/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.util.collection;

import java.io.Serializable;
import java.util.Comparator;

/**
 * <p>Object representing a key/value String set. </p>
 * 
 * @author Fugerit
 *
 */
public class OptionItem implements KeyObject<String>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8792384610955797338L;

	/**
	 * Comparator to sort OptionItem by label String value
	 * 
	 */
	public final static Comparator<OptionItem> LABEL_SORTER = new Comparator<OptionItem>() {
		public int compare(OptionItem object1, OptionItem object2) {
			return object1.getLabel().compareTo( object2.getLabel() );
		}
	};

	/**
	 * Comparator to sort OptionItem by value String value
	 * 
	 */
	public final static Comparator<OptionItem> VALUE_SORTER = new Comparator<OptionItem>() {
		public int compare(OptionItem object1, OptionItem object2) {
			return object1.getValue().compareTo( object2.getValue() );
		}
	};
	
	private String value;
	
	private String label;

	/**
	 * Getter for the value property
	 * 
	 * @return	the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter for the value property
	 * 
	 * @param value		the value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Getter for the label property
	 * 
	 * @return		the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Setter for the label property
	 * 
	 * @param label		the label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Set both the value and label of the OptionItem
	 * 
	 * @param value		the value
	 * @param label		the label
	 */
	public void setValueAndLabel( String value, String label ) {
		this.setValue( value );
		this.setLabel( label );
	}
	
	/**
	 * Set both the value and label of the OptionItem
	 * 
	 * @param valueAndLabel both the label and value
	 */
	public void setValueAndLabel( String valueAndLabel ) {
		this.setValueAndLabel( valueAndLabel, valueAndLabel );
	}
	
	@Override
	public String getKey() {
		return this.getValue();
	}

	/**
	 * Creates a new OptionItem with the given label and value.
	 * 
	 * @param value	the value
	 * @param label	the label
	 */
	public OptionItem(String value, String label) {
		super();
		this.setValueAndLabel(value, label);
	}

	/**
	 * Creates a new OptionItem with the given label and value.
	 * 
	 * @param valueAndLabel		both the label and value
	 */
	public OptionItem( String valueAndLabel ) {
		this( valueAndLabel, valueAndLabel );
	}
	
}
