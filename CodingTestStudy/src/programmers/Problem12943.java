package programmers;

public class Problem12943 {
	class Solution {
	    public int solution(int num) {
	        long n = num;
	        if(n == 1)
	            return 0 ;
	        
	        for(int i = 0 ; i < 500 ; i ++){
	            if(n%2==0){
	                n = n/ 2;
	            }
	            else{
	                n = n *3 + 1;
	            }
	            
	            // System.out.println(n);
	            if(n == 1)
	            {
	                return i+1;
	            }
	        }        
	        
	        return -1;
	    }
	}
}
