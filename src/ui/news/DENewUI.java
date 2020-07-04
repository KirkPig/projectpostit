package ui.news;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.base.CustomerBox;
import ui.base.DEBox;
import ui.base.GeneralBox;
import ui.selection.DESelection;

public class DENewUI extends VBox{
	public DENewUI() {
		// TODO Auto-generated constructor stub
		this.setAlignment(Pos.CENTER);

		HBox buttonGang = new HBox();
		Button backButton = new Button("Back");
		Button saveButton = new Button("Save");
		buttonGang.getChildren().addAll(backButton, saveButton);

		backButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new DESelection());
			// Stage newStage = new Stage();
			// VBox newBox = new VBox(new QuotationNewUI());
			// Scene newScene = new Scene(newBox);
			// newStage.setScene(newScene);
			// newStage.show();

		});

		HBox upper = new HBox();
		VBox left = new VBox();
		GeneralBox genBox = new GeneralBox(500, 120);
		DEBox qy = new DEBox(500, 120);
		CustomerBox cusBox = new CustomerBox(500, 250);
		left.getChildren().addAll(genBox, qy);
		left.setSpacing(4);
		upper.getChildren().addAll(left, cusBox);
		upper.setSpacing(5);
		upper.setAlignment(Pos.CENTER);
		TableView table = new TableView();

		TableColumn numberCol = new TableColumn("Code");
		numberCol.setMinWidth(30);

		TableColumn descriptionCol = new TableColumn("Product Description");

		descriptionCol.setMinWidth(300);

		TableColumn quantityCol = new TableColumn("Quantity");
		quantityCol.setMinWidth(30);

		TableColumn unitCol = new TableColumn("Unit");
		unitCol.setMinWidth(30);

		TableColumn priceCol = new TableColumn("Price/Unit");
		priceCol.setMinWidth(70);

		TableColumn discountCol = new TableColumn("Discount");
		quantityCol.setMinWidth(50);
		// TODO Auto-generated constructor stub
		TableColumn amountCol = new TableColumn("Amount");
		quantityCol.setMinWidth(100);

		table.getColumns().addAll(numberCol, descriptionCol, quantityCol, unitCol, priceCol, discountCol, amountCol);
		Button newBtn = new Button("new");
		newBtn.setMinSize(100, 50);
		Button editBtn = new Button("edit");
		editBtn.setMinSize(100, 50);
		Button deleteBtn = new Button("delete");
		deleteBtn.setMinSize(100, 50);
		table.setMinWidth(1160);
		HBox tableBox = new HBox();
		VBox button = new VBox();
		button.getChildren().addAll(newBtn, editBtn, deleteBtn);
		tableBox.getChildren().addAll(table, button);
		tableBox.setAlignment(Pos.CENTER);
		GridPane lower = new GridPane();
		Label valueBefore = new Label("Value Before:");
		Label valueBeforeText= new Label("457677");
		Label tax7 = new Label("Tax 7%:");
		Label tax7Text = new Label("4356789");
		Label plusTax = new Label("Value after tax:");
		Label plusTaxText = new Label("1234567890");
		
		lower.add(valueBefore, 0, 0);
		lower.add(valueBeforeText,1	, 0);
		lower.add(tax7, 2, 0);
		lower.add(tax7Text, 3, 0);
		lower.add(plusTax, 4, 0);
		lower.add(plusTaxText, 5, 0);
		lower.setAlignment(Pos.CENTER);
		lower.setHgap(20);
		lower.setVgap(10);
		this.getChildren().addAll(buttonGang, upper, tableBox, lower);
		this.setSpacing(20);// TODO Auto-generated constructor stub
	}

}
