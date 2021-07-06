package org.fugerit.java.ext.doc.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.log.BasicLogObject;
import org.w3c.dom.Element;

public abstract class DefaultDocTypeHandler extends BasicLogObject implements DocTypeHandler {

	@Override
	public void handleDocType(HttpServletRequest request, HttpServletResponse response, DocContext docContext) throws Exception {

	}

	@Override
	public void handleDocTypeInit(HttpServletRequest request, HttpServletResponse response, DocContext docContext) throws Exception {

	}

	@Override
	public void handleDocTypePost(HttpServletRequest request, HttpServletResponse response, DocContext docContext) throws Exception {

	}

	@Override
	public void init(Element config) throws ConfigException {

	}

}
