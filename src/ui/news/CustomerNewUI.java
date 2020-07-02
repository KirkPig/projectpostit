package ui.news;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.selection.DatabaseUI;

public class CustomerNewUI extends GridPane {
	public CustomerNewUI() {
		this.setAlignment(Pos.CENTER);
		this.setMinSize(800, 600);
		HBox buttonGang = new HBox();

		Button saveButton = new Button("Save");

		saveButton.setMinSize(60, 40);
		buttonGang.getChildren().addAll(saveButton);
		this.add(buttonGang, 0, 9);

		Label header = new Label("CUSTOMER");
		TextField codeBox = new TextField();
		TextField nameBox = new TextField();
		TextField taxIdBox = new TextField();
		TextArea addressBox = new TextArea();
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

		this.add(header, 0, 1);
		this.add(codeLabel, 0, 2);
		this.add(codeBox, 1, 2);
		this.add(nameLabel, 0, 3);
		this.add(nameBox, 1, 3);
		this.add(taxLabel, 0, 4);
		this.add(taxIdBox, 1, 4);
		this.add(addressLabel, 0, 5);
		this.add(addressBox, 1, 5);
		this.add(teleLabel, 0, 6);
		this.add(teleBox, 1, 6);
		this.add(faxLabel, 0, 7);
		this.add(faxBox, 1, 7);
		this.add(mailLabel, 0, 8);
		this.add(mailBox, 1, 8);
		this.setVgap(10);
	}

}
