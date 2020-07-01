package ui.news;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ui.selection.DatabaseUI;

public class ProductNewUI extends GridPane {
	public ProductNewUI() {
		super();
		HBox buttonGang = new HBox();

		Button backButton = new Button("Back");
		backButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				backToDatabase();
			}
		});
		Button saveButton = new Button("Save");

		buttonGang.getChildren().addAll(backButton, saveButton);
		
		Label header = new Label("Product");
		TextField desctiprionBox = new TextField();
		TextField unitBox = new TextField();
		Label descriptionLabel = new Label("Description:");
		Label unitLabel = new Label("Unit:");
		this.add(buttonGang, 0, 0);
		this.add(header, 0, 1);
		this.add(descriptionLabel, 0, 2);
		this.add(desctiprionBox, 1, 2);
		this.add(unitLabel, 0, 3);
		this.add(unitBox, 1, 3);
	}

	public void backToDatabase() {
		this.getChildren().clear();
		this.getChildren().add(new DatabaseUI());
	}
}