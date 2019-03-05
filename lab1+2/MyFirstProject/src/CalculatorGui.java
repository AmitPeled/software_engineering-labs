import java.awt.EventQueue;
import java.awt.TextField;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class CalculatorGui {

	private JFrame frame;
	private JTextField displayField;
	private ArrayList<JButton> buttons = new ArrayList<>();
	private static JPanel windowContent;
	private JPanel pl;
	//private boolean isAssign = false;

	String arithmeticExp = new String();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorGui window = new CalculatorGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalculatorGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Calculator");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowContent= new JPanel();
 
    // Set the layout manager for this panel
        BorderLayout bl = new BorderLayout();
        windowContent.setLayout(bl);
 
    //Create the display field and place it in the North area of the window
        displayField = new JTextField(30);
        windowContent.add("North",displayField);
		
		pl = new JPanel ();
        GridLayout gl =new GridLayout(4,3);
        pl.setLayout(gl);
        
        /* Gui shit. */
        String[] operatorsArr = {"*","/","+",".", "-","(",")"};
        ArrayList<String> operators = new ArrayList<>(Arrays.asList(operatorsArr));
        int[] numbers=new int[10];
        for(int i = 1; i < 10; i++) numbers[i-1] = i; numbers[9] = 0;
        
        /*
        for(int i = 0, j = 0; j < operators.size(); i++) {
        	if((i%4 == 3) || ((i-j) > 9)) { 
        		pl.add(new JButton(operators.get(j)));
        		j++;
        		System.out.println("if cond. i = : "+ i+ " j="+j);

        	}
        	else {
        		pl.add(new JButton(String.valueOf(numbers[i-j])));
        		System.out.println("else cond. i = : "+ i + " i-j="+(i-j));
        	}
        }
  */
        for(int num : numbers) {
        	JButton b = new JButton(String.valueOf(num));
        	b.setName(String.valueOf(num));
        	buttons.add(new JButton(String.valueOf(num)));
        }
        for(String op : operators) {
        	JButton b = new JButton(op);
        	b.setName(op);

        	System.out.println("buttonName: " + b.getName());
        	buttons.add(new JButton(op));

        }
        JButton but = new JButton(); but.setName("dgd");
    	System.out.println("newbutton name: " + but.getName());

        
        for(JButton button : buttons) {
        	pl.add(button);
        	button.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				arithmeticExp = arithmeticExp.concat(button.getText());
    				displayField.setText(arithmeticExp);
    				System.out.println("arithExp: "+arithmeticExp +" button name: " + button.getName() +"butText: "+ button.getText());
    			}
        	});
        }
        /* setting the rest operators */ 
        JButton assignButton = new JButton("=");
        assignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arithmeticExp = ArithmeticApp.CalcExpression(arithmeticExp); // need to add a validity check to the expression
				displayField.setText(arithmeticExp);
			}
        });
        pl.add(assignButton);
        JButton cleanButton = new JButton("C");
        cleanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arithmeticExp = ""; // need to add a validity check to the expression
				displayField.setText(arithmeticExp);
			}
        });
        pl.add(cleanButton);
        JButton eraseButton = new JButton("<[x]");
        eraseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!arithmeticExp.isEmpty())
					arithmeticExp = arithmeticExp.substring(0,arithmeticExp.length()-1); // need to add a validity check to the expression
				displayField.setText(arithmeticExp);
			}
        });
        pl.add(eraseButton);
    	
    	System.out.println("newbutton name: " + but.getName());

    	
    	/* need to organize the buttons + color the assign operator and/or the other operators */
    	
	 	windowContent.add("Center",pl);
		 //Create the frame and set its content pane
		frame.setContentPane(windowContent);
		//set the size of the window to be big enough to accomodate all controls.
		frame.pack();
		//Finnaly, display the window
		frame.setVisible(true);  frame.setVisible(true);
	
		
	}

}
