package chpater05;

import java.util.ArrayList;
import java.util.Arrays;


public class Problem4 {
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
			System.out.println("p 사람 차례! "+p);
			for (int as = 0; as < answers.length; as++) {
				System.out.print("answer: ");
				System.out.println(answers[as]);
				System.out.print("pick: ");
				System.out.println(picks[p][as%picks[p].length]);
				
				if(answers[as] == picks[p][as%picks[p].length]) {
					scores[p]++;
				}
			}
			
		}
		System.out.println(scores[0]);
		System.out.println(scores[1]);
		System.out.println(scores[2]);
		
		int maxScore = Arrays.stream(scores).max().getAsInt();
		
		ArrayList<Integer> answer = new ArrayList<>();
		
		for(int k = 0 ; k < scores.length; k ++) {
			if(scores[k] == maxScore) {
				answer.add(k+1);
			}
		}
		
		
		
		return answer.stream().mapToInt(Integer::intValue).toArray();
		
		
	}
	
	
	private static int[] solution_hj(int[] arr) {
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < arr.length-1; i++) {
			for (int j = i+1; j < arr.length; j++) {
				list.add(arr[i]+arr[j]);
			}
		}
		
		int[] result1 = new int[list.size()];
		for(int i = 0 ; i < result1.length; i++) {
			result1[i]=list.get(i);
		}
		
		// 1. 중복값 제거
		Integer[] result = Arrays.stream(result1).boxed().distinct().toArray(Integer[]::new);
		// 2. 내림차순 정렬
		Arrays.sort(result);
		// int형 배열로 변경 후 반환
		return Arrays.stream(result).mapToInt(Integer::intValue).toArray();
		
		
		
		
	}
}
