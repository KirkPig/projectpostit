package ui.base;

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
import javafx.scene.layout.*;
import logic.Customer;

public class CustomerBox extends VBox {
	Customer selectedCustomer;

	public CustomerBox(int width, int height) {
		Label header = new Label("CUSTOMER");
		TextField codeBox = new TextField();
		TextField nameBox = new TextField();
		Label taxIdBox = new Label();
		Label addressBox = new Label();
		Label teleBox = new Label();
		Label faxBox = new Label();
		Label mailBox = new Label();
		

		SortedSet<String> nameTree = new TreeSet<>();
		nameTree.add("KIRK");
		nameTree.add("JADE");
		nameTree.add("ZARA");
		
		ContextMenu nameSuggest = new ContextMenu();
		nameBox.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (nameBox.getText().length() == 0) {
					nameSuggest.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					searchResult.addAll(nameTree.subSet(nameBox.getText(), nameBox.getText() + Character.MAX_VALUE));
					if (nameTree.size() > 0) {
						populateNamePopup(searchResult);
						if (!nameSuggest.isShowing()) {
							nameSuggest.show(nameBox, Side.BOTTOM, 0, 0);
						}
					} else {
						nameSuggest.hide();
					}
				}

			}

			private void populateNamePopup(LinkedList<String> searchResult) {
				List<CustomMenuItem> menuItems = new LinkedList<>();

			    int maxEntries = 10;
			    int count = Math.min(searchResult.size(), maxEntries);
			    for (int i = 0; i < count; i++)
			    {
			      final String result = searchResult.get(i);
			      Label entryLabel = new Label(result);
			      CustomMenuItem item = new CustomMenuItem(entryLabel, true);
			      item.setOnAction(new EventHandler<ActionEvent>()
			      {
			        @Override
			        public void handle(ActionEvent actionEvent) {
			          nameBox.setText(result);
			          nameSuggest.hide();
			        }
			      });
			      menuItems.add(item);
			    }
			    nameSuggest.getItems().clear();
			    nameSuggest.getItems().addAll(menuItems);
			}
		});

		nameBox.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

				nameSuggest.hide();
			}
		});
		

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

		this.getChildren().addAll(header, code, name, tax, address, tele, fax, mail);
		this.setMinHeight(height);
		this.setMaxWidth(width);
	}

	public Customer getSelelectedCustomer() {
		return selectedCustomer;
	}
	
	public SortedSet<String> getNameTree(){
		return null;
	}
	
	
}
