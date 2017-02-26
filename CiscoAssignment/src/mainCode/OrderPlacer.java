package mainCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OrderPlacer extends Restaurant {

	private List<Restaurant> restaurantList;
	private int totalVegetariansAvailable = 0, totalGlutenFreeAvailable = 0, 
				totalNutFreeAvailable = 0, totalFishFreeAvailable = 0, 
				totalOtherAvailable = 0;
	private int orderVegetarian = 0, orderGlutenFree = 0, 
				orderNutFree = 0, orderFishFree = 0, 
				orderOther = 0;

    /**
     * Default constructor: creates a default list of default restaurant
     */
	public OrderPlacer() {
		this.restaurantList = new ArrayList<Restaurant>();
	}

    /**
     * Returns the number of available restaurants within the running time
     *
     * @return number of current available restaurant
     */
	public int getRestaurantListSize(){
		return this.restaurantList.size(); 
	}

    /**
     * Functionality to add to the system a restaurant list that contains 1 to many meals in a dictionary form
     *      
     */
	public void runAddRestaurant() {
		Boolean run = true;				
		
		while (run) {
			Boolean addNewRestaurant = getScannerBoolean("Would you like to add a new restaurant?(y/n)");
			
			if (addNewRestaurant) {
				//each restaurant has a list of meals and a rating
				Map <Integer, Meal> mealList = new HashMap<Integer, Meal>();				
				int restaurantRating = -1, dietaryId = -1, mealQuantity = -1;
				Boolean checkAddNewMeal = true;		
	
				restaurantRating = getScannerInt("Please enter the restaurant's rating (1-5) **Integer only: ", 1, 5);				
				
				while(checkAddNewMeal){			
					
					dietaryId = getScannerInt("Please enter the dietary restriction by entering the corresponding integer:\n"
							+ "[0] Vegetarian\n"
							+ "[1] Gluten Free\n"
							+ "[2] Nut Free\n"
							+ "[3] Fish Free\n"
							+ "[4] Other\n", 0, 4);					
					
					mealQuantity = getScannerInt("Please enter what quantity of the selected meal you would like order (0-100):", 0, 100);					
					
					try{
						mealList = insertNewMeal(mealList, dietaryId, mealQuantity);	
					}
					catch (Exception e){
						System.out.println(e.getMessage());
					}
					
					checkAddNewMeal = getScannerBoolean("Would you like to add a new meal to the selected restaurant?(y/n)");
				}				
				
				try {
					Restaurant newRestaurant = new Restaurant(restaurantRating, "Restaurant" + restaurantList.size(), mealList);
					restaurantList.add(newRestaurant);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} 
			else {
				run = false;
			}
		}
	}

	/**
     * Prompts the user what meals they wish to order from their configured restaurants
     * 
     */
	public void runAddOrder() {
		setTotalVegetariansAvailable(getTotalAvailable(Meal.VEGETARIAN));
		setTotalGlutenFreeAvailable(getTotalAvailable(Meal.GLUTEN_FREE));
		setTotalNutFreeAvailable(getTotalAvailable(Meal.NUT_FREE));
		setTotalFishFreeAvailable(getTotalAvailable(Meal.FISH_FREE));
		setTotalOtherAvailable(getTotalAvailable(Meal.OTHER));
		
		System.out.println("******************************************************");
		System.out.println("**********************Food Order**********************");
		System.out.println("******************************************************");
		setOrderVegetarian(getScannerInt("How many vegetarian plates would you like to order(" + 0 + ", " + getTotalVegetariansAvailable() + ")?", 0, getTotalVegetariansAvailable()));
		setOrderGlutenFree(getScannerInt("How many gluten free plates would you like to order(" + 0 + ", " + getTotalGlutenFreeAvailable() + ")?", 0, getTotalGlutenFreeAvailable())); 
		setOrderNutFree(getScannerInt("How many nut free plates would you like to order(" + 0 + ", " + getTotalNutFreeAvailable() + ")?", 0, getTotalNutFreeAvailable())); 
		setOrderFishFree(getScannerInt("How many fish free plates would you like to order(" + 0 + ", " + getTotalFishFreeAvailable() + ")?", 0, getTotalFishFreeAvailable())); 
		setOrderOther(getScannerInt("How many other plates would you like to order(" + 0 + ", " + getTotalOtherAvailable() + ")?", 0, getTotalOtherAvailable()));
	}

	/**
     * Prints computed best option
     * 
     */
	public void runComputeBestOption() {
		List <Restaurant> computedRestaurantList = new ArrayList<Restaurant>();
		computedRestaurantList = getComputedRestaurantList(restaurantList);		
		
		for(int indexRestaurant = 0; indexRestaurant < computedRestaurantList.size(); indexRestaurant++){
			System.out.println("*************************");
			System.out.println(computedRestaurantList.get(indexRestaurant).getName() 
					+ " - Rating: " + computedRestaurantList.get(indexRestaurant).getRating() + "/5");
			System.out.println("*************************");			
			for(int indexMeal = 0; indexMeal < 5; indexMeal++){
				Meal meal = computedRestaurantList.get(indexRestaurant).getRestaurantMeals().get(indexMeal);
				if (meal != null){
					printComputedOrderQuantity(indexMeal, 
							computedRestaurantList.get(indexRestaurant).getRestaurantMeals().get(indexMeal).getMealQuantity());	
				}	
			}
		}
	}
	
	/**
     * Finds the best combination for the placed order with the available restaurant's meals
     *
     * @return List of restaurants with their respective meals for final answer based on final food order
     * @param  List of restaurants available from the system
     * 
     */
	
	public List<Restaurant> getComputedRestaurantList(List<Restaurant> restaurantList){
		//1 - sort restaurants based on rating where 5 is the greatest and most likely place to eat
		Collections.sort(restaurantList, new RestaurantRating());
		//2 - cloned original restaurant list
		List <Restaurant> computedRestaurantList = new ArrayList<Restaurant>();
		computedRestaurantList = restaurantList;			
		//3 - set ordering meal quantities per restaurant and prints the results
		for(int indexRestaurant = 0; indexRestaurant < computedRestaurantList.size(); indexRestaurant++){						
			for(int indexMeal = 0; indexMeal < 5; indexMeal++){
				Meal meal = computedRestaurantList.get(indexRestaurant).getRestaurantMeals().get(indexMeal);
				if (meal != null){
					int availableQuantity = meal.getMealQuantity();
					int orderQuantity = getComputedOrderQuantity(indexMeal, availableQuantity);
					meal.setMealQuantity(orderQuantity);	
				}
			}
		}
		return computedRestaurantList;
	}
	
	/**
     * Computes the correct meal quantity for the selected restaurant
     *
     * @return Computed meal quantity for specific restaurant
     * @param  indexMeal: meal type id. 0 for vegetarian, 1 for gluten free, 2 for nut free, 3 for fish free, 4 for other
     * @param  availableQuantity: ordering meal quantity for a specific meal within a selected restaurant 
     * 
     */	
	private int getComputedOrderQuantity(int indexMeal, int availableQuantity) {
		int orderQuantity = 0;
		switch (indexMeal){
		case 0:
			if(getOrderVegetarian() >= availableQuantity){
				orderQuantity = availableQuantity;
				setOrderVegetarian(getOrderVegetarian() - availableQuantity);
			}
			else{
				orderQuantity = getOrderVegetarian();
				setOrderVegetarian(0);
			}
			break;
		case 1:
			if(getOrderGlutenFree() >= availableQuantity){
				orderQuantity = availableQuantity;
				setOrderGlutenFree(getOrderGlutenFree() - availableQuantity);
			}
			else{
				orderQuantity = getOrderGlutenFree();
				setOrderGlutenFree(0);
			}
			break;
		case 2:
			if(getOrderNutFree() >= availableQuantity){
				orderQuantity = availableQuantity;
				setOrderNutFree(getOrderNutFree() - availableQuantity);
			}
			else{
				orderQuantity = getOrderNutFree();
				setOrderNutFree(0);
			}
			break;
		case 3:
			if(getOrderFishFree() >= availableQuantity){
				orderQuantity = availableQuantity;
				setOrderFishFree(getOrderFishFree() - availableQuantity);
			}
			else{
				orderQuantity = getOrderFishFree();
				setOrderFishFree(0);
			}
			break;
		case 4:
			if(getOrderOther() >= availableQuantity){
				orderQuantity = availableQuantity;
				setOrderOther(getOrderOther() - availableQuantity);
			}
			else{
				orderQuantity = getOrderOther();
				setOrderOther(0);
			}
			break;
		default:
			System.out.println("Error on finding the proper meal quantity per restaurant");
			break;
		}
		
		return orderQuantity;
	}

	/**
     * Prints the correct meal quantity for the selected restaurant
     *
     * @param  indexMeal: meal type id. 0 for vegetarian, 1 for gluten free, 2 for nut free, 3 for fish free, 4 for other
     * @param  availableQuantity: ordering meal quantity for a specific meal within a selected restaurant 
     * 
     */	
	private void printComputedOrderQuantity(int indexMeal, int orderQuantity) {		
		switch (indexMeal){
		case 0:
			System.out.println("- Vegetarian plates: " + orderQuantity);
			break;
		case 1:
			System.out.println("- Gluten Free plates: " + orderQuantity);
			break;
		case 2:
			System.out.println("- Nut Free plates: " + orderQuantity);
			break;
		case 3:
			System.out.println("- Fish Free plates: " + orderQuantity);
			break;
		case 4:
			System.out.println("- Other plates: " + orderQuantity);
			break;
		default:
			System.out.println("Error on finding the proper meal quantity per restaurant");
			break;
		}
	}
	
	/**
     * Inserts new meal into restaurant's meal list	
     *
     * @return A dictionary containing the new meal
     * @param  mealList: current meal list for selected restaurant
     * @param  dietaryId: meal type id. 0 for vegetarian, 1 for gluten free, 2 for nut free, 3 for fish free, 4 for other
     * @param  mealQuantity: ordering meal quantity for a specific meal within a selected restaurant
     * @exception Throws exception if meal already exists (restaurants can only one unique meal type) 
     * 
     */
	public Map<Integer, Meal> insertNewMeal(Map<Integer, Meal> mealList, int dietaryId, int mealQuantity) throws Exception {	
		Meal newMeal = mealList.get(dietaryId);
		if (newMeal == null){
			newMeal = new Meal(dietaryId, mealQuantity);
			mealList.put(dietaryId, newMeal);	
		}
		else{
			throw new Exception("Cannot insert new meal because it's already inserted into this restaurant.");
		}
		
		return mealList;
	}
	
	/**
     * Prompts user to enter a valid integer value and returns it
     *
     * @return valid user answer based on limits 
     * @param  questionMessage: question to be asked to the end user
     * @param  lowLimit: low boundary limit for user's answer
     * @param  highLimit: high boundary limit for user's answer
     * 
     */
	private int getScannerInt(String questionMessage, int lowLimit, int highLimit){
		int response = -1;
		Boolean run = true;
		Scanner scanner;
		
		while(run){
			System.out.println(questionMessage);
			scanner = new Scanner(System.in);
			try{
				response = scanner.nextInt();
				if(response >= lowLimit && response <= highLimit){
					run = false;
				}	
			}
			catch (Exception e){						
				System.out.println("Could not convert entered value to int. Exception type: " + e.getClass().getName());
			}	
		}
		
		return response;
	}
	
	/**
     * Prompts user to enter y or n, returns true if y else if n returns false
     *
     * @return valid user answer based on limits
     * @param  questionMessage: question to be asked to the end user
     * 
     */	
	private Boolean getScannerBoolean(String questionMessage) {		
		Boolean response = true, run =true;
		Scanner scanner;
		
		while(run)
		{
			System.out.println(questionMessage);
			scanner = new Scanner(System.in);
			String ans = scanner.nextLine().toLowerCase();
			
			if(ans.equals("y")){
				run = false;				
			}
			else if (ans.equals("n")){
				run = false;
				response = false;
			}				
		}		
		return response;
	}

	/**
     * Computes the total available meals between all restaurants for a specific dietary id
     *
     * @return total available meals between all restaurants
     * @param  dietaryId: 0 for vegetarian, 1 for gluten free, 2 for nut free, 3 for fish free, 4 for other
     * 
     */
	public int getTotalAvailable(int dietaryId) {
		int total = 0;
		for(int index = 0; index < restaurantList.size(); index++)
		{
			Meal meal = restaurantList.get(index).getRestaurantMeals().get(dietaryId);
			if (meal != null){
				total += meal.getMealQuantity();	
			}			
		}
		return total;
	}

	/**
     * Returns total available meals for vegetarian plates for order
     *
     * @return total available vegetarian meals for order
     * 
     */
	public int getTotalVegetariansAvailable(){
		return this.totalVegetariansAvailable;
	}
	
	/**
     * Sets the total available meals for vegetarian plates for order
     *
     * @param totalVegetariansAvailable: quantity of vegetarian plates left to be ordered
     * 
     */
	public void setTotalVegetariansAvailable(int totalVegetariansAvailable){
		this.totalVegetariansAvailable = totalVegetariansAvailable;
	}
	
	/**
     * Returns total available meals for gluten free plates for order
     *
     * @return total available gluten free meals for order
     * 
     */
	public int getTotalGlutenFreeAvailable(){
		return this.totalGlutenFreeAvailable;
	}

	/**
     * Sets the total available meals for gluten free plates for order
     *
     * @param totalGlutenFreeAvailable: quantity of gluten free plates left to be ordered
     * 
     */
	public void setTotalGlutenFreeAvailable(int totalGlutenFreeAvailable){
		this.totalGlutenFreeAvailable = totalGlutenFreeAvailable;
	}
	
	/**
     * Returns total available meals for nut free plates for order
     *
     * @return total available nut free meals for order
     * 
     */
	public int getTotalNutFreeAvailable(){
		return this.totalNutFreeAvailable;
	}
	
	/**
     * Sets the total available meals for nut free plates for order
     *
     * @param totalNutFreeAvailable: quantity of nut free plates left to be ordered
     * 
     */
	public void setTotalNutFreeAvailable(int totalNutFreeAvailable){
		this.totalNutFreeAvailable = totalNutFreeAvailable;
	}
	
	/**
     * Returns total available meals for fish free plates for order
     *
     * @return total available fish free meals for order
     * 
     */
	public int getTotalFishFreeAvailable(){
		return this.totalFishFreeAvailable;
	}
	
	/**
     * Sets the total available meals for fish free plates for order
     *
     * @param totalVegetariansAvailable: quantity of fish free plates left to be ordered
     * 
     */
	public void setTotalFishFreeAvailable(int totalFishFreeAvailable){
		this.totalFishFreeAvailable = totalFishFreeAvailable;
	}
	
	/**
     * Returns total available meals for other plates for order
     *
     * @return total available other plate meals for order
     * 
     */
	public int getTotalOtherAvailable(){
		return this.totalOtherAvailable;
	}
	
	/**
     * Sets the total available meals for other plates for order
     *
     * @param totalVegetariansAvailable: quantity of other plates left to be ordered
     * 
     */
	public void setTotalOtherAvailable(int totalOtherAvailable){
		this.totalOtherAvailable = totalOtherAvailable;
	}

	/**
     * Returns order's meal quantity for vegetarian plates for specific restaurant
     *
     * @return total order's vegetarian meal quantity for specific restaurant
     * 
     */
	public int getOrderVegetarian(){
		return this.orderVegetarian;
	}
	
	/**
     * Sets the total available meals for vegetarian plates for specific restaurant
     *
     * @param totalVegetariansAvailable: quantity of vegetarian plates for specific restaurant
     * 
     */
	public void setOrderVegetarian(int orderVegetarian){
		this.orderVegetarian = orderVegetarian;
	}

	/**
     * Returns order's meal quantity for gluten free plates for specific restaurant
     *
     * @return total order's gluten free meal quantity for specific restaurant
     * 
     */	
	public int getOrderGlutenFree(){
		return this.orderGlutenFree;
	}

	/**
     * Sets the total available meals for gluten free plates for specific restaurant
     *
     * @param totalVegetariansAvailable: quantity of gluten free plates for specific restaurant
     * 
     */
	
	public void setOrderGlutenFree(int orderGlutenFree){
		this.orderGlutenFree = orderGlutenFree;
	}
	
	/**
     * Returns order's meal quantity for nut free plates for specific restaurant
     *
     * @return total order's nut free meal quantity for specific restaurant
     * 
     */
	
	public int getOrderNutFree(){
		return this.orderNutFree;
	}
		
	/**
     * Sets the total available meals for nut free plates for specific restaurant
     *
     * @param totalVegetariansAvailable: quantity of nut free plates for specific restaurant
     * 
     */
	
	public void setOrderNutFree(int orderNutFree){
		this.orderNutFree = orderNutFree;
	}
	
	/**
     * Returns order's meal quantity for fish free plates for specific restaurant
     *
     * @return total order's fish free meal quantity for specific restaurant
     * 
     */	
	
	public int getOrderFishFree(){
		return this.orderFishFree;
	}
	
	/**
     * Sets the total available meals for fish free plates for specific restaurant
     *
     * @param totalVegetariansAvailable: quantity of fish free plates for specific restaurant
     * 
     */
	
	public void setOrderFishFree(int orderFishFree){
		this.orderFishFree = orderFishFree;
	}
	
	/**
     * Returns order's meal quantity for other plates for specific restaurant
     *
     * @return total order's other meal quantity for specific restaurant
     * 
     */
	public int getOrderOther(){
		return this.orderOther;
	}
	
	/**
     * Sets the total available meals for other plates for specific restaurant
     *
     * @param totalVegetariansAvailable: quantity of other plates for specific restaurant
     * 
     */
	
	public void setOrderOther(int orderOther){
		this.orderOther = orderOther;
	}

	/**
     * Returns restaurant list
     *
     * @return restaurant list
     * 
     */
	public List<Restaurant> getRestaurantList(){
		return this.restaurantList;
	}
	
	/**
     * Sets the restaurant list
     *
     * @param restaurantList: restaurant list
     * 
     */
	public void setRestaurantList(List<Restaurant> restaurantList){
		this.restaurantList = restaurantList;
	}
}
