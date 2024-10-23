package programmers;

import java.util.stream.IntStream;

public class Problem87389 {
    public int solution(int n) {
        int answer = 0;
        
        
        for(int i =2 ; i <= n ; i++){
            if(n%i == 1){
                return i;
            }
        }      
        
        
        return answer;
    }
    
    public int solution_1(int n) {
    	return IntStream.range(2, n)
    					.filter(i->n%i==1)
    					.findFirst()
    					.orElse(0);
    }
}
