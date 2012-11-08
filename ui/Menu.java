package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Menu
 * @author Josh Chia
 * 
 * High-level abstraction for creating game menus 
 *
 */
public class Menu extends VBox {
	
	public Menu(String title, Font f) {
		// Menu text
		Text menu_title = new Text(title);
		menu_title.setFont(f);
		
		// Configure layout
		this.getChildren().add(menu_title);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(5);
	}
	
	/**
	 * Add a menu button to the Menu
	 * @param name, of button
	 * @param handler, the callback
	 */
	public void addMenuItem(String name, EventHandler<ActionEvent> handler) {
		Button menu_item = new Button(name);
		menu_item.setOnAction(handler);
		this.getChildren().add(menu_item);
	}
	
	/**
	 * Add additional descriptive text to the Menu
	 * @param text, to add
	 */
	public void addMenuText(String text) {
		this.getChildren().add(new Text(text));
	}
}
