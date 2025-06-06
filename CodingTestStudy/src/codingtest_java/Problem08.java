package codingtest_java;


import java.util.Stack;

public class Problem08 {
	public static void main(String[] args) {
		System.out.println(solution("(())()"));
		System.out.println(solution(")()("));
	}

	public static boolean solution(String s) {
        boolean answer = true;

        System.out.println(s);
        Stack<String> stack = new Stack<>();
        for(int i = 0 ;  i < s.length(); i++)
        {
        	System.out.println(s.charAt(i));
        	if(s.charAt(i)=='(') {
        		stack.push("(");
        	}
        	else if(s.charAt(i)==')') {
        		if(!stack.isEmpty()) {
        			stack.pop();        			
        		}
        		else
        			return false;
        	}
        	
        }
        if(stack.isEmpty())
        	answer = true;
        else
        	answer = false;
        

        return answer;
	}
	
	
	
}

