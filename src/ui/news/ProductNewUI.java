package ui.news;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ProductNewUI extends GridPane {
	public ProductNewUI() {
		super();
		Label header = new Label("Product");
		TextField desctiprionBox = new TextField();
		TextField unitBox = new TextField();
		Label descriptionLabel = new Label("Description:");
		Label unitLabel = new Label("Unit:");
		this.add(header, 0, 0);
		this.add(descriptionLabel, 0, 1);
		this.add(desctiprionBox, 1, 1);
		this.add(unitLabel, 0, 2);
		this.add(unitBox, 1, 2);
	}
}