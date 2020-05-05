package sudoku;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
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
		
		goodPuzzles = new LinkedList<SudokuPuzzle>();
		badPuzzles = new LinkedList<File>();
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
		
		File allFiles[] = directory.listFiles();
		
		for (int i = 0; i < allFiles.length; i++) {
			
			if (allFiles[i].isDirectory()) {
				readPuzzles(allFiles[i]);
				
			} else {
				
				try {
					
					SudokuPuzzle puzzle = new SudokuPuzzle(allFiles[i]);
					puzzle.initializePuzzle();
					
					// holds entire good puzzle
					goodPuzzles.add(puzzle);
					
				} catch (IllegalDataFormatException e) {
					// holds the file names of the bad puzzles
					badPuzzles.add(allFiles[i]);
					
				} catch (IllegalConfigurationException e) {
					// holds the file names of the bad puzzles
					badPuzzles.add(allFiles[i]);
					
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
		
		String badPuzzleNames = "";
		
		for (Iterator<File> iterator = badPuzzles.iterator(); iterator.hasNext();) {
			File file = iterator.next();
			badPuzzleNames += file.getAbsolutePath() + "\n\t";
		}
		
		return "There are " + (goodPuzzles.size() + badPuzzles.size()) + " total"
			 + " puzzles... \n\t" + badPuzzles.size() + " are bad puzzles \n\t"
			 + goodPuzzles.size() + " are good puzzles \n\nThe bad puzzles are... \n\t" 
			 + badPuzzleNames;
	}

}
