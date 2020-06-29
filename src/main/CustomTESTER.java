package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.BLSelection;
import ui.CustomerBox;
import ui.DESelection;
import ui.DatabaseUI;
import ui.GeneralBox;
import ui.POSelection;
import ui.QYBox;
import ui.QYSelection;


public class CustomTESTER extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub


		CustomerBox a = new CustomerBox(400, 200);
		GeneralBox general = new GeneralBox(100, 200);
		QYBox other = new QYBox(100, 200);
		VBox left = new VBox();

		left.getChildren().addAll(general, other);
		HBox upper = new HBox();
		upper.getChildren().addAll(left, a);
		

		Scene testScene = new Scene(new  QYSelection());
	


		arg0.setScene(testScene);
		arg0.setFullScreen(true);
		arg0.setResizable(false);
		arg0.show();
	}
}
