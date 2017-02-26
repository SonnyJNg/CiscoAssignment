package mainCode;
import java.util.HashMap;
import java.util.Map;

public class Restaurant extends Meal {

	private Map<Integer, Meal> restaurantMeals;
	private int rating;
	private String name;

	/**
     * Default Constructor: set restaurant's rating to zero, name to restaurant0, and places a default meal
     *
     */
	public Restaurant() {		
		this.rating = 0;
		this.name = "Restaurant0";
		Meal meal = new Meal();
		restaurantMeals = new HashMap<Integer, Meal>();
		restaurantMeals.put(Meal.OTHER, meal);
	}

	/**
     * Constructor
     *
     * @param  rating: restaurant's rating
     * @param  name: restaurant's name
     * @param  restaurantMeal: meal added to the restaurant
     * 
     */
	public Restaurant(int rating, String name, Map <Integer,Meal> restaurantMeal){
		this.restaurantMeals = new HashMap<Integer, Meal>();
		this.restaurantMeals = restaurantMeal;		
		this.rating = rating;
		this.name = name;
	}

	/**
     * Returns dictionary containing the restaurant's meals where the id the meal's dietary id
     *
     * @return restaurant's available meals
     * 
     */
	public Map<Integer, Meal> getRestaurantMeals() {
		return this.restaurantMeals;
	}

	/**
     * Returns the restaurant's rating
     *
     * @return restaurant's rating
     * 
     */
	public int getRating() {
		return this.rating;
	}

	/**
     * Sets the restaurant's rating
     * 
     * @param  rating: restaurant's rating
     * 
     */
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	/**
     * Returns restaurant's name
     *
     * @return restaurant's name
     */
	public String getName(){
		return this.name;
	}
	
	/**
     * Sets the restaurant's name
     *
     * @param name: restaurant's name
     * 
     */
	public void setName(String name){
		this.name = name;
	}
}
