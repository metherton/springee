package rewards.messaging;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.print.DocFlavor.STRING;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({"spring-integration-idempotent-receiver-config.xml",
	                   "spring-integration-infrastructure-config.xml",
                       "spring-integration-marshalling-config.xml",
                       "spring-integration-xml-splitting-config.xml",
	                   "classpath:system-test-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RewardMessagingIntegrationTests {

	@Autowired MessagingTemplate template;

	@Test
	public void sendSingleXmlDiningTwice() throws Exception {
		File xmlFile = new ClassPathResource("dining-sample.xml", getClass()).getFile();
		template.convertAndSend("xmlDinings", xmlFile);
		
		String payload = template.receiveAndConvert("xmlConfirmations", String.class);		
		assertTrue(payload.contains("<reward-confirmation"));
		
		template.convertAndSend("xmlDinings", xmlFile);
		assertEquals(payload, template.receiveAndConvert("xmlConfirmations", String.class));
	}
	
	@Test 
	public void sendMultipleDinings() throws Exception {
		File xmlFile = new ClassPathResource("dinings-sample.xml", getClass()).getFile();
		template.convertAndSend("mixedXmlDinings", xmlFile);
		
		assertNotNull(template.receiveAndConvert("xmlConfirmations", Object.class));
		assertNotNull(template.receiveAndConvert("xmlConfirmations", Object.class));
	}


	@Test
	public void configOk() throws Exception {
		// testing the fixture
	}
}
