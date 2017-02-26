package mainCode;
import java.util.Comparator;

public class RestaurantRating implements Comparator<Restaurant> {

	/**
     * Compares two restaurants based on rating and returns the restaurant with higher rating,
     * if both have same rating, then returns first restaurant as default behaviour and for simplicity
     *
     * @return restaurant with higher rating
     * @param restaurant0: first restaurant to compare
     * @param restaurant1: second restaurant to compare
     * 
     */
	public int compare(Restaurant restaurant0, Restaurant restaurant1) {
		// compare restaurant's rating
		return restaurant1.getRating() - restaurant0.getRating();
	}
}
