package sudoku;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * organizes the puzzles and randomly selects a puzzle from the puzzle pool
 * @author Ava Downey
 * @version 4/22/20
 */
public class PuzzlePool {

	private List<SudokuPuzzle> goodPuzzles;
	private List<File> badPuzzles;

	/**
	 * creates a puzzle pool
	 * @param rootDirectory main directory to be read from
	 */
	public PuzzlePool(String rootDirectory) {
		
		goodPuzzles = new ArrayList<SudokuPuzzle>();
		badPuzzles = new ArrayList<File>();
		File file = new File(rootDirectory);

		if (!file.exists()) {
			throw new IllegalArgumentException(rootDirectory + " does not exist");
		}
		
		if (!file.isDirectory()) {
			throw new IllegalArgumentException(rootDirectory + " is not a valid directory");
		}
		
		readPuzzles(file);

	}
	
	/**
	 * reads all the files in a directory... also reads subdirectories files
	 * @param directory name of the directory to be read
	 */
	private void readPuzzles(File directory) {
		
		File[] listFiles = directory.listFiles();
		
		for (int i = 0; i < listFiles.length; i++) {
			
			if (listFiles[i].isDirectory()) {
				readPuzzles(listFiles[i]);
				
			} else {
				
				try {
					
					SudokuPuzzle puzzle = new SudokuPuzzle(listFiles[i]);
					puzzle.initializePuzzle();
					
					// holds entire good puzzle
					goodPuzzles.add(puzzle);
					
				} catch (IllegalDataFormatException e) {
					// holds the file names of the bad puzzles
					badPuzzles.add(listFiles[i]);
					
				} catch (IllegalConfigurationException e) {
					// holds the file names of the bad puzzles
					badPuzzles.add(listFiles[i]);
					
				}
				
			}
			
		}
		
	}
	
	/**
	 * random selects a good puzzle from the goodPuzzle array list
	 * @return random good puzzle
	 */
	public SudokuPuzzle getRandomPuzzle() {
		Random random = new Random();
		return goodPuzzles.get(random.nextInt(goodPuzzles.size()-1));
	}
	
	/**
	 * returns good puzzle boards
	 * @return boards of good puzzles
	 */
	public List<SudokuPuzzle> getGoodPuzzles() {
		return goodPuzzles;
	}
	
	/**
	 * returns bad file names
	 * @return list of bad puzzle files
	 */
	public List<File> getBadPuzzles() {
		return badPuzzles;
	}

	/**
	 * lists the total number of puzzles, as well as total of good and bad
	 */
	@Override
	public String toString() {
		return "There are " + (goodPuzzles.size() + badPuzzles.size()) + " total"
			 + " puzzles... \n\t" + badPuzzles.size() + " are bad puzzles \n\t"
			 + goodPuzzles.size() + " are good puzzles \n\nThe bad puzzles are..." 
			 + badPuzzles;
	}
	
	

}
