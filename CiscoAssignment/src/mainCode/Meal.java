package mainCode;

public class Meal {

	public static final int VEGETARIAN = 0;
	public static final int GLUTEN_FREE = 1;
	public static final int NUT_FREE = 2;
	public static final int FISH_FREE = 3;
	public static final int OTHER = 4;

	private int dietaryId;
	private int mealQuantity;

	/**
     * Default constructor for Meal object. A default meal is expected to be dietary type other
     * with zero quantity.
     */
	public Meal() {
		this.dietaryId = OTHER;
		this.mealQuantity = 0;
	}

	/**
     * Generates a meal object for a restaurant based on the selected dietary type and order's
     * quantity
     * 
     * @param  dietaryId: 0 for vegetarian, 1 for gluten free, 2 for nut free, 3 for fish free, 4 for other
     * @param  mealQuantity: integer representing how many items for this meal are to be stored
     */
	public Meal(int dietaryId, int mealQuantity) {
		this.dietaryId = dietaryId;
		this.mealQuantity = mealQuantity;
	}

	/**
     * Returns the meal's dietary type id
     * 0: vegetarian
     * 1: gluten free
     * 2: nut free
     * 3: fish free
     * 4: other
     *
     * @return meal's dietary id
     */
	public int getDietaryId() {
		return dietaryId;
	}

	/**
     * Set meal's dietary type id
     * 0: vegetarian
     * 1: gluten free
     * 2: nut free
     * 3: fish free
     * 4: other
     *
     * @param  dietaryId: meal's dietary type id
     */
	public void setDietaryId(int dietaryId) {
		this.dietaryId = dietaryId;
	}

	/**
     * Returns assigned meal's quantity
     *
     * @return amount of meal's quantity
     */
	public int getMealQuantity() {
		return mealQuantity;
	}

	/**
     * Set assigned meal's quantity
     *
     * @param  mealQuantity: meal's quantity
     */
	public void setMealQuantity(int mealQuantity) {
		this.mealQuantity = mealQuantity;
	}

}
