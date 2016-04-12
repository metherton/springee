package rewards.ui;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rewards.Dining;


/**
 * A command line interface for entering {@link Dining} events.
 */

//	TODO 01  Run this class as a Java application (right-click / run as / java application)
//	Use these values as test data (see Console view):
//		Dining Amount: 100
//		Member credit card number: 1234123412341234
//		Merchant number: 1234567890	
//      Notice the Result: null above? It appears that there is something wrong with the implementation.
//      Enter 'n' to end the process and then move on to the next step where you'll diagnose and fix the problem.

public class DiningEntryUI {

	/**
	 * Bootstrap a new {@link DiningEntryUI} from the command line.
	 * @param args no arguments expected
	 */
	public static void main(String[] args) throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("rewards/ui/diningentry-config.xml");
		context.getBean(DiningEntry.class).start();
	}

}
