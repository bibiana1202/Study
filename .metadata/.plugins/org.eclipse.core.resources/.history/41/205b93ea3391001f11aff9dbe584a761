package lv1;
import java.util.Arrays;
import java.util.ArrayList;

public class Problem12910 {

	class Solution {
	    public int[] solution(int[] arr, int divisor) {
	        int[] answer = {};
	        
	        Arrays.sort(arr);
	        // System.out.println(Arrays.toString(arr));
	        
	        ArrayList<Integer> al = new ArrayList<>();
	        
	        for(int i = 0 ; i <arr.length ; i ++){
	            if(arr[i]%divisor == 0){
	                al.add(arr[i]);
	            }
	        }
//	         System.out.println(al);

//	         System.out.println(al.size());
	        
	        if(al.size() != 0)
	        {
	             int[] answer1 = new int[al.size()];
	             for(int i = 0 ; i < al.size();i++){
	                 answer1[i]=al.get(i);
	             }   
	            answer = answer1;
	        }
	        else{
	            int[] answer2 = {-1};
	            answer = answer2;
	        }
	        return answer;
	    }
	}
	
	
	class Solution_1 {
		  public int[] solution(int[] arr, int divisor) {
		          int[] answer = Arrays.stream(arr).filter(factor -> factor % divisor == 0).toArray();
		          if(answer.length == 0) answer = new int[] {-1};
		          java.util.Arrays.sort(answer);
		          return answer;
		  }
		}
}
