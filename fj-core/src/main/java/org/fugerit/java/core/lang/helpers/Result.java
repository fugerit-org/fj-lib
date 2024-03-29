package org.fugerit.java.core.lang.helpers;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 
 *
 * @author Fugerit
 *
 */
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5022803261949916909L;

	private void printList( List<Exception> v, PrintStream stream) {
	        for (int k=0; k<v.size(); k++) {
	            stream.println(""+(k+1)+" - "+v.get(k));
	        }
	    }
	    
	    public void printErrorReport(PrintStream stream) {
	        stream.println("Fatal count   : "+this.fatalCount());
	        this.printList(this.fatalList, stream);
	        stream.println("Error count   : "+this.errorCount());
	        this.printList(this.errorList, stream);
	        stream.println("Warning count : "+this.warningCount());
	        this.printList(this.warningList, stream);
	    }
	    
	    public boolean isPartialSuccess() {
	        return (this.errorList.isEmpty() &&
	                this.fatalList.isEmpty());
	    }
	    
	    public boolean isTotalSuccess() {
	        return (this.errorList.isEmpty() &&
	                this.fatalList.isEmpty() &&
	                this.warningList.isEmpty());
	    }
	    
	    public void clear() {
	        this.errorList.clear();
	        this.fatalList.clear();
	        this.warningList.clear();
	    }
	    
	    public int warningCount() {
	        return this.warningList.size();
	    }
	    
	    public int fatalCount() {
	        return this.fatalList.size();
	    }    
	    
	    public int errorCount() {
	        return this.errorList.size();
	    }
	    
	    public void putWarning(Exception ex) {
	        this.warningList.add(ex);
	    }    
	    
	    public void putFatal(Exception ex) {
	        this.fatalList.add(ex);
	    }    
	    
	    public void putError(Exception ex) {
	        this.errorList.add(ex);
	    }
	    
	    public Exception getWarning(int index) {
	        return this.warningList.get(index);
	    }
	    
	    public Exception getFatal(int index) {
	        return this.fatalList.get(index);
	    }    
	    
	    public Exception getError(int index) {
	        return this.errorList.get(index);
	    }
	    
	    private List<Exception> errorList;
	    private List<Exception> fatalList;
	    private List<Exception> warningList;
	    
	    /*
	     * <p>Crea un nuovo Result</p>
	     * 
	     * 
	     */
	    public Result() {
	        super();
	        this.errorList = new ArrayList<>();
	        this.fatalList = new ArrayList<>();
	        this.warningList = new ArrayList<>();
	    }
	    
	    public Collection<Exception> errors() {
	    	return Collections.unmodifiableCollection( this.errorList );
	    }

	    public Collection<Exception> fatals() {
	    	return Collections.unmodifiableCollection( this.fatalList );
	    }
	    
	    public Collection<Exception> warnings() {
	    	return Collections.unmodifiableCollection( this.warningList );
	    }
	    

	    public Collection<Exception> fatalsAndErrors() {
	    	return Collections.unmodifiableCollection( CollectionUtils.merge( new ArrayList<Exception>( this.fatalList ) ,  this.errorList ) );
	    }
	    
}
