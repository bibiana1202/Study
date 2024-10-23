package lv1;

public class Problem12916 {

	class Solution {
	    boolean solution(String s) {
	        boolean answer = true;
	        int pCount = 0;
	        int yCount = 0;
	        s= s.toLowerCase();
	        for(int i = 0 ; i< s.length();i++){
	            if(s.charAt(i) =='p')
	                pCount++;
	            else if(s.charAt(i) =='y')
	                yCount++;
	        }
	        answer = (pCount==yCount) ? true: false;
	        return answer;
	    }
	}
	
	    boolean solution_1(String s) {
	        s = s.toUpperCase();
	
	        return s.chars() 				// char[] 로 stream 생성
	        		.filter( e -> 'P'== e)	// 특정 조건을 만족하는 데이터만 걸러내는데 사용
	        		.count() 
	        	== s.chars()
	        		.filter( e -> 'Y'== e)
	        		.count();
	    }
	    
	    boolean solution_2(String s) {

	        return s.replaceAll("[^yY]", "").length() // 문자열에서 정규식과 일치하는 문자열을 찾아 바꿔주는 메소드
	        	 - s.replaceAll("[^pP]", "").length() == 0 ? true : false;
	    }
		
	
}
