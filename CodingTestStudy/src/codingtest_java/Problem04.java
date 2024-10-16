package codingtest_java;

import java.util.ArrayList;
import java.util.Arrays;


public class Problem04 {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution(new int[] { 1,2,3,4,5})));
		System.out.println(Arrays.toString(solution(new int[] { 1,3,2,4,2})));
	}

	public static int[] solution(int[] answers) {
		
		
		int [][] picks = new int[][] {
										{1,2,3,4,5},
										{2,1,2,3,2,4,2,5},
										{3,3,1,1,2,2,4,4,5,5}
										};
		int [] scores = new int[3];
			
		for(int p = 0 ; p < 3 ; p++) {
			for (int as = 0; as < answers.length; as++) {
				
				if(answers[as] == picks[p][as%picks[p].length]) {
					scores[p]++;
				}
			}
			
		}

		
		int maxScore = Arrays.stream(scores).max().getAsInt();
		
		ArrayList<Integer> answer = new ArrayList<>();
		
		for(int k = 0 ; k < scores.length; k ++) {
			if(scores[k] == maxScore) {
				answer.add(k+1);
			}
		}
		
		
		
		return answer.stream().mapToInt(Integer::intValue).toArray();
		
		
	}
	
}

