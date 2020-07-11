package ui.selection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Customer;
import logic.DatabaseConnection;
import logic.Product;
import ui.news.CustomerNewUI;
import ui.news.ProductNewUI;

public class DatabaseUI extends VBox {
	private static TableView<Product> productTable;
	private static TableView<Customer> customerTable;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DatabaseUI() {
		// Setting
		this.setSpacing(5);

		// Tab Select
		HBox tab = new HBox();
		tab.setMinHeight(30);
		HBox switchBox = new HBox();
		Button btnProduct = new Button("Product");
		btnProduct.setMinWidth(100);
		Button btnCustomer = new Button("Customer");
		btnCustomer.setMinWidth(100);

		switchBox.getChildren().add(btnProduct);
		switchBox.getChildren().add(btnCustomer);
		
		switchBox.setSpacing(5);
		
		HBox searchBox = new HBox();
		TextField searchBar = new TextField();
		searchBar.setPromptText("search");
		Button searchButton = new Button("search");
		searchBox.getChildren().addAll(searchBar,searchButton);
		tab.getChildren().addAll(switchBox,searchBox);
		tab.setSpacing(350);
		searchBox.setSpacing(5);
		// Product Database
		HBox productPane = new HBox();
		productPane.setMinHeight(570);
		productPane.setSpacing(10);

		productTable = new TableView();
		productTable.setMinWidth(1160);

		TableColumn colProductCode = new TableColumn("Code");
		colProductCode.setCellValueFactory(new PropertyValueFactory<>("code"));
		TableColumn colProductDesciption = new TableColumn("Description");
		colProductDesciption.setCellValueFactory(new PropertyValueFactory<>("description"));
		TableColumn colProductPrice = new TableColumn("Price");
		colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		TableColumn colProductUnit = new TableColumn("Unit");
		colProductUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
		TableColumn colProductQuantity = new TableColumn("Quantity");
		colProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		productTable.getColumns().addAll(colProductCode, colProductDesciption, colProductQuantity, colProductUnit,
				colProductPrice);
		updateProductTable("");
		VBox productControl = new VBox();
		productControl.setMinWidth(100);
		productControl.setFillWidth(true);

		Button btnProductNew = new Button("New");
		btnProductNew.setMinSize(100, 50);
		btnProductNew.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				newProduct();
			}
		});

		Button btnProductEdit = new Button("Edit");
		btnProductEdit.setMinSize(100, 50);
		Button btnProductDelete = new Button("Delete");
		btnProductDelete.setMinSize(100, 50);

		productControl.getChildren().addAll(btnProductNew, btnProductEdit, btnProductDelete);

		productPane.getChildren().add(productTable);
		productPane.getChildren().add(productControl);

		// Customer Database
		HBox customerPane = new HBox();
		customerPane.setMinHeight(570);
		customerPane.setSpacing(10);

		customerTable = new TableView();
		customerTable.setMinWidth(1160);

		TableColumn colCustomerCode = new TableColumn("Code");
		colCustomerCode.setCellValueFactory(new PropertyValueFactory<>("code"));
		TableColumn colCustomerName = new TableColumn("Name");
		colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn colCustomerTaxID = new TableColumn("TaxID");
		colCustomerTaxID.setCellValueFactory(new PropertyValueFactory<>("taxID"));
		TableColumn colCustomerAddress = new TableColumn("Address");
		colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		TableColumn colCustomerTel = new TableColumn("Tel");
		colCustomerTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
		TableColumn colCustomerFax = new TableColumn("Fax");
		colCustomerFax.setCellValueFactory(new PropertyValueFactory<>("fax"));
		TableColumn colCustomerEmail = new TableColumn("Email");
		colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

		customerTable.getColumns().addAll(colCustomerCode, colCustomerName, colCustomerTaxID, colCustomerAddress,
				colCustomerTel, colCustomerFax, colCustomerEmail);
		updateCustomerTable("");
		VBox customerControl = new VBox();
		customerControl.setMinWidth(100);
		customerControl.setFillWidth(true);

		Button btnCustomerNew = new Button("New");
		btnCustomerNew.setMinSize(100, 50);
		btnCustomerNew.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				newCustomer();
			}
		});

		Button btnCustomerEdit = new Button("Edit");
		btnCustomerEdit.setMinSize(100, 50);
		Button btnCustomerDelete = new Button("Delete");
		btnCustomerDelete.setMinSize(100, 50);

		customerControl.getChildren().addAll(btnCustomerNew, btnCustomerEdit, btnCustomerDelete);

		customerPane.getChildren().add(customerTable);
		customerPane.getChildren().add(customerControl);

		// Added Children
		this.getChildren().add(tab);
		this.getChildren().add(productPane);

		btnProduct.setOnMouseClicked((MouseEvent e) -> {
			if (this.getChildren().contains(customerPane)) {
				this.getChildren().remove(customerPane);
				this.getChildren().add(productPane);

			}
		});

		btnCustomer.setOnMouseClicked((MouseEvent e) -> {
			if (this.getChildren().contains(productPane)) {
				this.getChildren().remove(productPane);
				this.getChildren().add(customerPane);

			}
		});

	}

	public void newCustomer() {
		Stage newCustomerStage = new Stage();
		Scene newCustomerScene = new Scene(new CustomerNewUI(newCustomerStage));
		newCustomerStage.setScene(newCustomerScene);
		newCustomerStage.setTitle("New Customer");
		newCustomerStage.show();
	}

	public void newProduct() {
		Stage newProductStage = new Stage();
		Scene newProductScene = new Scene(new ProductNewUI(newProductStage));
		newProductStage.setScene(newProductScene);
		newProductStage.setTitle("New Product");
		newProductStage.show();
	}

	public static void updateProductTable(String e) {
		try {
			productTable.getItems().clear();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from product;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println((rs.getString("code") + rs.getString("description") + rs.getInt("quantity")
						+ rs.getString("unit") + rs.getFloat("price")));
				productTable.getItems().add(new Product(rs.getString("code"), rs.getString("description"),
						rs.getString("unit"), rs.getFloat("price"), rs.getInt("quantity")));

			}

			stmt.close();
			conn.close();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void updateCustomerTable(String e) {
		try {
			customerTable.getItems().clear();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from customer";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("code") + rs.getString("name") + rs.getString("taxid")
						+ rs.getString("address") + rs.getString("tel") + rs.getString("fax") + rs.getString("email"));
				customerTable.getItems()
						.add(new Customer(rs.getString("code"), rs.getString("name"), rs.getString("taxid"),
								rs.getString("address"), rs.getString("tel"), rs.getString("fax"),
								rs.getString("email")));

			}

			stmt.close();
			conn.close();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public static void deleteProduct(String e) {
		
	}
	
	public static void deleteCustomer(String e) {
		
	}
}
