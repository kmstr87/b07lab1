import java.io.File;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Polynomial {
	double [] coeff;
	int [] degree;
	public Polynomial() {
		coeff = new double[1];
		degree = new int[1];
		this.coeff[0] = 0;
		this.degree[0] = 0;
	}
	
	public Polynomial(double [] coeff, int [] degree) {
		this.coeff = coeff;
		this.degree= degree; 
	}
	
	public Polynomial(File x) throws FileNotFoundException, IOException {
		BufferedReader input = new BufferedReader(new FileReader(x));
		String rawLine = input.readLine();
		rawLine = rawLine.replace("-", "+-");
		String [] parts = rawLine.split("+");
		
		int newLength = parts.length;
		int [] newDegree = new int[newLength];
		double [] newCoeff = new double[newLength];
		String [] temp;
		
		for (int i = 0; i < newLength; i++) {
			if (parts[i].indexOf('x') == -1) {
				newCoeff[i] = Double.parseDouble(parts[i]);
				newDegree[i] = 0;
			} else {
				temp = parts[i].split("x");
				newCoeff[i] = Double.parseDouble(temp[0]);
				newDegree[i] = Integer.parseInt(temp[1]);
			}
		}
		
		this.coeff = newCoeff;
		this.degree = newDegree;
		input.close();
	}
	
	public double addCoeff(Polynomial other, int index) {
		int thisIndex = -1;
		int otherIndex = -1;
		for (int i = 0; i < this.degree.length; i++) {
			if (this.degree[i] == index) thisIndex = i;
		}
		for (int i = 0; i < other.degree.length; i++) {
			if (other.degree[i] == index) otherIndex = i;
		} 
		
		if ((thisIndex != -1) && (otherIndex != -1)) {
			return this.coeff[thisIndex] + other.coeff[otherIndex];
		} else if (thisIndex != -1) {
			return this.coeff[thisIndex];
		} else {
			return other.coeff[otherIndex];
		} 
		
	}
	
	public Polynomial add(Polynomial other) {
		int checker = 0;
		int lengCount = this.degree.length;
		int thisLength = this.degree.length;
		for (int i = 0; i < other.degree.length; i++) {
			for (int j = 0; j < thisLength; j++) {
				if (other.degree[i] == this.degree[j]) {
					checker = 1;
				}
			}
			if (checker == 0) {
				lengCount++;
			} else {
				checker = 0;
			}
		}		
		
		int [] addDegree = new int[lengCount];
		double [] newCoeff = new double[lengCount];
		
		for (int i = 0; i < thisLength; i++) {
			addDegree[i] = this.degree[i];
		}
		
		lengCount = 0;
		
		for (int i = 0; i < other.degree.length; i++) {
			for (int j = 0; j < thisLength; j++) {
				if (other.degree[i] == this.degree[j]) checker = 1;
			}
			if (checker == 0) {
				addDegree[thisLength + lengCount] = other.degree[i];
				lengCount++;
			} else {
				checker = 0;
			}			
		}		
		
		for (int i = 0; i < addDegree.length; i++) {
			newCoeff[i] = addCoeff(other, addDegree[i]);
		}
		
		Polynomial addPoly = new Polynomial(newCoeff, addDegree);
		
		return addPoly;
	}
	
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < this.coeff.length; i++) {
			result += this.coeff[i] * Math.pow(x, (double)this.degree[i]);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		double result = evaluate(x);
		return result == 0;
	}
	
	public Polynomial multiply(Polynomial other) {
		int counter = 0;
		int [] tempDegree = new int[this.degree.length * other.degree.length];
		double [] tempCoeff = new double[this.coeff.length * other.coeff.length];
		for (int i = 0; i < this.degree.length; i++) {
			for (int j = 0; j < other.degree.length; j++) {
				tempDegree[counter] = this.degree[i] + other.degree[j];
				tempCoeff[counter] = this.coeff[i] * other.coeff[j];
			}
		}
		
		counter = 0;
		
		for (int i = 0; i < tempDegree.length; i++) {
			for (int j = i + 1; j < tempDegree.length; j++) {
				if (tempDegree[i] == tempDegree[j]) counter++;
			}
		}
		
		int diff = tempDegree.length - counter;
		int [] newDegree = new int[diff];
		double [] newCoeff = new double[diff];
		
		for (int i = 0; i < newDegree.length; i++) {
			newDegree[i] = -1;
		}
		
		counter = 0;
		
		for (int i = 0; i < tempDegree.length; i++) {
			for (int j = 0; j < newDegree.length; j++) {
				if (tempDegree[i] == newDegree[j]) {
					newCoeff[j] += tempCoeff[i];
					break;
				}
			}
			newDegree[counter] = tempDegree[i];
			newCoeff[counter] = tempCoeff[i];
			counter++;
		}
		
		Polynomial newPoly = new Polynomial(newCoeff, newDegree);
		return newPoly;
	}
	
	public void saveToFile(String name) throws FileNotFoundException, IOException {
		String newLine = "";
		int leng = this.coeff.length;
		for (int i = 0; i < leng; i++) {
			if (this.degree[i] == 0) {
				newLine += this.coeff[i];
			} else {
				newLine += this.coeff[i] + "x" + this.degree[i];
			}
			if (i != leng - 1) newLine+= "+";
		}
		newLine = newLine.replace("+-", "-");
		
		File f = new File(name);
		f.createNewFile();
		FileWriter output = new FileWriter(name, false);
		output.write(newLine);
		output.close();
		
		
	}
}