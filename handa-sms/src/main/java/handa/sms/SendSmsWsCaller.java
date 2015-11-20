package handa.sms;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static com.pldt.itidm.core.utils.SoapUtils.getFirstText;
import static com.pldt.itidm.core.utils.SoapUtils.hasTag;

import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.base.Optional;
import com.pldt.itidm.core.utils.SoapWsCaller;

import handa.beans.dto.SendSmsInput;
import handa.beans.dto.SendSmsOutput;

public class SendSmsWsCaller
extends SoapWsCaller<SendSmsInput, SendSmsOutput, SOAPBody>
{
    protected SendSmsWsCaller(WSParams wsParams)
    {
        super(wsParams);
    }

    private static Logger log = LoggerFactory.getLogger(SendSmsWsCaller.class);

    @Override
    protected Optional<SOAPBody> invokeWS(final SendSmsInput sendSmsInput)
    {
        checkNotNull(emptyToNull(sendSmsInput.getChargeId()), "sendSmsInput.chargeId should not be null.");
        checkNotNull(emptyToNull(sendSmsInput.getKeyword()), "sendSmsInput.keyword should not be null.");
        checkNotNull(emptyToNull(sendSmsInput.getMessage()), "sendSmsInput.message should not be null.");
        checkNotNull(emptyToNull(sendSmsInput.getMobile()), "sendSmsInput.mobile should not be null.");
        checkNotNull(emptyToNull(sendSmsInput.getQueuePath()), "sendSmsInput.queuePath should not be null.");
        SOAPConnection soapConnection = null;
        try
        {
            soapConnection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(sendSmsInput), getWsParams().getUri());
//            log.info(SoapUtils.soapMessageToString(soapResponse));
            return of(soapResponse.getSOAPBody());
        }
        catch (Exception e)
        {
            log.error("Error occurred while sending SOAP Request to Server", e);
            throw new RuntimeException(e);
        }
        finally
        {
            if(soapConnection != null)
            {
                try
                {
                    soapConnection.close();
                }
                catch(SOAPException e) {} // ignore
            }
        }
    }

    @Override
    protected Optional<SendSmsOutput> transform(final SOAPBody body)
    {
        final String expectedNode = "SendSMSResponse";
        if(hasTag(body, expectedNode))
        {
            NodeList list = body.getElementsByTagName(expectedNode);
            if(list.getLength() == 0)
            {
                return absent();
            }
            final Element ele = (Element) list.item(0);
            SendSmsOutput sendSmsOutput = new SendSmsOutput()
            {{
                setResult(getFirstText(ele, "SendSMSResult"));
            }};
            return of(sendSmsOutput);
        }
        return absent();
    }

/*
<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns:xsd="http://www.w3.org/2001/XMLSchema"
               xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <SendSMS xmlns="http://smart/services/messaging">
      <keyword>5010</keyword>
      <chargeId>0000</chargeId>
      <mobile>09497397439</mobile>
      <message>
          <![CDATA[Test sms message from HANDA<>[]"'|~`!@#$%^&*()_-=+]]>
      </message>
      <queuePath>.\new5010sender</queuePath>
    </SendSMS>
  </soap:Body>
</soap:Envelope>
*/
    private SOAPMessage createSOAPRequest(SendSmsInput sendSmsInput) throws Exception
    {
        SOAPMessage soapMessage = createDefaultSoapMessage();
        // SOAP Body
        SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
        soapBody.setPrefix(SOAP);
        SOAPElement parentElem = soapBody.addChildElement(new QName("http://smart/services/messaging", "SendSMS"));
        parentElem.addChildElement(new QName("keyword")).addTextNode(sendSmsInput.getKeyword());
        parentElem.addChildElement(new QName("chargeId")).addTextNode(sendSmsInput.getChargeId());
        String mobileNumber = sendSmsInput.getMobile();
        if(mobileNumber.startsWith("+")) { mobileNumber = mobileNumber.replace("+", ""); }
        parentElem.addChildElement(new QName("mobile")).addTextNode(mobileNumber);
        String message= sendSmsInput.getMessage();
        if(message.contains("`")) { message = message.replace('`', '\''); }
        parentElem.addChildElement(new QName("message")).addTextNode(message);
        parentElem.addChildElement(new QName("queuePath")).addTextNode(sendSmsInput.getQueuePath());
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", getWsParams().getSoapAction());
        soapMessage.saveChanges();
//        log.info("REQUEST = {}", SoapUtils.soapMessageToString(soapMessage));
        return soapMessage;
    }
}