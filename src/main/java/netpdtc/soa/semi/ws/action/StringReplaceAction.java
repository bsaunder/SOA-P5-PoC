package netpdtc.soa.semi.ws.action;

import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.configure.ConfigProperty;
import org.jboss.soa.esb.message.Message;

/**
 * Replaces a Specified Value with Another Specified Value inside the Message Body.
 * 
 * @author Bryan Saunders <bsaunder@redhat.com>
 * 
 */
public class StringReplaceAction {

    /**
     * String Value to Search for. Can also be a Regular Expression.
     */
    @ConfigProperty(name = "findValue")
    private String find = "MSFT";

    /**
     * Value to Replace the String With.
     */
    @ConfigProperty(name = "replaceValue")
    private String replace = "RHT";

    /**
     * Process the Message and Replace the String on the Message Body.
     * 
     * @param message
     *            Incoming ESB Message
     * @return ESB Aware Message
     */
    @Process
    public Message process(final Message message) {

        String body = (String) message.getBody().get();

        body = body.replaceAll(this.find, this.replace);

        message.getBody().add(body);

        return message;
    }

    /**
     * Sets the find.
     * 
     * @param pFind
     *            the find to set
     */
    protected void setFind(final String pFind) {
        this.find = pFind;
    }

    /**
     * Sets the replace.
     * 
     * @param pReplace
     *            the replace to set
     */
    protected void setReplace(final String pReplace) {
        this.replace = pReplace;
    }

}
