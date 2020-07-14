package ui.base;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BLBox extends VBox{
	private TextField contactText;
	public BLBox(int width,int height) {
		Label header = new Label("BLBOX");
		contactText = new TextField();
		HBox contactBox = new HBox();
		Label contactLabel = new Label("CONTACT:");
		contactBox.getChildren().addAll(contactLabel,contactText);
		contactBox.setSpacing(5);
		contactBox.setAlignment(Pos.BOTTOM_LEFT);
		
		this.getChildren().addAll(header,contactBox);
		this.setMinSize(width, height);
		
		
	}
	public String getContact() {
		return contactText.getText();
	}
	public void setContact(String contactText) {
		this.contactText.setText(contactText);;
	}

}
