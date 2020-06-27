package ui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Login extends VBox{
	public Login() {
		
		Font font30 = new Font(30);
		
		Label header = new Label("Login");
		header.setFont(font30);
		
		
		Label userLabel = new Label("Username : ");
		TextField userField = new TextField();
		HBox user = new HBox();
		user.getChildren().addAll(userLabel,userField);
		user.setSpacing(10);
		
		Label passwordLabel = new Label("Password  :");
		TextField passwordField = new TextField();
		HBox password = new HBox();
		password.getChildren().addAll(passwordLabel,passwordField);
		password.setSpacing(13);
		
		Button bt = new Button("Login");
		
		GridPane pane = new GridPane();
		
		pane.add(header,0,0);
		pane.add(user,0,1);
		pane.add(password,0,2);
		pane.add(bt,0,3);
		
		GridPane.setHalignment(header,HPos.CENTER);
		GridPane.setHalignment(user,HPos.CENTER);
		GridPane.setHalignment(password,HPos.CENTER);
		GridPane.setHalignment(bt,HPos.CENTER);
		pane.setVgap(15);
		
		this.setMinHeight(200);
		this.setMaxWidth(500);
		
		this.getChildren().addAll(pane);
	}
	

}
