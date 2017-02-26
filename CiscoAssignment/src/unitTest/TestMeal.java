/**
 * 
 */
package unitTest;

import static org.junit.Assert.*;
import org.junit.Test;
import mainCode.Meal;

/**
 * @author Sonny
 *
 */
public class TestMeal {	
	private Meal meal = new Meal();
		
	//test the default object is not null
	@Test
	public void testDefaultObjectIsNotNull(){
		assertNotNull(meal);
	}
	
	//test the default dietary id
	@Test
	public void testDefaultMealDietaryId(){		
		assertEquals(meal.getDietaryId(), Meal.OTHER);
	}
	
	//test the default quantity is zero
	@Test
	public void testDefaultMealQuantity(){
		assertEquals(meal.getMealQuantity(), 0);
	}
	
	//test vegetarian meals can be created
	@Test
	public void testVegetarianMealId(){
		meal = new Meal(0, 0);
		assertEquals(meal.getDietaryId(), Meal.VEGETARIAN);
	}
	
	//test gluten free meals can be created
	@Test
	public void testGlutenFreeMealId(){
		meal = new Meal (1, 0);
		assertEquals(meal.getDietaryId(), Meal.GLUTEN_FREE);
	}
	
	//test nut free meals can be created
	@Test
	public void testNutFreeMealId(){
		meal = new Meal (2, 0);
		assertEquals(meal.getDietaryId(), Meal.NUT_FREE);
	}
	
	//test fish meals can be created
	@Test
	public void testFishMealId(){
		meal = new Meal(3, 0);
		assertEquals(meal.getDietaryId(), Meal.FISH_FREE);
	}

	//test other meals can be created
	@Test
	public void testOtherMealId(){
		meal = new Meal(4, 0);
		assertEquals(meal.getDietaryId(), Meal.OTHER);
	}
	
	//test function setDietaryId
	@Test	
	public void testSetDietaryId(){
		meal.setDietaryId(3);
		assertEquals(meal.getDietaryId(), 3);
	}
	
	//test function setMealQuantity
	@Test
	public void testSetMealQuantity(){
		meal.setMealQuantity(-1);
		assertEquals(meal.getMealQuantity(), -1);
	}
}