import java.util.ArrayList;
import java.util.Scanner;

public class MutlidegreePolynomial {

	public static int coef;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		// input
		System.out.print("What is the highest exponent?: ");
		coef = in.nextInt();

		//setting up intial size
		ArrayList<Double> polynomial = new ArrayList<Double>();
		for (int i = 0; i < coef+1; i++) polynomial.add(0.0);
		
		
		for (int i = coef; i > -1; i--) {
			System.out.print("Please input coefficent for exponenet of x^" + i + ": ");
			polynomial.set(i, in.nextDouble());
		}
		
		// print
		System.out.println("\nInput: ");
		print(polynomial);
		System.out.println("= 0\n");
		
		
		
		// find factors O(sqrt(n))
		ArrayList<Double> factorConstant = new ArrayList<>();
		factorConstant = findFactors(factorConstant, polynomial.get(0));
		ArrayList<Double> factorLeading = new ArrayList<>();
		factorLeading = findFactors(factorLeading, polynomial.get(coef));
		
		
		// rational root test
		ArrayList<ArrayList<Double>> factored = new ArrayList<>();
		ArrayList<Double> simplified = new ArrayList<>();
		simplified = polynomial;
		for (Double i : factorConstant) {
			for (Double j : factorLeading) {
				double rationalRoot = i/j; // + or -
				
				ArrayList<Double> newPoly = syntheticDivision(simplified, rationalRoot);
				if (newPoly.size() != 0) {
					simplified = newPoly;
					ArrayList<Double> factor = new ArrayList<>();
					factor.add(rationalRoot*-1);
					factor.add(1.0);
					factored.add(factor);
				}
				
				rationalRoot *= -1;
				newPoly = syntheticDivision(simplified, rationalRoot);
				if (newPoly.size() != 0) {
					simplified = newPoly;
					ArrayList<Double> factor = new ArrayList<>();
					factor.add(rationalRoot*-1);
					factor.add(1.0);
					factored.add(factor);
				}
				
			}
		}
		
		// printing out simplified
		System.out.print("Factored: \n");
		for (ArrayList<Double> i : factored) {
			System.out.print("( ");
			print(i);
			System.out.print(")");
		}
		if (simplified.size() > 1) {
				System.out.print("( ");
				print(simplified);
				System.out.print(")");
		}
		System.out.println(" = 0");
		
	}
	
		
	public static ArrayList<Double> syntheticDivision(ArrayList<Double> polynomial, double factor) {
		
		ArrayList<Double> factored = new ArrayList<>();
		for (int i = 0; i < polynomial.size(); i++) factored.add(0.0);
		factored.set(polynomial.size()-1, polynomial.get(polynomial.size()-1)); 
		
		for (int i = polynomial.size()-2; i > -1; i--) {
			factored.set(i, polynomial.get(i) + (factored.get(i+1)*factor));
		}
		
		if (factored.get(0) != 0) return new ArrayList<>();
		factored.remove(0);
		return factored;
		
	}

	
	public static ArrayList<Double> findFactors(ArrayList<Double> arr, double num) {
		num = Math.abs(num);
		int half = (int) Math.sqrt(num);
		
		for (int i = 1; i <= half; i++) {
			if (num % i == 0) { // if it is a factor
				
				if (num/i == i) {  // multiplied by itself to be a factor
					arr.add((double) i);
				}
				else {  // there are two factors for the number
					arr.add((double) i);
					arr.add((double) (num/i));
				}
				
			}
		}
		
		return arr;
		
	}
	
	public static void printNum(Double x) {
		if (x - x.intValue() == 0.0) {
			int num = x.intValue();
			System.out.print(num);
			return;
		}
		else {
			double num = x;
			System.out.print(num);
			return;
		}
		
	}
	
	public static void print(ArrayList<Double> polynomial) {
		for (int i = polynomial.size()-1; i > -1; i--) {
			if (polynomial.get(i) == 0) continue;
			
			if (polynomial.get(i) > 0 && i == polynomial.size()-1) {
				if (polynomial.get(i) != 1.0) printNum(polynomial.get(i));
			}
			else if (polynomial.get(i) < 0) {
				if (polynomial.get(i) == -1.0) {
					System.out.print("- ");
					if (i == 0) System.out.print("1");
				}
				
				else {
					System.out.print("- ");
					printNum(Math.abs(polynomial.get(i)));
				}
			}
			else {
				System.out.print("+ ");
				if (polynomial.get(i) != 1) printNum(polynomial.get(i));
			}
			if (i == 1) System.out.print("x");
			else if (i != 0) System.out.print("x^" + i);
			
			System.out.print(" ");
			
		
			
		}
		
	}
}
