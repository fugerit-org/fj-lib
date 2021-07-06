package org.fugerit.java.ext.doc.type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.ext.doc.filter.DocContext;
import org.fugerit.java.ext.doc.filter.DocTypeHandler;
import org.w3c.dom.Element;

public class BasicTypeHandler extends BasicLogObject implements DocTypeHandler {

	public void setContentDisposition( HttpServletRequest request, HttpServletResponse response, DocContext docContext ) {
		setContentDisposition(request, response, docContext.getFileName() );
	}
	
	public void setContentDisposition( HttpServletRequest request, HttpServletResponse response, String fileName ) {
		response.addHeader("Content-Disposition", "attachment; filename="+fileName );
	}
	
	private String type;
	
	private String extension;

	
	@Override
	public String getExtension() {
		return this.extension;
	}

	@Override
	public String getType() {
		return this.type;
	}

	public BasicTypeHandler(String type, String extension) {
		super();
		this.type = type;
		this.extension = extension;
	}

	@Override
	public void handleDocTypeInit(HttpServletRequest request, HttpServletResponse response, DocContext docContext) throws Exception {
		this.setContentDisposition( request, response, docContext );
	}
	
	@Override
	public void handleDocType(HttpServletRequest request, HttpServletResponse response, DocContext docContext) throws Exception {
	}

	@Override
	public void handleDocTypePost(HttpServletRequest request, HttpServletResponse response, DocContext docContext) throws Exception {
	}

	@Override
	public void init(Element config) throws ConfigException {
	}

}
