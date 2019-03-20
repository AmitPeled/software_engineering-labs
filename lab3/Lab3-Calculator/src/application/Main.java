package application;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	 @Override
	 public void start(Stage primaryStage) throws IOException {

	 // constructing our scene
	 URL url = getClass().getResource("CalculatorFX.fxml");
	 AnchorPane pane = FXMLLoader.load( url );
	 Scene scene = new Scene( pane );

	 // setting the stage
	 primaryStage.setScene( scene );
	 primaryStage.setTitle( "Calculator" );
	 primaryStage.show();

	 }
	public static void main(String[] args) {
	launch(args);
	}
}
