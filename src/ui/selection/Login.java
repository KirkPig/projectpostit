package ui.selection;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.sql.*;



public class Login extends VBox{
	public Login() {
		
		
		load();
	}
	public void load() {
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
		

		EventHandler<MouseEvent> e = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					//Connection conn = DriverManager.getConnection("jdbc:mysql://103.253.72.156/yonotool_app","yonotool","2aF::on0T8E6oY");
					//PreparedStatement ps = conn.prepareStatement("insert into user(username,password) value(?,?);");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","000000");
					PreparedStatement ps = conn.prepareStatement("insert into user(username,password) value(?,?);");
					
					ps.setString(1, userField.getText());
					ps.setString(2, passwordField.getText());
					int x=ps.executeUpdate();
					if (x>0) {
						System.out.println("Register done sucessfully...");
					}
					else {
						System.out.println("Register Fail...");
					}
				}catch(Exception e1) {
					System.out.println(e1);
				};
			}
		};
		
		bt.addEventFilter(MouseEvent.MOUSE_RELEASED, e);
	}
	

}
