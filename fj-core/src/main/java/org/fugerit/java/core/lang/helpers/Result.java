package org.fugerit.java.core.lang.helpers;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author Fugerit
 *
 */
public class Result {

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
	        return (Exception)this.warningList.get(index);
	    }
	    
	    public Exception getFatal(int index) {
	        return (Exception)this.fatalList.get(index);
	    }    
	    
	    public Exception getError(int index) {
	        return (Exception)this.errorList.get(index);
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
	        this.errorList = new ArrayList<Exception>();
	        this.fatalList = new ArrayList<Exception>();
	        this.warningList = new ArrayList<Exception>();
	    }
	
}
