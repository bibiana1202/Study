package programmers;

public class Problem12931 {

	
	public class Solution {
	    public int solution(int n) {
	        int answer = 0;

	        for(int i = 10 ; ;){
			    
	            int mok = n/i;
	            int na  = n%i;
	            answer += na/(i/10);
	            if(mok<10){
	                answer += mok;
	                break;
	            }
	                
	            i=i*10;
	        }
	        return answer;
	    }
	    
	    public int solution_1(int n) {
	        int answer = 0;

	        while(true){
	            answer+=n%10;
	            if(n<10)
	                break;

	            n=n/10;
	        }

	        return answer;
	    }
	    
	    public int solution_2(int n) {
	        int answer = 0;
	        String[] array = String.valueOf(n).split(""); // String 으로 형변환
	        for(String s : array){
	            answer += Integer.parseInt(s); //String 의 숫자를 int 타입으로 변환
	        }
	        return answer;
	    }
	    
	    public int solution_3(int n) {
	        int answer = 0;

	        String str = Integer.toString(n);
	        for (int i = 0; i < str.length(); i++) {
	            answer += Integer.parseInt("" + str.charAt(i)); // String의 문자열에서 char 타입으로 변환
	        }

	        return answer;
	    }
	}
	
	
	
	
}
