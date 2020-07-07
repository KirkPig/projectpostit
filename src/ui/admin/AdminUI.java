package ui.admin;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminUI extends VBox {
	
	private Stage stage;
	private AdminAccountPane adminAccountPane = new AdminAccountPane();
	
	public AdminUI(Stage stage, double width, double height) {
		
		this.setStage(stage);
		setWidth(width);
		setHeight(height);
		
		stage.setTitle("ProjectPostIt: Admin Panel");
		
		HBox tab = new HBox();
		tab.setMinHeight(50);
		Button btnAccount = new Button("Account");
		btnAccount.setPrefSize(200, 50);
		tab.getChildren().add(btnAccount);
		
		/*
		 * Account Tab
		 */
		
		
		getChildren().add(tab);
		getChildren().add(adminAccountPane);
		
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
