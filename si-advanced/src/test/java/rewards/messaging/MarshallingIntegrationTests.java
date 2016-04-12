package rewards.messaging;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rewards.Dining;
import rewards.RewardConfirmation;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class MarshallingIntegrationTests {

	@Autowired MessagingTemplate template;
	
	@Test
	public void inboundDiningXml() throws Exception {
		File xmlFile = new ClassPathResource("dining-sample.xml", getClass()).getFile();
		
		template.convertAndSend("xmlDinings", xmlFile);
		
		Dining receivedPayload = template.receiveAndConvert("dinings", Dining.class);
		// TODO 01: Assert that the received Dining contains values originally found in the dining-sample.xml.
		//			Run this test, it should initially fail.
		
	}

	@Test
	public void outboundConfirmation() throws Exception {
		RewardConfirmation confirmation = mock(RewardConfirmation.class);
		when(confirmation.getDiningTransactionId()).thenReturn("UUID");
		
		template.convertAndSend("confirmations", confirmation);
		
		String receivedPayload = template.receiveAndConvert("xmlConfirmations", String.class);
		// TODO 03:	Add assertions on the returned String to ensure it contains an XML confirmation.
		//			(For example, check that the String contains a "dining-transaction-id" sub-string.)
		//			Run this test, it should initially fail.
		
	}

	@Test
	public void configOk() throws Exception {
		//
	}	
}
