package codingtest_java;

import java.util.ArrayList;
// https://school.programmers.co.kr/learn/courses/30/lessons/68644
import java.util.Arrays;
import java.util.HashSet;

public class Problem03 {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution(new int[] { 2, 1, 3, 4, 1 })));
		System.out.println(Arrays.toString(solution(new int[] { 5, 0, 2, 7 })));
	}

	public static int[] solution(int[] numbers) {
		// 1. 중복값 제거를 위한 해시셋 생성
		HashSet<Integer> set = new HashSet<>(); 

		// 2. 두 수를 선택하는 모든 경우의 수를 반복문으로 구함
		for (int i = 0; i < numbers.length - 1; i++) {
			for (int j = i + 1; j < numbers.length; j++) {
				// 3. 두 수를 덯나 결과를 해시셋에 추가
				set.add(numbers[i] + numbers[j]);
			}
		}

		// 4. 해시셋의 값을 오름차순 정렬하고 int[] 형태의 배열로 변환하여 반환
		return set.stream().sorted().mapToInt(Integer::intValue).toArray();
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

