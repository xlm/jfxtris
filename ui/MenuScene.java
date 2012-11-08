package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * MenuScene
 * @author Josh Chia
 * 
 * High-level abstraction used to help create game menus
 * See Menu.java
 *
 */
public class MenuScene extends Scene {

	/**
	 * Constructs a Scene for the menu
	 * @param title, of the menu
	 * @param x, position along x-coordinate
	 * @param y, position along y-coordinate
	 * @param p, color of the menu
	 * @param f, font of the menu text 
	 */
	public MenuScene(String title, double x, double y, Paint p, Font f) {
		super(new Menu(title, f), x, y, p);
	}
	
	/**
	 * Add a item to the menu
	 * @param name, to display on the button
	 * @param eventHandler, the callback
	 */
	public void addMenuItem(String name, EventHandler<ActionEvent> eventHandler) {
		((Menu) this.getRoot()).addMenuItem(name, eventHandler);
	}
	
	/**
	 * Add additional descriptive text to the menu
	 * @param text, to add to the menu
	 */
	public void addMenuText(String text) {
		((Menu) this.getRoot()).addMenuText(text);
	}
}