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
		HBox code = new HBox();
		code.getChildren().addAll(codeLabel,codeBox);
		code.setSpacing(13);
		code.setAlignment(Pos.BOTTOM_LEFT);
		
		Label nameLabel = new Label("NAME:");
		HBox name = new HBox();
		name.getChildren().addAll(nameLabel,nameBox);
		name.setSpacing(10);
		name.setAlignment(Pos.BOTTOM_LEFT);
		
		Label taxLabel = new Label("TAX ID:");
		HBox tax = new HBox();
		tax.getChildren().addAll(taxLabel,taxIdBox);
		tax.setSpacing(10);
		tax.setAlignment(Pos.BOTTOM_LEFT);
		
		Label addressLabel = new Label("ADDRESS:");
		HBox address = new HBox();
		address.getChildren().addAll(addressLabel,addressBox);
		address.setSpacing(10);
		address.setAlignment(Pos.BOTTOM_LEFT);
		
		Label teleLabel = new Label("TELE:");
		HBox tele = new HBox();
		tele.getChildren().addAll(teleLabel,teleBox);
		tele.setSpacing(10);
		tele.setAlignment(Pos.BOTTOM_LEFT);
		
		Label faxLabel = new Label("FAX:");
		HBox fax = new HBox();
		fax.getChildren().addAll(faxLabel,faxBox);
		fax.setSpacing(10);
		fax.setAlignment(Pos.BOTTOM_LEFT);
		
		Label mailLabel = new Label("EMAIL:");
		HBox mail = new HBox();
		mail.getChildren().addAll(mailLabel,mailBox);
		mail.setSpacing(10);
		mail.setAlignment(Pos.BOTTOM_LEFT);
	}
	
	public void backToDatabase() {
		this.getChildren().clear();
		this.getChildren().add(new DatabaseUI());
	}
}
