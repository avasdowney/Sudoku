package sudoku;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * unit testing for sudoku program
 * @author Ava Downey
 * @version 4/22/20
 */
public class SudokuPuzzleTest {

	@Test
	public void testReadValidBoard() {
		SudokuPuzzle puzzle = new SudokuPuzzle("data.txt");
		puzzle.initializePuzzle();
		
		//assertArrayEquals({'1'}, puzzle);
	}

	@Test(expected = IllegalDataFormatException.class)
	public void testReadBoardTooManyRows() {
		SudokuPuzzle puzzle = new SudokuPuzzle("dataTooManyRows.txt");
		puzzle.initializePuzzle();
	}

}
