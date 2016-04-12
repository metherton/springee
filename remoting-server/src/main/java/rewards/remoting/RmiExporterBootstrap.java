package rewards.remoting;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiExporterBootstrap {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("rewards/remoting/rmi-exporter-config.xml");
	}
}
