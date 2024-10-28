package programmers;
import java.util.stream.IntStream;

public class Problem70128 {

	class Solution {
	    public int solution(int[] a, int[] b) {
	        int answer = 0;
	        for(int i = 0 ; i <a.length ; i++){
	            answer += a[i] * b[i];
	        }
	        
	        return answer;
	    }
	}
	
	class Solution_1 {
	    public int solution(int[] a, int[] b) {
	        return IntStream.range(0, a.length).map(index -> a[index] * b[index]).sum();
	    }
	}
}
