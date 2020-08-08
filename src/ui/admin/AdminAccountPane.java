package ui.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gson.Gson;

import database.Permission;
import database.User;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
	private Button btnChangeAccount = new Button("Save");
	private Button btnDeleteAccount = new Button("Delete");
	private HBox editPermissionBox = new HBox();
	private String usernamebf;
	private CheckBox QY = new CheckBox("QY");
	private CheckBox PO = new CheckBox("PO");
	private CheckBox DE = new CheckBox("DE");
	private CheckBox CR = new CheckBox("CR");
	private CheckBox RB = new CheckBox("RB");
	private CheckBox YN = new CheckBox("YN");
	private CheckBox BL = new CheckBox("BL");

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

		editPermissionBox.setAlignment(Pos.CENTER);
		editPermissionBox.setSpacing(5);
		editPermissionBox.setMinHeight(50);

		usernameField.setPromptText("Username");
		passwordField.setPromptText("Password");
		nameField.setPromptText("Name");

		btnAddAccount.setMinWidth(100);

		btnAddAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
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

		editPermissionBox.getChildren().addAll(QY, PO, DE, CR, RB, YN, BL);

		

		

		Button btAdd = new Button("Add new account");
		btAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				getChildren().add(accountAddBox);
			}

		});
		
		Label l = new Label("double click to edit");
		HBox hb = new HBox();
		hb.getChildren().add(l);
		hb.setAlignment(Pos.CENTER);
		
		HBox hb2 = new HBox();
		hb2.getChildren().add(btAdd);
		hb2.setAlignment(Pos.CENTER);

		getChildren().add(accountTable);
		getChildren().add(hb);
		getChildren().add(hb2);

		getChildren().add(accountChangeBox);
		accountChangeBox.setVisible(false);

		getChildren().add(editPermissionBox);
		editPermissionBox.setVisible(false);
		
		updateTable();

		accountTable.setRowFactory(tv -> {
			TableRow<User> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

					User clickedRow = row.getItem();
					usernamebf = clickedRow.getUsername();
					System.out.println(clickedRow.getName());
					usernameField2.setText(clickedRow.getUsername());
					passwordField2.setText(clickedRow.getPassword());
					nameField2.setText(clickedRow.getName());

					accountChangeBox.setVisible(true);
					editPermissionBox.setVisible(true);
					updateCheckBox(usernamebf);

				}
			});
			return row;
		});

	}

	private void addNewAccount() {
		try {
			Connection conn = DatabaseConnection.getConnection();
			User user = new User(usernameField.getText(), passwordField.getText(), nameField.getText());
			String sql = "insert into account values('" + usernameField.getText() + "','" + passwordField.getText()
					+ "','" + nameField.getText() + "','" + user.getGson() + "')";

			PreparedStatement stmt = conn.prepareStatement(sql);
			int x = stmt.executeUpdate();

		} catch (Exception e1) {
			System.out.println(e1);
		}
		;

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

			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String name = rs.getString("name");
				User user = new User(username, password, name);
				accountTable.getItems().add(user);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateCheckBox(String username) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select permission from account WHERE username = '" + username + "';";
			ResultSet rs = stmt.executeQuery(sql);

			Permission permission = null;

			while (rs.next()) {
				permission = gson.fromJson(rs.getString("permission"), Permission.class);
			}
			if (permission == null) {
				System.out.println("sefs");
				return;

			}

			QY.setSelected(permission.QY);
			YN.setSelected(permission.YN);
			PO.setSelected(permission.PO);
			CR.setSelected(permission.CR);
			DE.setSelected(permission.DE);
			RB.setSelected(permission.RB);
			BL.setSelected(permission.BL);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addTable() {
		accountTable.getItems().add(new User(usernameField.getText(), passwordField.getText(), nameField.getText()));

	}

	private void editUser() {
		Permission permission = new Permission(QY.isSelected(), YN.isSelected(), PO.isSelected(), CR.isSelected(),
				DE.isSelected(), RB.isSelected(), BL.isSelected(), true);

		try {
			Connection conn = DatabaseConnection.getConnection();
			
			String sql = "UPDATE account SET username = '" + usernameField2.getText() + "',password = '"
					+ passwordField2.getText() + "',name = '" + nameField2.getText() + "',permission = '"
					+ gson.toJson(permission) + "' WHERE username = '" + usernamebf + "';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int x = stmt.executeUpdate();
			accountChangeBoxClear();
		} catch (Exception e1) {
			System.out.println(e1);
		}
		;

		updateTable();
		accountChangeBox.setVisible(false);
		editPermissionBox.setVisible(false);

	}

	private void deleteUser() {
		try {
			Connection conn = DatabaseConnection.getConnection();
			String sql = "DELETE FROM account WHERE username = '" + usernamebf + "';";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int x = stmt.executeUpdate();
			accountChangeBoxClear();
		} catch (Exception e1) {
			System.out.println(e1);
		}
		;
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