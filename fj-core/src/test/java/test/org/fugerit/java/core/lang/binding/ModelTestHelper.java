package test.org.fugerit.java.core.lang.binding;

import org.fugerit.java.core.lang.annotate.DefineImpl;

@DefineImpl( as = ModelTestHelperImpl.class )
public interface ModelTestHelper {

	String getMyField();

	void setMyField(String myField);

}