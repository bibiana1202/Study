package programmers;

public class Problem76501 {
	class Solution {
	    public int solution(int[] absolutes, boolean[] signs) {
	        int answer = 0;
	        
	        for(int i = 0 ; i < absolutes.length;i++){
	            if(signs[i] == true){
	                answer = answer + absolutes[i];
	            }
	            else{
	                answer = answer + absolutes[i] * -1 ;
	            }
	        }
	        
	        
	        
	        return answer;
	    }
	}
	
	
	class Solution_1 {
	    public int solution(int[] absolutes, boolean[] signs) {
	        int answer = 0;
	        for (int i=0; i<signs.length; i++)
	            answer += absolutes[i] * (signs[i]? 1: -1);
	        return answer;
	    }
	}
}
