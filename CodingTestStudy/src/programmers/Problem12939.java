package programmers;
import java.util.*;

public class Problem12939 {
	class Solution {
	    public String solution(String s) {
	        String answer = "";
	        String[] s_arr = s.split(" ");
	        int[] i_arr = new int[s_arr.length];
	        
	        for(int i = 0 ; i < s_arr.length ; i++){
	            i_arr[i]=Integer.parseInt(s_arr[i]);
	        }
	        
	        // Arrays.sort(i_arr);
	        // answer = i_arr[0] +" " + i_arr[i_arr.length-1];
	        int min = Arrays.stream(i_arr)
	                        .min()
	                        .getAsInt();
	        int max = Arrays.stream(i_arr)
	                        .max()
	                        .getAsInt();
	        
	        answer = min + " " + max;

	        return answer;
	    }
	}
}
