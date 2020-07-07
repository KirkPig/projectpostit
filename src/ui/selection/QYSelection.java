package ui.selection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bill.Item;
import bill.Quotation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Customer;
import logic.DatabaseConnection;
import ui.news.QYNewUI;

public class QYSelection extends VBox {
	private int monthSelecting = Integer.parseInt(LocalDate.now().toString().substring(5, 7));
	private int yearSelecting = Integer.parseInt(LocalDate.now().toString().substring(0, 4));
	private static TableView<Quotation> table;
	private static TableView<Quotation> table2;
	public QYSelection() {
		Button deleteBtn = new Button("delete");
		HBox allFunc = new HBox();
		HBox simpleFunc = new HBox();
		HBox moreFunc = new HBox();
		HBox searchBox = new HBox();
		Button newButton = new Button("new");
		Button switchButton = new Button("Customer");
		ComboBox<Integer> month = new ComboBox<Integer>();
		month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		month.getSelectionModel().select(Integer.parseInt(LocalDate.now().toString().substring(5, 7)) - 1);
		month.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				monthSelecting = month.getValue();
			}
		});

		ComboBox<Integer> year = new ComboBox<Integer>();
		year.getItems().addAll(2563, 2564, 2565, 2566, 2567, 2568, 2569, 2570, 2571, 2572);
		year.setValue(Integer.parseInt(LocalDate.now().toString().substring(0, 4)) + 543);
		year.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				yearSelecting = year.getValue();
			}
		});

		newButton.setOnMouseClicked((MouseEvent e) -> {
			Stage newStage = new Stage();

			Scene qynewScene = new Scene(new QYNewUI(newStage));
			newStage.setScene(qynewScene);
			newStage.show();
		});

		// Button
		TextField search = new TextField();
		search.setPromptText("Search");
		ComboBox<String> genre = new ComboBox<String>();
		genre.getItems().addAll("Code", "Product", "Customer Name", "Creator", "Amount");
		
		simpleFunc.getChildren().addAll(newButton, new Button("open/edit"), deleteBtn, new Button("bin"));
		simpleFunc.setSpacing(3);
		moreFunc.getChildren().addAll(new Button("print report"), switchButton, month, year);
		moreFunc.setSpacing(3);
		searchBox.getChildren().addAll(search, genre);

		allFunc.getChildren().addAll(simpleFunc, moreFunc, searchBox);
		allFunc.setSpacing(250);

		this.getChildren().add(allFunc);

		// =============================================================================
		table = new TableView();
		TableColumn code = new TableColumn("ID");
		code.setMinWidth(60);
		code.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn date = new TableColumn("date");
		date.setMinWidth(100);
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn customerName = new TableColumn("Customer Name.");
		customerName.setMinWidth(200);
		customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		TableColumn totalAmount = new TableColumn("Total Amount");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("valueAfterTaxForTable"));
		totalAmount.setMinWidth(120);
		TableColumn creator = new TableColumn("Created by");
		creator.setCellValueFactory(new PropertyValueFactory<>("creator"));
		creator.setMinWidth(200);
		table.getColumns().addAll(code, date, customerName, totalAmount, creator);
		table.setMaxHeight(500);
		this.getChildren().add(table);
		this.setSpacing(5);
//=========================
		table2 = new TableView();
		TableColumn codeCol = new TableColumn("code");
		codeCol.setMinWidth(20);
		codeCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn dateCol = new TableColumn("date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn descriptionCol = new TableColumn("Description");
		descriptionCol.setMinWidth(600);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
		TableColumn quantityCol = new TableColumn("Quantity");
		quantityCol.setMinWidth(100);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
		TableColumn unitCol = new TableColumn("Unit");
		unitCol.setMinWidth(100);
		unitCol.setCellValueFactory(new PropertyValueFactory<>("productUnit"));

		table2.getColumns().addAll(codeCol,dateCol,descriptionCol, quantityCol, unitCol, creator);

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
		updateQY("");
	}

	public static void updateQY(String search) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from quotation;";
			ResultSet rs = stmt.executeQuery(sql);
			table.getItems().clear();
			table2.getItems().clear();
			while (rs.next()) {
				String id = rs.getString("id");
				String date = rs.getString("date");
				String code = rs.getString("customercode");
				String attn = rs.getString("attn");
				String cr = rs.getString("cr");
				
				double valueBeforeTax = rs.getDouble("valuebeforetax");
				double valueTax = rs.getDouble("valuetax");
				double valueAfterTax = rs.getDouble("valueaftertax");
				Gson gson = new Gson();
				TypeToken<ArrayList<Item>> token = new TypeToken<ArrayList<Item>>() {
				};
				ArrayList<Item> itemList = gson.fromJson(rs.getString("product"), token.getType());
				String sql2 = "select * from customer where code = '" + code + "';";
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sql2);
				rs2.next();
				Customer customer = new Customer(rs2.getString("code"), rs2.getString("name"), rs2.getString("taxid"),
						rs2.getString("address"), rs2.getString("tel"),rs2.getString("fax"),rs2.getString("email"));
				
				Quotation quotation = new Quotation(id, date, customer, itemList, attn, cr,"NAEM");
				table.getItems().add(quotation);
				table2.getItems().add(quotation);
				stmt2.close();
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
