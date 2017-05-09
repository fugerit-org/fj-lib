package org.fugerit.java.core.web.servlet.config;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.log.BasicLogObject;

public class CommandBase extends BasicLogObject implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = -289413529900746052L;

	private String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public boolean execute( ServletContext context, HttpServletRequest request, HttpServletResponse response, String[] params ) throws Exception {
		boolean ok = true;
		return ok;
	}
	
}
