package codingtest_java;

import java.util.ArrayList;
import java.util.Arrays;


public class Problem05 {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution(new int[][] {{1,4},{3,2},{4,1}}, new int[][] {{2,3},{4,2}})));
	}

    public static int[][] solution(int[][] arr1, int[][] arr2) {
        int[][] answer = new int [arr1.length][arr2[0].length];
        
        for(int row1 = 0 ; row1 < arr1.length ; row1++) {
        	for(int col2 = 0 ; col2 < arr2[0].length;col2++) {
        		System.out.println("["+row1+","+col2+"]");
        		int value =0;
        		for(int row2 = 0 ; row2 < arr2.length;row2++) {
        			System.out.print(arr1[row1][row2]);
        			System.out.print(arr2[row2][col2]);
        			System.out.println();
        			value += arr1[row1][row2] * arr2[row2][col2] ; 
        		}
        		answer[row1][col2] = value;
        		System.out.println(value);
        	}
        }
        
        System.out.println(answer);
        return answer;
    }
	
}

