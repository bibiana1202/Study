package programmers;

import java.util.Arrays;

public class Problem12944 {

	
	class Solution {
	    public double solution(int[] arr) {
	        double answer = 0;
	        int sum = 0;
	        for(int i = 0 ; i < arr.length; i ++){
	            sum += arr[i];
	        }
	        answer = (double)sum / arr.length;
	        return answer;
	    }
	}
	
	
	public double solution_1(int[] arr) {
		return Arrays.stream(arr).average().getAsDouble();
	}
}
