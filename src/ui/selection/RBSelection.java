package ui.selection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bill.Billing;
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
import ui.news.RBNewUI;

public class RBSelection extends VBox {
	private static TableView<Billing> table;
	private static TableView<Billing> table2;
	private static ComboBox<Integer> month;
	private static ComboBox<Integer> year;
	private static ComboBox<String> genre;
	private static TextField search;

	@SuppressWarnings("unchecked")
	public RBSelection() {
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
				updateRB("");
			}
		});

		year = new ComboBox<Integer>();
		year.getItems().addAll(2563, 2564, 2565, 2566, 2567, 2568, 2569, 2570, 2571, 2572);
		year.setValue(Integer.parseInt(LocalDate.now().toString().substring(0, 4)) + 543);
		year.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				updateRB("");
			}
		});

		Button newButton = new Button("new");
		newButton.setOnMouseClicked((MouseEvent e) -> {
			Stage newStage = new Stage();
			Scene crnewScene = new Scene(new RBNewUI(newStage));
			newStage.setScene(crnewScene);
			newStage.show();
		});

		Button editButton = new Button("open/edit");
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (switchButton.getText().equals("Customer")) {
					openRB(table.getItems().get(table.getFocusModel().getFocusedCell().getRow()));
				} else {
					openRB(table2.getItems().get(table2.getFocusModel().getFocusedCell().getRow()));
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
				updateRB(search.getText());
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
		table = new TableView<Billing>();
		TableColumn<Billing, String> code = new TableColumn<Billing, String>("ID");
		code.setMinWidth(60);
		code.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Billing, String> date = new TableColumn<Billing, String>("date");
		date.setMinWidth(100);
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<Billing, String> customerName = new TableColumn<Billing, String>("Customer Name.");
		customerName.setMinWidth(200);
		customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		TableColumn<Billing, String> totalAmount = new TableColumn<Billing, String>("Amount");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("value"));
		totalAmount.setMinWidth(120);
		TableColumn<Billing, String> creator = new TableColumn<Billing, String>("Created by");
		creator.setCellValueFactory(new PropertyValueFactory<>("creator"));
		creator.setMinWidth(200);
		
		table.getColumns().addAll(code, date, customerName,  totalAmount,  creator);
		table.setMaxHeight(500);
		this.getChildren().add(table);
		this.setSpacing(5);

		table.setRowFactory(tv -> {
			TableRow<Billing> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					openRB(row.getItem());
				}
			});
			return row;
		});

		table.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				Billing rb = table.getSelectionModel().getSelectedItem();
				if (rb != null) {
					if (keyEvent.getCode().equals(KeyCode.DELETE)) {
						delete(rb);

					}

				}
			}
		});
//=========================
		table2 = new TableView<Billing>();
		TableColumn<Billing, String> codeCol = new TableColumn<Billing, String>("code");
		codeCol.setMinWidth(20);
		codeCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Billing, String> dateCol = new TableColumn<Billing, String>("date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		TableColumn<Billing, String> invoiceid2 = new TableColumn<Billing, String>("Invoice ID");
		invoiceid2.setCellValueFactory(new PropertyValueFactory<>("invoiceIdForTable"));
		invoiceid2.setMinWidth(100);
		TableColumn<Billing, String> valueAfterTax2 = new TableColumn<Billing, String>("PS");
		valueAfterTax2.setCellValueFactory(new PropertyValueFactory<>("psForTable"));
		valueAfterTax2.setMinWidth(100);

		
		table2.getColumns().addAll(codeCol, dateCol, invoiceid2, valueAfterTax2, creator);
		table2.setRowFactory(tv -> {
			TableRow<Billing> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					openRB(row.getItem());
				}
			});

			return row;
		});
		table2.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				Billing rb = table.getSelectionModel().getSelectedItem();
				if (rb != null) {
					if (keyEvent.getCode().equals(KeyCode.DELETE)) {
						delete(rb);

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
		updateRB("");

	}

	public static void updateRB(String search) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from billing;";
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
				
				// String user = rs.getString("user");
				
				String billingBy = rs.getString("billingby");
				String billingDate = rs.getString("billingdate");
				String ps = rs.getString("ps");
				Gson gson = new Gson();
				String invoiceId = rs.getString("invoice");
				String pslist = rs.getString("pslist");
				TypeToken<ArrayList<String>> token = new TypeToken<ArrayList<String>>() {
				};
				ArrayList<String> invoiceIdList = gson.fromJson(invoiceId, token.getType());
				ArrayList<String> psList = gson.fromJson(pslist, token.getType());
				
				ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
				
				String sql2 = "select * from customer where code = '" + code + "';";
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sql2);
				rs2.next();
				Customer customer = new Customer(rs2.getString("code"), rs2.getString("name"), rs2.getString("taxid"),
						rs2.getString("address"), rs2.getString("tel"), rs2.getString("fax"), rs2.getString("email"));
				for (Integer i = 0 ; i<invoiceIdList.size(); i++) {
					String sql3 = "select * from invoice where id = '" + invoiceIdList.get(i) + "';";
					Statement stmt3 = conn.createStatement();
					ResultSet rs3 = stmt3.executeQuery(sql3);
					rs3.next();
					TypeToken<ArrayList<Item>> token2 = new TypeToken<ArrayList<Item>>() {
					};
					ArrayList<Item> itemList = gson.fromJson(rs3.getString("product"), token2.getType());

					Invoice invoice = new Invoice(invoiceIdList.get(i), rs3.getString("date"), customer, itemList,
							rs3.getString("ponum"), rs3.getString("orderby"), rs3.getString("termofpayment"),
							rs3.getString("datedue"), rs3.getString("sales"), rs3.getString("user"));
					invoice.setPs(psList.get(i));
					invoice.setSelect(true);
					invoiceList.add(invoice);
				}
				
					
			
				
				
				Billing billing =new Billing(id,date,customer,invoiceList,psList,billingBy,billingDate,ps,rs.getString("user"));
				boolean addToTable = false;
				if (genre.getValue() != null && !search.isEmpty()) {
					switch (genre.getValue()) {
					case "Code":
						addToTable = id.contains(search);
						break;
					case "Product":
						//TODO  Get Invoice;
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
							addToTable = (searchNum == billing.getValue());
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

					table.getItems().add(billing);
					table2.getItems().add(billing);

				}
				stmt2.close();
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void openRB(Billing rb) {
		Stage newStage = new Stage();
		Scene rbnewScene = new Scene(new RBNewUI(newStage, rb));
		newStage.setScene(rbnewScene);
		newStage.show();
	}
	
	
	public static void delete(Billing rb) {
		Connection conn;
		try {
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();

			String sql = "DELETE from billing WHERE id ='" + rb.getId() + "'";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
			updateRB("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
