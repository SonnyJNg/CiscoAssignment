#Author: Sonny Ng

##Instructions:
We're ordering meals for a team lunch. Every member in the team needs one meal, some have dietary 
restrictions such as vegetarian, gluten free, nut free, and fish free. We have a list of restaurants 
which serve meals that satisfy some of these restrictions. Each restaurant has a rating, and a 
limited amount of meals in stock that they can make today. 

Implement an object oriented system with automated tests that can automatically produce the best 
possible meal orders with reasonable assumptions.
 
##Example:
 *- Team needs:*<br/>
	* Total 50 meals including 5 vegetarians and 7 gluten free.
 
 *- Restaurants:*<br/>
	* Restaurant A has a rating of 5/5 and can serve 40 meals including 4 vegetarians, <br/>
	* Restaurant B has a rating of 3/5 and can serve 100 meals including 20 vegetarians, and 20 gluten free.
 
 *- Expected meal orders:*<br/>
	* Restaurant A (4 vegetarian + 36 others), <br/>
	* Restaurant B (1 vegetarian + 7 gluten free + 2 others)
 
##Assumptions:
	*	There is a limit of dietary restriction options based on the instruction’s example
		of dietary restrictions: vegetarian, gluten free, nut free, fish free and other. 
		The main reason of why this limitation is for simplicity, considering the dietary 
		restriction options should be always the same regardless of which company is placing 
		the order.
	
	*	Restaurant’s rating goes from 1 to 5 as integers.


	*	Meal quantity per restaurant goes from 0 to 100. 


	*	Each restaurant’s meal dietary restriction is unique and mandatory. 
		Without specifying meal’s name, the user will forget which meals have been entered, 
		thus the dietary restriction is used as an id to keep track of the meal uniqueness 
		per restaurant.