package programmers;
import java.util.*;

public class Problem12906 {

	public class Solution {
	    public int[] solution(int []arr) {
	        
	        ArrayList<Integer> al = new ArrayList<Integer>();
	        al.add(arr[0]);
	   
	        for(int i=1; i < arr.length;i++){
	            if(arr[i] !=arr[i-1]){
	                al.add(arr[i]);
	            }
	        }
	        int[] answer = new int[al.size()];
	        for(int i = 0 ; i < al.size(); i++){
	            answer[i] = (int)al.get(i);
	        }
	        return answer;
	    }
	}
}