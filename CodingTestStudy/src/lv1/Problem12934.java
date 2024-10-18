package lv1;

public class Problem12934 {
    public long solution(long n) {
        long answer = -1;
        
        for(long i = 1 ; i <= n ; i ++){
            if(n/i == i && n%i == 0)
            {
                answer = (i+1)*(i+1);
                break; 
            }
               
        }
        
        return answer;
    }
    
    
    public long solution_1(long n) {
        if (Math.pow((int)Math.sqrt(n), 2) == n) {
              return (long) Math.pow(Math.sqrt(n) + 1, 2);
          }
          return -1;
    }
}
