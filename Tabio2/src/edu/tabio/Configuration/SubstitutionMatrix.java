package edu.tabio.Configuration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SubstitutionMatrix {


	public int getAffineGap_A() {
		return affineGap_A;
	}

	public int getAffineGap_B() {
		return affineGap_B;
	}

	private int affineGap_A = 1;	//starting a new gap
	private int affineGap_B = 1;	//elongation of gap
	
	private int[][] mat = null;
	
	
	public SubstitutionMatrix(String filename) {

		boolean didIReadSubstitutionMatrix = false;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!didIReadSubstitutionMatrix)
				{
					readSubsMatrix(br);
					didIReadSubstitutionMatrix = true;
				}
				else
				{
					if (line.charAt(0) == '#')		continue;

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
	
	private int getIndexByLetter(char c, boolean isHorizontal){
		if (c=='_' || c==' ') c='*';
		
		char[] array = null;
		
		array = isHorizontal?horizontalLetters:verticalLetters;
		
		for (int i = 0; i < array.length; i++) {
			if (array[i]==c)	return i;
		}
		c='N';
		for (int i = 0; i < array.length; i++) {
			if (array[i]==c)	return i;
		}
		System.err.println("Cant find letter in substitution matrix");
		System.exit(0);
		return -1;
	}
	
	public int score(char letter1, char letter2) {
		return mat[getIndexByLetter(letter1,false)][getIndexByLetter(letter2,true)];
	}
	

	private char horizontalLetters[];
	private char verticalLetters[];

	
	private void readSubsMatrix(BufferedReader br)	throws IOException
	{
		String line;

		while ((line = br.readLine()) != null && line.charAt(0) == '#') {}

		String[] arr = line.trim().split(" +");
		horizontalLetters = new char[arr.length];
		for (int i = 0; i < horizontalLetters.length; i++) {
			horizontalLetters[i] = arr[i].charAt(0);
		}
		List<String> rows = new ArrayList<>();
		
		while ((line = br.readLine()) != null && line.charAt(0) != '#') {
			rows.add(line);
		}

		verticalLetters = new char[rows.size()];

		mat = new int[verticalLetters.length][horizontalLetters.length];
		
		for (int i = 0; i < verticalLetters.length; i++) {
			arr = rows.get(i).trim().split(" +");
			verticalLetters[i] = arr[0].charAt(0);
			for (int j = 0; j < horizontalLetters.length; j++) {
				mat[i][j] = Integer.parseInt(arr[j+1]);
			}
		}
		
	}
	
	@Override
	public String toString() {
		//System.out.println(affineGap_B);
		StringBuilder builder = new StringBuilder();

		builder.append("First Row is: ");
		for (char c : horizontalLetters) {
			builder.append(c+" ");
		}
		builder.append("\nFirst Col is: ");
		for (char c : verticalLetters) {
			builder.append(c+" ");
		}
		builder.append("\n-----------------------------------------------------\n");
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				builder.append(mat[i][j]+"\t");
			}
			builder.append("\n");
		}
		builder.append("-----------------------------------------------------");
		return builder.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SubstitutionMatrix matrix  = new SubstitutionMatrix("Fake.matrix");
		System.out.println(matrix);
		System.out.println(matrix.score('A', 'A'));
	}

	public Integer similarity(String a, String b, int t){
		int punish = 0;
		for (int i = 0; i < a.length(); i++) {
			punish = punish + score(a.charAt(i), b.charAt(i));
		}
		if (punish >= t) return punish;
		else return null;
	}
	
}
