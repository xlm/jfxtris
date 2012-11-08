package model;

import game.GridCanvas;

import java.util.HashMap;
import java.util.Map;

import data_types.RotateDirection;
import data_types.RotateState;
import data_types.Vector2D;

import javafx.scene.paint.Color;

/**
 * Piece
 * @author Josh Chia
 * 
 * Defines the different types of Tetrominos that can be formed 
 * in the game.
 * 
 * Uses the Factory Design Pattern to keep the logic associated 
 * with spawning each Piece self-contained within the enumeration 
 * class for maintainability and cohesion. See the Tetromino class 
 * for more details.
 * 
 * Uses the simplified (still correct) Wall Kick logic used by 
 * The Tetris Company (TTC) for efficiency:
 * http://tetrisconcept.net/wiki/SRS#Wall_Kicks
 * 
 * Pieces implemented are: I, O, T, S, Z, J, L
 * For more details on the type of Pieces implemented see:
 * http://tetrisconcept.net/wiki/Tetromino 
 * 
 */

public enum Piece implements TetrominoFactory<Tetromino> {
	/**
	 * Each enumeration implements the Factory methods (create) specific
	 * to them.
	 */
	I {
		@Override
		public Tetromino create(GridCanvas gc) {
			Vector2D pos = gc.getSpawnPos(this);
			return new Tetromino(this, pos, new Vector2D(pos, 0.0, 0.0), new Mino[] { 
					new Mino(gc, new Vector2D(pos, 0, 0), Color.SKYBLUE),
					new Mino(gc, new Vector2D(pos, -1, 0), Color.SKYBLUE),
					new Mino(gc, new Vector2D(pos, 1, 0), Color.SKYBLUE),
					new Mino(gc, new Vector2D(pos, 2, 0), Color.SKYBLUE)
			});
		}
	},
	O {
		@Override
		public Tetromino create(GridCanvas gc) {
			Vector2D pos = gc.getSpawnPos(this);
			return new Tetromino(this, pos, new Vector2D(pos, 0.0, -0.0), new Mino[] { 
					new Mino(gc, new Vector2D(pos, 0, 0), Color.GOLD),
					new Mino(gc, new Vector2D(pos, 1, 0), Color.GOLD),
					new Mino(gc, new Vector2D(pos, 0, 1), Color.GOLD),
					new Mino(gc, new Vector2D(pos, 1, 1), Color.GOLD)
			});
		}
	},
	T {
		@Override
		public Tetromino create(GridCanvas gc) {
			Vector2D pos = gc.getSpawnPos(this);
			return new Tetromino(this, pos, new Vector2D(pos, 0.0, 0.0), new Mino[] { 
					new Mino(gc, new Vector2D(pos, 0, 0), Color.DARKORCHID),
					new Mino(gc, new Vector2D(pos, -1, 0), Color.DARKORCHID),
					new Mino(gc, new Vector2D(pos, 0, 1), Color.DARKORCHID),
					new Mino(gc, new Vector2D(pos, 1, 0), Color.DARKORCHID)
			});
		}
	},
	S {
		@Override
		public Tetromino create(GridCanvas gc) {
			Vector2D pos = gc.getSpawnPos(this);
			return new Tetromino(this, pos, new Vector2D(pos, 0.0, 0.0), new Mino[] { 
					new Mino(gc, new Vector2D(pos, 0, 0), Color.LIMEGREEN),
					new Mino(gc, new Vector2D(pos, -1, 0), Color.LIMEGREEN),
					new Mino(gc, new Vector2D(pos, 0, 1), Color.LIMEGREEN),
					new Mino(gc, new Vector2D(pos, 1, 1), Color.LIMEGREEN)
			});
		}
	},
	Z {
		@Override
		public Tetromino create(GridCanvas gc) {
			Vector2D pos = gc.getSpawnPos(this);
			return new Tetromino(this, pos, new Vector2D(pos, 0.0, -0.0), new Mino[] { 
					new Mino(gc, new Vector2D(pos, 0, 0), Color.RED),
					new Mino(gc, new Vector2D(pos, 0, 1), Color.RED),
					new Mino(gc, new Vector2D(pos, -1, 1), Color.RED),
					new Mino(gc, new Vector2D(pos, 1, 0), Color.RED)
			});
		}
	},
	J {
		@Override
		public Tetromino create(GridCanvas gc) {
			Vector2D pos = gc.getSpawnPos(this);
			return new Tetromino(this, pos, new Vector2D(pos, 0.0, -0.0), new Mino[] { 
					new Mino(gc, new Vector2D(pos, 0, 0), Color.ROYALBLUE),
					new Mino(gc, new Vector2D(pos, -1, 0), Color.ROYALBLUE),
					new Mino(gc, new Vector2D(pos, -1, 1), Color.ROYALBLUE),
					new Mino(gc, new Vector2D(pos, 1, 0), Color.ROYALBLUE)
			});
		}
	},
	L {
		@Override
		public Tetromino create(GridCanvas gc) {
			Vector2D pos = gc.getSpawnPos(this);
			return new Tetromino(this, pos, new Vector2D(pos, 0.0, 0.0), new Mino[] { 
					new Mino(gc, new Vector2D(pos, 0, 0), Color.DARKORANGE),
					new Mino(gc, new Vector2D(pos, -1, 0), Color.DARKORANGE),
					new Mino(gc, new Vector2D(pos, 1, 0), Color.DARKORANGE),
					new Mino(gc, new Vector2D(pos, 1, 1), Color.DARKORANGE)
			});
		}
	};
	
