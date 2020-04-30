package sudoku;

/**
 * Sudoku board is not formatted correctly 
 * eg. incorrect rows/columns, illegal character found
 * @author Ava Downey
 * @version 4/22/20
 */
public class IllegalDataFormatException extends RuntimeException {
	
	/**
	 * catches error and reads message.
	 * errors may include incorrect rows/columns, illegal character found, etc.
	 * @param error message to be displayed
	 */
	public IllegalDataFormatException(String message) {
		super(message);
	}
}
