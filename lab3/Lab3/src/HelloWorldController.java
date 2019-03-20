import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class HelloWorldController {
	boolean sayHellow=true;
	int clickClounter=0;

    @FXML
    private Button helloBtn;

    @FXML
    private TextField helloTF;
    
    @FXML
    private TextField counterTF;

    @FXML
    void sayHello(ActionEvent event) {
    	if(sayHellow)
    		helloTF.setText( "Hello World!" );
    	else
    		helloTF.setText( "" );
    	sayHellow=!sayHellow;
    	clickClounter++;
    	counterTF.setText(clickClounter+"");
    }

}