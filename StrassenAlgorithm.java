import java.io.PrintWriter;
import java.util.Scanner;

import Jama.Matrix;

///////////////////////////////////////////////////////
// Strassen Algorithm - Lab 2
//			deGrasse Schrader
//
//Using Jama(Java matrix) class
//to run must have jama.jar in working directory
//jar download: 
//http://www.cs.princeton.edu/IntroCS/95linear/jama.jar
////////////////////////////////////////////////////////

public class StrassenAlgorithm {
	
	public static Matrix strassen(Matrix A, Matrix B){
		//check that both matrices have n by n dimensions 
		int n = A.getColumnDimension();
		int n2 = A.getRowDimension();
		if (n != n2) throw new RuntimeException("Matrix A mismatch dimensions");
		int n3 = B.getColumnDimension();
		int n4 = B.getRowDimension();
		if(n3 != n4) throw new RuntimeException("Matrix B mismatch dimensions");
		if(n != n3 || n2!= n4) throw new RuntimeException("Matrix have different dimensions");
		
		//each matrix divided into paritions of size n/2
		int partition = n/2;
		//initialize result matrix C
		Matrix C = new Matrix(n,n);                                                        
		
		if(n ==1){
			//if 1 by 1 just multiply a and b to get c
			C.set(0, 0,(A.get(0,0)*B.get(0,0))) ;
		}
		else{
			
			//initialize sub matrices of A and B
			Matrix a11 = new Matrix(n/2, n/2);
			Matrix a12 = new Matrix(n/2, n/2);
			Matrix a21 = new Matrix(n/2,n/2);
			Matrix a22 = new Matrix(n/2, n/2);
			
			Matrix b11 = new Matrix(n/2, n/2);
			Matrix b12 = new Matrix(n/2, n/2);
			Matrix b21 = new Matrix(n/2, n/2);
			Matrix b22 = new Matrix(n/2, n/2);
			
			//fil sub matrices 
			// top right, top left, bottom right, bottom left
			for(int i = 0; i < partition; i++){
				for(int j = 0;  j < partition; j++){
					//set a11 at i,j to A at i, j
					a11.set(i,j, A.get(i,j));
					a12.set(i, j, A.get(i, j + partition));
					a21.set(i, j, A.get(i + partition, j));
					a22.set(i, j, A.get(i + partition, j + partition));
					
					b11.set(i, j, B.get(i, j));
					b12.set(i, j, B.get(i, j + partition));
					b21.set(i, j, B.get(i + partition, j));
					b22.set(i, j, B.get(i + partition, j));
				}
			}
				//a11.setMatrix()	
			//calculate P1-P7 recursively
			Matrix aAdd =  new Matrix(partition, partition);
			Matrix bAdd = new Matrix(partition, partition);
			Matrix aMin = new Matrix(partition, partition);
			Matrix bMin = new Matrix(partition, partition);
	
			
			//p1 = (a11 + a22) * (b11 + b22)
			aAdd = a11.plus(a22);
			bAdd = b11.plus(b22);
			Matrix p1 = strassen(aAdd, bAdd);
			//p2 = (a21 + a220 * b11
			aAdd = a21.plus(a22);
			Matrix p2 = strassen(aAdd, b11);
			//p3 = a11 * (b12 - b22)
			bMin = b12.minus(b22);
			Matrix p3 = strassen(a11, bMin);
			//p4 = a22 * ( b21 - b11)
			bMin = b21.minus(b11);
			Matrix p4 = strassen(a22, bMin);
			//p5 = (a11 + a12) * b22
			aAdd = a11.plus(a12);
			Matrix p5 = strassen(aAdd, b22);
			//p6 = (a21 - a11) * (b11 + b12)
			aMin = a21.minus(a11);
			bAdd = b11.plus(b12);
			Matrix p6 = strassen(aMin, bAdd);
			//p7 = (a12 - a22) * (b21 + b22)
			aMin = a12.minus(a22);
			bAdd = b21.plus(b22);
			Matrix p7 = strassen(aMin, bAdd);
			
			//c11 = p1 + p4 - p5 + p7
			Matrix c11 = (p1.plus(p4)).minus((p5.plus(p7)));
			//c12 = p3 + p5
			Matrix c12 = p3.plus(p5);
			//c21 = p2 + p4
			Matrix c21 = p2.plus(p4);
			//c22 = p1 - p2 + p3 + p6
			Matrix c22 = (p1.minus(p2)).plus(p3.plus(p6));
			
			//put result matrix C together
			for(int i = 0; i < partition; i++){
				for(int j = 0;  j < partition; j++){
					C.set(i, j, c11.get(i, j));
					C.set(i, j + partition, c12.get(i, j));
					C.set(i + partition, j, c21.get(i, j));
					C.set(i + partition,  j + partition, c22.get(i, j));
					
				}
			}
		}
		return C;	
	}
	
	////////////////////////////////////////
	/////////////Main test/////////////////
	//////////////////////////////////////

	public static void main(String[] args){
		System.out.println("Enter integer n for the dimensions of n by n matrix");
		Scanner sc = new Scanner(System.in);
		final int N = sc.nextInt();

		PrintWriter out = new PrintWriter(System.out);
		
		matrixMultiply matrix = new matrixMultiply();
		
		//create matrix A with input dimensions
		Matrix x = matrix.createMatrix(N, N);
		//System.out.println("Matrix A");
		//x.print(N,N);
		//create matrix B with input dimensions
		Matrix y = matrix.createMatrix(N,N);
		//System.out.println("Matrix B");
		//y.print(N, N);
		
		//time and run strassen algorithm and output result matrix
		Matrix z = matrix.createMatrix(N, N);
		System.out.println("Matrix C = A*B");
		Long start_time;
		start_time = System.nanoTime();
		z = strassen(x,y);
		Long diff_time = System.nanoTime() - start_time;
		//prints matrix C to three decimal places
		//z.print(N, 3);
		System.out.println("Took " + diff_time + " nano seconds");

		
	}

}
