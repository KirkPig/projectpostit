package ui.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bill.Invoice;
import bill.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Customer;
import logic.DatabaseConnection;
import ui.news.CRNewUI;

public class CRBox extends VBox {
	private TextField invoiceText;
	private Label date;
	private TextField valueRealText;
	private Invoice invoice;

	public CRBox(int width, int height) {
		Label header = new Label("CRBOX");
		invoiceText = new TextField();
		invoiceText.setPromptText("Invoice ID");
		HBox invoiceBox = new HBox();
		Label invoiceLabel = new Label("ID:");
		invoiceBox.getChildren().addAll(invoiceLabel, invoiceText);
		invoiceBox.setSpacing(5);

		date = new Label();
		HBox dateBox = new HBox();
		Label dateLabel = new Label("DATE:");
		dateBox.getChildren().addAll(dateLabel, date);
		dateBox.setSpacing(5);

		valueRealText = new TextField();
		HBox valueBox = new HBox();
		Label valueLabel = new Label("Value Real:");
		valueBox.getChildren().addAll(valueLabel, valueRealText);

		this.getChildren().addAll(header, invoiceBox, dateBox, valueBox);
		this.setMinSize(width, height);

		//////// all autofill

		SortedSet<String> allTree = getTree();

		ContextMenu allSuggest = new ContextMenu();
		invoiceText.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if ((invoiceText.getText().length() == 0) || (!invoiceText.isFocused())) {
					allSuggest.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					searchResult
							.addAll(allTree.subSet(invoiceText.getText(), invoiceText.getText() + Character.MAX_VALUE));
					if (allTree.size() > 0) {
						populatePopup(searchResult);
						if (!allSuggest.isShowing()) {
							allSuggest.show(invoiceText, Side.BOTTOM, 0, 0);
						}
					} else {
						allSuggest.hide();
					}
				}

			}

			private void populatePopup(LinkedList<String> searchResult) {
				List<CustomMenuItem> menuItems = new LinkedList<>();

				int maxEntries = 10;
				int count = Math.min(searchResult.size(), maxEntries);

				for (int i = 0; i < count; i++) {
					final String result = searchResult.get(i);
					Label entryLabel = new Label(result);
					CustomMenuItem item = new CustomMenuItem(entryLabel, true);
					item.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent actionEvent) {
							System.out.println(result);
							try {
								Connection conn = DatabaseConnection.getConnection();
								Statement stmt = conn.createStatement();
								String sql = "SELECT * FROM invoice WHERE id ='" + result + "'";
								ResultSet rs = stmt.executeQuery(sql);
								
								while (rs.next()) {
									invoiceText.setText(rs.getString("id"));
									date.setText(rs.getString("date"));
									Gson gson = new Gson();
									TypeToken<ArrayList<Item>> token = new TypeToken<ArrayList<Item>>() {
									};
									ArrayList<Item> itemList = gson.fromJson(rs.getString("product"), token.getType());
									
									String sql2 = "select * from customer where code = '" + rs.getString("customercode") + "';";
									Statement stmt2 = conn.createStatement();
									ResultSet rs2 = stmt2.executeQuery(sql2);
									rs2.next();
									Customer customer = new Customer(rs2.getString("code"), rs2.getString("name"), rs2.getString("taxid"),
											rs2.getString("address"), rs2.getString("tel"), rs2.getString("fax"), rs2.getString("email"));
									
									setInvoice(new Invoice(rs.getString("id"), rs.getString("date"),
											customer , itemList, rs.getString("ponum"),
											rs.getString("orderby"), rs.getString("termofpayment"),
											rs.getString("datedue"), rs.getString("sales"), rs.getString("user")));
									CRNewUI.calculateTax();
									CRNewUI.updateInvoice(itemList,customer);
									
									stmt2.close();
								}

								stmt.close();
								conn.close();
							} catch (Exception e) {
								e.printStackTrace();
							}

							allSuggest.hide();
						}
					});
					menuItems.add(item);
				}
				allSuggest.getItems().clear();
				allSuggest.getItems().addAll(menuItems);
			}
		});

		invoiceText.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

				allSuggest.hide();
			}
		});
		
		

		valueRealText.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				CRNewUI.calculateTax();
			}
		});
	}

	public String getDate() {
		return date.getText();
	}

	public void setDate(String date) {
		this.date.setText(date);
	}

	public void setInvoiceText(String invoiceText) {
		this.invoiceText.setText(invoiceText);
	}

	public String getInvoiceText() {
		return invoiceText.getText();
	}

	public SortedSet<String> getTree() {
		try {
			SortedSet<String> treeSet = new TreeSet<String>();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select id from invoice";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String a = rs.getString("id");

				treeSet.add(a);

			}
			stmt.close();
			conn.close();
			return treeSet;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public double getValueRealText() {
		if (!this.valueRealText.getText().isEmpty()) {
			return Double.parseDouble(this.valueRealText.getText());
		}
		return 0;
	}

	public void setValueRealText(double valueRealText) {
		this.valueRealText.setText(Double.toString(valueRealText));
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
}
