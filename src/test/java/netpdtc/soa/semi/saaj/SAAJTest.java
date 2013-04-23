package netpdtc.soa.semi.saaj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.Text;

import org.w3c.dom.Node;

public class SAAJTest {

    public static final String TEST_XML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.webserviceX.NET/\"><soapenv:Header><wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsse:UsernameToken wsu:Id=\"UsernameToken-1\"><wsse:Username>TestUser</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">TestPass</wsse:Password></wsse:UsernameToken></wsse:Security></soapenv:Header><soapenv:Body><web:GetQuote><web:symbol>MSFT</web:symbol></web:GetQuote></soapenv:Body></soapenv:Envelope>";

    /* Instance variable(s): */
    /** Factory for creating SOAPMessage objects. */
    private MessageFactory mSOAPMsgFactory;
    /** Factory for creating objects associated with SOAP messages. */
    private SOAPFactory mSOAPObjectsFactory;


    /**
     * Creates an instance of this class and performs the following initialization:<br/>
     * - Creates a SOAP 1.2 messages factory.<br/>
     * - Creates a factory that creates objects associated with SOAP messages.<br/>
     * 
     * @throws SOAPException
     *             If error occurs creating SOAP factory.
     */
    public SAAJTest() throws SOAPException {
        /*
         * Create a factory for creating SOAP message objects. A message factory can be configured to create SOAP 1.2
         * messages, as in the case below, or SOAP 1.1 messages or to allow for both versions of SOAP messages. The
         * latter allow for processing of incoming SOAP 1.1 and 1.2 messages.
         */
        mSOAPMsgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
        /*
         * Create a factory that creates various objects that can exist in a SOAP message.
         */
        mSOAPObjectsFactory = SOAPFactory.newInstance();
    }

    private void removeHeader(SOAPMessage msg) throws SOAPException {
        System.out.println("Original Message:");
        System.out.println(SAAJTest.outputSOAPMsg(msg));
        
        Iterator childElements = msg.getSOAPHeader().getChildElements();
        while(childElements.hasNext()){
            SOAPElement next = (SOAPElement) childElements.next();
            next.detachNode();
        }
        
        System.out.println("Modified Message:");
        System.out.println(SAAJTest.outputSOAPMsg(msg));
    }

    /**
     * Creates an empty SOAP message. The code in this method also demonstrates how to access the different parts of a
     * SOAP message.
     * 
     * @return Empty SOAP message.
     * @throws SOAPException
     *             If error occurs creating or accessing SOAP message.
     */
    public SOAPMessage createEmptySOAPMessage() throws SOAPException {
        SOAPMessage theMsg = mSOAPMsgFactory.createMessage();

        System.out.println("Full Message:");
        System.out.println(SAAJTest.outputSOAPMsg(theMsg));

        /*
         * Here, different ways of accessing the different parts of a SOAP message are shown. First going via the SOAP
         * part and the SOAP envelope...
         */
        SOAPPart thePart = theMsg.getSOAPPart();
        SOAPEnvelope theEnvelope = thePart.getEnvelope();
        SOAPBody theBody = theEnvelope.getBody();
        SOAPHeader theHeader = theEnvelope.getHeader();
        System.out.println(" The SOAP part: " + thePart);
        System.out.println(" The SOAP envelope: " + theEnvelope);
        System.out.println(" The SOAP body: " + theBody);
        System.out.println(" The SOAP header: " + theHeader);

        return theMsg;
    }

    public SOAPMessage createSOAPMessageFromString() throws SOAPException, IOException {
        SOAPMessage theMsg = mSOAPMsgFactory.createMessage(new MimeHeaders(),
                new ByteArrayInputStream(TEST_XML.getBytes()));

        System.out.println("Full Message:");
        System.out.println(SAAJTest.outputSOAPMsg(theMsg));

        /*
         * Here, different ways of accessing the different parts of a SOAP message are shown. First going via the SOAP
         * part and the SOAP envelope...
         */
        SOAPPart thePart = theMsg.getSOAPPart();
        SOAPEnvelope theEnvelope = thePart.getEnvelope();
        SOAPBody theBody = theEnvelope.getBody();
        SOAPHeader theHeader = theEnvelope.getHeader();
        System.out.println(" The SOAP part: " + thePart);
        System.out.println(" The SOAP envelope: " + theEnvelope);
        System.out.println(" The SOAP body: " + theBody);
        System.out.println(" The SOAP header: " + theHeader);

        Node firstChild = theHeader.getFirstChild();
        System.out.println("First Header Child: " + firstChild.getLocalName());
        System.out.println(firstChild.toString());

        System.out.println("\n Header Elements");
        Iterator childElements = theHeader.getChildElements();
        printChildren(childElements);
        

        return theMsg;
    }

    private void printChildren(Iterator childElements) {
        while (childElements.hasNext()) {
            Object next = childElements.next();
            if(next instanceof SOAPElement){
                SOAPElement element = (SOAPElement) next;
                System.out.println(element.getLocalName());
                if(element.hasChildNodes()){
                    printChildren(element.getChildElements());
                }
            }else if(next instanceof Text){
                Text txt = (Text) next;
                System.out.println(txt.toString());
            }
            
        }
    }
    
    /**
     * Creates a string representation of the supplied SOAP message, including MIME headers and attachments.
     * 
     * @param inSOAPMsg
     *            SOAP message to create string representation of.
     */
    public static String outputSOAPMsg(final SOAPMessage inSOAPMsg) {

        ByteArrayOutputStream theBAOS = new ByteArrayOutputStream();
        PrintWriter theWriter = new PrintWriter(theBAOS);
        theWriter.println("MIME Headers:");
        Iterator theMimeHdrIter = inSOAPMsg.getMimeHeaders().getAllHeaders();

        for (; theMimeHdrIter.hasNext();) {
            MimeHeader theHdr = (MimeHeader) theMimeHdrIter.next();
            theWriter.println(theHdr.getName() + " - " + theHdr.getValue());
        }

        try {
            theWriter.println();
            theWriter.println("SOAP Message:");
            theWriter.flush();
            inSOAPMsg.writeTo(theBAOS);
        } catch (Exception theException) {
            theException.printStackTrace();
        }

        return new String(theBAOS.toByteArray());
    }
    
    public static void main(String[] args) {
        System.out.println("Starting SAAJ Test");

        try {
            SAAJTest theInstance = new SAAJTest();
            System.out.println("==== CREATING EMPTY MESSAGE =====");
            theInstance.createEmptySOAPMessage();
            System.out.println("\n\n");

            System.out.println("==== CREATING MESSAGE FROM STRING =====");
            SOAPMessage msg = theInstance.createSOAPMessageFromString();
            System.out.println("\n\n");
            
            System.out.println("==== REMOVING HEADER FOMR MESSAGE =====");
            theInstance.removeHeader(msg);
            System.out.println("\n\n");
            
        } catch (Exception theException) {
            theException.printStackTrace();
        }
    }
}
