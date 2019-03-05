import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import static java.util.Comparator.comparing;

public class ArithmeticApp {
	static public class BraceletPair {
	   public Integer openIndex;
	   public Integer closeIndex;
	   public BraceletPair(){this.openIndex = 0; this.closeIndex=0;}
	   public BraceletPair(int openIndex, int closeIndex){
		   this.openIndex = openIndex;
		   this.closeIndex=closeIndex;
	   }
	}
	/* try1:
	 static String CalcExpression(List<Character> opsList,List<String> valStrings){
		int opsLen = opsList.size(), valsLen = valStrings.size();
		String oper1,oper2;
		for(int i = 0; i < opsLen; i++) {
			if(valStrings.get(i).equals("(")) CalcExpression(opsList, valStrings.subList(i+1,valsLen-1));
			if(valStrings.get(i).equals(")")) break;
			if(opsList.get(i)=='*' || opsList.get(i)=='/') {
				if(valStrings.get(i).equals("(")) CalcExpression(opsList.subList(i+1,opsLen-1), valStrings.subList(i+1,valsLen-1));
				if(valStrings.get(i).equals(")")) break;
				String res = DoOperation(valStrings.get(i),valStrings.get(i+1), opsList.get(i));
				adjust(valStrings,opsList,res,i); // replacing operand1[operation]operand2 expression with corresponding
							// result --> replacing valStrings[i,i+1] to res & removing operation from the list
				i--;
			}
		}
		for(int i = 0; i < opsLen; i++) {
			if(opsList.get(i)=='+' || opsList.get(i)=='-') {
				if(valStrings.get(i).equals("(")) CalcExpression(opsList.subList(i+1,opsLen-1), valStrings.subList(i+1,valsLen-1));
				if(valStrings.get(i).equals(")")) break;
				String res = DoOperation(valStrings.get(i),valStrings.get(i+1), opsList.get(i));
				adjust(valStrings,opsList,res,i);
				i--;
			}
		}
		return  valStrings.get(0);
	}
	*/
	
	static String CalcExpression(String arithmeticExp){ /* need to add a validity check to the expression*/ 
		Stack<Integer> openBracelets=new Stack<>();
		for(int i = 0; i < arithmeticExp.length();) {
			if(arithmeticExp.charAt(i) == '(') {
				System.out.println("before- " +arithmeticExp);
				System.out.println("'('. i = " + i);
				arithmeticExp = arithmeticExp.replace("(","");
				System.out.println("after- " +arithmeticExp);

				openBracelets.push(i);
			}
			else if(arithmeticExp.charAt(i) == ')') {
				System.out.println("')'. i = " + i);
				System.out.println("before- " +arithmeticExp);

				arithmeticExp = arithmeticExp.replace(")","");
				System.out.println("after1- " +arithmeticExp);
				int correspondOpenIndex = openBracelets.pop();
				String internalExpression = arithmeticExp.substring(correspondOpenIndex,i);
				System.out.println("expression sent: " + internalExpression);
				String res = ExecuteSimpleExpression(internalExpression);
				arithmeticExp = arithmeticExp.replace(internalExpression, res);
				System.out.println("returned result=: " + res
				 +" current expression:" + arithmeticExp);

				i = correspondOpenIndex; // all the sub expression is calculated and swapped with the corresponding result.
			}
			else i++;
		}
		return ExecuteSimpleExpression(arithmeticExp);
	}
		
	
	static String ExecuteSimpleExpression(String arithmeticExpression){// there is a simple problem now that and expression like 5*-1 can occur, therefore requires a little ugly handling.
		/* Binary subtraction: operand1[-]operand2. unary: [-]operand1 
		 one solution is that we split first by (only) the binary '-' operators, and then by the rest as usual.  
		 */
		ArrayList<String> splitByBinarySubtract = new ArrayList<String>();
		int j = 0;
		for(int i = 0; i < arithmeticExpression.length(); i++) {
			if(arithmeticExpression.charAt(i) == '-' && // checking if subtraction is'nt unary
					i!=0 && arithmeticExpression.charAt(i-1)<='9'&& arithmeticExpression.charAt(i-1)>='0') {
				splitByBinarySubtract.add(arithmeticExpression.substring(j, i));
				j = i+1;
			}		
		}
		splitByBinarySubtract.add(arithmeticExpression.substring(j, arithmeticExpression.length())); // inserting the last expression
		ArrayList<String> valStrings=new ArrayList<String>();
		for(String subExp: splitByBinarySubtract) {  // spliting every sub expression by the rest of the operators:
			valStrings.addAll(Arrays.asList(subExp.split("\\*|\\+|\\/")));
		}
		ArrayList<Character> opsList = new ArrayList<Character>(); List<Character> ops;
		Character[] operations = {'*','/','-','+'}; ops = Arrays.asList(operations);
		char[] expressionChars = arithmeticExpression.toCharArray();
		char prev_c = 0;
		for(char c : expressionChars) {
			if(ops.contains(c)&& (prev_c<='9'&&prev_c>='0')) opsList.add(c); // before legal binary operation has to be a number  
			prev_c = c;
		}
		for(int i = 0; i < opsList.size(); i++) {
			if(opsList.get(i)=='*' || opsList.get(i)=='/') {
				String res = DoOperation(valStrings.get(i),valStrings.get(i+1), opsList.get(i));
				adjust(valStrings,opsList,res,i); // replacing operand1[operation]operand2 expression with corresponding
							// result --> replacing valStrings[i,i+1] to res & removing operation from the list
				i--;
			}
		}
		for(int i = 0; i < opsList.size(); i++) {
			if(opsList.get(i)=='+' || opsList.get(i)=='-') {
				
				String res = DoOperation(valStrings.get(i),valStrings.get(i+1), opsList.get(i));
				adjust(valStrings,opsList,res,i);
				i--;
			}
		}
		arithmeticExpression = valStrings.get(0);
		return arithmeticExpression;
	}
	static String DoOperation(String oper1Str, String oper2Str, char op) {//returning the result of the operation
		double oper1 = new Double(oper1Str.trim()), oper2 = new Double(oper2Str.trim());
		double res = 0;
		switch(op) {
			case '*':
				res = oper1*oper2;
				break;
			case '/':
				res = oper1/oper2;
				break;
			case '+':
				res = oper1+oper2;
				break;
			case '-':
				res = oper1-oper2;
				break;
			default: System.out.println("Error. unrecognized operation");
		}
		return ""+res;
	}
	public static void adjust(ArrayList<String> valStrings, List<Character> ops, String res, int i) {
		valStrings.set(i, res);
		ops.remove(i);
		valStrings.remove(i+1);
	}

	public static void main(String[] args) {
		System.out.println("Please enter expression: \n");
		
		Scanner in = new Scanner(System.in);
		String arithmeticExpression = in.nextLine().replaceAll(" ", "");
		
		// firstly calculating the most priority operations (multiplication&division) by order, later on the rest.
		
		
		System.out.println("The value of expression "+ arithmeticExpression + " is: = "+CalcExpression(arithmeticExpression));
	}
}
