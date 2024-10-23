package programmers;

import java.util.Arrays;

public class Problem12933 {
	
	public long solution(long n) {
        long answer = 0;
        
        String[] s = String.valueOf(n).split("");
        int[] i_arr = new int[s.length];
        for(int i = 0 ; i < s.length; i++){
            i_arr[i] = Integer.parseInt(s[i]);
        }
        Arrays.sort(i_arr);
        // System.out.println(Arrays.toString(i_arr));


        int d = 1;
        for(int j = 0 ; j <i_arr.length;j++){
            answer = answer + (long)i_arr[j] * d ;
            
            d = d*10;
        }
        
        return answer;
    }
	
	public long solution_1(long n) {
		long answer = 0 ;
		String[] s_arr = String.valueOf(n).split("");
		Arrays.sort(s_arr); // String 도 정렬이 되는구나 ! 오름차순 !
		
		StringBuilder sb = new StringBuilder();
		for(String s : s_arr) {
			sb.append(s); // 문자열 추가 ! 
		}
		
		// String 을 long 타입으로 변경!!!!
		answer = Long.parseLong(sb.reverse().toString()); // StringBulilder의 reverse 라는 메소드가 ! 
		
		
		return answer;
	}
	
	
}
