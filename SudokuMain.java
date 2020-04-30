package sudoku;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Allows a user to play a game of Sudoku
 * @author Ava Downey
 * @version 4/22/20
 */
public class SudokuMain {

	public static void main(String[] args) {
		
		// gets random puzzle
		PuzzlePool puzzlePool = new PuzzlePool("data");	
		SudokuPuzzle puzzle = puzzlePool.getRandomPuzzle();

		int row;
		int col;
		int value;
		String choice = "a";
		
		// initial setup of sudoku
		System.out.println(puzzlePool.toString());
		System.out.println("\nHere is the good puzzle chosen! \n");
		puzzle.initializePuzzle();
		
		while (true) {
			Scanner scan = new Scanner(System.in);
			System.out.println(puzzle + "\nWould you like to add a value(s), get"
									  + " possible values(g), clear the puzzle(c),"
									  + " get new puzzle(r), or quit(q)?");
			choice = scan.nextLine().toLowerCase();

			
			if (choice.equals("s")) {	// adds a value to the puzzle in a specific location
				
				try {
					
					System.out.println("What row?  ");
					row = scan.nextInt();
					row -= 1;
					System.out.println("What column?  ");
					col = scan.nextInt();
					col -= 1;
					System.out.println("What value?  ");
					value = scan.nextInt();
					boolean goodGuess = puzzle.addGuess(row, col, value);
					
					if (goodGuess == false) {
						System.out.println("\nThe value you inputed cannot be added "
								         + "to the puzzle. \n");
						
					// tells user if puzzle is completed
					} else if (puzzle.isFull() == true) {
						System.out.println("Congratulations, you finished the puzzle!");
						System.out.println("Type \"r\" to get a new puzzle, or \"q\" to quit.\n");
					}
					
				// catch non-integer inputs	
				} catch (InputMismatchException e) {
					System.out.println("The last value you inputed is not an integer\n");
				}
				
			} else if (choice.equals("c")) {		// clears the puzzle
				puzzle.initializePuzzle();
				System.out.println("Puzzle cleared!");
				
			} else if (choice.equals("q")) {		// quits the puzzle
				System.out.println("\ngoodbye!");
				scan.close();
				break;
				
			} else if (choice.equals("g")) {	// gets possible values at a specific point in the puzzle
				
				try {
					System.out.println("What row?  ");
					row = scan.nextInt();
					row -= 1;
					System.out.println("What column?  ");
					col = scan.nextInt();
					col -= 1;
					boolean[] allowedValues = puzzle.getAllowedValues(row, col);
					String possibleValues = "";
					// determines all possible values in specified location
					for (int i=1; i<allowedValues.length; i++) {
						boolean allowedValue = allowedValues[i];
						if (allowedValue == true) {
							possibleValues += i + ", ";
						}
						
					}
					
					System.out.println("Possible Values: "+ possibleValues + "\n");
				
					// catch non-integer inputs
				} catch (InputMismatchException e) {
					System.out.println("The last value you inputed is not an integer\n");
				}
				
				
			} else if (choice.equals("r")) {	// resets the puzzle board with a new puzzle
				puzzle = puzzlePool.getRandomPuzzle();
				System.out.println("Here is your new puzzle! \n");
				puzzle.initializePuzzle();
			}
			
		}
		
	}

}