	/**
	 * Calculate the position translations to check when attempting 
	 * to Wall Kick a Tetromino into a valid position when it attempts 
	 * to rotate.
	 * 
	 * Employs the simplified Wall Kick logic used by TTC
	 * 
	 * @param t, Tetromino being rotated
	 * @param d, rotation direction
	 * @return, list of translations to check
	 */
	public static Vector2D[] getWallKickTranslations(Tetromino t, RotateDirection d) {
		Vector2D[] current_offsets, new_offsets, translations; 
		
		switch (t.piece) {
		case I:
			current_offsets = WALL_KICK_OFFSET_I.get(t.getRotState());
			new_offsets = WALL_KICK_OFFSET_I.get(t.getRotState().rotate(d));
			break;
		case O:
			current_offsets = WALL_KICK_OFFSET_O.get(t.getRotState());
			new_offsets = WALL_KICK_OFFSET_O.get(t.getRotState().rotate(d));
			break;
		case J:
		case L:
		case S:
		case T:
		case Z:
		default:
			current_offsets = WALL_KICK_OFFSET_JLSTZ.get(t.getRotState());
			new_offsets = WALL_KICK_OFFSET_JLSTZ.get(t.getRotState().rotate(d));
			break;
		}
		
		translations = new Vector2D[current_offsets.length];
		for (int i=0; i<current_offsets.length; i++) {
			translations[i] = calculateWallKickTranslation(current_offsets[i], new_offsets[i]);
		}
		return translations;
	}
	
	private static Vector2D calculateWallKickTranslation(Vector2D offset1, Vector2D offset2) {
		return new Vector2D(offset1.getAbsX()-offset2.getAbsX(), offset1.getAbsY()-offset2.getAbsY());
	}
	
	/**
	 * Storage of Wall Kick Offset data for the Pieces J, L, S, T, Z
	 */
	private static final Map<RotateState,Vector2D[]> WALL_KICK_OFFSET_JLSTZ = new HashMap<RotateState,Vector2D[]>();
	static {
		WALL_KICK_OFFSET_JLSTZ.put(RotateState.ZERO, new Vector2D[] {
				new Vector2D(0, 0),
				new Vector2D(0, 0),
				new Vector2D(0, 0),
				new Vector2D(0, 0),
				new Vector2D(0, 0)
		});
		WALL_KICK_OFFSET_JLSTZ.put(RotateState.RIGHT, new Vector2D[] {
				new Vector2D(0, 0),
				new Vector2D(1, 0),
				new Vector2D(1, -1),
				new Vector2D(0, 2),
				new Vector2D(1, 2)
		});
		WALL_KICK_OFFSET_JLSTZ.put(RotateState.TWO, new Vector2D[] {
				new Vector2D(0, 0),
				new Vector2D(0, 0),
				new Vector2D(0, 0),
				new Vector2D(0, 0),
				new Vector2D(0, 0)
		});
		WALL_KICK_OFFSET_JLSTZ.put(RotateState.LEFT, new Vector2D[] {
				new Vector2D(0, 0),
				new Vector2D(-1, 0),
				new Vector2D(-1, -1),
				new Vector2D(0, 2),
				new Vector2D(-1, 2)
		});
	}
	
	/**
	 * Storage of Wall Kick Offset data for the Pieces I
	 */
	private static final Map<RotateState,Vector2D[]> WALL_KICK_OFFSET_I = new HashMap<RotateState,Vector2D[]>();
	static {
		WALL_KICK_OFFSET_I.put(RotateState.ZERO, new Vector2D[] {
				new Vector2D(0, 0),
				new Vector2D(-1, 0),
				new Vector2D(2, 0),
				new Vector2D(-1, 0),
				new Vector2D(2, 0)
		});
		WALL_KICK_OFFSET_I.put(RotateState.RIGHT, new Vector2D[] {
				new Vector2D(-1, 0),
				new Vector2D(0, 0),
				new Vector2D(0, 0),
				new Vector2D(0, 1),
				new Vector2D(0, -2)
		});
		WALL_KICK_OFFSET_I.put(RotateState.TWO, new Vector2D[] {
				new Vector2D(-1, 1),
				new Vector2D(1, 1),
				new Vector2D(-2, 1),
				new Vector2D(1, 0),
				new Vector2D(-2, 0)
		});
		WALL_KICK_OFFSET_I.put(RotateState.LEFT, new Vector2D[] {
				new Vector2D(0, 1),
				new Vector2D(0, 1),
				new Vector2D(0, 1),
				new Vector2D(0, -1),
				new Vector2D(0, 2)
		});
	}
	
	/**
	 * Storage of Wall Kick Offset data for the Pieces O
	 */
	private static final Map<RotateState,Vector2D[]> WALL_KICK_OFFSET_O = new HashMap<RotateState,Vector2D[]>();
	static {
		WALL_KICK_OFFSET_O.put(RotateState.ZERO, new Vector2D[] {
				new Vector2D(0, 0)
		});
		WALL_KICK_OFFSET_O.put(RotateState.RIGHT, new Vector2D[] {
				new Vector2D(0, -1)
		});
		WALL_KICK_OFFSET_O.put(RotateState.TWO, new Vector2D[] {
				new Vector2D(-1, -1)
		});
		WALL_KICK_OFFSET_O.put(RotateState.LEFT, new Vector2D[] {
				new Vector2D(-1, 0)
		});
	}
}