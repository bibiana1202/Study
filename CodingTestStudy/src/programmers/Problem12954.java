package programmers;

import java.util.stream.LongStream;

public class Problem12954 {
	public static void main(String[] args) {
		System.out.println(solution_1(2,5).toString());
	}
	
	
    public long[] solution(int x, int n) {
        long[] answer = new long[n];
        
        answer[0] = x;
        for(int i = 1; i < n ; i++){
            answer[i]= answer[i-1] + x;
        }
        
        
        return answer;
    }

	
	
	  public static long[] solution_1(int x, int n) {
		  System.out.println(LongStream.iterate(x, i->i+x).limit(n).toString());
	      return LongStream.iterate(x, i->i+x).limit(n).toArray();
	  }
	
	
}
