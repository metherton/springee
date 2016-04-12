package rewards.messaging.client;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rewards.Dining;

/**
 * Tests the Dining batch processor
 */
@ContextConfiguration(locations = {
		"/rewards/messaging/client/client-config.xml",
		"/rewards/messaging/jms-rewards-config.xml",
		"/rewards/messaging/jms-infrastructure-config.xml",
		"/system-test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DiningProcessorTests {

	@Autowired
	private DiningProcessor diningProcessor;

	@Autowired
	private RewardConfirmationLogger confirmationLogger;

	@Test
	public void testBatch() throws Exception {
		
		/*  TODO 01: Execute the following tasks:
		 
		  			 1.  Process five Dining objects through the diningProcessor.
		   
		  			 2.  Run the test. You will see an UnsupportedOperationException.
		 
		*/
		

  // --------------------------------------------------------------------------------------------------------------
		

		/*  TODO 04: Execute the following tasks:
		 
					 1.  Wait for the batch for 10 seconds, and assert it's size is 5. Modify the assertion below.

					 2.  Run the test. It will fail.

		*/
			assertThat(confirmationLogger.getConfirmations().size(), is(0));
	}

	private void waitForBatch(int batchSize, int timeout) throws InterruptedException {
		int sleepTime = 100;
		while (confirmationLogger.getConfirmations().size() < batchSize
				&& timeout > 0) {
			Thread.sleep(sleepTime);
			timeout -= sleepTime;
		}
	}

	private List<Dining> getTestDinings() {
		List<Dining> dinings = new ArrayList<Dining>();
		dinings.add(Dining.createDining("80.93", "1234123412341234", "1234567890"));
		dinings.add(Dining.createDining("56.12", "1234123412341234", "1234567890"));
		dinings.add(Dining.createDining("32.64", "1234123412341234", "1234567890"));
		dinings.add(Dining.createDining("77.05", "1234123412341234", "1234567890"));
		dinings.add(Dining.createDining("94.50", "1234123412341234", "1234567890"));
		return dinings;
	}
}
