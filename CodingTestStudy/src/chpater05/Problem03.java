package chpater05;
import java.util.ArrayList;
// https://school.programmers.co.kr/learn/courses/30/lessons/68644
import java.util.Arrays;


public class Problem03 {
	public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{2,1,3,4,1})));
        System.out.println(Arrays.toString(solution(new int[]{5,0,2,7})));
	}
	
	private static int[] solution(int[] arr) {
		
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