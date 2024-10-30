package programmers;

public class Problem77884 {

	
	class Solution {
	    public int solution(int left, int right) {
	        int answer = 0;
	        for(int soo =left ; soo <= right ; soo++){
	            // 약수 인지?
	            int yaksoo_count = 0;        
	            for(int r = 1 ; r <= soo; r++){
	                if(soo%r == 0){
	                   yaksoo_count++;
	                }
	            }

	            if(yaksoo_count%2==0)
	                answer += soo;
	            else
	                answer -= soo;
	        }
	                
	        
	        return answer;
	    }
	}
	
	class Solution_1 {
	    public int solution(int left, int right) {
	        int answer = 0;

	        for (int i=left;i<=right;i++) {
	            //제곱수인 경우 약수의 개수가 홀수
	            if (i % Math.sqrt(i) == 0) {
	                answer -= i;
	            }
	            //제곱수가 아닌 경우 약수의 개수가 짝수
	            else {
	                answer += i;
	            }
	        }

	        return answer;
	    }
	}
}
