package rewards.batch;

import java.io.IOException;
import java.util.UUID;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bootstrap {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:batch-*-config.xml", "db-config.xml", "integration-config.xml");
		// workaround for Windows XP that causes hang when generating UUID while reading from STDIN:
		UUID.randomUUID();
		System.in.read();
		context.close();
	}
}
