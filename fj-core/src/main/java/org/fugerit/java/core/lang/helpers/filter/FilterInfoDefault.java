package org.fugerit.java.core.lang.helpers.filter;

public class FilterInfoDefault implements FilterInfo {

	public static FilterInfo newFilter( Object value, String evaluate ) {
		return newFilter(value, evaluate, null);
	}
	
	public static FilterInfo newFilter( String evaluate, String path ) {
		return newFilter(null, evaluate, path);
	}
	
	public static FilterInfo newFilter( Object value, String evaluate, String path ) {
		return new FilterInfoDefault(value, evaluate, path);
	}
	
	public FilterInfoDefault() {
		
	}
	
	private FilterInfoDefault(Object value, String evaluate, String path) {
		super();
		this.value = value;
		this.evaluate = evaluate;
		this.path = path;
	}

	private Object value;
	
	private String evaluate;
	
	private String path;

	@Override
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	@Override
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
