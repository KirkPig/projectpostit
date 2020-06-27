package ui;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DatabaseUI extends VBox {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DatabaseUI() {
		// Setting
		this.setSpacing(10);

		// Tab Select
		HBox tab = new HBox();
		tab.setMinHeight(30);

		Button btnProduct = new Button("Product");
		btnProduct.setMinWidth(100);
		Button btnCustomer = new Button("Customer");
		btnCustomer.setMinWidth(100);

		tab.getChildren().add(btnProduct);
		tab.getChildren().add(btnCustomer);

		// Product Database
		HBox productPane = new HBox();
		productPane.setMinHeight(570);
		productPane.setSpacing(10);

		TableView productTable = new TableView();
		productTable.setMinWidth(800);

		TableColumn colProductCode = new TableColumn("Code");
		TableColumn colProductDesciption = new TableColumn("Description");
		TableColumn colProductPrice = new TableColumn("Price");
		TableColumn colProductUnit = new TableColumn("Unit");

		productTable.getColumns().addAll(colProductCode, colProductDesciption, colProductPrice, colProductUnit);

		VBox productControl = new VBox();
		productControl.setMinWidth(100);
		productControl.setFillWidth(true);

		Button btnProductNew = new Button("New");
		btnProductNew.setMinSize(100, 50);
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

		TableView customerTable = new TableView();
		customerTable.setMinWidth(800);

		TableColumn colCustomerCode = new TableColumn("Code");
		TableColumn colCustomerName = new TableColumn("Name");
		TableColumn colCustomerTaxID = new TableColumn("TaxID");
		TableColumn colCustomerAddress = new TableColumn("Address");
		TableColumn colCustomerTel = new TableColumn("Tel");
		TableColumn colCustomerFax = new TableColumn("Fax");
		TableColumn colCustomerEmail = new TableColumn("Email");

		customerTable.getColumns().addAll(colCustomerCode, colCustomerName, colCustomerTaxID, colCustomerAddress,
				colCustomerTel, colCustomerFax, colCustomerEmail);

		VBox customerControl = new VBox();
		customerControl.setMinWidth(100);
		customerControl.setFillWidth(true);

		Button btnCustomerNew = new Button("New");
		btnCustomerNew.setMinSize(100, 50);
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
	}

}
