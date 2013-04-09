/**
 * 
 */
package netpdtc.soa.semi.ws.action;

import junit.framework.Assert;

import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit Tests for the StringReplaceActionTest.
 * 
 * @author Bryan Saunders <bsaunder@redhat.com>
 * 
 */
public class StringReplaceActionTest {

    /**
     * Standard Test Message.
     */
    private static final String TEST_MESSAGE = "The Stock Value for MSFT is $52.00";

    /**
     * Test Message to be used by the Unit Tests.
     */
    private Message testMessage;

    /**
     * Action Under Test.
     */
    private StringReplaceAction strReplaceAction;

    /**
     * Sets up each Unit Test by creating a new Blank Message and a new Action.
     */
    @Before
    public void testSetup() {
        // Create new Message
        this.testMessage = MessageFactory.getInstance().getMessage();
        this.testMessage.getBody().add(StringReplaceActionTest.TEST_MESSAGE);

        // Create new Action to Test
        this.strReplaceAction = new StringReplaceAction();
    }

    /**
     * If the Default "find" Value is found then replace it with the default "replace" Value.
     */
    @Test
    public void ifDefaultFoundThenReplaceWithDefault() {
        // Given
        final String expectedResult = "The Stock Value for RHT is $52.00";

        // When
        final Message resultMessage = this.strReplaceAction.process(this.testMessage);
        final String resultString = (String) resultMessage.getBody().get();

        // Then
        Assert.assertEquals("Message Strings Do Not Match", expectedResult, resultString);
    }

    /**
     * If the Custom RegEx "find" Value is found then replace it with the Custom "replace" Value.
     */
    @Test
    public void ifRegExFoundThenReplaceWithCustom() {
        // Given
        final String expectedResult = "The Stock Value for MSFT is $00.00";
        this.strReplaceAction.setFind("[0-9]{2}");
        this.strReplaceAction.setReplace("00");

        // When
        final Message resultMessage = this.strReplaceAction.process(this.testMessage);
        final String resultString = (String) resultMessage.getBody().get();

        // Then
        Assert.assertEquals("Message Strings Do Not Match", expectedResult, resultString);
    }

    /**
     * If the Custom "find" Value is found then replace it with the Custom "replace" Value.
     */
    @Test
    public void ifCustomFoundThenReplaceWithCustom() {
        // Given
        final String expectedResult = "The Stock High for MSFT is $52.00";
        this.strReplaceAction.setFind("Value");
        this.strReplaceAction.setReplace("High");

        // When
        final Message resultMessage = this.strReplaceAction.process(this.testMessage);
        final String resultString = (String) resultMessage.getBody().get();

        // Then
        Assert.assertEquals("Message Strings Do Not Match", expectedResult, resultString);
    }

    /**
     * If the Default "find" Value is not found then do not replace anything.
     */
    @Test
    public void ifNotFoundThenDontReplace() {
        // Given
        this.strReplaceAction.setFind("Hello");

        // When
        final Message resultMessage = this.strReplaceAction.process(this.testMessage);
        final String resultString = (String) resultMessage.getBody().get();

        // Then
        Assert.assertEquals("Message Strings Do Not Match", StringReplaceActionTest.TEST_MESSAGE, resultString);
    }

}
