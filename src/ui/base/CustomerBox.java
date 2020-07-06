package ui.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import logic.Customer;
import logic.DatabaseConnection;

public class CustomerBox extends VBox {
	private Label codeBox;
	public CustomerBox(int width, int height) {
		Label header = new Label("CUSTOMER");
		codeBox = new Label();
		Label nameBox = new Label();
		Label taxIdBox = new Label();
		Label addressBox = new Label();
		Label teleBox = new Label();
		Label faxBox = new Label();
		Label mailBox = new Label();
		TextField searchBox = new TextField();
		searchBox.setPromptText("search");

		//////// all autofill

		SortedSet<String> allTree = getTree();

		ContextMenu allSuggest = new ContextMenu();
		searchBox.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if ((searchBox.getText().length() == 0) || (!searchBox.isFocused())) {
					allSuggest.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					searchResult.addAll(allTree.subSet(searchBox.getText(), searchBox.getText() + Character.MAX_VALUE));
					if (allTree.size() > 0) {
						populatePopup(searchResult);
						if (!allSuggest.isShowing()) {
							allSuggest.show(searchBox, Side.BOTTOM, 0, 0);
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
								String sql = "SELECT * FROM customer WHERE name ='" + result + "'";
								ResultSet rs = stmt.executeQuery(sql);

								while (rs.next()) {
									nameBox.setText(result);
									codeBox.setText(rs.getString("code"));
									taxIdBox.setText(rs.getString("taxid"));
									addressBox.setText(rs.getString("address"));
									teleBox.setText(rs.getString("tel"));
									faxBox.setText(rs.getString("fax"));
									mailBox.setText(rs.getString("email"));

								}
								String sql2 = "SELECT * FROM customer WHERE code ='" + result + "'";
								ResultSet rs2 = stmt.executeQuery(sql2);
								while (rs2.next()) {
									codeBox.setText(result);
									nameBox.setText(rs2.getString("name"));
									taxIdBox.setText(rs2.getString("taxid"));
									addressBox.setText(rs2.getString("address"));
									teleBox.setText(rs2.getString("tel"));
									faxBox.setText(rs2.getString("fax"));
									mailBox.setText(rs2.getString("email"));

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

		searchBox.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

				allSuggest.hide();
			}
		});

		HBox headerBox = new HBox();
		headerBox.getChildren().addAll(header, searchBox);
		headerBox.setSpacing(5);
		headerBox.setAlignment(Pos.BOTTOM_LEFT);
		///////
		Label codeLabel = new Label("CODE:");
		HBox code = new HBox();
		code.getChildren().addAll(codeLabel, codeBox);
		code.setSpacing(13);
		code.setAlignment(Pos.BOTTOM_LEFT);

		Label nameLabel = new Label("NAME:");
		HBox name = new HBox();
		name.getChildren().addAll(nameLabel, nameBox);
		name.setSpacing(10);
		name.setAlignment(Pos.BOTTOM_LEFT);

		Label taxLabel = new Label("TAX ID:");
		HBox tax = new HBox();
		tax.getChildren().addAll(taxLabel, taxIdBox);
		tax.setSpacing(10);
		tax.setAlignment(Pos.BOTTOM_LEFT);

		Label addressLabel = new Label("ADDRESS:");
		HBox address = new HBox();
		address.getChildren().addAll(addressLabel, addressBox);
		address.setSpacing(10);
		address.setAlignment(Pos.BOTTOM_LEFT);

		Label teleLabel = new Label("TELE:");
		HBox tele = new HBox();
		tele.getChildren().addAll(teleLabel, teleBox);
		tele.setSpacing(10);
		tele.setAlignment(Pos.BOTTOM_LEFT);

		Label faxLabel = new Label("FAX:");
		HBox fax = new HBox();
		fax.getChildren().addAll(faxLabel, faxBox);
		fax.setSpacing(10);
		fax.setAlignment(Pos.BOTTOM_LEFT);

		Label mailLabel = new Label("EMAIL:");
		HBox mail = new HBox();
		mail.getChildren().addAll(mailLabel, mailBox);
		mail.setSpacing(10);
		mail.setAlignment(Pos.BOTTOM_LEFT);

		this.getChildren().addAll(headerBox, code, name, tax, address, tele, fax, mail);
		this.setMinHeight(height);
		this.setMaxWidth(width);
	}

	private SortedSet<String> getCodeTree() {
		try {
			SortedSet<String> codeSet = new TreeSet<String>();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select code from customer";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String a = rs.getString("code");
				codeSet.add(a);
			}
			stmt.close();
			conn.close();
			return codeSet;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	

	public SortedSet<String> getNameTree() {
		try {
			SortedSet<String> nameSet = new TreeSet<String>();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select name from customer";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String a = rs.getString("name");
				nameSet.add(a);
			}
			stmt.close();
			conn.close();
			return nameSet;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}

	public SortedSet<String> getTree() {
		try {
			SortedSet<String> treeSet = new TreeSet<String>();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select code,name from customer";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String a = rs.getString("name");
				String b = rs.getString("code");
				treeSet.add(a);
				treeSet.add(b);
			}
			stmt.close();
			conn.close();
			return treeSet;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	public String getCustomer() {
		return codeBox.getText();
	}
}
