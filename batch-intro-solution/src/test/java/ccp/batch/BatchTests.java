package ccp.batch;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:system-test-config.xml")
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
		JobExecution execution = testUtils.launchJob();

		assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
		int processed = jdbcTemplate.queryForInt(NR_OF_CONFIRMED_DININGS);
		assertEquals(150, processed);

		assertEquals(150L, diningQueueView.getQueueSize());
	}
}
