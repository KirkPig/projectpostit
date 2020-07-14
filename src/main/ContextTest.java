package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.selection.BLSelection;

public class ContextTest extends Application{
	public void main(String[] args) {
		Stage arg0 = new Stage();
		Scene testScene = new Scene(new BLSelection());
		arg0.setScene(testScene);
		arg0.setResizable(false);
		arg0.show();
		launch(args);
	}
	
	public void start(Stage arg0) throws Exception {
		Scene testScene = new Scene(new BLSelection());
		arg0.setScene(testScene);
		arg0.setResizable(false);
		arg0.show();
	}
}
