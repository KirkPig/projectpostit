package ui.news;

import java.sql.Connection;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.DatabaseConnection;
import logic.Product;
import ui.selection.DatabaseUI;
import ui.selection.IVSelection;

public class ProductNewUI extends GridPane {
	private TextField codeBox;
	private TextField descriptionBox;
	private TextField unitBox;
	private TextField quantityBox;
	private TextField priceBox;
	private Stage productStage;
	private boolean createNew;
	
	public ProductNewUI(Stage productStage) {
		super();
		createNew = true;
		this.productStage  = productStage;
		this.setMinSize(400, 300);
		this.setAlignment(Pos.CENTER);
		HBox buttonGang = new HBox();

		Button saveButton = new Button("save");

		saveButton.setMinSize(60, 40);
		buttonGang.getChildren().addAll(saveButton);

		Label header = new Label("Product");
		codeBox = new TextField();
		Label codelabel = new Label("Code:");
		descriptionBox = new TextField();
		unitBox = new TextField();
		Label descriptionLabel = new Label("Description:");
		Label unitLabel = new Label("Unit:");
		quantityBox = new TextField();
		Label quantityLabel = new Label("Quantity");
		priceBox = new TextField();
		Label priceLabel = new Label("Price:");
		this.add(buttonGang, 0, 6);
		this.add(codelabel, 0, 1);
		this.add(codeBox, 1, 1);
		this.add(header, 0, 0);
		this.add(descriptionLabel, 0, 2);
		this.add(descriptionBox, 1, 2);
		this.add(unitLabel, 0, 3);
		this.add(unitBox, 1, 3);
		this.add(quantityLabel, 0, 4);
		this.add(quantityBox, 1, 4);
		this.add(priceLabel, 0, 5);
		this.add(priceBox, 1, 5);

		this.setVgap(10);

		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Thread th = new Thread(new Runnable() {

					@Override
					public void run() {
						save();
					}
				});
				th.start();
				
			}
		});
	}
	public ProductNewUI(Stage productStage,Product pr) {
		this(productStage);
		createNew = false;
		codeBox.setText(pr.getCode());
		descriptionBox.setText(pr.getDescription());
		unitBox.setText(pr.getUnit());
		quantityBox.setText(Integer.toString(pr.getQuantity()));
		priceBox.setText(Double.toString(pr.getPrice()));
	}

	public void save() {
		if (!(codeBox.getText().isEmpty()) && (!descriptionBox.getText().isEmpty())
				&& (!unitBox.getText().isEmpty()) && (!quantityBox.getText().isEmpty())
				&& (!priceBox.getText().isEmpty())) {
			if (!createNew) {
				try {
					Connection conn = DatabaseConnection.getConnection();
					Statement stmt = conn.createStatement();

					String sql = "DELETE from product WHERE code ='" + codeBox.getText().trim() + "'";
					stmt.executeUpdate(sql);
					stmt.close();
					conn.close();
					DatabaseUI.refresh();
				} catch (Exception e) {
					
					e.printStackTrace();
				}

			}
			try {
				Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();

				String sql = "insert into product values(" + "'" + codeBox.getText() + "','"
						+ descriptionBox.getText().trim().replaceAll("\n", " ") + "'," + Integer.parseInt(quantityBox.getText().trim()) + ",'"
						+ unitBox.getText().trim().replaceAll("\n", " ")+ "'," + Float.parseFloat(priceBox.getText().trim()) + ");";
				int x = stmt.executeUpdate(sql);
				System.out.println(sql);
				if (x > 0) {
					System.out.println("Updated Successfully");
				} else {
					System.out.println("Failed");
				}

				stmt.close();
				conn.close();
				DatabaseUI.updateProductTable("");
				productStage.close();
				DatabaseUI.refresh();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
		} else {
			System.out.println("Some Box is Empty");
		}
	}

}