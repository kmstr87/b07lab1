public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,1,-2,5};
		int [] d1 = {0, 1, 2, 3};
		Polynomial p1 = new Polynomial(c1, d1);
		double [] c2 = {2,6,-9};
		int [] d2 = {0, 3, 5};
		Polynomial p2 = new Polynomial(c2, d2);
		Polynomial s = p1.add(p2);
		System.out.println("Coefficients:");
		for (int i = 0; i < s.degree.length; i++) {
			System.out.print(s.degree[i] + ", ");
		}
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1)) {
			System.out.println("1 is a root of s");
		} else {
			System.out.println("1 is not a root of s");
		}
	}
}