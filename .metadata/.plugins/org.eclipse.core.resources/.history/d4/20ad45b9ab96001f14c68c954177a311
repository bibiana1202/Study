package programmers;

public class Problem12918 {
	class Solution {
	    public boolean solution(String s) {
	        boolean answer = true;
	        
	        for(int i = 0 ; i <s.length();i++){
	            // System.out.println(s.charAt(i));           
	            if(!Character.isDigit(s.charAt(i)) || s.length() != 4 && s.length() != 6){
	                answer = false;
	                break;
	            }
	        }
	        
	        
	        return answer;
	    }
	}
	
	class Solution_1 {
		  public boolean solution(String s) {
		        if (s.length() == 4 || s.length() == 6) return s.matches("(^[0-9]*$)");
		        return false;
		  }
		}
}
