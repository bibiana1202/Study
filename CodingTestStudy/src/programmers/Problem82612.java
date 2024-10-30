package programmers;

public class Problem82612 {
	class Solution {
	    public long solution(int price, int money, int count) {
	        long answer = -1;
	        long use_money = 0;
	        
	        for(int i = 1 ; i <= count ; i++){
	            use_money += (long)price * i;
	        }
	        // System.out.println(use_money);
	        
	        if((long)money >= use_money)
	            answer = 0;
	        else
	            answer = use_money - (long)money;
	        
	        return answer;
	    }
	}
}
