package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * RandomGenerator
 * @author Josh Chia
 * 
 * An enumeration generic implementation of the BPS Random Generator 
 * that conforms to the Tetris Guideline.
 * 
 * Uses a buffer to adequately match the the look ahead needed by
 * PiecePreview whilst staying true to the Guideline.
 * 
 */

public class RandomGenerator<T extends Enum<T>> {
	int buffer_size;
	Class<T> enum_type;
	Stack<T> sequence;
	
	/**
	 * Construct a BPS random generator
	 * @param e, the bag to randomly pick from
	 * @param bs, the buffer to maintain for previewing
	 */
	public RandomGenerator(Class<T> e, int bs) {
		buffer_size = (bs<=0) ? 1: bs;
		enum_type = e;
		sequence = new Stack<T>();
		appendSequence();
	}
	
	/**
	 * Generate a BPS random sequence using Collections interface.
	 * Collections interface employs Fisher-Yates for shuffle.
	 * @return, randomised list of enums
	 */
	public List<T> randomSequence() {
		List<T> sequence = new ArrayList<T>(Arrays.asList(enum_type.getEnumConstants()));
		Collections.shuffle(sequence);
		return sequence;
	}
	
	/**
	 * Get the next Piece in sequence
	 * Extend the sequence if needed to maintain the amount 
	 * to look ahead (preview)
	 * @return, next Piece
	 */
	public T next() {
		if (sequence.size() <= buffer_size) {
			appendSequence();
		}
		return sequence.pop();
	}
	
	/**
	 * Peek at the next Piece
	 * @return, next Piece
	 */
	public T peek() {
		return sequence.peek(); 
	}
	
	/**
	 * Get list of the Pieces to preview
	 * @return, list of upcoming Pieces 
	 */
	public List<T> getPreviewList() {
		List<T> preview = new ArrayList<T>(buffer_size);
		
		for (int i=0; i<buffer_size; i++) {
			preview.add(i, sequence.elementAt(sequence.size()-1-i));
		}
		
		return preview;
	}
	
	/**
	 * Extend the random sequence
	 */
	private void appendSequence() {
		Stack<T> s = new Stack<T>();
		s.addAll(randomSequence());
		s.addAll(sequence);
		sequence = s;
	}
}