package ui.admin;

import com.google.gson.Gson;

import database.User;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminAccountPane extends VBox {
	
	private Gson gson = new Gson();
	private TableView<User> accountTable = new TableView<User>();
	private HBox accountAddBox = new HBox();
	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private TextField nameField = new TextField();
	private Button btnAddAccount = new Button("Add");
	
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
		
		accountAddBox.getChildren().add(usernameField);
		accountAddBox.getChildren().add(passwordField);
		accountAddBox.getChildren().add(nameField);
		accountAddBox.getChildren().add(btnAddAccount);
		
		getChildren().add(accountTable);
		getChildren().add(accountAddBox);
		
	}
	
	private void addNewAccount() {

		User user = new User(usernameField.getText(), passwordField.getText(), nameField.getText());
		String sql = "insert into account values('" + usernameField.getText() + "','" + passwordField.getText() + "','"
				+ nameField.getText() + "','" + gson.toJson(user) + "')";
		
		accountAddBoxClear();

	}
	
	private void accountAddBoxClear() {
		
		usernameField.clear();
		passwordField.clear();
		nameField.clear();
		
	}

}
