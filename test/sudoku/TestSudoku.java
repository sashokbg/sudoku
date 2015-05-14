package sudoku;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestSudoku {

	@Test
	public void testTransposed(){
		Sudoku sudoku = new Sudoku();
		sudoku.initWithSequential();
		
		Cell cell = sudoku.getCells().get(0).get(8);
		Cell trqnsposedCell = sudoku.getTransposedCells().get(8).get(0);
		
		assertTrue(cell==trqnsposedCell);
		cell.setValue(0);
		cell.setValue(5);
		assertTrue(trqnsposedCell.getValue()==5);
	}
	
	@Test
	public void testGetSquare(){
		Sudoku sudoku = new Sudoku();
		sudoku.getCells().get(3).get(3).setValue(1);
		sudoku.getCells().get(4).get(4).setValue(2);
		sudoku.getCells().get(4).get(5).setValue(3);
		sudoku.getCells().get(6).get(6).setValue(4);
		System.out.println(sudoku.toString());
		List<Cell> square = sudoku.getSquare(4, 5);
		assertTrue(square.contains(new Cell(1)));
		assertTrue(square.contains(new Cell(2)));
		assertTrue(square.contains(new Cell(3)));
		assertFalse(square.contains(new Cell(4)));
	}
	
	@Test
	public void testIsAllowed(){
		Sudoku sudoku = new Sudoku();
		
		sudoku.getCells().get(0).get(1).setValue(1);
		assertFalse(sudoku.isAllowed(1, 0, 0));
		
		sudoku = new Sudoku();
		sudoku.getCells().get(1).get(0).setValue(1);
		assertFalse(sudoku.isAllowed(1, 0, 0));
		
		sudoku = new Sudoku();
		sudoku.getCells().get(3).get(0).setValue(1);
		sudoku.getCells().get(0).get(3).setValue(1);
		assertFalse(sudoku.isAllowed(1, 0, 0));
		assertTrue(sudoku.isAllowed(2, 0, 0));
		
		sudoku = new Sudoku();
		sudoku.getCells().get(2).get(0).setValue(1);
		sudoku.getCells().get(0).get(3).setValue(1);
		assertFalse(sudoku.isAllowed(1, 0, 0));
	}
	
	@Test
	public void getAllowedNumbers(){
		Sudoku sudoku = new Sudoku();
		sudoku.getCells().get(0).get(0).setValue(1);
		sudoku.getCells().get(0).get(1).setValue(2);
		sudoku.getCells().get(0).get(2).setValue(3);
		sudoku.getCells().get(1).get(1).setValue(4);
		sudoku.getCells().get(1).get(0).setValue(5);
		System.out.println(sudoku);
		
		List<Integer> allowedNumbers = sudoku.getAllowedNumbers(1, 2);
		
		assertTrue(allowedNumbers.contains(6));
		assertTrue(allowedNumbers.contains(7));
		assertTrue(allowedNumbers.contains(8));
		assertTrue(allowedNumbers.contains(9));
		
		assertFalse(allowedNumbers.contains(1));
		assertFalse(allowedNumbers.contains(2));
		assertFalse(allowedNumbers.contains(3));
		assertFalse(allowedNumbers.contains(4));
		assertFalse(allowedNumbers.contains(5));
	}
	
	/**
	 * Based on http://www.programme.tv/sudoku/jouer.php?annee_grille=2015&jour_grille=134&niveau_grille=difficile
	 * 
	 */
	@Test
	public void testSovleDifficult(){
		Sudoku sudoku = new Sudoku();
		String csvSudoku1=
				"1, , ,4, ,6, , , ,"+
				" ,8, ,3, ,7,1, , ,"+
				" ,6,5, , , , , , ,"+
				" , ,4, , , , ,9, ,"+
				"7,5,9, ,4, ,6,3,1,"+
				" ,2, , , , ,5, , ,"+
				" , , , , , ,7,6, ,"+
				" , ,3,2, ,8, ,1, ,"+
				" , , ,1, ,4, , ,5,";
		sudoku.initFromCsv(csvSudoku1);
		sudoku.solve(sudoku);
		
		assertEquals(sudoku.getCells().get(0).get(1).getValue(), new Integer(3));
		assertEquals(sudoku.getCells().get(0).get(2).getValue(), new Integer(7));
		assertEquals(sudoku.getCells().get(1).get(0).getValue(), new Integer(9));
		assertEquals(sudoku.getCells().get(1).get(2).getValue(), new Integer(2));
		
		assertEquals(sudoku.getCells().get(3).get(3).getValue(), new Integer(6));
		assertEquals(sudoku.getCells().get(3).get(4).getValue(), new Integer(3));
		assertEquals(sudoku.getCells().get(4).get(5).getValue(), new Integer(2));
	}
	
	/**
	 * http://www.programme.tv/sudoku/jouer.php?annee_grille=2015&jour_grille=134&niveau_grille=expert
	 * 
	 */
	@Test
	public void testSovleDiabolic(){
		Sudoku sudoku = new Sudoku();
		String csvSudoku2=
				" , , , ,5, , ,4, ,"+
				"1, , , ,9,4,2, , ,"+
				" ,7, , , ,2, ,6, ,"+
				" ,8,1, , , , , ,3,"+
				" , ,4,2, ,8,1, , ,"+
				"3, , , , , ,8,2, ,"+
				" ,1, ,4, , , ,5, ,"+
				" , ,9,8,2, , , ,7,"+
				" ,2, , ,1, , , , ,";
		sudoku.initFromCsv(csvSudoku2);
		sudoku.solve(sudoku);
		
		assertEquals(sudoku.getCells().get(0).get(0).getValue(), new Integer(9));
		assertEquals(sudoku.getCells().get(0).get(1).getValue(), new Integer(3));
		assertEquals(sudoku.getCells().get(0).get(2).getValue(), new Integer(2));
		assertEquals(sudoku.getCells().get(1).get(1).getValue(), new Integer(6));
		assertEquals(sudoku.getCells().get(1).get(2).getValue(), new Integer(8));
		
		assertEquals(sudoku.getCells().get(3).get(3).getValue(), new Integer(9));
		assertEquals(sudoku.getCells().get(3).get(4).getValue(), new Integer(4));
		assertEquals(sudoku.getCells().get(4).get(4).getValue(), new Integer(3));
	}
	
	@Test
	public void generateEmpty(){
		Sudoku sudoku = new Sudoku();
		sudoku.solve(sudoku);
		
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				Integer valueToTest = sudoku.getCells().get(i).get(j).getValue();
				sudoku.getCells().get(i).get(j).setValue(null);
				assertTrue(sudoku.isAllowed(valueToTest, i, j));
				sudoku.getCells().get(i).get(j).setValue(valueToTest);
			}
		}
	}
	
	@Test
	public void testPrint(){
		Sudoku sudoku = new Sudoku();
		System.out.println(sudoku.toString());
		System.out.println("");
		System.out.println(sudoku.toStringTransposed());
	}
}
