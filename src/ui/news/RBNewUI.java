package ui.news;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bill.Billing;
import bill.Invoice;
import bill.Item;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import logic.Customer;
import logic.DatabaseConnection;
import logic.Report;
import ui.base.CustomerBox;
import ui.base.GeneralBox;
import ui.base.RBBox;
import ui.selection.Login;
import ui.selection.RBSelection;

public class RBNewUI extends VBox {
	private boolean createNew;

	private Stage yourOwnStage;
	private static TableView<Invoice> invoiceTable;
	private GeneralBox genBox;
	private RBBox rb;
	private CustomerBox cusBox;
	private static double total;
	private String id;
	private static ComboBox<Integer> month;
	private static ComboBox<Integer> year;
	private static ComboBox<String> genre;
	private static TextField search;
	private static Label totalAmount;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RBNewUI(Stage yourOwnStage) {
		createNew = true;
		this.yourOwnStage = yourOwnStage;
		this.setAlignment(Pos.CENTER);
		this.setPrefSize(800, 600);
		HBox buttonGang = new HBox();
		Button backButton = new Button("Cancel");
		Button saveButton = new Button("Save");
		buttonGang.getChildren().addAll(backButton, saveButton);

		backButton.setOnMouseClicked((MouseEvent e) -> {
			yourOwnStage.close();

		});

		month = new ComboBox<Integer>();
		month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		month.getSelectionModel().select(Integer.parseInt(LocalDate.now().toString().substring(5, 7)) - 1);
		month.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				

						updateNewRB("");
				
			}
		});

		year = new ComboBox<Integer>();
		year.getItems().addAll(2563, 2564, 2565, 2566, 2567, 2568, 2569, 2570, 2571, 2572);
		year.setValue(Integer.parseInt(LocalDate.now().toString().substring(0, 4)) + 543);
		year.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
						updateNewRB("");
				
			}
		});

		search = new TextField();
		search.setPromptText("Search");
		search.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				

						updateNewRB("");
				

			}
		});
		genre = new ComboBox<String>();
		genre.getItems().addAll("Code", "Product", "Customer Name", "Creator", "Amount");

		CheckBox selectAll = new CheckBox();
		selectAll.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
						if (selectAll.isSelected()) {
							for (Invoice invoice : invoiceTable.getItems()) {
								if (!invoice.getSelect()) {
									invoice.setSelect(true);
								}

							}
						} else {

							for (Invoice invoice : invoiceTable.getItems()) {
								if (invoice.getSelect()) {
									invoice.setSelect(false);
								}
							}

						}

						invoiceTable.refresh();
						calculate();
				
			}

		});

		selectAll.setAlignment(Pos.CENTER_LEFT);
		HBox searchBox = new HBox();
		searchBox.getChildren().addAll(selectAll, month, year, search, genre);
		searchBox.setAlignment(Pos.CENTER);

		HBox upper = new HBox();
		VBox left = new VBox();
		genBox = new GeneralBox(300, 120);
		rb = new RBBox(300, 120);
		cusBox = new CustomerBox(300, 250);

		left.getChildren().addAll(genBox, rb);
		left.setSpacing(4);
		upper.getChildren().addAll(left, cusBox);
		upper.setSpacing(5);
		upper.setAlignment(Pos.CENTER);

		HBox tableBox = new HBox();

		invoiceTable = new TableView<Invoice>();
		invoiceTable.setMinWidth(1000);
		invoiceTable.setEditable(true);
		TableColumn<Invoice, Boolean> selectCol = new TableColumn<>("Select");

		selectCol.setCellFactory(column -> new CheckBoxTableCell<>());
		selectCol.setCellValueFactory(cellData -> {
			Invoice cellValue = cellData.getValue();
			BooleanProperty property = new SimpleBooleanProperty(cellValue.getSelect());

			property.addListener((observable, oldValue, newValue) -> {
				
						cellValue.setSelect(newValue);
						calculate();
					
			});

			return property;
		});

		TableColumn idCol = new TableColumn("Invoice ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn dateCol = new TableColumn("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

		TableColumn datedueCol = new TableColumn("Date Due");
		datedueCol.setCellValueFactory(new PropertyValueFactory<>("dateDue"));

		TableColumn amountCol = new TableColumn("Amount");
		amountCol.setCellValueFactory(new PropertyValueFactory<>("valueAfterTax"));
		amountCol.setMinWidth(200);
		TableColumn customerCol = new TableColumn("Customer");
		customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		customerCol.setMinWidth(200);
		TableColumn<Invoice, String> psCol = new TableColumn<Invoice, String>("PS");
		psCol.setCellValueFactory(new PropertyValueFactory<>("ps"));
		psCol.setEditable(true);
		psCol.setCellFactory(TextFieldTableCell.forTableColumn());
		psCol.setMinWidth(400);
		psCol.setOnEditCommit(new EventHandler<CellEditEvent<Invoice, String>>() {
			@Override
			public void handle(CellEditEvent<Invoice, String> t) {
				
			
						(t.getTableView().getItems().get(t.getTablePosition().getRow())).setPs(t.getNewValue());
						invoiceTable.refresh();
					
			}

		});

		invoiceTable.getColumns().addAll(selectCol, idCol, customerCol, dateCol, datedueCol, psCol);
		updateNewRB("");

		tableBox.getChildren().addAll(invoiceTable);
		tableBox.setAlignment(Pos.CENTER);
		GridPane lower = new GridPane();
		totalAmount = new Label();
		Label totalLabel = new Label("Total Amount:");
		lower.add(totalAmount, 1, 0);
		lower.add(totalLabel, 0, 0);

		this.getChildren().addAll(buttonGang, upper, searchBox, tableBox, lower);
		this.setSpacing(20);

		saveButton.setOnMouseClicked((MouseEvent e) -> {
			if (isFilled()) {
				
				
						save();
						RBSelection.updateRB("");
				
			} else {
				Alert error = new Alert(AlertType.WARNING, "Some Box is missing", ButtonType.OK);
				error.show();
			}

		});

	}

	public static void updateNewRB(String search) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from invoice;";
			ResultSet rs = stmt.executeQuery(sql);
			invoiceTable.getItems().clear();

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
				Invoice invoice = new Invoice(id, date, customer, itemList, po, order, payment, datedue, sale,
						rs.getString("user"));
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

						addToTable = rs.getString("user").contains(search);

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

					invoiceTable.getItems().add(invoice);

				}
				stmt2.close();
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public RBNewUI(Stage yourOwnStage, Billing billing) {
		this(yourOwnStage);
		createNew = false;
		id = billing.getId();
		genBox.setGenBox(billing.getId(), billing.getDate());
		cusBox.setSelectedCustomer(billing.getCustomer());
		rb.setDate(billing.getBillingDate());
		rb.setPsText(billing.getPs());
		rb.setBillingByBox(billing.getBillingBy());
		invoiceTable.getItems().clear();

		invoiceTable.getItems().addAll(billing.getInvoiceList());
		calculate();

	}

	public static void calculate() {
		total = 0;
		for (Invoice invoice : invoiceTable.getItems()) {
			if (invoice.getSelect()) {
				total += invoice.getValueAfterTax();
			}

		}
		DecimalFormat df = new DecimalFormat("#,###.##");
		totalAmount.setText(df.format(total+0.00));
	}

	public void save() {
		Connection conn;

		if (!createNew) {
			try {
				conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();

				String sql = "DELETE from billing WHERE id ='" + id + "'";
				stmt.executeUpdate(sql);
				stmt.close();
				conn.close();
				RBSelection.updateRB("");
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		try {
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String date = genBox.getSelectedDate();
			String id = generateId(date);
			String ps = rb.getPsText();
			String billingBy = rb.getBillingByBox();
			String billingDate = rb.getSelectedDate();
			String code = cusBox.getCustomer();
			ArrayList<String> invoiceIdList = new ArrayList<String>();
			ArrayList<String> psList = new ArrayList<String>();
			ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
			for (Invoice invoice : invoiceTable.getItems()) {
				if (invoice.getSelect()) {
					invoiceIdList.add(invoice.getId());
					psList.add(invoice.getPs());
					invoiceList.add(invoice);
				}

			}

			Gson gson = new Gson();
			String invoiceJson = gson.toJson(invoiceIdList);
			String psJson = gson.toJson(psList);

			String sql = "insert into billing values('" + id + "','" + date + "','" + code + "','" + billingBy + "','"
					+ billingDate + "','" + ps + "'," + total + ",'" + invoiceJson + "','" + psJson + "','"
					+ Login.usernameShow + "');";

			int x = stmt.executeUpdate(sql);
			if (x > 0) {
				System.out.println("Updated Successfully");

			}
			stmt.close();

			conn.close();
			yourOwnStage.close();
			saveOnPC(new Billing(id, date, cusBox.getSelectedCustomer(), invoiceList, psList, billingBy, billingDate,
					ps, Login.usernameShow));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String generateId(String date) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String gid = "";
			int k = 1;

			while (true) {
				gid = "RB" + date.substring(8) + "0" + date.substring(3, 5) + String.format("%03d", k);
				String str = "select * from billing where id like '" + gid + "'";
				ResultSet rs = stmt.executeQuery(str);
				if (rs.next()) {
					k += 1;
					continue;
				}
				break;
			}

			stmt.close();
			conn.close();
			return gid;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public boolean isFilled() {
		String date = genBox.getSelectedDate();
		String billingBy = rb.getBillingByBox();
		String ps = rb.getPsText();
		String code = cusBox.getCustomer();

		return !date.isEmpty() && !billingBy.isEmpty() && !ps.isEmpty() && !code.isEmpty()
				&& !invoiceTable.getItems().isEmpty() && total != 0;
	}

	public static ComboBox<String> getGenre() {
		return genre;
	}

	public void saveOnPC(Billing rb) throws Exception {
		FileChooser file = new FileChooser();
		ExtensionFilter ext = new ExtensionFilter("pdffile", ".pdf");
		file.getExtensionFilters().add(ext);
		File f = file.showSaveDialog(yourOwnStage);
		if (f != null) {
			Report.printBilling(rb, f.getPath().toString());
			Desktop.getDesktop().open(f);
		}
	}
}
