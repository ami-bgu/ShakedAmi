package edu.tabio.Configuration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SubstitutionMatrix {

	
	private final int A =  0;
	private final int T =  1;
	private final int G =  2;
	private final int C =  3;
	private final int U =  4;
	private final int N =  5;
	private final int BLANK =  6;
	
	
	public int getAffineGap_A() {
		return affineGap_A;
	}

	public int getAffineGap_B() {
		return affineGap_B;
	}

	private int affineGap_A = 0;	//starting a new gap
	private int affineGap_B = 0;	//elongation of gap
	
	private int[][] mat = new int[7][7]; 
	
	
	

	public SubstitutionMatrix(String filename) {

		boolean didIReadSubstitutionMatrix = false;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.charAt(0) == '#')		continue;
				if (!didIReadSubstitutionMatrix)
				{
					readSubsMatrix(br);
					didIReadSubstitutionMatrix = true;
				}
				else
				{
					String[] arrA = line.split(" +");
					affineGap_A = Integer.parseInt(arrA[1]);
					
					line = br.readLine();
					String[] arrB = line.split(" +");
					affineGap_B = Integer.parseInt(arrB[1]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private int letterToIndex(char letter)
	{
		switch (letter) {
			case 'A':	return A;
			case 'T':	return T;
			case 'C':	return C;
			case 'G':	return G;
			case ' ':	return BLANK;
		}
		System.err.println("Unknow letter!");
		return -1;
	}
	
	public int score(char letter1, char letter2) {
		return mat[letterToIndex(letter1)][letterToIndex(letter2)];
	}
	
//	private int score(int a, int b) {
//		return mat[a][b];
//	}
	
	
	private void readSubsMatrix(BufferedReader br)	throws IOException
	{
		String line;
		for (int i = 0; i < 7; i++) {
			line = br.readLine();
			String[] arr = line.split(" +");
			for (int j = 1; j < arr.length; j++) {
				mat[i][j-1] = Integer.parseInt(arr[j]);
			}
		}
	}
	
	@Override
	public String toString() {
		//System.out.println(affineGap_B);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				builder.append(mat[i][j]+"\t");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SubstitutionMatrix matrix  = new SubstitutionMatrix("Score.matrix");
		System.out.println(matrix);
		
		
		
	}

}
