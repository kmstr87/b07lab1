public class Polynomial {
	double [] coeff;
	public Polynomial() {
		coeff = new double[1];
		this.coeff[0] = 0;
	}
	public Polynomial(double [] input) {
		coeff = input;
	}
	public Polynomial add(Polynomial other) {
		Polynomial newPoly = new Polynomial(new double[Math.max(this.coeff.length, other.coeff.length)]);
		for (int i = 0; i < newPoly.coeff.length; i++) {
			newPoly.coeff[i] = 0;
			if (this.coeff.length > i) newPoly.coeff[i] += this.coeff[i];
			if (other.coeff.length > i) newPoly.coeff[i] += other.coeff[i];
		}
		return newPoly;
	}
	
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < this.coeff.length; i++) {
			result += this.coeff[i] * Math.pow(x, (double)i);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		double result = evaluate(x);
		return result == 0;
	}
}