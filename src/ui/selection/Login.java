package ui.selection;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logic.DatabaseConnection;
import ui.admin.AdminUI;
import ui.base.Header;
import javafx.scene.*;
import java.sql.*;

import com.sun.prism.paint.Color;



public class Login extends VBox{
	private String status = "";
	private Label statusLabel = new Label(status);
	private GridPane pane = new GridPane();
	public static String usernameShow = "";
	public Login() {
		this.setPadding(new Insets(10, 10, 10, 10));  
	    this.setSpacing(10);
	    this.setBackground(Background.EMPTY);
	    
	    String style = "-fx-background-color: rgba(200, 200, 200, 0.5);";
	    this.setStyle(style);
		load();
	}
	public void load() {
		Font font30 = new Font(30);
		Font fontS = new Font(13);
		Label header = new Label("YONOTOOL");
		header.setFont(font30);
		
		
		header.setTextFill(javafx.scene.paint.Color.CADETBLUE);
		
		
		Label userLabel = new Label("Username : ");
		userLabel.setFont(fontS);
		TextField userField = new TextField();
		userField.setStyle("-fx-font-size : 13;");
		userLabel.setTextFill(javafx.scene.paint.Color.CADETBLUE);
		userField.setPromptText("username");
		HBox user = new HBox();
		user.getChildren().addAll(userLabel,userField);
		user.setSpacing(10);
		
		Label passwordLabel = new Label("Password   :");
		userLabel.setFont(fontS);
		TextField passwordField = new PasswordField();
		passwordField.setStyle("-fx-font-size : 13;");
		passwordLabel.setTextFill(javafx.scene.paint.Color.CADETBLUE);
		passwordField.setPromptText("password");
		HBox password = new HBox();
		password.getChildren().addAll(passwordLabel,passwordField);
		password.setSpacing(13);
		
		
		
		Button bt = new Button("Login");
		bt.setTextFill(javafx.scene.paint.Color.CADETBLUE);
		
		
		String space = "";
		Label spaceLabel = new Label(space);
		
		pane.add(header,0,1);
		pane.add(user,0,2);
		pane.add(password,0,3);
		pane.add(statusLabel,0,4);
		pane.add(spaceLabel, 0, 5);
		
		
		GridPane.setHalignment(header,HPos.CENTER);
		GridPane.setHalignment(user,HPos.CENTER);
		GridPane.setHalignment(password,HPos.CENTER);
		GridPane.setHalignment(statusLabel,HPos.CENTER);
		//GridPane.setHalignment(bt,HPos.CENTER);
		pane.setVgap(15);
		
		this.setMinHeight(200);
		this.setMaxWidth(500);
		
		this.getChildren().addAll(pane);
		bt.setStyle("-fx-font-size : 2em;");
		EventHandler e = new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	        			
	        			Connection conn = DatabaseConnection.getConnection();
	        			String sql = "select * from account WHERE username = '"+userField.getText()+"';";
	        			Statement stmt = conn.createStatement();
	        			ResultSet rs = stmt.executeQuery(sql);
	        			
	        			
	        			String passwordCheck = "";
	        			while(rs.next()) {
	        				
	        				passwordCheck = rs.getString("password");
	        				
	        				
	        			}
	        			
	        			
	        			if(passwordCheck.equals(passwordField.getText())&& !passwordField.getText().equals("")) {	
	        				if(userField.getText().equals("admin")) {
	        					Stage stage = new Stage();
	        					Scene testScene = new Scene(new AdminUI(stage, 1280, 720), 1280, 720);
	        					stage.setScene(testScene);
	        					stage.setResizable(false);
	        					stage.show();
	        					
	        			}
	        			else {
	        					System.out.println("you login sucessfully!!."); 
	        					usernameShow = userField.getText();
	        					Stage stage = new Stage();
	        					Scene testScene = new Scene(new Header(stage),1280,720);
	        					stage.setScene(testScene);
	        					stage.setResizable(false);
	        					stage.show();
	        			}
	        				
	        				
	        			}
	        			else {
	        				String status2 = "you login fail.";
	        				Label statusLabel2 = new Label(status2);
	        				GridPane.setHalignment(statusLabel2,HPos.CENTER);
	        				statusLabel2.setTextFill(javafx.scene.paint.Color.RED);
	        				pane.add(statusLabel2,0,4);
	        			}
	        			
	        			
	        			
	        			rs.close();
	        			stmt.close();
	        			conn.close();
	        			
	        		}catch(Exception e1) {
	        			System.out.println(e1);
	        		};
	            }
	        }
	    };

		userField.addEventFilter(KeyEvent.KEY_RELEASED, e);
		passwordField.addEventFilter(KeyEvent.KEY_RELEASED, e);
	}

}
