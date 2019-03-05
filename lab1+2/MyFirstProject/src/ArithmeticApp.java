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
	
	//calculate mathematical expression
	static String CalcExpression(String arithmeticExp){ /* need to add a validity check to the expression*/ 

		Stack<Integer> openBracelets=new Stack<>();
		for(int i = 0; i < arithmeticExp.length();i++) {
			if(arithmeticExp.charAt(i) == '(') 
				openBracelets.push(i);
			else if(arithmeticExp.charAt(i) == ')') 
			{
				int correspondOpenIndex = openBracelets.pop();
				String internalExpression = arithmeticExp.substring(correspondOpenIndex+1,i);
				//System.out.println("expression sent: " + internalExpression);
				String res = ExecuteSimpleExpression(internalExpression);
				arithmeticExp = arithmeticExp.substring(0,correspondOpenIndex)+res+arithmeticExp.substring(i+1);
				//System.out.println("new exp: "+arithmeticExp );
				i = correspondOpenIndex-1; // all the sub expression is calculated and swapped with the corresponding result.
			}
		}
		return ExecuteSimpleExpression(arithmeticExp);
	}
		
	//calculate expression without (,)
	static String ExecuteSimpleExpression(String arithmeticExpression){
		//separate the expression to values and operators
		ArrayList<String> valStrings=new ArrayList<>();
        ArrayList<Character> opsList=new ArrayList<>();
        int indexStartNumber=-1;
        for(int i=0;i<arithmeticExpression.length();i++)
        {
            char c=arithmeticExpression.charAt(i);
            if((Character.isDigit(c) || c=='.')  && indexStartNumber<0)//start new number
                indexStartNumber=i;
            else if(!(Character.isDigit(c) || c=='.'))//if its an operator
            {
                if(indexStartNumber<0)//if we didn't start a number yet it must be - for negative number
                    indexStartNumber=i;
                else//we finished a  number and on a binary operator
                {
                    valStrings.add(arithmeticExpression.substring(indexStartNumber,i));
                    opsList.add(c);
                    indexStartNumber=-1;
                }
            }
        }
        if(indexStartNumber>=0)
            valStrings.add(arithmeticExpression.substring(indexStartNumber));
        // handle operations * and /, because they have priority
        handleOperations(valStrings,opsList,new ArrayList<>(Arrays.asList('*','/')));
        // handle operations + and -
        handleOperations(valStrings,opsList,new ArrayList<>(Arrays.asList('+','-')));
        arithmeticExpression = valStrings.get(0);
        return arithmeticExpression;
	}
	//calculate the result of operation op on the 2 values
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
    //adjust the valStrings array list to replace the values in i,i+1 with the
    public static void adjust(ArrayList<String> valStrings, List<Character> ops, String res, int i) {
        valStrings.set(i, res);
        ops.remove(i);
        valStrings.remove(i+1);
    }
    //calculate the operators in opsToHandle that in the expression
    public static void handleOperations(ArrayList<String> valStrings, ArrayList<Character> ops,ArrayList<Character> opsToHandle)
    {
        for(int i = 0; i < ops.size(); i++) {
            if(opsToHandle.contains(ops.get(i))) {
                String res = DoOperation(valStrings.get(i),valStrings.get(i+1), ops.get(i));
                adjust(valStrings,ops,res,i); // replacing operand1[operation]operand2 expression with corresponding
                // result --> replacing valStrings[i,i+1] to res & removing operation from the list
                i--;
            }
        }
    }
	public static void main(String[] args) {
		System.out.println("Please enter expression: \n");
		
		Scanner in = new Scanner(System.in);
		String arithmeticExpression = in.nextLine().replaceAll(" ", "");
		
		// firstly calculating the most priority operations (multiplication&division) by order, later on the rest.
		
		double result = new Double(CalcExpression(arithmeticExpression));
		System.out.println("The value of expression "+ arithmeticExpression + " is: = "+String.format( "%.2f",result));
	}
}
