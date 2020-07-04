package ui.news;

import bill.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import logic.Product;
import ui.base.CustomerBox;
import ui.base.GeneralBox;
import ui.base.ProductAdd;
import ui.base.QYBox;
import ui.selection.QYSelection;

public class QYNewUI extends VBox {
	private TableView<Item> productTable;

	public QYNewUI() {
		
		this.setAlignment(Pos.CENTER);
		
		HBox buttonGang = new HBox();
		Button backButton = new Button("Back");
		Button saveButton = new Button("Save");
		buttonGang.getChildren().addAll(backButton,saveButton);
		
		backButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new QYSelection());
			//Stage newStage = new Stage();
			//VBox newBox = new VBox(new QuotationNewUI());
			//Scene newScene = new Scene(newBox);
			//newStage.setScene(newScene);
			//newStage.show();
		
		});		
		
		HBox upper = new HBox();
		VBox left = new VBox();
		GeneralBox genBox = new GeneralBox(500,120);
		QYBox qy = new QYBox(500, 120);
		CustomerBox cusBox = new CustomerBox(500,250);
		left.getChildren().addAll(genBox,qy);
		left.setSpacing(4);
		upper.getChildren().addAll(left,cusBox);
		upper.setSpacing(5);
		upper.setAlignment(Pos.CENTER);
		productTable = new TableView();
		productTable.setEditable(true);
		TableColumn codeCol = new TableColumn("Code");
		codeCol.setMinWidth(30);
		codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
		
		TableColumn descriptionCol = new TableColumn("Product Description");
		descriptionCol.setMinWidth(300);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		TableColumn quantityCol = new TableColumn("Quantity");
		quantityCol.setMinWidth(30);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
		quantityCol.setCellFactory(
			    TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		quantityCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, Integer>>() {
			@Override
			public void handle(CellEditEvent<Item, Integer> t) {
				(t.getTableView().getItems().get(t.getTablePosition().getRow())).setItemQuantity(t.getNewValue());
				(t.getTableView().getItems().get(t.getTablePosition().getRow())).setAmount();
				productTable.refresh();
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
		discountCol.setCellFactory(
			    TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		discountCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, Double>>() {
			@Override
			public void handle(CellEditEvent<Item, Double> t) {
				(t.getTableView().getItems().get(t.getTablePosition().getRow())).setDiscount(t.getNewValue());
				(t.getTableView().getItems().get(t.getTablePosition().getRow())).setAmount();
				productTable.refresh();
			}
		});
		
		TableColumn amountCol = new TableColumn("Amount");
		amountCol.setMinWidth(100);
		amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		
		productTable.getColumns().addAll(codeCol,descriptionCol,quantityCol,unitCol,priceCol,discountCol,amountCol);
		productTable.getSelectionModel().setCellSelectionEnabled(true);
		ProductAdd productAdd = new ProductAdd(productTable);
		
		Button newBtn = new Button("new");
		newBtn.setMinSize(100, 50);
		Button editBtn = new Button("edit");
		editBtn.setMinSize(100, 50);
		Button deleteBtn = new Button("delete");
		deleteBtn.setMinSize(100, 50);
		productTable.setMinWidth(1160);
		HBox tableBox = new HBox();
		VBox button = new VBox();
		button.getChildren().addAll(newBtn, editBtn, deleteBtn);
		tableBox.getChildren().addAll(productTable, button);
		tableBox.setAlignment(Pos.CENTER);
		GridPane lower = new GridPane();
		Label valueBefore = new Label("Value Before:");
		Label valueBeforeText= new Label();
		Label tax7 = new Label("Tax 7%:");
		Label tax7Text = new Label();
		Label plusTax = new Label("Value after tax:");
		Label plusTaxText = new Label();
		
		lower.add(valueBefore, 0, 0);
		lower.add(valueBeforeText,1	, 0);
		lower.add(tax7, 2, 0);
		lower.add(tax7Text, 3, 0);
		lower.add(plusTax, 4, 0);
		lower.add(plusTaxText, 5, 0);
		lower.setAlignment(Pos.CENTER);
		lower.setHgap(20);
		lower.setVgap(10);
	
		this.getChildren().addAll(buttonGang,upper,tableBox,productAdd,lower);
		this.setSpacing(20);
	}

}
