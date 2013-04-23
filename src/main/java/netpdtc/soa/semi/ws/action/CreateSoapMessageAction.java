package netpdtc.soa.semi.ws.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Message;

/**
 * Creates a SOAP Message.
 * 
 * @author BryanS
 * 
 */
public class CreateSoapMessageAction {

    /**
     * Process the Message and Convert the Message Body into a SOAPMessage.
     * 
     * @param message
     *            Incoming ESB Message
     * @return ESB Aware Message
     * @throws SOAPException
     *             SOAP Error.
     * @throws IOException
     *             IOError.
     */
    @Process
    public Message process(final Message message) throws SOAPException, IOException {

        // Get XML String
        final String xmlString = (String) message.getBody().get();

        // Create SOAP Message
        final SOAPMessage msg = this.createSOAPMessageFromString(xmlString);

        // Set SOAP Message on ESB Message
        message.getBody().add("soapMessage", msg);

        return message;
    }

    /**
     * Creates a SOAP Message.
     * 
     * @param xmlString
     *            XML String
     * @return SOAPMessage
     * @throws SOAPException
     *             SOAP Error.
     * @throws IOException
     *             IO Error.
     */
    private SOAPMessage createSOAPMessageFromString(final String xmlString) throws SOAPException, IOException {
        /*
         * Create a factory for creating SOAP message objects. A message factory can be configured to create SOAP 1.2
         * messages, as in the case below, or SOAP 1.1 messages or to allow for both versions of SOAP messages. The
         * latter allow for processing of incoming SOAP 1.1 and 1.2 messages.
         */
        final MessageFactory mSOAPMsgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

        final SOAPMessage theMsg = mSOAPMsgFactory.createMessage(new MimeHeaders(),
                new ByteArrayInputStream(xmlString.getBytes()));

        return theMsg;
    }
}
