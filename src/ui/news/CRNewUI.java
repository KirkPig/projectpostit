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
import ui.base.CRBox;
import ui.base.CustomerBox;
import ui.base.GeneralBox;
import ui.selection.CRSelection;

public class CRNewUI extends VBox {
	public CRNewUI() {
		this.setAlignment(Pos.CENTER);

		HBox buttonGang = new HBox();
		Button backButton = new Button("Back");
		Button saveButton = new Button("Save");
		buttonGang.getChildren().addAll(backButton, saveButton);

		backButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new CRSelection());
			// Stage newStage = new Stage();
			// VBox newBox = new VBox(new QuotationNewUI());
			// Scene newScene = new Scene(newBox);
			// newStage.setScene(newScene);
			// newStage.show();

		});

		HBox upper = new HBox();
		VBox left = new VBox();
		GeneralBox genBox = new GeneralBox(500, 120);
		CRBox qy = new CRBox(500, 120);
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

		TableColumn amountCol = new TableColumn("Amount");
		amountCol.setMinWidth(100);

		table.getColumns().addAll(numberCol, descriptionCol, amountCol);
		Button newBtn = new Button("new");
		newBtn.setMinSize(100, 50);
		Button editBtn = new Button("edit");
		editBtn.setMinSize(100, 50);
		Button deleteBtn = new Button("delete");
		deleteBtn.setMinSize(100, 50);
		HBox tableBox = new HBox();
		VBox button = new VBox();
		button.getChildren().addAll(newBtn, editBtn, deleteBtn);
		tableBox.getChildren().addAll(table, button);
		tableBox.setAlignment(Pos.CENTER);
		table.setMinWidth(1160);
		GridPane lower = new GridPane();
		Label valueOld = new Label("Value Old:");
		Label valueOldText = new Label("123456");
		Label valueReal = new Label("Value Real:");
		Label valueRealText = new Label("163864");
		Label valueBefore = new Label("Value Before:");
		Label valueBeforeText = new Label("112234");
		Label tax7 = new Label("tax 7%:");
		Label tax7Text = new Label("12435678");
		Label plusTax = new Label("Value After:");
		Label plusTaxText = new Label("442345");
		lower.add(valueOld, 0, 0);
		lower.add(valueOldText, 1, 0);
		lower.add(valueReal, 2, 0);
		lower.add(valueRealText, 3, 0);
		lower.add(valueBefore, 4, 0);
		lower.add(valueBeforeText, 5, 0);
		lower.add(tax7, 0, 1);
		lower.add(tax7Text, 1, 1);
		lower.add(plusTax, 2, 1);
		lower.add(plusTaxText, 3, 1);
		lower.setAlignment(Pos.CENTER);
		lower.setHgap(20);
		lower.setVgap(10);
		this.getChildren().addAll(buttonGang, upper, tableBox, lower);
		this.setSpacing(20);
	}

}