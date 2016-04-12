package rewards.messaging.server;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartServer {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"rewards/messaging/server/infrastructure-config.xml",
			"rewards/messaging/server/jms-rewards-config.xml",
			"rewards/messaging/server/jmx-config.xml"
		);
		System.out.println("Started server, press Enter to stop");
		System.in.read();
		// ensure a proper shutdown of ActiveMQ and Derby
		context.close();
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (SQLException e) {
			// expected, indicates successful shutdown
			System.out.println(e.getMessage());
		}
		System.out.println("Server is shut down");
	}
}
