package codingtest_java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class Problem06 {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(solution(5, new int[] {2,1,2,6,2,4,3,3})));
	}

	public static int[] solution(int N , int[] stages) {
		int[] answer = {};
		
		
		// 1. 스테이지별 도전자 수를 구함
		int[] challenger = new int [N+2];
		for(int i = 0 ; i <stages.length;i++) {
			challenger[stages[i]] += 1;
		}
		
		// 2. 스테이지별 실패한 사용자 수 계산
		HashMap<Integer,Double> fails = new HashMap<>();
		double total = stages.length;
		
		// 3. 각 스테이지를 순회하며, 실패율 계산
		for(int i =1 ; i <= N ; i++) {
			if(challenger[i] == 0) { //  4. 도전한 사람이 없는 경우 , 실패율은 0 
				fails.put(i, 0.);
			}
			else {
				fails.put(i, challenger[i]/total); // 5. 실패율을 구함
				total -= challenger[i]; // 6. 다음 스테이지 실패율을 구하기 위해 현재 스테이지의 인원을 뺌
			}
		}
		
		System.out.println(Arrays.toString(challenger));
		System.out.println(fails);
		
		// 7. 실패율이 높은 스테이지 부터 내림차순으로 정렬
		answer = fails.entrySet().stream().sorted((o1,o2)->Double.compare(o2.getValue(),o1.getValue())).mapToInt(HashMap.Entry::getKey).toArray();
		
		return answer;
	}
	
	public static int[] solution_hj(int N, int[] stages) {
        int[] answer = {};
        
        float[] result = new float[N];
        
        for(int i = 0 ; i <N ; i++) {
        	int count = 0;
        	int fail = 0 ;
        	for(int j = 0 ; j < stages.length;j++) {
        		if(stages[j] > i) {			
        			count++;
        			if(stages[j] == i+1) {
        				fail++;
        			}
        		}
        	}
        	result[i]=(float)fail/count;
        }
        System.out.println(Arrays.toString(result));
        
        TreeMap<Integer,Float> result_map = new TreeMap<>();
        for(int i = 0 ; i < result.length; i++) {
        	result_map.put(i+1, result[i]);
        }
        System.out.println(result_map);
        
        List<Entry<Integer,Float>> entryList =new ArrayList<>(result_map.entrySet());
        entryList.sort((o1,o2) -> o2.getValue().compareTo(o1.getValue()));
        
        Map<Integer,Float> result_map_reverse = new LinkedHashMap<>();
        for(Entry<Integer,Float> entry : entryList) {
        	result_map_reverse.put(entry.getKey(),entry.getValue());
        }
        
        System.out.println(result_map_reverse.keySet());

        answer = result_map_reverse.keySet().stream().mapToInt(Integer::intValue).toArray();
        return answer;
    }
	
}

