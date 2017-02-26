package unitTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import mainCode.Restaurant;
import mainCode.Meal;;

public class TestRestaurant {	
	private Restaurant restaurant1 = new Restaurant();
	private Restaurant restaurant2 = new Restaurant();
	private Meal meal = new Meal();
	private Meal vegetarian = new Meal(0, 100);
	private Map<Integer, Meal> restaurantMeals1 = new HashMap<Integer, Meal>();
	private Map<Integer, Meal> restaurantMeals2 = new HashMap<Integer, Meal>();
		
	// constructor to set working variables
	public TestRestaurant(){
		restaurantMeals1.put(Meal.VEGETARIAN, vegetarian);
		restaurant1 = new Restaurant(5, "Restaurant 0", restaurantMeals1);
		
		restaurantMeals2.put(Meal.VEGETARIAN, vegetarian);
		restaurantMeals2.put(Meal.OTHER, meal);
		restaurant2 = new Restaurant(5, "Restaurant 0", restaurantMeals2);
	}
	
	//test the default object is not null
	@Test
 	public void testDefaultObjectIsNotNull(){
		restaurant1 = new Restaurant();
		assertNotNull(restaurant1);
	}
	
	//test the default rating is zero
	@Test
	public void testDefaultRestaurantRating(){
		restaurant1 = new Restaurant();
		assertEquals(restaurant1.getRating(), 0);
	}
	
	//test the default name is Restaurant0
	@Test
	public void testDefaultMealQuantity(){
		restaurant1 = new Restaurant();
		assertEquals(restaurant1.getName(), "Restaurant0");
	}
	
	//test a restaurant with a specific meal can be created
	@Test
	public void testRestaurantOneMealSize(){		
		assertEquals(restaurant1.getRestaurantMeals().size(), 1);	
	}
	
	//test a restaurant with a specific meal can get its proper dietary id 
	@Test
	public void testRestaurantOneMeal_MealID(){		
		assertEquals(restaurant1.getRestaurantMeals().get(0).getDietaryId(), Meal.VEGETARIAN);
	}
	
	//test a restaurant with a specific meal can get its proper meal's quantity 
	@Test
	public void testRestaurantOneMeal_MealQty(){		
		assertEquals(restaurant1.getRestaurantMeals().get(0).getMealQuantity(), 100);
	}
	
	//test a restaurant with two specific meals can be created
	@Test
	public void testRestaurantTwoMealSize(){
				
		assertEquals(restaurant2.getRestaurantMeals().size(), 2);		
	}
	
	//test a restaurant with two specific meal can get its proper dietary id 
	@Test
	public void testRestaurantTwoMeal_MealID(){
		assertEquals(restaurant2.getRestaurantMeals().get(4).getDietaryId(), Meal.OTHER);
	}
	
	//test a restaurant with two specific meal can get its proper meal's quantity 
	@Test
	public void testRestaurantTwoMeal_MealQty(){		
		assertEquals(restaurant2.getRestaurantMeals().get(4).getMealQuantity(), 0);
	}
	
	//test a restaurant with no findable meal is null 
	@Test
	public void testRestaurantNoFoundMeal(){		
		assertNull(restaurantMeals2.get(1));
	}

	//test function setRating
	@Test	
	public void testSetRating(){
		restaurant1.setRating(3);
		assertEquals(restaurant1.getRating(), 3);
	}
	
	//test function setName
	@Test
	public void testSetName(){
		restaurant2.setName("Sonny's restaurant");
		assertEquals(restaurant2.getName(), "Sonny's restaurant");
	}
	
}