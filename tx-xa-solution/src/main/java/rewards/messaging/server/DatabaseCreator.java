package rewards.messaging.server;

import org.springframework.core.io.ClassPathResource;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class DatabaseCreator {

	private boolean dropOnStartup = true;

	public void setDropOnStartup(boolean dropOnStartup) {
		this.dropOnStartup = dropOnStartup;
	}

	public DatabaseCreator(JdbcTemplate jdbcTemplate) {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		// are there any tables defined already?
		int count = jdbcTemplate.queryForInt("select count(0) from SYS.SYSTABLES where TABLENAME='T_REWARD'");
		if (dropOnStartup && count == 1) {
			populator.addScript(new ClassPathResource("rewards/messaging/server/drop-tables.sql"));
		}
		if (dropOnStartup || count == 0) {
			populator.addScript(new ClassPathResource("rewards/messaging/server/schema.sql"));
			populator.addScript(new ClassPathResource("rewards/messaging/server/test-data.sql"));
			jdbcTemplate.execute(new ConnectionCallback<Object>() {
				public Object doInConnection(java.sql.Connection con) throws java.sql.SQLException {
					populator.populate(con);
					return null;
				}
			});
		}
	}

}
