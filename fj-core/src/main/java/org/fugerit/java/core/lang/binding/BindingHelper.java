package org.fugerit.java.core.lang.binding;

import org.fugerit.java.core.cfg.xml.IdConfigType;

public interface BindingHelper extends IdConfigType {

	void bind( BindingConfig binding, BindingFieldConfig field, Object from, Object to ) throws BindingException;
	
}
