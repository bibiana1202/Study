package programmers;

public class Problem12919 {
	class Solution {
	    public String solution(String[] seoul) {
	        String answer = "";
	        
	        for(int i = 0 ; i < seoul.length; i++){
	            // System.out.println(seoul[i].equals("Kim"));
	            // System.out.println(seoul[i]);
	            if(seoul[i].equals("Kim")){
	                answer = "김서방은 "+i+"에 있다";
	                // System.out.println(answer);
	                break;
	            }
	        }
	        
	        
	        return answer;
	    }
	}
}
