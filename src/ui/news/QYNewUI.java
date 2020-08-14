package ui.news;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.google.gson.Gson;

import bill.Item;
import bill.Quotation;
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
import ui.base.ProductAdd;
import ui.base.QYBox;
import ui.selection.Login;
import ui.selection.QYSelection;

public class QYNewUI extends VBox {
	// @TODO create save the edited to database sql
	private boolean createNew;

	private Stage yourOwnStage;
	private TableView<Item> productTable;
	private Label valueBeforeTaxText;
	private Label valueTaxText;
	private Label valueAfterTaxText;
	private GeneralBox genBox;
	private QYBox qy;
	private CustomerBox cusBox;
	private double total;
	private String id;

	@SuppressWarnings("unchecked")
	public QYNewUI(Stage yourOwnStage) {
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
		qy = new QYBox(300, 120);
		cusBox = new CustomerBox(300, 250);
		left.getChildren().addAll(genBox, qy);
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
					(t.getTableView().getItems().get(t.getTablePosition().getRow())).setItemQuantity(t.getNewValue());
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
				Thread th = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						save();
						QYSelection.updateQY("");
					}
				});
				
				th.start();
			} else {
				Alert error = new Alert(AlertType.WARNING, "Some Box is missing", ButtonType.OK);
				error.show();
			}

		});

	}

	public QYNewUI(Stage yourOwnStage, Quotation quotation) {
		this(yourOwnStage);
		createNew = false;
		id = quotation.getId();
		genBox.setGenBox(quotation.getId(), quotation.getDate());
		cusBox.setSelectedCustomer(quotation.getCustomer());
		qy.setCr(quotation.getCr());
		qy.setAttn(quotation.getAttn());
		productTable.getItems().addAll(quotation.getItemList());
		calculateTax();
	}

	public void save() {
		Connection conn;

		if (!createNew) {
			try {
				conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();

				String sql = "DELETE from quotation WHERE id ='" + id + "'";
				stmt.executeUpdate(sql);
				stmt.close();
				conn.close();
				QYSelection.updateQY("");
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		try {
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String date = genBox.getSelectedDate();
			String id = generateId(date);
			String attn = qy.getAttn();
			String cr = qy.getCr();
			String code = cusBox.getCustomer();
			double valueBeforeTax = total;
			double valueTax = total * 7 / 100;
			double valueAfterTax = total * 107 / 100;
			ArrayList<Item> itemList = new ArrayList<>();
			for (Item item : productTable.getItems()) {
				if (item.getItemQuantity() > 0) {
					itemList.add(item);
				}

			}

			Gson gson = new Gson();
			String json = gson.toJson(itemList);

			String sql = "insert into quotation values('" + id + "','" + date + "','" + code + "','" + attn + "','" + cr
					+ "'," + valueBeforeTax + "," + valueTax + "," + valueAfterTax + ",'" + json + "','"
					+ Login.usernameShow + "');";

			int x = stmt.executeUpdate(sql);
			if (x > 0) {
				System.out.println("Updated Successfully");

			}
			stmt.close();

			conn.close();
			yourOwnStage.close();
			saveOnPC(new Quotation(id,date,cusBox.getSelectedCustomer(),itemList,attn,cr,Login.usernameShow));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void calculateTax() {
		total = 0;
		for (Item item : productTable.getItems()) {
			total += item.getAmount();
		}
		DecimalFormat df = new DecimalFormat("#,###.##");
		valueBeforeTaxText.setText(df.format(total));
		valueTaxText.setText(df.format(total*7/100));
		valueAfterTaxText.setText(df.format(total*107/100));
	}

	public String generateId(String date) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String gid = "";
			int k = 1;

			while (true) {
				gid = "QY" + date.substring(8) + "0" + date.substring(3, 5) + String.format("%03d", k);
				String str = "select * from quotation where id like '" + gid + "'";
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
		String attn = qy.getAttn();
		String cr = qy.getCr();
		String code = cusBox.getCustomer();

		return !date.isEmpty() && !attn.isEmpty() && !cr.isEmpty() && !code.isEmpty()
				&& !productTable.getItems().isEmpty() && total != 0;
	}

	public void saveOnPC(Quotation qy) throws Exception {
		FileChooser file = new FileChooser();
		ExtensionFilter ext = new ExtensionFilter("pdffile", ".pdf");
		file.getExtensionFilters().add(ext);
		File f = file.showSaveDialog(yourOwnStage);
		if (f != null) {
			Report.printQuotation(qy, f.getPath().toString());
			Desktop.getDesktop().open(f);
		}
	}
}
