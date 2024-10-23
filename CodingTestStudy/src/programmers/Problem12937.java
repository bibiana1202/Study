package programmers;

public class Problem12937 {

	
	class Solution {
	    public String solution(int num) {
	        String answer = "";
	        if(num%2 ==0)
	            answer ="Even";
	        else
	            answer="Odd";
	        return answer;
	    }
	}
	
	class Solution_1 {
	    public String solution(int num) {
	         return (num % 2 == 0) ? "Even" : "Odd";
	    }
	}

}
