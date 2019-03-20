package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class CalculatorController {

    @FXML
    private Button button1;

    @FXML
    private TextField textTF;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Button buttonOpen;

    @FXML
    private Button button0;

    @FXML
    private Button buttonClose;

    @FXML
    private Button buttonPlus;

    @FXML
    private Button buttonMinus;

    @FXML
    private Button buttonMull;

    @FXML
    private Button buttonPoint;

    @FXML
    private Button buttonPoints;

    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonEqual;

    @FXML
    void click0(ActionEvent event) {
    	textTF.setText(textTF.getText()+"0");
    }

    @FXML
    void click1(ActionEvent event) {
    	textTF.setText(textTF.getText()+"1");
    }

    @FXML
    void click2(ActionEvent event) {
    	textTF.setText(textTF.getText()+"2");
    }

    @FXML
    void click3(ActionEvent event) {
    	textTF.setText(textTF.getText()+"3");
    }

    @FXML
    void click4(ActionEvent event) {
    	textTF.setText(textTF.getText()+"4");
    }

    @FXML
    void click5(ActionEvent event) {
    	textTF.setText(textTF.getText()+"5");
    }

    @FXML
    void click6(ActionEvent event) {
    	textTF.setText(textTF.getText()+"6");
    }

    @FXML
    void click7(ActionEvent event) {
    	textTF.setText(textTF.getText()+"7");
    }

    @FXML
    void click8(ActionEvent event) {
    	textTF.setText(textTF.getText()+"8");
    }

    @FXML
    void click9(ActionEvent event) {
    	textTF.setText(textTF.getText()+"9");
    }

    @FXML
    void clickClear(ActionEvent event) {
    	textTF.setText("");
    }

    @FXML
    void clickClose(ActionEvent event) {
    	textTF.setText(textTF.getText()+")");
    }

    @FXML
    void clickEqual(ActionEvent event) {
    	String arithmeticExpression =textTF.getText();
    	String result = ArithmeticApp.CalcExpression(arithmeticExpression);
    	textTF.setText(result);
    }

    @FXML
    void clickMinus(ActionEvent event) {
    	textTF.setText(textTF.getText()+"-");
    }

    @FXML
    void clickMull(ActionEvent event) {
    	textTF.setText(textTF.getText()+"*");
    }

    @FXML
    void clickOpen(ActionEvent event) {
    	textTF.setText(textTF.getText()+"(");
    }

    @FXML
    void clickPlus(ActionEvent event) {
    	textTF.setText(textTF.getText()+"+");
    }

    @FXML
    void clickPoint(ActionEvent event) {
    	textTF.setText(textTF.getText()+".");
    }

    @FXML
    void clickPoints(ActionEvent event) {
    	textTF.setText(textTF.getText()+"/");
    }

}

