package programmers;
import java.util.*;

public class Problem12935 {
	class Solution {
	    public int[] solution(int[] arr) {
	        ArrayList<Integer> al = new ArrayList<Integer>();
	        
	        if(arr.length==1){
	            al.add(-1);
	        }
	        else{
	            int min = Arrays.stream(arr).min().getAsInt();
	            for(int i = 0;  i < arr.length ; i++){
	                if(arr[i] != min){
	                    al.add(arr[i]);
	                }
	            }
	        }
	        
	        int[] answer= new int[al.size()];
	        for(int i = 0 ; i <answer.length;i++){
	            answer[i]=al.get(i);
	        }
	        return answer;
	    }
	}
	
	class Solution_1 {
		  public int[] solution(int[] arr) {
		      if (arr.length <= 1) return new int[]{ -1 };
		      int min = Arrays.stream(arr).min().getAsInt();
		      return Arrays.stream(arr).filter(i -> i != min).toArray();
		  }
	}
	
	class Solution_2 {
		  public int[] solution(int[] arr) {
		      if(arr.length == 1){
		          int[] answer = {-1};
		          return answer;
		      }

		      int[] answer = new int[arr.length-1];
		      int minIndex=0;

		      for(int i=0;i<arr.length;i++){
		          if(arr[minIndex]>arr[i]){
		              minIndex = i;
		          }
		      }
		      for(int i=minIndex+1;i<arr.length;i++){
		          arr[i-1] = arr[i];
		      }
		      for(int i=0;i<answer.length;i++){
		          answer[i] = arr[i];
		      }
		      return answer;
		  }
	}
	
	
}
