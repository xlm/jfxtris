package application;

import game.DemoGame;
import game.Game;
import ui.MenuScene;
import data_types.DropType;
import data_types.RotateDirection;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main
 * @author Josh Chia
 * 
 * Application class for launching the Main JavaFX
 * application.
 * 
 * Allows players to play either single or multiplayer
 * games.
 * 
 * Also provides demonstrate games to showcase features
 * such as recursive gravity and wall kicks.
 * 
 * The application setting are highly customizable and 
 * can be tweaked by modifying the constants.
 * Given more time, this could be incorporated into a
 * settings menu for players to customize.
 *
 */

public class Main extends javafx.application.Application {
	// Game Setting Constants
	private final int COLS = 10;
	private final int ROWS = 22;
	private final int VANISH = 2;
	private final String NAME = "JFXTRIS";
	
	// Display Constants
	private final int FPS = 60;
	private final double RES_X = 1024;
	private final double RES_Y = 600;
	private final Color BACKGROUND_COLOR = Color.SLATEGREY;
	private final Font MENU_FONT = Font.font("Tahoma",  FontWeight.NORMAL, 40);
	
	// Player One Keyboard Controls
	private final KeyCode P1_SHIFT_LEFT = KeyCode.LEFT;
	private final KeyCode P1_SHIFT_RIGHT = KeyCode.RIGHT;
	private final KeyCode P1_ROTATE_LEFT = KeyCode.COMMA;
	private final KeyCode P1_ROTATE_RIGHT = KeyCode.PERIOD;
	private final KeyCode P1_SOFT_DROP = KeyCode.DOWN;
	private final KeyCode P1_HARD_DROP = KeyCode.UP;
	private final KeyCode P1_HOLD = KeyCode.SLASH;
	
	// Player Two Keyboard Controls
	private final KeyCode P2_SHIFT_LEFT = KeyCode.A;
	private final KeyCode P2_SHIFT_RIGHT = KeyCode.D;
	private final KeyCode P2_ROTATE_LEFT = KeyCode.C;
	private final KeyCode P2_ROTATE_RIGHT = KeyCode.V;
	private final KeyCode P2_SOFT_DROP = KeyCode.S;
	private final KeyCode P2_HARD_DROP = KeyCode.W;
	private final KeyCode P2_HOLD = KeyCode.B;
	
	// General Controls
	private final KeyCode MENU = KeyCode.ESCAPE;
	
	// UI Components
	private MenuScene main_menu, ingame_menu;
	private Scene game_scene;
	private AnimationTimer timer;
	
