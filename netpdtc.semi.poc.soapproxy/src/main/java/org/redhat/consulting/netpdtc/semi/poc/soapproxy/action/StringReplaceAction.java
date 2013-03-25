package org.redhat.consulting.netpdtc.semi.poc.soapproxy.action;

import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.configure.ConfigProperty;
import org.jboss.soa.esb.message.Message;

public class StringReplaceAction {
	
	@ConfigProperty(name="findValue")
	protected String find = "RHT";
	
	@ConfigProperty(name="replaceValue")
	protected String replace = "MS";

	public StringReplaceAction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	@Process
	public Message process(Message message) {

		String body = (String) message.getBody().get();
		
		body = body.replaceAll(find, replace);
		
		message.getBody().add(body);
		
		return message;
	}

}
