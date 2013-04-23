package netpdtc.soa.semi.ws.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.message.Message;

/**
 * Strips Headers.
 * 
 * @author BryanS
 * 
 */
public class StripHeadersAction {

    /**
     * Process the Message and Strip the WS-Security Headers from the Message Body.
     * 
     * @param message
     *            Incoming ESB Message
     * @return ESB Aware Message
     * @throws SOAPException
     *             On SOAP Error.
     * @throws IOException
     *             IO Error.
     */
    @SuppressWarnings("rawtypes")
    @Process
    public Message process(final Message message) throws SOAPException, IOException {

        final SOAPMessage theMsg = (SOAPMessage) message.getBody().get("soapMessage");

        final SOAPPart thePart = theMsg.getSOAPPart();
        final SOAPEnvelope theEnvelope = thePart.getEnvelope();
        final SOAPHeader theHeader = theEnvelope.getHeader();

        // final Iterator securityChildren = theHeader.getChildElements(theEnvelope.createName("Security"));
        
        // This could be an Issue if they use a different Version of WS-Security XSD
        final Iterator securityChildren = theHeader.getChildElements(new QName(
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security"));
        while (securityChildren.hasNext()) {
            final SOAPElement next = (SOAPElement) securityChildren.next();
            next.detachNode();
        }

        message.getBody().add(this.outputSOAPMsg(theMsg));

        return message;
    }

    /**
     * Creates a string representation of the supplied SOAP message, including MIME headers and attachments.
     * 
     * @param inSOAPMsg
     *            SOAP message to create string representation of.
     * @return XML String.
     * @throws IOException
     *             IOError.
     * @throws SOAPException
     *             SOAP Error.
     */
    private String outputSOAPMsg(final SOAPMessage inSOAPMsg) throws SOAPException, IOException {

        final ByteArrayOutputStream theBAOS = new ByteArrayOutputStream();

        inSOAPMsg.writeTo(theBAOS);

        return new String(theBAOS.toByteArray());
    }

}
