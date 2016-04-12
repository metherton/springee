package ccp.batch;

import javax.sql.DataSource;

import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/system-test-config.xml")
public class BatchTests {
	static final String NR_OF_CONFIRMED_DININGS = "select count(*) from T_DINING where CONFIRMED=1";

	@Autowired JobLauncherTestUtils testUtils;
	@Autowired QueueViewMBean diningQueueView;
	JdbcTemplate jdbcTemplate;

	@Autowired
	public void initJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Test @DirtiesContext  // updates the in-memory database, so dirty the context
	public void runBatch() throws Exception {
		// TODO 06: use the JobLauncherTestUtils to launch the job.
		// Assert that the resulting JobExecution has an exitStatus of ExitStatus.COMPLETED
		// and use the jdbcTemplate to assert that the number of confirmed dinings in the database
		// is now 150. (the same as the number of confirmation messages that were on the queue.)

		// TODO 12: assert that the batch sent 150 messages using the diningQueueView's queueSize property
	}
}
