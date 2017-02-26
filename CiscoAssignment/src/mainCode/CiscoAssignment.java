package mainCode;
/**
 * @author Sonny Ng Purpose:
 * 
 *         Example:
 * 
 *         Instructions for running:
 * 
 */

public class CiscoAssignment {

	public static void main(String[] args) {
		System.out.println("Hello,\n\n" + "Welcome to Sonny's Smart Lunch Order Placer.\n"
				+ "In order to commence, let's first set up the restaurants, "
				+ "then their corresponding meal availability.\n"
				+ "Finally, let's analyze which outcome is the best option for a happy and joinful "
				+ "staff meal depending on collegues' dietary restrictions");
		
		OrderPlacer orderPlacer = new OrderPlacer();
		orderPlacer.runAddRestaurant();
		orderPlacer.runAddOrder();
		orderPlacer.runComputeBestOption();
		
		System.out.println("The program has ended. Thanks for your time, bye now!");
	}

}