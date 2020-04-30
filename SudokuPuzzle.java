package sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents what needs to happen in order to play a game of sudoku
 * @author Ava Downey
 * @version 4/22/20
 */
public class SudokuPuzzle {
	private int board[][];
	private boolean orig[][];
	private String boardFile;

	/**
	 * instantiates the sudoku puzzle with a string
	 * @param boardFile string of board name to be read
	 */
	public SudokuPuzzle(String boardFile) {
		board = new int[9][9];
		orig = new boolean[9][9];
		this.boardFile = boardFile;
	}
	
	/**
	 * instantiates the sudoku puzzle with a file
	 * @param boardFile file of board name to be read
	 */
	public SudokuPuzzle(File boardFile) {
		this(boardFile.toString());
	}

	/**
	 * prints out sudoku board
	 */
	public String toString() {
		String results = "    1 2 3  | 4 5 6  | 7 8 9\n";
		results += "----------------------------\n";
		// vertical lines
		for (int i = 0; i < 9; i++) {
			results = results + (i + 1);
			for (int j = 0; j < 9; j++) {
				String seperator = "";
				if (j % 3 == 0) {
					seperator = " | ";
				}
				results = results + seperator;
				// adds values into the board
				String value = String.valueOf(board[i][j]);
				if (board[i][j] == 0) {
					value = ".";
				}
				results = results + value + " ";
			}
			// horizontal lines
			if ((i + 1) % 3 == 0) {
				results += "\n----------------------------";
			}
			results = results + "\n";
		}
		return results;
	}

	/**
	 * Initialize a square with value and sets orig at that location to true
	 * 
	 * @param row   the row number for the square
	 * @param col   the column number for the square
	 * @param value the value in the square
	 */
	public void addInitial(int row, int col, int value) {
		board[row][col] = value;
		orig[row][col] = true;
	}

	/**
	 * Initialize a square with a value and sets orig at that location to false Adds
	 * a value to the puzzle if a value can be added
	 * 
	 * @param row   the row number for the square
	 * @param col   the column number for the square
	 * @param value the value in the square
	 */
	public boolean addGuess(int row, int col, int value) {
		int originalValue = board[row][col];
		board[row][col] = value;
		// if guess cant possibly work, dont let it pass
		if (checkPuzzle() == false || value > 9) {
			board[row][col] = originalValue;
			return false;
		}

		return true;

	}

	/**
	 * Checks to see if a value can be added to the puzzle
	 * 
	 * @return returns true if a value can be
	 */
	public boolean checkPuzzle() {
		boolean check = false;
		// check row
		for (int i = 0; i < 9; i++) {
			check = checkRow(i);
			if (check == false) {
				return false;
			}
		}
		for (int j = 0; j < 9; j++) {
			// check column
			check = checkCol(j);
			if (check == false) {
				return false;
			}
		}
		for (int i = 0; i < 3; i++) {
			// check subarray
			for (int j = 0; j < 3; j++) {
				check = checkSubArray(i, j);
				if (check == false) {
					return false;
				}
				
			}

		}

		// return true if pass all
		return true;
	}

	/**
	 * checks a row to determine if each digit appears only once
	 * 
	 * @param row row number in the board
	 * @return true if there is no duplicate digit in the row
	 */
	private boolean checkRow(int row) {
		boolean[] valuesSeen = new boolean[10];

		for (int i = 0; i < 9; i++) {
			int value = board[row][i];
			if (valuesSeen[value] == true && value != 0) {
				return false;
			} else {
				valuesSeen[value] = true;
			}

		}

		return true;

	}

	/**
	 * checks a column to make sure each digit only appears once
	 * 
	 * @param col column in the board
	 * @return true if there is no duplicate digit in the column
	 */
	private boolean checkCol(int col) {
		boolean[] valuesSeen = new boolean[10];

		for (int i = 0; i < 9; i++) {
			int value = board[i][col];
			if (valuesSeen[value] == true && value != 0) {
				return false;
			} else {
				valuesSeen[value] = true;
			}

		}

		return true;

	}

	/**
	 * checks a box to make sure each digit only appears once
	 * 
	 * @param row row in the board
	 * @param col column in the board
	 * @return returns true if no digits are repeated
	 */
	private boolean checkSubArray(int subRow, int subCol) {
		// separates board into a 3*3 grid instead of 9*9
		int startRow = subRow * 3;
		int startCol = subCol * 3;
		boolean[] valuesSeen = new boolean[10];

		// checks each of the larger boxes / subarrays
		for (int i = startRow; i < startRow + 3; i++) {
			for (int j = startCol; j < startCol + 3; j++) {
				int value = board[i][j];
				// checks to see if value is repeated
				if (valuesSeen[value] == true && value != 0) {
					return false;
				} else {
					valuesSeen[value] = true;
				}
			}
		}
		return true;
	}

	/**
	 * returns the value in a specific row and column
	 * 
	 * @param row row in the board
	 * @param col column in the board
	 * @return returns the value of the in a specific row and column
	 */
	public int getValueIn(int row, int col) {
		return board[row][col];
	}

