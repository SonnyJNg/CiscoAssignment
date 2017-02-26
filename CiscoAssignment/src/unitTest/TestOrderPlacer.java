/**
 * 
 */
package unitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import mainCode.Meal;
import mainCode.Restaurant;
import mainCode.OrderPlacer;

/**
 * @author Sonny
 *
 */
public class TestOrderPlacer {
	private Meal meal0_CompanyA = new Meal(Meal.VEGETARIAN, 4);
	private Meal meal1_CompanyA = new Meal(Meal.OTHER, 36);
	private Meal meal0_CompanyB = new Meal(Meal.VEGETARIAN, 20);
	private Meal meal1_CompanyB = new Meal(Meal.GLUTEN_FREE, 20);
	private Meal meal2_CompanyB = new Meal(Meal.OTHER, 60);	
	private Map<Integer, Meal> restaurantMealsA = new HashMap<Integer, Meal>();
	private Map<Integer, Meal> restaurantMealsB = new HashMap<Integer, Meal>();	
	private Restaurant restaurantA;
	private Restaurant restaurantB;
	private int ratingA = 5;
	private int ratingB = 3;
	private OrderPlacer order = new OrderPlacer();
	private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
	
	//	Constructor to set test environment
	public TestOrderPlacer(){
		restaurantMealsA.put(meal0_CompanyA.getDietaryId(), meal0_CompanyA);
		restaurantMealsA.put(meal1_CompanyA.getDietaryId(), meal1_CompanyA);
		restaurantA = new Restaurant(ratingA, "Restaurant A", restaurantMealsA);
		restaurantMealsB.put(meal0_CompanyB.getDietaryId(), meal0_CompanyB);
		restaurantMealsB.put(meal1_CompanyB.getDietaryId(), meal1_CompanyB);
		restaurantMealsB.put(meal2_CompanyB.getDietaryId(), meal2_CompanyB);
		restaurantB = new Restaurant(ratingB, "Restaurant B", restaurantMealsB);
		restaurantList.add(restaurantA);
		restaurantList.add(restaurantB);		
		order.setRestaurantList(restaurantList);
	}
	
	public void setExpectedOrder(){
		order.setOrderVegetarian(5);
		order.setOrderGlutenFree(7);
		order.setOrderOther(38);
	}
	
	// test restaurant list size	
	@Test
	public void testRestaurantListSize(){
		assertEquals(order.getRestaurantList().size(), 2);
	}

	// test getTotalAvailable vegetarian meals
	@Test
	public void testGetTotalAvailable_vegetarian(){
		assertEquals(order.getTotalAvailable(Meal.VEGETARIAN), 24);
	}
	
	// test getTotalAvailable gluten free meals
	@Test
	public void testGetTotalAvailable_glutenFree(){
		assertEquals(order.getTotalAvailable(Meal.GLUTEN_FREE), 20);
	}
	
	// test getTotalAvailable other meals
	@Test
	public void testGetTotalAvailable_other(){
		assertEquals(order.getTotalAvailable(Meal.OTHER), 96);
	}
	
	// test getTotalAvailable nut free meals
	@Test
	public void testGetTotalAvailable_nutFree(){
		assertEquals(order.getTotalAvailable(Meal.NUT_FREE), 0);
	}
	
	// test getTotalAvailable fish free meals
	@Test
	public void testGetTotalAvailable_fishFree(){
		assertEquals(order.getTotalAvailable(Meal.FISH_FREE), 0);
	}
	
	// test insertNewMeal when a meal does not exist within a restaurant, it allows you to save it
	@Test
	public void testInsertNewMeal_MealIsNew(){
		String exception = "";
		try{
			restaurantMealsA = order.insertNewMeal(restaurantMealsA, Meal.GLUTEN_FREE, 50);			
		}
		catch (Exception e){
			exception = e.getMessage();
		}
		assertEquals(exception, "");
	}

	// test insertNewMeal when a meal exists within a restaurant, it throws an exception
	@Test
	public void testInsertNewMeal_MealExists(){
		String exception = "";
		try{
			restaurantMealsA = order.insertNewMeal(restaurantMealsA, Meal.VEGETARIAN, 50);			
		}
		catch (Exception e){
			exception = e.getMessage();
		}
		assertEquals(exception, "Cannot insert new meal because it's already inserted into this restaurant.");
	}
	
	// test runComputeBestOption for computing company A vegetarian plates
	@Test
	public void testRunCompueBestOption_CompanyA_Vegetarian(){
		setExpectedOrder();
		assertEquals(order.getComputedRestaurantList(restaurantList).get(0).getRestaurantMeals().get(Meal.VEGETARIAN).getMealQuantity(), 4);
	}
	
	// test runComputeBestOption for computing company A gluten free plates
	@Test
	public void testRunCompueBestOption_CompanyA_GlutenFree(){
		setExpectedOrder();
		assertNull(order.getComputedRestaurantList(restaurantList).get(0).getRestaurantMeals().get(Meal.GLUTEN_FREE));
	}
	
	// test runComputeBestOption for computing company A nut free plates
	@Test
	public void testRunCompueBestOption_CompanyA_NutFree(){
		setExpectedOrder();
		assertNull(order.getComputedRestaurantList(restaurantList).get(0).getRestaurantMeals().get(Meal.NUT_FREE));
	}
	
	// test runComputeBestOption for computing company A fish free plates
	@Test
	public void testRunCompueBestOption_CompanyA_FishFree(){
		setExpectedOrder();
		assertNull(order.getComputedRestaurantList(restaurantList).get(0).getRestaurantMeals().get(Meal.FISH_FREE));
	}
	
	// test runComputeBestOption for computing company A other plates
	@Test
	public void testRunCompueBestOption_CompanyA_Other(){
		setExpectedOrder();
		assertEquals(order.getComputedRestaurantList(restaurantList).get(0).getRestaurantMeals().get(Meal.OTHER).getMealQuantity(), 36);
	}
	
	// test runComputeBestOption for computing company B vegetarian plates
	@Test
	public void testRunCompueBestOption_CompanyB_Vegetarian(){
		setExpectedOrder();
		assertEquals(order.getComputedRestaurantList(restaurantList).get(1).getRestaurantMeals().get(Meal.VEGETARIAN).getMealQuantity(), 1);
	}
	
	// test runComputeBestOption for computing company B gluten free plates
	@Test
	public void testRunCompueBestOption_CompanyB_GlutenFree(){
		setExpectedOrder();
		assertEquals(order.getComputedRestaurantList(restaurantList).get(1).getRestaurantMeals().get(Meal.GLUTEN_FREE).getMealQuantity(), 7);
	}
	
	// test runComputeBestOption for computing company B nut free plates
	@Test
	public void testRunCompueBestOption_CompanyB_NutFree(){
		setExpectedOrder();
		assertNull(order.getComputedRestaurantList(restaurantList).get(1).getRestaurantMeals().get(Meal.NUT_FREE));
	}
	
	// test runComputeBestOption for computing company B fish free plates
	@Test
	public void testRunCompueBestOption_CompanyB_FishFree(){
		setExpectedOrder();
		assertNull(order.getComputedRestaurantList(restaurantList).get(1).getRestaurantMeals().get(Meal.FISH_FREE));
	}
	
	// test runComputeBestOption for computing company B other plates
	@Test
	public void testRunCompueBestOption_CompanyB_Other(){
		setExpectedOrder();
		assertEquals(order.getComputedRestaurantList(restaurantList).get(1).getRestaurantMeals().get(Meal.OTHER).getMealQuantity(), 2);
	}
}