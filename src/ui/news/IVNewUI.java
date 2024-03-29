package ui.news;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.google.gson.Gson;

import bill.Invoice;
import bill.Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import logic.DatabaseConnection;
import logic.Report;
import ui.base.CustomerBox;
import ui.base.GeneralBox;
import ui.base.IVBox;
import ui.base.ProductAdd;
import ui.selection.IVSelection;
import ui.selection.Login;

public class IVNewUI extends VBox {
	private boolean createNew;

	private Stage yourOwnStage;
	private TableView<Item> productTable;
	private Label valueBeforeTaxText;
	private Label valueTaxText;
	private Label valueAfterTaxText;
	private GeneralBox genBox;
	private IVBox iv;
	private CustomerBox cusBox;
	private double total;
	private String id;

	@SuppressWarnings("unchecked")
	public IVNewUI(Stage yourOwnStage) {
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

		HBox upper = new HBox();
		VBox left = new VBox();
		genBox = new GeneralBox(300, 120);
		iv = new IVBox(300, 120);
		cusBox = new CustomerBox(300, 250);
		left.getChildren().addAll(genBox, iv);
		left.setSpacing(4);
		upper.getChildren().addAll(left, cusBox);
		upper.setSpacing(5);
		upper.setAlignment(Pos.CENTER);
		productTable = new TableView<Item>();
		productTable.setEditable(true);

		TableColumn<Item, String> codeCol = new TableColumn<Item, String>("ID.");
		codeCol.setMinWidth(30);
		codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));

		TableColumn<Item, String> descriptionCol = new TableColumn<Item, String>("Product Description");
		descriptionCol.setMinWidth(200);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

		TableColumn<Item, Integer> quantityCol = new TableColumn<Item, Integer>("Quantity");
		quantityCol.setMinWidth(30);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
		quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		quantityCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, Integer>>() {
			@Override
			public void handle(CellEditEvent<Item, Integer> t) {
				if (t.getNewValue() <= (t.getTableView().getItems().get(t.getTablePosition().getRow())).getQuantity()) {

					
							(t.getTableView().getItems().get(t.getTablePosition().getRow()))
									.setItemQuantity(t.getNewValue());
							(t.getTableView().getItems().get(t.getTablePosition().getRow())).setAmount();
							productTable.refresh();
							calculateTax();

						
				} else {
					Alert error = new Alert(AlertType.WARNING, "Out of stock", ButtonType.OK);
					productTable.refresh();
					error.show();
				}
			}

		});

		TableColumn<Item, String> unitCol = new TableColumn<Item, String>("Unit");
		unitCol.setMinWidth(30);
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));

		TableColumn<Item, Double> priceCol = new TableColumn<Item, Double>("Price/Unit");
		priceCol.setMinWidth(70);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn<Item, Double> discountCol = new TableColumn<Item, Double>("Discount");
		discountCol.setMinWidth(50);
		discountCol.setEditable(true);
		discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
		discountCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		discountCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, Double>>() {
			@Override
			public void handle(CellEditEvent<Item, Double> t) {

				
						(t.getTableView().getItems().get(t.getTablePosition().getRow())).setDiscount(t.getNewValue());
						(t.getTableView().getItems().get(t.getTablePosition().getRow())).setAmount();
						productTable.refresh();
						calculateTax();

					
			}
		});

		TableColumn<Item, Integer> amountCol = new TableColumn<Item, Integer>("Amount");
		amountCol.setMinWidth(100);
		amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

		productTable.getColumns().addAll(codeCol, descriptionCol, quantityCol, unitCol, priceCol, discountCol,
				amountCol);
		productTable.getSelectionModel().setCellSelectionEnabled(true);
		HBox productBox = new HBox();
		ProductAdd productAdd = new ProductAdd(productTable);
		Button productBtn = new Button("New product");
		Button deleteBtn = new Button("Delete");

		deleteBtn.setOnAction(e -> {
			Item selectedItem = productTable.getSelectionModel().getSelectedItem();
			productTable.getItems().remove(selectedItem);
		});
		productBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stage newProductStage = new Stage();
				Scene newProductScene = new Scene(new ProductNewUI(newProductStage));
				newProductStage.setScene(newProductScene);
				newProductStage.setTitle("New Product");
				newProductStage.show();
			}
		});
		productBox.getChildren().addAll(productAdd, productBtn, deleteBtn);
		productBox.setAlignment(Pos.CENTER);
		productBox.setSpacing(5);
		productTable.setMaxWidth(750);
		HBox tableBox = new HBox();

		tableBox.getChildren().addAll(productTable);
		tableBox.setAlignment(Pos.CENTER);
		GridPane lower = new GridPane();
		Label valueBefore = new Label("Value Before:");
		valueBeforeTaxText = new Label();
		Label valueTax = new Label("Tax 7%:");
		valueTaxText = new Label();
		Label plusTax = new Label("Value after tax:");
		valueAfterTaxText = new Label();

		lower.add(valueBefore, 0, 0);
		lower.add(valueBeforeTaxText, 1, 0);
		lower.add(valueTax, 2, 0);
		lower.add(valueTaxText, 3, 0);
		lower.add(plusTax, 4, 0);
		lower.add(valueAfterTaxText, 5, 0);
		lower.setAlignment(Pos.CENTER);
		lower.setHgap(20);
		lower.setVgap(10);

		this.getChildren().addAll(buttonGang, upper, productBox, tableBox, lower);
		this.setSpacing(20);

		saveButton.setOnMouseClicked((MouseEvent e) -> {
			if (isFilled()) {
				
				
						save();
						IVSelection.updateIV("");

					
			} else {
				Alert error = new Alert(AlertType.WARNING, "Some Box is missing", ButtonType.OK);
				error.show();
			}

		});

	}

	public IVNewUI(Stage yourOwnStage, Invoice invoice) {
		this(yourOwnStage);
		createNew = false;
		id = invoice.getId();
		genBox.setGenBox(invoice.getId(), invoice.getDate());
		cusBox.setSelectedCustomer(invoice.getCustomer());
		iv.setPoText(invoice.getPoNum());
		iv.setSaleText(invoice.getSales());
		iv.setPaymentText(invoice.getPaymentTerm());
		iv.setOrderText(invoice.getOrderBy());
		iv.setDate(invoice.getDateDue());
		productTable.getItems().addAll(invoice.getItemList());
		calculateTax();
	}

	public void save() {
		Connection conn;

		if (!createNew) {
			try {
				conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();

				String sql = "DELETE from invoice WHERE id ='" + id + "'";
				stmt.executeUpdate(sql);
				stmt.close();
				conn.close();
				IVSelection.updateIV("");
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		try {
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String date = genBox.getSelectedDate();
			String id = generateId(date);
			String po = iv.getPoText();
			String order = iv.getOrderText();
			String sale = iv.getSaleText();
			String payment = iv.getPaymentText();
			String datedue = iv.getSelectedDate();
			String code = cusBox.getCustomer();
			DecimalFormat df = new DecimalFormat("#,###.##");
			double valueBeforeTax = Double.parseDouble(df.format(total+0.00).replace(",", ""));
			double valueTax = Double.parseDouble(df.format(total * 7 / 100 + 0.00).replace(",", ""));
			double valueAfterTax = valueBeforeTax + valueTax;
			ArrayList<Item> itemList = new ArrayList<>();
			for (Item item : productTable.getItems()) {
				if (item.getItemQuantity() > 0) {
					item.getProduct().setDescription(item.getProduct().getDescription().replaceAll("\n", " "));
					item.getProduct().setUnit(item.getProduct().getUnit().replaceAll("\n", " "));
					itemList.add(item);
				}

			}

			Gson gson = new Gson();
			
			String json = gson.toJson(itemList);
			
			String sql = "insert into invoice values('" + id + "','" + date + "','" + code + "','" + po + "','" + order
					+ "','" + payment + "','" + datedue + "','" + sale + "'," + valueBeforeTax + "," + valueTax + ","
					+ valueAfterTax + ",'" + json + "','" + Login.usernameShow + "');";

			int x = stmt.executeUpdate(sql);
			if (x > 0) {
				System.out.println("Updated Successfully");

			}
			stmt.close();

			conn.close();
			yourOwnStage.close();
			saveOnPC(new Invoice(id, date, cusBox.getSelectedCustomer(), itemList, po, order, payment, datedue, sale,
					Login.usernameShow));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void calculateTax() {
		total = 0;
		for (Item item : productTable.getItems()) {
			total += item.getAmount();
		}
		total+= 0.00;
		DecimalFormat df = new DecimalFormat("#,###.##");
		valueBeforeTaxText.setText(df.format(total));
		valueTaxText.setText(df.format(total * 7 / 100));
		valueAfterTaxText.setText(df.format(total + Double.parseDouble(df.format((total * 7 / 100)+0.00))));
	}

	public String generateId(String date) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String gid = "";
			int k = 1;

			while (true) {
				gid = "YN" + date.substring(8) + "00" + date.substring(3, 5) + String.format("%03d", k);
				String str = "select * from invoice where id like '" + gid + "'";
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
		String po = iv.getPoText();
		String order = iv.getOrderText();
		String sale = iv.getSaleText();
		String payment = iv.getPaymentText();
		String code = cusBox.getCustomer();

		return !date.isEmpty() && !po.isEmpty() && !order.isEmpty() && !code.isEmpty()
				&& !productTable.getItems().isEmpty() && total != 0 && !sale.isEmpty() && !payment.isEmpty();
	}

	public void saveOnPC(Invoice iv) throws Exception {
		FileChooser file = new FileChooser();
		ExtensionFilter ext = new ExtensionFilter("pdffile", ".pdf");
		file.getExtensionFilters().add(ext);
		File f = file.showSaveDialog(yourOwnStage);
		if (f != null) {
			Report.printInvoice(iv, f.getPath().toString());
			Desktop.getDesktop().open(f);
		}
	}
}
