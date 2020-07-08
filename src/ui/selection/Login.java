package ui.selection;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logic.DatabaseConnection;
import ui.admin.AdminUI;

import java.sql.*;



public class Login extends VBox{
	public String status = "";
	public Label statusLabel = new Label(status);
	
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
		TextField passwordField = new PasswordField();
		HBox password = new HBox();
		password.getChildren().addAll(passwordLabel,passwordField);
		password.setSpacing(13);
		
		
		
		Button bt = new Button("Login");
		
		GridPane pane = new GridPane();
		
		String space = "";
		Label spaceLabel = new Label(space);
		
		pane.add(header,0,0);
		pane.add(user,0,1);
		pane.add(password,0,2);
		pane.add(statusLabel,0,3);
		pane.add(bt,0,4);
		pane.add(spaceLabel,0,5);
		
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
					
					Connection conn = DatabaseConnection.getConnection();
					String sql = "select * from account WHERE username = '"+userField.getText()+"';";
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					
					
					String passwordCheck = "";
					while(rs.next()) {
						
						passwordCheck = rs.getString("password");
						
						
					}
					
					
					if(passwordCheck.equals(passwordField.getText()) ) {	
						if(userField.getText().equals("admin")) {
							Stage stage = new Stage();
							Scene testScene = new Scene(new AdminUI(stage, 1280, 720), 1280, 720);
							stage.setScene(testScene);
							stage.setResizable(false);
							stage.show();
							
						}
						else {
							System.out.println("you login sucessfully!!."); 
							Stage stage = new Stage();
							Scene testScene = new Scene(new BLSelection());
							stage.setScene(testScene);
							stage.setResizable(false);
							stage.show();
						}
						
						
					}
					else {
						System.out.println("you login fail."); 	
						status = "you login fail.";
					}
					
					
					
					rs.close();
					stmt.close();
					conn.close();
					
				}catch(Exception e1) {
					System.out.println(e1);
				};
			}
		};
		
		bt.addEventFilter(MouseEvent.MOUSE_RELEASED, e);
	}
	

}
