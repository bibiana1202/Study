package programmers;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Problem12969 {
	class Solution {
	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        int a = sc.nextInt();
	        int b = sc.nextInt();

	        for(int r = 0 ; r < b ; r++){
	            for(int c = 0 ; c<a ; c++){
	                System.out.print('*');        
	            }
	            System.out.println();
	        }
	        
	    }
	}
	
	public class Solution_1 {
	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        int a = sc.nextInt();
	        int b = sc.nextInt();

	        StringBuilder sb = new StringBuilder();
	        IntStream.range(0, a).forEach(s -> sb.append("*"));
	        IntStream.range(0, b).forEach(s -> System.out.println(sb.toString()));
	    }
	}
}
