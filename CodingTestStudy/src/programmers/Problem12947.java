package programmers;

public class Problem12947 {

	
	class Solution {
	    public boolean solution(int x) {
	        boolean answer = true;
	        String[] s_arr = String.valueOf(x).split("");
	        int[] i_arr = new int[s_arr.length];

	        // for(String s:s_arr){
	        //     System.out.println(s); 
	        // }
	        
	        for(int i = 0 ; i < s_arr.length; i++){
	            i_arr[i] = Integer.parseInt(s_arr[i]);
	        }
	        
	        int a = 0;
	        for(int i:i_arr){
	            a += i;
	            
	        }
	        // System.out.println(a);
	        
	        if(x%a == 0) 
	            answer = true;
	        else 
	            answer = false;

	        
	        return answer;
	    }
	}
	
	
	class Solution_1 {
	    public boolean solution(int x) {
	        int sum = String.valueOf(x).chars().map(ch -> ch - '0').sum();
	        return x % sum == 0;
	    }
	}
}
