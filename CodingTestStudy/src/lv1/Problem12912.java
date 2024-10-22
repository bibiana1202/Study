package lv1;

public class Problem12912 {
	class Solution {
	    public long solution(int a, int b) {
	        long answer = 0;
	        
	        if(a<b){
	            for(int i = a ; i <= b ; i++){
	                answer += i ;
	            }
	        }
	        else if(a>b){
	            for(int i = b ; i <= a ; i++){
	                answer += i ;
	            }
	        }
	        else{
	            answer = a;
	        }
	        
	        
	        return answer;
	    }    
	}
	
	class Solution_1 {
	    public long solution(int a, int b) {
	        return sumAtoB(Math.min(a, b), Math.max(b, a));
	    }

	    private long sumAtoB(long a, long b) {
	        return (b - a + 1) * (a + b) / 2;
	    }
	}
	
	class Solution_2 {
		  public long solution(int a, int b) {
		      long answer = 0;
		      for (int i = ((a < b) ? a : b); i <= ((a < b) ? b : a); i++) 
		          answer += i;

		      return answer;
		  }
		}
}
