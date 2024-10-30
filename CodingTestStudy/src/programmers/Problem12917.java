package programmers;
import java.util.*;

public class Problem12917 {

	class Solution {
	    public String solution(String s) {
	        String answer = "";
	        
	        String[] s_arr = s.split("");
	        
	       //System.out.println(Arrays.toString(s_arr));
	        
	        Arrays.sort(s_arr,Collections.reverseOrder());
	    
	        //System.out.println(Arrays.toString(s_arr));
	        
	        for(int i = 0 ; i < s_arr.length ; i++){
	            answer +=s_arr[i];
	        }
	        
	        return answer;
	    }
	}
}