	@Override
	public void start(final Stage primary_stage) throws Exception {
		
		// Handler for configuring single player controls
		class OnePlayerController implements EventHandler<KeyEvent> {
			Game game;
			
			public OnePlayerController(Game g) {
				game = g;
			}
			
			@Override
			public void handle(KeyEvent k) {
				if (k.getCode().equals(P1_HARD_DROP)) {
					game.setDrop(DropType.HARD);
				}
				else if (k.getCode().equals(P1_SOFT_DROP)) {
					game.setDrop(DropType.SOFT);
				}
				else if (k.getCode().equals(P1_SHIFT_LEFT)) {
					game.shiftLeft();
				}
				else if (k.getCode().equals(P1_SHIFT_RIGHT)) {
					game.shiftRight();
				}
				else if (k.getCode().equals(P1_ROTATE_LEFT)) {
					game.rotate(RotateDirection.LEFT);
				}
				else if (k.getCode().equals(P1_ROTATE_RIGHT)) {
					game.rotate(RotateDirection.RIGHT);
				}
				else if (k.getCode().equals(P1_HOLD)) {
					game.holdPiece();
				}
				else if (k.getCode().equals(MENU)) {
					timer.stop();
					primary_stage.setScene(ingame_menu);
				}
			}
		}
		
		// Handler for configuring two player controls
		class TwoPlayerController implements EventHandler<KeyEvent> {
			Game game1, game2;
			
			public TwoPlayerController(Game g1, Game g2) {
				game1 = g1;
				game2 = g2;
			}
			
			@Override
			public void handle(KeyEvent k) {
				if (k.getCode().equals(P1_HARD_DROP)) {
					game1.setDrop(DropType.HARD);
				}
				else if (k.getCode().equals(P1_SOFT_DROP)) {
					game1.setDrop(DropType.SOFT);
				}
				else if (k.getCode().equals(P1_SHIFT_LEFT)) {
					game1.shiftLeft();
				}
				else if (k.getCode().equals(P1_SHIFT_RIGHT)) {
					game1.shiftRight();
				}
				else if (k.getCode().equals(P1_ROTATE_LEFT)) {
					game1.rotate(RotateDirection.LEFT);
				}
				else if (k.getCode().equals(P1_ROTATE_RIGHT)) {
					game1.rotate(RotateDirection.RIGHT);
				}
				else if (k.getCode().equals(P1_HOLD)) {
					game1.holdPiece();
				}
				else if (k.getCode().equals(P2_HARD_DROP)) {
					game2.setDrop(DropType.HARD);
				}
				else if (k.getCode().equals(P2_SOFT_DROP)) {
					game2.setDrop(DropType.SOFT);
				}
				else if (k.getCode().equals(P2_SHIFT_LEFT)) {
					game2.shiftLeft();
				}
				else if (k.getCode().equals(P2_SHIFT_RIGHT)) {
					game2.shiftRight();
				}
				else if (k.getCode().equals(P2_ROTATE_LEFT)) {
					game2.rotate(RotateDirection.LEFT);
				}
				else if (k.getCode().equals(P2_ROTATE_RIGHT)) {
					game2.rotate(RotateDirection.RIGHT);
				}
				else if (k.getCode().equals(P2_HOLD)) {
					game2.holdPiece();
				}
				else if (k.getCode().equals(MENU)) {
					timer.stop();
					primary_stage.setScene(ingame_menu);
				}
			}
		}
		
		/*
		 * Locks the game to run at 60FPS regardless of JavaFX refresh
		 * Abstracts away logic associated with game frane updates 
		 */
		class GameTimer extends AnimationTimer {
			Game[] games;
			long prev;
			double delta_secs, frame_count;
			int frames; 
			
			public GameTimer(Game[] gs) {
				super();
				games = gs;
				prev = 0;
				delta_secs = 0;
				frames = 0;
				frame_count = 0;
			}
			
			@Override
			public void handle(long now) {
				prev = (prev==0) ? now: prev;
				delta_secs = (now-prev)/1000000000.0;
				frame_count += FPS*delta_secs;
				
				if (frame_count > 1) {
					frames = (int)frame_count;
					for(Game g: games) {
						g.updateGame(frames);
					}
					frame_count -= frames;
				}
				prev = now;
			}
			
			@Override
			public void stop() {
				super.stop();
				prev = 0;
			}
			
			@Override
			public void start() {
				super.start();
			}
		}
		
		// Handler for the creation of single player games
		class NewOnePlayerGameHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent arg0) {
				final Game game1  = new Game(RES_X/4, 0, RES_X/2, RES_Y, ROWS, COLS, VANISH);
				game_scene = new Scene(game1, RES_X, RES_Y);
				primary_stage.setScene(game_scene);
				
				timer = new GameTimer(new Game[] {game1});
				timer.start();
				
