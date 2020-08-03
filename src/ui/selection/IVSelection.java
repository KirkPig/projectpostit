package ui.selection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bill.Invoice;
import bill.Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Customer;
import logic.DatabaseConnection;
import ui.news.IVNewUI;

public class IVSelection extends VBox {
	private static TableView<Invoice> table;
	private static TableView<Invoice> table2;
	private static ComboBox<Integer> month;
	private static ComboBox<Integer> year;
	private static ComboBox<String> genre;
	private static TextField search;

	@SuppressWarnings("unchecked")
	public IVSelection() {
		HBox allFunc = new HBox();
		HBox simpleFunc = new HBox();
		HBox moreFunc = new HBox();
		HBox searchBox = new HBox();
		Button switchButton = new Button("Customer");

		month = new ComboBox<Integer>();
		month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		month.getSelectionModel().select(Integer.parseInt(LocalDate.now().toString().substring(5, 7)) - 1);
		month.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				updateIV("");
			}
		});

		year = new ComboBox<Integer>();
		year.getItems().addAll(2563, 2564, 2565, 2566, 2567, 2568, 2569, 2570, 2571, 2572);
		year.setValue(Integer.parseInt(LocalDate.now().toString().substring(0, 4)) + 543);
		year.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				updateIV("");
			}
		});

		Button newButton = new Button("new");
		newButton.setOnMouseClicked((MouseEvent e) -> {
			Stage newStage = new Stage();
			Scene ivnewScene = new Scene(new IVNewUI(newStage));
			newStage.setScene(ivnewScene);
			newStage.show();
		});

		Button editButton = new Button("open/edit");
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (switchButton.getText().equals("Customer")) {
					openIV(table.getItems().get(table.getFocusModel().getFocusedCell().getRow()));
				} else {
					openIV(table2.getItems().get(table2.getFocusModel().getFocusedCell().getRow()));
				}
			}
		});
		Button deleteBtn = new Button("delete");
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (switchButton.getText().equals("Customer")) {
					delete(table.getItems().get(table.getFocusModel().getFocusedCell().getRow()));
				} else {
					delete(table2.getItems().get(table2.getFocusModel().getFocusedCell().getRow()));
				}
			}
		});
		// Button
		search = new TextField();
		search.setPromptText("Search");
		search.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent k) {
				if (k.getCode().equals(KeyCode.ENTER)) {
					updateIV(search.getText());
				}
			}
		});
		genre = new ComboBox<String>();
		genre.getItems().addAll("Code", "Product", "Customer Name", "Creator", "Amount");

		simpleFunc.getChildren().addAll(newButton, editButton, deleteBtn);

		simpleFunc.setSpacing(3);
		moreFunc.getChildren().addAll(new Button("print report"), switchButton, month, year);
		moreFunc.setSpacing(3);
		searchBox.getChildren().addAll(search, genre);

		allFunc.getChildren().addAll(simpleFunc, moreFunc, searchBox);
		allFunc.setSpacing(250);

		this.getChildren().add(allFunc);

		// =============================================================================
		table = new TableView<Invoice>();
		TableColumn<Invoice, String> code = new TableColumn<Invoice, String>("ID");
		code.setMinWidth(60);
		code.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Invoice, String> date = new TableColumn<Invoice, String>("date");
		date.setMinWidth(100);
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<Invoice, String> customerName = new TableColumn<Invoice, String>("Customer Name.");
		customerName.setMinWidth(200);
		customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		TableColumn<Invoice, String> totalAmount = new TableColumn<Invoice, String>("Total Amount");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("valueAfterTaxForTable"));
		totalAmount.setMinWidth(120);
		TableColumn<Invoice, String> creator = new TableColumn<Invoice, String>("Created by");
		creator.setCellValueFactory(new PropertyValueFactory<>("creator"));

		creator.setMinWidth(200);
		TableColumn<Invoice, String> datedue = new TableColumn<Invoice, String>("date due");
		datedue.setMinWidth(100);
		datedue.setCellValueFactory(new PropertyValueFactory<>("dateDue"));

		table.getColumns().addAll(code, date, customerName, totalAmount, datedue, creator);
		table.setMaxHeight(500);
		this.getChildren().add(table);
		this.setSpacing(5);

		table.setRowFactory(tv -> {
			TableRow<Invoice> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					openIV(row.getItem());
				}
			});
			return row;
		});

		table.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				Invoice iv = table.getSelectionModel().getSelectedItem();
				if (iv != null) {
					if (keyEvent.getCode().equals(KeyCode.DELETE)) {
						delete(iv);

					}

				}
			}
		});
