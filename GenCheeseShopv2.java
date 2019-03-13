import java.util.*;

public class GenCheeseShopv2 {

	public static void intro(String[] names, double[] prices, double[] amounts) {
		Random ranGen = new Random(100);
		System.out.println("We sell " + names.length + " kinds of Cheese (in 0.5 lb packages)\r\n");

		for (int i = 0; i < names.length; i++) {
			if (i == 0) {
				names[i] = "Humboldt Fog";
				prices[i] = 25.00;
			} else if (i == 1) {
				names[i] = "Red Hawk";
				prices[i] = 40.50;
			} else if (i == 2) {
				names[i] = "Teleme";
				prices[i] = 17.25;
			} else {
				names[i] = "Cheese Type " + (char)('A' + i);
				prices[i] = ranGen.nextInt(1000)/100.0;
			}
			System.out.println(names[i] + ": $" + prices[i] + " per pound");
			amounts[i] = 0;
		}
		System.out.println();
	}

	public static void getAmount(Scanner sc, String[] names, double[] amounts) {
		for (int i = 0; i < amounts.length; i++) {
			System.out.print("Enter number of " + names[i] + ": ");
			while(true) {
				amounts[i] = sc.nextDouble();
				if (check(amounts[i])) break;
			}
		}
	}

	public static void itemizedList(String[] names, double[] prices, double[] amounts) {
		int count = 0;
		for (int i = 0; i < amounts.length; i++) {
			if (amounts[i] == 0) count++;
		}

		if (count != amounts.length) {
			for (int i = 0; i < names.length; i++) {
				System.out.println(amounts[i] + " of " + names[i] + " @ $" + prices[i] + " = $" + (amounts[i] * prices[i]));
			}
		} else System.out.println("No items were purchased.");	
	}

	public static double calcSubTotal(double[] prices, double[] amounts) {
		double sub = 0.0;
		for (int i = 0; i < prices.length; i++) {
			sub += prices[i] * amounts[i];
		}
		return sub;
	}

	public static double[] discountSpecials(double[] amounts, double[] prices){
		double[] disSpecials = new double[amounts.length];
		System.out.println();
		System.out.println("Humboldt Fog (Buy 1 get 1 free): \t -$" + (Math.round(amounts[0] * 2) / 2.0)/2 * prices[0]);
		disSpecials[0] += (Math.round(amounts[0] * 2) / 2.0)/2 * prices[0];

		if (disSpecials.length > 1) {
			System.out.println("Red Hawk (Buy 2 Get 1 Free): \t\t -$" + (int)(amounts[1] * 2 / 3) * (prices[1] / 2));
			disSpecials[1] += (int)(amounts[1] * 2 / 3) * (prices[1] / 2);
		}

		for (int i = 2; i < disSpecials.length; i++) disSpecials[i] = 0; 
		return disSpecials;	
	}

	public static double printSubTotals(double subTotal, double[] disSpecials) {
		double sub = 0.0;

		for (int i = 0; i < disSpecials.length; i++) sub += disSpecials[i];
		subTotal -= sub;
		System.out.printf("New subtotal: \t\t\t\t  $%.2f" , subTotal);
		return subTotal;
	}

	public static void printFinalTotal(double subTotal) {
		if (subTotal > 250.00) {
			System.out.printf("\nAdditional 25%% Discount: \t\t -$%.2f" , subTotal * 0.25);
			subTotal *= 0.75;
		} else if (subTotal > 100.00) {
			System.out.printf("\nAdditional 10%% Discount: \t\t -$%.2f" , subTotal * 0.10);
			subTotal *= 0.90;
		} 
		System.out.printf("\nFinal total: \t\t\t\t  $%.2f" , subTotal);
	}

	//I added the method below myself
	public static boolean check (double uIn) {
		if (uIn < 0) {
			System.out.print("Invalid Input. Enter a value >= 0:");
			return false;
		} else if (uIn % 0.5 != 0) {
			System.out.print("Invalid input. Enter a value that's multiple of 0.5:");
			return false;
		} else return true;
	}

	/*
	 * Program starts here
	 */
	public static void main(String[] args) {

		final int MAXCHEESE;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number of Cheeses for shop setup: ");
		MAXCHEESE = sc.nextInt();

		// DO NOT CHANGE ANYTHING BELOW
		String[] names = new String[MAXCHEESE];
		double[] prices = new double[MAXCHEESE];
		double[] amounts = new double[MAXCHEESE];

		intro(names, prices, amounts);
		getAmount(sc, names, amounts);

		double subTotal = calcSubTotal(prices, amounts);

		if (MAXCHEESE != 0 ) {
			System.out.print("\nDisplay the itemized list? (1 for yes) ");
			int display = sc.nextInt();

			if (display == 1) {
				itemizedList(names, prices, amounts);
			}
		}

		double newSubTotal = printSubTotals(subTotal, discountSpecials(amounts, prices));

		printFinalTotal(newSubTotal);

		sc.close();
	}
}