				game_scene.setOnKeyPressed(new OnePlayerController(game1));
				game_scene.setFill(BACKGROUND_COLOR);
			}
		}
		
		// Handler for the creation of demonstration games
		class NewDemoGameHandler implements EventHandler<ActionEvent> {
			int lvl;
			
			public NewDemoGameHandler(int l) {
				super();
				lvl = l;
			}
			
			@Override
			public void handle(ActionEvent arg0) {
				final Game game1  = new DemoGame(RES_X/4, 0, RES_X/2, RES_Y, lvl);
				game_scene = new Scene(game1, RES_X, RES_Y);
				primary_stage.setScene(game_scene);
				
				timer = new GameTimer(new Game[] {game1});
				timer.start();
				
				game_scene.setOnKeyPressed(new OnePlayerController(game1));
				game_scene.setFill(BACKGROUND_COLOR);
			}
		}
		
		// Handler for the creation of two player games
		class NewTwoPlayerGameHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent arg0) {
				final Game game1  = new Game(RES_X/2, 0, RES_X/2, RES_Y, ROWS, COLS, VANISH);
				final Game game2  = new Game(0, 0, RES_X/2, RES_Y, ROWS, COLS, VANISH);
				
				Group root = new Group();
				root.getChildren().add(game1);
				root.getChildren().add(game2);
				
				game_scene = new Scene(root, RES_X, RES_Y);
				primary_stage.setScene(game_scene);
				
				timer = new GameTimer(new Game[] {game1, game2});
				timer.start();
				
				game_scene.setOnKeyPressed(new TwoPlayerController(game1, game2));
				game_scene.setFill(BACKGROUND_COLOR);
			}
		}
		
		// Handler for the resuming a game
		class ResumeGameHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				timer.start();
				primary_stage.setScene(game_scene);				
			}
		}
		
		/*
		 * Handler for exiting the application
		 * On Linux machines in the lab, an intermitten SIGSEGV fault
		 * may occur. This is likely due to the JRE.
		 * Please see the ANU CECS StReaMS forums
		 */
		class ExitHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		}
		
		// Setup Game Menus
		main_menu = new MenuScene(NAME, RES_X, RES_Y, BACKGROUND_COLOR, MENU_FONT);
		main_menu.addMenuItem("New 1P Game", new NewOnePlayerGameHandler());
		main_menu.addMenuItem("New 2P Game", new NewTwoPlayerGameHandler());
		main_menu.addMenuItem("Demo Level 1", new NewDemoGameHandler(1));
		main_menu.addMenuItem("Demo Level 2", new NewDemoGameHandler(2));
		main_menu.addMenuItem("Exit Game", new ExitHandler());
		main_menu.addMenuText(getHelpText());
		
		ingame_menu = new MenuScene(NAME, RES_X, RES_Y, BACKGROUND_COLOR, MENU_FONT);
		ingame_menu.addMenuItem("Resume", new ResumeGameHandler());
		ingame_menu.addMenuItem("New 1P Game", new NewOnePlayerGameHandler());
		ingame_menu.addMenuItem("New 2P Game", new NewTwoPlayerGameHandler());
		ingame_menu.addMenuItem("Demo Level 1", new NewDemoGameHandler(1));
		ingame_menu.addMenuItem("Demo Level 2", new NewDemoGameHandler(2));
		ingame_menu.addMenuItem("Exit Game", new ExitHandler());
		ingame_menu.addMenuText(getHelpText());
		
		// Set Initial Scene
		primary_stage.setScene(main_menu);
		primary_stage.initStyle(StageStyle.UTILITY);
		primary_stage.setTitle(NAME);
		primary_stage.setResizable(false);
		primary_stage.show();
	}
	
	/**
	 * Provide player with help text on the controls 
	 * @return, help text
	 */
	private String getHelpText() {
		String help = "\n\n";
		help += "P1 Controls:\n";
		help += "[Left: " + P1_SHIFT_LEFT + "] ";
		help += "[Right: " + P1_SHIFT_RIGHT + "] ";
		help += "[Soft drop: " + P1_SOFT_DROP+ "] ";
		help += "[Hard drop: " + P1_HARD_DROP + "] ";
		help += "[Rotate left: " + P1_ROTATE_LEFT + "] ";
		help += "[Rotate right: " + P1_ROTATE_RIGHT + "] ";
		help += "[Hold: " + P1_HOLD + "]\n";
		help += "\nP2 Controls:\n";
		help += "[Left: " + P2_SHIFT_LEFT + "] ";
		help += "[Right: " + P2_SHIFT_RIGHT + "] ";
		help += "[Soft drop: " + P2_SOFT_DROP+ "] ";
		help += "[Hard drop: " + P2_HARD_DROP + "] ";
		help += "[Rotate left: " + P2_ROTATE_LEFT + "] ";
		help += "[Rotate right :" + P2_ROTATE_RIGHT + "] ";
		help += "[Hold: " + P2_HOLD + "]\n";
		help += "\n[Show Menu: " + MENU + "]";
		help += "\n\nAuthor: Josh Chia (u5024740)";
		return help;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}