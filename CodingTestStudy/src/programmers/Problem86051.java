package programmers;
import java.util.*;
import java.util.stream.IntStream;


public class Problem86051 {
	class Solution {
	    public int solution(int[] numbers) {
	        int answer = 0;
	        
	        Arrays.sort(numbers);
	        HashSet<Integer> hm = new HashSet<>();
	        
	        for(int i = 0 ; i <numbers.length;i++){
	            // System.out.println(numbers[i]);
	            hm.add(numbers[i]);
	        }
	        
	        // System.out.println(hm);
	        // if(hm.contains(1)){
	        //     System.out.println("있음!");
	        // }
	        
	        for (int k =1 ; k <= 9 ; k++){
	            if(!hm.contains(k)){
	                answer += k ;
	            }
	        }
	        
	        return answer;
	    }
	}
	
	class Solution_1 {
	    public int solution(int[] numbers) {
	        int sum = 45;
	        for (int i : numbers) {
	            sum -= i;
	        }
	        return sum;
	    }
	}
	
	class Solution_2 {
	    public int solution(int[] numbers) {
	        return 45-Arrays.stream(numbers).sum();
	    }
	}
	
	class Solution_3 {
	    public int solution(int[] numbers) {
	            return IntStream.range(0, 10)
	            				.filter(i -> Arrays.stream(numbers).noneMatch(num -> i == num))
	            				.sum();
	        }
	}
}
