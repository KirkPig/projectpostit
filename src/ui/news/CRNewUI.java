package ui.news;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.google.gson.Gson;

import bill.CreditNote;
import bill.Item;
import bill.Quotation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import logic.Customer;
import logic.DatabaseConnection;
import logic.Report;
import ui.base.CRBox;
import ui.base.CustomerBox;
import ui.base.GeneralBox;
import ui.base.ProductAdd;
import ui.base.QYBox;
import ui.selection.CRSelection;
import ui.selection.Login;
import ui.selection.QYSelection;

public class CRNewUI extends VBox {
	private boolean createNew;

	private Stage yourOwnStage;
	private static TableView<Item> productTable;
	private static Label valueOldText;
	private static Label valueRealText;
	private static Label valueBeforeTaxText;
	private static Label valueTaxText;
	private static Label valueAfterTaxText;
	private GeneralBox genBox;
	private static CRBox cr;
	private static CustomerBox cusBox;
	private String id;
	private static double valueOld;
	private static double valueReal;
	private static double valueBeforeTax;
	private static double valueTax;
	private static double valueAfterTax;

	public CRNewUI(Stage yourOwnStage) {
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
			// Stage newStage = new Stage();
			// VBox newBox = new VBox(new QuotationNewUI());
			// Scene newScene = new Scene(newBox);
			// newStage.setScene(newScene);
			// newStage.show();

		});

		HBox upper = new HBox();
		VBox left = new VBox();
		genBox = new GeneralBox(300, 120);
		cr = new CRBox(300, 120);
		cusBox = new CustomerBox(300, 250);
		left.getChildren().addAll(genBox, cr);
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

		TableColumn<Item, Integer> amountCol = new TableColumn<Item, Integer>("Amount");
		amountCol.setMinWidth(100);
		amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

		productTable.getColumns().addAll(codeCol, descriptionCol, amountCol);
		productTable.getSelectionModel().setCellSelectionEnabled(true);

		productTable.setMaxWidth(750);
		HBox tableBox = new HBox();

		tableBox.getChildren().addAll(productTable);
		tableBox.setAlignment(Pos.CENTER);
		GridPane lower = new GridPane();
		Label valueOld = new Label("Value Old:");
		valueOldText = new Label("");
		Label valueReal = new Label("Value Real:");
		valueRealText = new Label("");
		Label valueBefore = new Label("Value Before:");
		valueBeforeTaxText = new Label("");
		Label tax7 = new Label("tax 7%:");
		valueTaxText = new Label("");
		Label plusTax = new Label("Value After:");
		valueAfterTaxText = new Label("");
		lower.add(valueOld, 0, 0);
		lower.add(valueOldText, 1, 0);
		lower.add(valueReal, 2, 0);
		lower.add(valueRealText, 3, 0);
		lower.add(valueBefore, 4, 0);
		lower.add(valueBeforeTaxText, 5, 0);
		lower.add(tax7, 0, 1);
		lower.add(valueTaxText, 1, 1);
		lower.add(plusTax, 2, 1);
		lower.add(valueAfterTaxText, 3, 1);
		lower.setAlignment(Pos.CENTER);
		lower.setHgap(20);
		lower.setVgap(10);
		this.getChildren().addAll(buttonGang, upper, tableBox, lower);
		this.setSpacing(20);

		saveButton.setOnMouseClicked((MouseEvent e) -> {
			if (isFilled()) {
				save();
				CRSelection.updateCR("");
			} else {
				Alert error = new Alert(AlertType.WARNING, "Some Box is missing", ButtonType.OK);
				error.show();
			}

		});

	}

	public CRNewUI(Stage yourOwnStage, CreditNote creditnote) {
		this(yourOwnStage);
		createNew = false;
		id = creditnote.getId();
		genBox.setGenBox(creditnote.getId(), creditnote.getDate());
		cusBox.setSelectedCustomer(creditnote.getCustomer());
		cr.setInvoiceText(creditnote.getInvoiceID());
		cr.setDate(creditnote.getInvoice().getDate());
		cr.setValueRealText(creditnote.getValueReal());
		cr.setInvoice(creditnote.getInvoice());
		productTable.getItems().addAll(creditnote.getInvoice().getItemList());
		calculateTax();
	}

	public static void calculateTax() {
		if (cr.getInvoice() != null) {
			DecimalFormat df = new DecimalFormat("#.##");
			valueOld = cr.getInvoice().getValueAfterTax();
			valueReal = cr.getValueRealText();
			valueBeforeTax = valueOld - valueReal;
			valueTax = valueBeforeTax * 7 / 100;
			valueAfterTax = valueBeforeTax + valueTax;

			valueOldText.setText(df.format(valueOld));
			valueRealText.setText(df.format(valueReal));
			valueBeforeTaxText.setText(df.format(valueBeforeTax));
			valueTaxText.setText(df.format(valueTax));
			valueAfterTaxText.setText(df.format(valueAfterTax));
		}

	}

	public void save() {
		Connection conn;

		if (!createNew) {
			try {
				conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();

				String sql = "DELETE from creditnote WHERE id ='" + id + "'";
				stmt.executeUpdate(sql);
				stmt.close();
				conn.close();
				CRSelection.updateCR("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			calculateTax();
			String date = genBox.getSelectedDate();
			String id = generateId(date);
			String invoiceid = cr.getInvoiceText();
			String invoicedate = cr.getDate();
			String code = cusBox.getCustomer();

			String sql = "insert into creditnote values('" + id + "','" + date + "','" + code + "','" + invoiceid
					+ "','" + invoicedate + "'," + valueOld + "," + valueReal + "," + valueBeforeTax + "," + valueTax
					+ "," + valueAfterTax + ",'" + Login.usernameShow + "');";

			int x = stmt.executeUpdate(sql);
			if (x > 0) {
				System.out.println("Updated Successfully");

			}
			stmt.close();

			conn.close();
			yourOwnStage.close();
			saveOnPC(new CreditNote(id, date, cusBox.getSelectedCustomer(), cr.getInvoice(), valueReal, Login.usernameShow));
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
				gid = "CR" + date.substring(8) + "0" + date.substring(3, 5) + String.format("%03d", k);
				String str = "select * from creditnote where id like '" + gid + "'";
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
		String code = cusBox.getCustomer();
		
		return !date.isEmpty() && !code.isEmpty() && cr.getInvoice() != null && cr.getValueRealText() != 0
				&& !productTable.getItems().isEmpty() ;
	}

	public static void updateInvoice(ArrayList<Item> itemList,Customer customer) {
		productTable.getItems().clear();
		for (Item item: itemList) {
			productTable.getItems().add(item);
		}
		cusBox.setSelectedCustomer(customer);
	}
	
	public void saveOnPC(CreditNote cr) throws Exception {
		FileChooser file = new FileChooser();
		ExtensionFilter ext = new ExtensionFilter("pdffile", ".pdf");
		file.getExtensionFilters().add(ext);
		File f = file.showSaveDialog(yourOwnStage);
		if (f != null) {
			Report.printCreditNote(cr, f.getPath().toString());
			Desktop.getDesktop().open(f);
		}
	}
	
	
}