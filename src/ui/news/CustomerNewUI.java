package ui.news;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.selection.DatabaseUI;

public class CustomerNewUI extends GridPane {
	public CustomerNewUI() {
		this.setAlignment(Pos.CENTER);

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
		this.getChildren().add(buttonGang);

		Label header = new Label("CUSTOMER");
		TextField codeBox = new TextField();
		TextField nameBox = new TextField();
		TextField taxIdBox = new TextField();
		TextField addressBox = new TextField();
		TextField teleBox = new TextField();
		TextField faxBox = new TextField();
		TextField mailBox = new TextField();
		
		Label codeLabel = new Label("CODE:");
		Label nameLabel = new Label("NAME:");
		Label taxLabel = new Label("TAX ID:");
		Label addressLabel = new Label("ADDRESS:");
		Label teleLabel = new Label("TELE:");
		Label faxLabel = new Label("FAX:");
		Label mailLabel = new Label("EMAIL:");
		
		this.add(header, 0, 0);
		this.add(codeLabel, 0, 1);
		this.add(codeBox, 1, 1);
		this.add(nameLabel, 0, 2);
		this.add(nameBox, 1, 2);
		this.add(taxLabel, 0, 3);
		this.add(taxIdBox, 1, 3);
		this.add(addressLabel, 0, 4);
		this.add(addressBox, 1, 4);
		this.add(teleLabel, 0, 5);
		this.add(teleBox, 1, 5);
		this.add(faxLabel, 0, 6);
		this.add(faxBox, 1, 6);
		this.add(mailLabel, 0, 7);
		this.add(mailBox, 1, 7);
	}
	
	public void backToDatabase() {
		this.getChildren().clear();
		this.getChildren().add(new DatabaseUI());
	}
}
