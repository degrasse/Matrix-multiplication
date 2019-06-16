
import java.io.PrintStream;

import java.io.PrintWriter;
import java.lang.Object;
import java.lang.reflect.Array;
import java.util.Random;
import java.util.Scanner;

import Jama.Matrix;

///////////////////////////////////////////////////////
// Matrix Multiplication - Lab 2
//		deGrasse Schrader
//
//Using Jama(Java matrix) class
//to run must have jama.jar in working directory
// jar download: 
//	 http://www.cs.princeton.edu/IntroCS/95linear/jama.jar
////////////////////////////////////////////////////////


public class matrixMultiply {
	
	//method to create a two matrices A & B 
	//filled with random numbers
	public static Matrix createMatrix(int a, int b){
		
		//fill matrix A
		Matrix A = new Matrix(a, b);
		double[][] x = A.getArray();
		for(int i = 0; i < a; i++){
			for(int j = 0; j < b; j++){
				x[i][j] = fillRand();
				}
			}
		return A;
		}

	
	//method to generate random numbers to fill array
	public static double fillRand(){
			Random rand = new Random();
			int s = rand.nextInt(50)+1;
			return s;
		}
	
	
	//method to multiply two matrices
	public static double[][] multiply(Matrix A, Matrix B){
		int m = A.getRowDimension();
		int r = A.getColumnDimension();
		int r2 = B.getRowDimension();
		int n = B.getColumnDimension();
		//row dimension of A should = column dimension of B
		if( r != r2) throw new RuntimeException("Illegal Matrix Dimensions");
		
		//dimensions of product Matrix C = m x n 
		double[][] C = new double[m][n]; //new Matrix(m,n);
		
		//dot product
		// (a,b,c) * (x,y,z) = a*x + b*y + c*z
		double[] v = new double[m];
		double[] t = new double[n];
		
		for ( int i = 0; i < m; i++){
			for( int j = 0; j < n; j++){
				for( int k = 0; k < r; k++){
					C[i][j] += A.get(i, k)*B.get(k, j); 
				}
				}
			}
			return C;
		}

	////////////////////////////////////////
	/////////////Main test/////////////////
	//////////////////////////////////////
	
	public static void main(String[] args){
		
		System.out.println("Create Matix A(m x r) and Matrix B(r x n)");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter integer for dimension m");
		final int m = sc.nextInt();
		System.out.println("Enter integer for dimension r");
		final int r = sc.nextInt();
		System.out.println("Enter integer for dimension n");
		final int n = sc.nextInt();
		
		
		PrintWriter out = new PrintWriter(System.out);
		Matrix x = createMatrix(m,r);
		//System.out.println("Matrix A");
		//x.print(m,r);
		Matrix y = createMatrix(r,n);
		//System.out.println("Matrix B");
		//y.print(r, n);
		
		System.out.println("Matrix C = A*B");
		
		Long start_time;
		start_time = System.nanoTime();
		Matrix z = new Matrix(multiply(x, y));
		Long diff_time = System.nanoTime() - start_time;
		//prints matrix C to three decimal places
		//z.print(n, 3);
		System.out.println("Took " + diff_time + " nano seconds");
	}
	
}
