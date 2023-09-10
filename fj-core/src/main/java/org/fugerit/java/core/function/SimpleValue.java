package org.fugerit.java.core.function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class SimpleValue<T> {

	@Getter @Setter T value;
	
}
