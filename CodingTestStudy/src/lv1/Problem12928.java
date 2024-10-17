package lv1;

public class Problem12928 {
    public int solution(int n) {
        int answer = 0;
        
        for(int i =1; i<= n ; i++){
            if(n%i==0)
                answer +=i;
        }
        return answer;
    }
        public int solution_1(int num) {
            int answer = 0;
        for(int i =1 ; i<=num/2;i++){
          if(num%i==0){
            answer+=i;}}


            return answer+num;
        }

}
