package main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.CustomerBox;
import ui.GeneralBox;
import ui.QYBox;
import ui.QYselection;
public class CustomTESTER extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
		Scene testScene = new Scene(new QYselection());
		arg0.setTitle("BETA TESTER");
		arg0.setScene(testScene);
		arg0.setResizable(false);
		arg0.show();
	}
}