//=========================
		table2 = new TableView<Invoice>();
		TableColumn<Invoice, String> codeCol = new TableColumn<Invoice, String>("code");
		codeCol.setMinWidth(20);
		codeCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Invoice, String> dateCol = new TableColumn<Invoice, String>("date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<Invoice, String> descriptionCol = new TableColumn<Invoice, String>("Description");
		descriptionCol.setMinWidth(600);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
		TableColumn<Invoice, String> quantityCol = new TableColumn<Invoice, String>("Quantity");
		quantityCol.setMinWidth(100);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
		TableColumn<Invoice, String> unitCol = new TableColumn<Invoice, String>("Unit");
		unitCol.setMinWidth(100);
		unitCol.setCellValueFactory(new PropertyValueFactory<>("productUnit"));

		TableColumn<Invoice, String> datedue2 = new TableColumn<Invoice, String>("date due");
		datedue2.setMinWidth(100);
		datedue2.setCellValueFactory(new PropertyValueFactory<>("dateDue"));
		table2.getColumns().addAll(codeCol, dateCol, descriptionCol, quantityCol, unitCol, datedue2, creator);
		table2.setRowFactory(tv -> {
			TableRow<Invoice> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					openIV(row.getItem());
				}
			});

			return row;
		});
		table2.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				Invoice iv = table.getSelectionModel().getSelectedItem();
				if (iv != null) {
					if (keyEvent.getCode().equals(KeyCode.DELETE)) {
						delete(iv);

					}

				}
			}
		});

		switchButton.setOnMouseClicked((MouseEvent e) -> {
			if (this.getChildren().contains(table)) {
				this.getChildren().remove(table);
				this.getChildren().add(table2);
				switchButton.setText("Product");

			} else {
				this.getChildren().remove(table2);
				this.getChildren().add(table);
				switchButton.setText("Customer");
			}
		});
		updateIV("");

	}

	public static void updateIV(String search) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from invoice;";
			ResultSet rs = stmt.executeQuery(sql);
			table.getItems().clear();
			table2.getItems().clear();
			while (rs.next()) {
				String id = rs.getString("id");
				String date = rs.getString("date"); // 28-07-2563
				if (Integer.parseInt(date.substring(3, 5)) != month.getValue()
						|| Integer.parseInt(date.substring(6)) != year.getValue()) {
					continue;
				}
				String code = rs.getString("customercode");
				String po = rs.getString("ponum");
				String order = rs.getString("orderby");
				String sale = rs.getString("sales");
				String payment = rs.getString("termofpayment");
				String datedue = rs.getString("datedue");
				Gson gson = new Gson();
				TypeToken<ArrayList<Item>> token = new TypeToken<ArrayList<Item>>() {
				};
				ArrayList<Item> itemList = gson.fromJson(rs.getString("product"), token.getType());
				String sql2 = "select * from customer where code = '" + code + "';";
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sql2);
				rs2.next();
				Customer customer = new Customer(rs2.getString("code"), rs2.getString("name"), rs2.getString("taxid"),
						rs2.getString("address"), rs2.getString("tel"), rs2.getString("fax"), rs2.getString("email"));
				Invoice invoice = new Invoice(id, date, customer, itemList, po, order, payment, datedue, sale,  rs.getString("user"));
				boolean addToTable = false;
				if (genre.getValue() != null && !search.isEmpty()) {
					switch (genre.getValue()) {
					case "Code":
						addToTable = id.contains(search);
						break;
					case "Product":
						for (Item item : itemList) {
							if (item.getCode().contains(search) || item.getDescription().contains(search)) {
								addToTable = true;
								break;
							}
							try {
								Double searchNum = Double.parseDouble(search);
								if (searchNum == item.getItemQuantity() || searchNum == item.getPrice()) {
									addToTable = true;
								}
							} catch (NumberFormatException nfe) {
								continue;
							}
						}
						break;
					case "Customer Name":
						addToTable = customer.getName().contains(search);
						break;
					case "Creator":

						// TODO create getting user name
						break;
					case "Amount":
						try {
							Double searchNum = Double.parseDouble(search);
							addToTable = (searchNum == invoice.getValueAfterTax());
						} catch (NumberFormatException nfe) {
							break;
						}
						break;
					default:
						break;
					}
				} else {
					addToTable = true;
				}
				if (addToTable) {

					table.getItems().add(invoice);
					table2.getItems().add(invoice);

				}
				stmt2.close();
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void openIV(Invoice iv) {
		Stage newStage = new Stage();
		Scene ivnewScene = new Scene(new IVNewUI(newStage, iv));
		newStage.setScene(ivnewScene);
		newStage.show();
	}

	public static void delete(Invoice iv) {
		Connection conn;
		try {
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();

			String sql = "DELETE from invoice WHERE id ='" + iv.getId() + "'";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
			updateIV("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
