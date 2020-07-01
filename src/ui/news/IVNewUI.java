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
import ui.base.GeneralBox;
import ui.base.IVBox;
import ui.selection.IVSelection;

public class IVNewUI extends VBox{
	public IVNewUI() {
		this.setAlignment(Pos.CENTER);

		HBox buttonGang = new HBox();
		Button backButton = new Button("Back");
		Button saveButton = new Button("Save");
		buttonGang.getChildren().addAll(backButton, saveButton);

		backButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new IVSelection());
			// Stage newStage = new Stage();
			// VBox newBox = new VBox(new QuotationNewUI());
			// Scene newScene = new Scene(newBox);
			// newStage.setScene(newScene);
			// newStage.show();

		});

		HBox upper = new HBox();
		VBox left = new VBox();
		GeneralBox genBox = new GeneralBox(500, 120);
		IVBox qy = new IVBox(500, 120);
		CustomerBox cusBox = new CustomerBox(500, 250);
		left.getChildren().addAll(genBox, qy);
		left.setSpacing(4);
		upper.getChildren().addAll(left, cusBox);
		upper.setSpacing(5);
		upper.setAlignment(Pos.CENTER);
		TableView table = new TableView();

		TableColumn numberCol = new TableColumn("No.");
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

		GridPane lower = new GridPane();
		Label valueBefore = new Label("Value Before:");
		Label tax7 = new Label("Tax 7%:");
		Label plusTax = new Label("Value after tax:");
		lower.add(valueBefore, 0, 0);
		lower.add(tax7, 1, 0);
		lower.add(plusTax, 2, 0);
		this.getChildren().addAll(buttonGang, upper, table, lower);
		this.setSpacing(5);// TODO Auto-generated constructor stub// TODO Auto-generated constructor stub
	}
	
}