	/**
	 * returns a one-dimensional array of nine booleans, each of which corresponds
	 * to a digit and is true if the digit can be placed in the given square without
	 * violating the restrictions
	 * 
	 * @param row row in the board
	 * @param col column in the board
	 * @return allowed sudoku values
	 */
	public boolean[] getAllowedValues(int row, int col) {
		boolean[] results = new boolean[9];

		for (int i = 0; i < 9; i++) {
			int originalValue = getValueIn(row, col);

			// if guess is good add true to boolean[]
			boolean goodGuess = addGuess(row, col, i);
			if (goodGuess == true) {
				results[i] = true;
			} else {
				results[i] = false;
			}

			addGuess(row, col, originalValue);

		}

		return results;

	}

	/*
	 * creates initial sudoku puzzle based on a file being read (boardFile)
	 */
	public void initializePuzzle() {

		try (BufferedReader readFrom = new BufferedReader(new FileReader(boardFile))) {

			int row = 0;
			String line;

			// reads one line at a time until done
			while ((line = readFrom.readLine()) != null) {
				
				// EXCEPTION 1:  Make sure there aren't too many rows
				if (row == 9) {
					throw new IllegalDataFormatException("Data file has too many rows.");
				}
				
				// EXCEPTION 1:  checks for illegal row length
				if (line.length() != 9) {
					throw new IllegalDataFormatException("Illegal line length. Nine characters expected but "
							+ line.length() + " characters received.");
				}

				for (int column = 0; column < 9; column++) {
					char nextChar = line.charAt(column);
					
					// EXCEPTION 1:  checks if valid input
					if (!isValidSudokoValue(nextChar)) {
						throw new IllegalDataFormatException("Received invalid character that was " + nextChar
								+ ". Only numbers 1-9 and . are allowed.");
					}
					
					// Convert the character just read into the proper int,
					// taking care to convert '.' to 0.
					int boardValue = 0;
					if (nextChar != '.') {
						boardValue = Integer.valueOf(String.valueOf(nextChar));
					}
					
					addInitial(row, column, boardValue);
				}
			
				row++;
				
			}
			
			// EXCEPTION 1:  checks to make sure file fills 9 rows
			if (row != 9) {
				throw new IllegalDataFormatException("Data file only has " + (row-1) + " rows.");
			}
			
			// EXCEPTION 2:  checks to make sure board is valid
			if (!checkPuzzle()) {
				throw new IllegalConfigurationException("Board cannot be solved.");
			}
		
		} catch (IOException e) {
			// EXCEPTION 2:  catch all for IOExceptions and throws that error message
			throw new IllegalConfigurationException(e.getMessage());
		}

	}

	/**
	 * checks if value is valid
	 * 
	 * @param nextChar character being read
	 * @return true if character is valid
	 */
	private boolean isValidSudokoValue(char nextChar) {
		boolean result = false;
		switch (nextChar) {
		case '1':
			result = true;
			break;
		case '2':
			result = true;
			break;
		case '3':
			result = true;
			break;
		case '4':
			result = true;
			break;
		case '5':
			result = true;
			break;
		case '6':
			result = true;
			break;
		case '7':
			result = true;
			break;
		case '8':
			result = true;
			break;
		case '9':
			result = true;
			break;
		case '.':
			result = true;
			break;
		default:
			result = false;
			break;
		}

		return result;
	}

	/**
	 * creates the sudoku board with initial values and blank values
	 * does not read from a file
	 */
	public void initializeDefaultPuzzle() {
		int[][] init = { { 5, 3, 9, 0, 0, 0, 4, 0, 0 }, 
						 { 7, 2, 8, 3, 0, 4, 9, 0, 0 }, 
						 { 6, 4, 1, 0, 0, 0, 7, 3, 0 },
						 { 4, 6, 2, 5, 3, 9, 8, 7, 1 }, 
						 { 3, 8, 5, 7, 2, 1, 6, 4, 9 }, 
						 { 1, 9, 7, 4, 6, 8, 2, 5, 3 },
						 { 2, 5, 6, 1, 8, 7, 3, 9, 4 }, 
						 { 9, 1, 3, 0, 4, 0, 5, 8, 7 }, 
						 { 8, 7, 4, 9, 5, 3, 1, 2, 6 } };
		for (int i = 0; i < init.length; i++) {
			for (int j = 0; j < init[i].length; j++) {
				addInitial(i, j, init[i][j]);
			}
			
		}

	}

	/**
	 * checks to see if the sudoku puzzle is filled
	 * 
	 * @return returns true if every square has a value
	 */
	public boolean isFull() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// makes sure there are no zeros on the board
				if (board[i][j] == 0) {
					return false;
				}
				
			}
			
		}

		return true;
	}

	/**
	 * changes all the non original squares back to blanks
	 */
	public void reset() {
		initializePuzzle();
	}

}
