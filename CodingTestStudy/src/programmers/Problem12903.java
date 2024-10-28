package programmers;

public class Problem12903 {
    public String solution(String s) {
        String answer = "";
        
        if(s.length()%2 == 0){
            // System.out.println(s.substring(s.length()/2-1,s.length()/2+1));
            answer = s.substring(s.length()/2-1,s.length()/2+1);
        }
        else
        {
            // System.out.println(s.substring(s.length()/2,s.length()/2+1));
            answer =s.substring(s.length()/2,s.length()/2+1);
        }
        
        
        return answer;
    }
}
