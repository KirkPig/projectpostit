package ui.news;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;

import bill.Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import logic.DatabaseConnection;
import ui.base.CustomerBox;
import ui.base.GeneralBox;
import ui.base.ProductAdd;
import ui.base.QYBox;

public class QYNewUI extends VBox {
	private Stage yourOwnStage;
	private TableView<Item> productTable;
	private Label valueBeforeTaxText;
	private Label valueTaxText;
	private Label valueAfterTaxText;
	private GeneralBox genBox;
	private QYBox qy;
	private CustomerBox cusBox;
	private double total;

	public QYNewUI(Stage yourOwnStage) {
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
		qy = new QYBox(300, 120);
		cusBox = new CustomerBox(300, 250);
		left.getChildren().addAll(genBox, qy);
		left.setSpacing(4);
		upper.getChildren().addAll(left, cusBox);
		upper.setSpacing(5);
		upper.setAlignment(Pos.CENTER);
		productTable = new TableView();
		productTable.setEditable(true);

		TableColumn codeCol = new TableColumn("Code");
		codeCol.setMinWidth(30);
		codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));

		TableColumn descriptionCol = new TableColumn("Product Description");
		descriptionCol.setMinWidth(200);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

		TableColumn quantityCol = new TableColumn("Quantity");
		quantityCol.setMinWidth(30);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
		quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		quantityCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, Integer>>() {
			@Override
			public void handle(CellEditEvent<Item, Integer> t) {
				(t.getTableView().getItems().get(t.getTablePosition().getRow())).setItemQuantity(t.getNewValue());
				(t.getTableView().getItems().get(t.getTablePosition().getRow())).setAmount();
				productTable.refresh();
				calculateTax();
			}
		});

		TableColumn unitCol = new TableColumn("Unit");
		unitCol.setMinWidth(30);
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));

		TableColumn priceCol = new TableColumn("Price/Unit");
		priceCol.setMinWidth(70);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn discountCol = new TableColumn("Discount");
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

		TableColumn amountCol = new TableColumn("Amount");
		amountCol.setMinWidth(100);
		amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

		productTable.getColumns().addAll(codeCol, descriptionCol, quantityCol, unitCol, priceCol, discountCol,
				amountCol);
		productTable.getSelectionModel().setCellSelectionEnabled(true);
		HBox productBox = new HBox();
		ProductAdd productAdd = new ProductAdd(productTable);
		Button productBtn = new Button("New product");
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
		productBox.getChildren().addAll(productAdd, productBtn);
		productBox.setAlignment(Pos.CENTER);
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
			
			save();
			yourOwnStage.close();
		});

	}

	public void save() {
		Connection conn;
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
					+ "'," + valueBeforeTax + "," + valueTax +"," + valueAfterTax+",'"+json+"','"+ "naem" +"');";
			
			int x= stmt.executeUpdate(sql);
			if (x>0) {
				System.out.println("Updated Successfully");
			}
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void calculateTax() {
		total = 0;
		for (Item item : productTable.getItems()) {
			total += item.getAmount();
		}
		valueBeforeTaxText.setText(Double.toString(total));
		valueTaxText.setText(Double.toString(total * 7 / 100));
		valueAfterTaxText.setText(Double.toString(total * 107 / 100));
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

				while (rs.next()) {

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

}
