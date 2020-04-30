package sudoku;

/**
 * Sudoku board is invalid
 * eg. too many of one number in a row, column, box...
 * @author Ava Downey
 * @version 4/22/20
 */
public class IllegalConfigurationException extends RuntimeException {
	
	/**
	 * catches error and reads message. 
	 * errors may include too many of one number in a row, column, box... etc.
	 * @param error message to be displayed 
	 */
	public IllegalConfigurationException(String message) {
		super(message);
	}

}
