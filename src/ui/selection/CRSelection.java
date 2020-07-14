package ui.selection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bill.CreditNote;
import bill.Invoice;
import bill.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import ui.news.CRNewUI;

public class CRSelection extends VBox {
	private static TableView<CreditNote> table;
	private static TableView<CreditNote> table2;
	private static ComboBox<Integer> month;
	private static ComboBox<Integer> year;
	private static ComboBox<String> genre;
	private static TextField search;

	@SuppressWarnings("unchecked")
	public CRSelection() {
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
				updateCR("");
			}
		});

		year = new ComboBox<Integer>();
		year.getItems().addAll(2563, 2564, 2565, 2566, 2567, 2568, 2569, 2570, 2571, 2572);
		year.setValue(Integer.parseInt(LocalDate.now().toString().substring(0, 4)) + 543);
		year.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				updateCR("");
			}
		});

		Button newButton = new Button("new");
		newButton.setOnMouseClicked((MouseEvent e) -> {
			Stage newStage = new Stage();
			Scene crnewScene = new Scene(new CRNewUI(newStage));
			newStage.setScene(crnewScene);
			newStage.show();
		});

		Button editButton = new Button("open/edit");
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (switchButton.getText().equals("Customer")) {
					openCR(table.getItems().get(table.getFocusModel().getFocusedCell().getRow()));
				} else {
					openCR(table2.getItems().get(table2.getFocusModel().getFocusedCell().getRow()));
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
		search.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				updateCR(search.getText());
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
		table = new TableView<CreditNote>();
		TableColumn<CreditNote, String> code = new TableColumn<CreditNote, String>("ID");
		code.setMinWidth(60);
		code.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<CreditNote, String> date = new TableColumn<CreditNote, String>("date");
		date.setMinWidth(100);
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<CreditNote, String> customerName = new TableColumn<CreditNote, String>("Customer Name.");
		customerName.setMinWidth(200);
		customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		TableColumn<CreditNote, String> totalAmount = new TableColumn<CreditNote, String>("Old Amount");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("valueOldForTable"));
		totalAmount.setMinWidth(120);
		TableColumn<CreditNote, String> creator = new TableColumn<CreditNote, String>("Created by");
		creator.setCellValueFactory(new PropertyValueFactory<>("creator"));
		creator.setMinWidth(200);
		TableColumn<CreditNote, String> invoiceid = new TableColumn<CreditNote, String>("Invoice ID");
		invoiceid.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
		invoiceid.setMinWidth(200);
		TableColumn<CreditNote, String> valueAfterTax = new TableColumn<CreditNote, String>("New Amount");
		valueAfterTax.setCellValueFactory(new PropertyValueFactory<>("valueAfterTaxForTable"));
		valueAfterTax.setMinWidth(200);
		table.getColumns().addAll(code, date, customerName, invoiceid, totalAmount, valueAfterTax, creator);
		table.setMaxHeight(500);
		this.getChildren().add(table);
		this.setSpacing(5);

		table.setRowFactory(tv -> {
			TableRow<CreditNote> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					openCR(row.getItem());
				}
			});
			return row;
		});

		table.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				CreditNote cr = table.getSelectionModel().getSelectedItem();
				if (cr != null) {
					if (keyEvent.getCode().equals(KeyCode.DELETE)) {
						delete(cr);

					}

				}
			}
		});
//=========================
		table2 = new TableView<CreditNote>();
		TableColumn<CreditNote, String> codeCol = new TableColumn<CreditNote, String>("code");
		codeCol.setMinWidth(20);
		codeCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<CreditNote, String> dateCol = new TableColumn<CreditNote, String>("date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<CreditNote, String> descriptionCol = new TableColumn<CreditNote, String>("Description");
		descriptionCol.setMinWidth(600);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("productName"));

		TableColumn<CreditNote, String> invoiceid2 = new TableColumn<CreditNote, String>("Invoice ID");
		invoiceid2.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
		invoiceid2.setMinWidth(100);
		TableColumn<CreditNote, String> valueAfterTax2 = new TableColumn<CreditNote, String>("New Amount");
		valueAfterTax2.setCellValueFactory(new PropertyValueFactory<>("valueAfterTaxForTable"));
		valueAfterTax2.setMinWidth(100);

		table2.getColumns().addAll(codeCol, dateCol, invoiceid2, descriptionCol, valueAfterTax2, creator);
		table2.setRowFactory(tv -> {
			TableRow<CreditNote> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					openCR(row.getItem());
				}
			});

			return row;
		});
		table2.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				CreditNote cr = table.getSelectionModel().getSelectedItem();
				if (cr != null) {
					if (keyEvent.getCode().equals(KeyCode.DELETE)) {
						delete(cr);

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
		updateCR("");

	}

	public static void updateCR(String search) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from creditnote;";
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
				String invoiceid = rs.getString("invoiceid");
				double valueReal = rs.getDouble("valuereal");
				String user = rs.getString("user");

				String sql2 = "select * from customer where code = '" + code + "';";
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sql2);
				rs2.next();
				Customer customer = new Customer(rs2.getString("code"), rs2.getString("name"), rs2.getString("taxid"),
						rs2.getString("address"), rs2.getString("tel"), rs2.getString("fax"), rs2.getString("email"));

				String sql3 = "select * from invoice where id = '" + invoiceid + "';";
				Statement stmt3 = conn.createStatement();
				ResultSet rs3 = stmt3.executeQuery(sql3);
				rs3.next();
				Gson gson = new Gson();
				TypeToken<ArrayList<Item>> token = new TypeToken<ArrayList<Item>>() {
				};
				ArrayList<Item> itemList = gson.fromJson(rs3.getString("product"), token.getType());

				Invoice invoice = new Invoice(invoiceid, rs3.getString("date"), customer, itemList,
						rs3.getString("ponum"), rs3.getString("orderby"), rs3.getString("termofpayment"),
						rs3.getString("datedue"), rs3.getString("sales"), rs3.getString("user"));

				CreditNote creditnote = new CreditNote(id, date, customer, invoice, valueReal, user);
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
							addToTable = (searchNum == creditnote.getValueAfterTax());
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

					table.getItems().add(creditnote);
					table2.getItems().add(creditnote);

				}
				stmt2.close();
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void openCR(CreditNote cr) {
		Stage newStage = new Stage();
		Scene crnewScene = new Scene(new CRNewUI(newStage, cr));
		newStage.setScene(crnewScene);
		newStage.show();
	}
	public static void delete(CreditNote cr) {
		Connection conn;
		try {
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();

			String sql = "DELETE from creditnote WHERE id ='" + cr.getId() + "'";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
			updateCR("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
