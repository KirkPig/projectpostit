package ui.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gson.Gson;

import database.User;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.DatabaseConnection;

public class AdminAccountPane extends VBox {
	
	private Gson gson = new Gson();
	private TableView<User> accountTable = new TableView<User>();
	private HBox accountAddBox = new HBox();
	private TextField usernameField = new TextField();
	private TextField passwordField = new TextField();
	private TextField nameField = new TextField();
	private Button btnAddAccount = new Button("Add");
	private HBox accountChangeBox = new HBox();
	private TextField usernameField2 = new TextField();
	private TextField passwordField2 = new TextField();
	private TextField nameField2 = new TextField();
	private Button btnChangeAccount = new Button("Edit");
	private Button btnDeleteAccount = new Button("Delete");
	private String usernamebf ;
	@SuppressWarnings("unchecked")
	public AdminAccountPane() {

		TableColumn<User, String> usernameCol = new TableColumn<User, String>("Username");
		usernameCol.setMinWidth(200);
		usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		TableColumn<User, String> passwordCol = new TableColumn<User, String>("Password");
		passwordCol.setMinWidth(200);
		passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

		TableColumn<User, String> nameCol = new TableColumn<User, String>("Name");
		nameCol.setMinWidth(200);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		accountTable.getColumns().addAll(usernameCol, passwordCol, nameCol);
		
		accountAddBox.setAlignment(Pos.CENTER);
		accountAddBox.setSpacing(5);
		accountAddBox.setMinHeight(50);
		
		accountChangeBox.setAlignment(Pos.CENTER);
		accountChangeBox.setSpacing(5);
		accountChangeBox.setMinHeight(50);
		
		usernameField.setPromptText("Username");
		passwordField.setPromptText("Password");
		nameField.setPromptText("Name");
		
		btnAddAccount.setMinWidth(100);
		
		btnAddAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				addNewAccount();
			}
			
		});
		btnChangeAccount.setMinWidth(100);
		btnChangeAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event) {
	
				editUser();
			}
			
		});
		btnDeleteAccount.setMinWidth(100);
		btnDeleteAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event) {
	
				deleteUser();
			}
			
		});
		accountAddBox.getChildren().add(usernameField);
		accountAddBox.getChildren().add(passwordField);
		accountAddBox.getChildren().add(nameField);
		accountAddBox.getChildren().add(btnAddAccount);
		
		accountChangeBox.getChildren().add(usernameField2);
		accountChangeBox.getChildren().add(passwordField2);
		accountChangeBox.getChildren().add(nameField2);
		accountChangeBox.getChildren().add(btnChangeAccount);
		accountChangeBox.getChildren().add(btnDeleteAccount);
		
		
		Label l = new Label("double click to edit");
		HBox hb = new HBox();
		hb.getChildren().add(l);
		hb.setAlignment(Pos.CENTER);
		
		
		
		Button btAdd = new Button("Add new account");
		btAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				getChildren().add(accountAddBox);
			}
			
		});
		HBox hb2 = new HBox();
		hb2.getChildren().add(btAdd);
		hb2.setAlignment(Pos.CENTER);
		
		getChildren().add(accountTable);
		getChildren().add(hb);
		getChildren().add(hb2);
		
		
		
		updateTable();
		
		accountTable.setRowFactory(tv -> {
			TableRow<User> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
		             && event.getClickCount() == 2) {

		            User clickedRow = row.getItem();
		            usernamebf = clickedRow .getUsername();
		            System.out.println(clickedRow .getName());
		            usernameField2.setText(clickedRow .getUsername());
		    		passwordField2.setText(clickedRow .getPassword());
		    		nameField2.setText(clickedRow .getName());
		    		getChildren().add(accountChangeBox);
		        }
		    });
		    return row ;
		});

		

	}
	
	private void addNewAccount() {
		try {
		Connection conn = DatabaseConnection.getConnection();
		User user = new User(usernameField.getText(), passwordField.getText(), nameField.getText());
		String sql = "insert into account values('" + usernameField.getText() + "','" + passwordField.getText() + "','"
				+ nameField.getText() + "','" + user.getGson() + "')";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		int x = stmt.executeUpdate();
		
		
		}
		catch(Exception e1) {
			System.out.println(e1);
		};
		
		addTable();
		accountAddBoxClear();

	}
	private void updateTable() {
		accountTable.getItems().clear();
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from account;";
			ResultSet rs = stmt.executeQuery(sql);
			
			
			while(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String name = rs.getString("name");
				User user = new User (username,password,name);
				accountTable.getItems().add(user);
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void addTable() {
		accountTable.getItems().add(new User(usernameField.getText(), passwordField.getText(),nameField.getText()));
		
	}
	private void editUser() {
		try {
			Connection conn = DatabaseConnection.getConnection();
			User user = new User(usernameField.getText(), passwordField.getText(), nameField.getText());
			String sql = "UPDATE account SET username = '" + usernameField2.getText() + "',password = '"+ passwordField2.getText() +"',name = '"+nameField2.getText() + "'"+"WHERE username = '"+usernamebf+"';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int x = stmt.executeUpdate();
			accountChangeBoxClear();
			}
		catch(Exception e1) {
				System.out.println(e1);
			};
		updateTable();
		getChildren().remove(accountChangeBox);
		
	}
	private void deleteUser() {
		try {
			Connection conn = DatabaseConnection.getConnection();
			String sql = "DELETE FROM account WHERE username = '"+ usernamebf +"';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int x = stmt.executeUpdate();
			accountChangeBoxClear();
			}
		catch(Exception e1) {
				System.out.println(e1);
			};
		updateTable();
	}
	private void accountAddBoxClear() {
		
		
		usernamebf = "";
		usernameField.clear();
		passwordField.clear();
		nameField.clear();
		
	}
	private void accountChangeBoxClear() {
		
		
		
		usernameField2.clear();
		passwordField2.clear();
		nameField2.clear();
		
	}

}
