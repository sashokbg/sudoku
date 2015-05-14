package sudoku;

public class Main {

	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();
//		String csvSudoku1=
//				"1, , ,4, ,6, , , ,"+
//				" ,8, ,3, ,7,1, , ,"+
//				" ,6,5, , , , , , ,"+
//				" , ,4, , , , ,9, ,"+
//				"7,5,9, ,4, ,6,3,1,"+
//				" ,2, , , , ,5, , ,"+
//				" , , , , , ,7,6, ,"+
//				" , ,3,2, ,8, ,1, ,"+
//				" , , ,1, ,4, , ,5,";
//		sudoku.initFromCsv(csvSudoku1);
//		sudoku.solve(sudoku);
//		System.out.println(sudoku.toString());
		
		sudoku = new Sudoku();
		String csvSudoku2=
				" , , , , ,2,6,5, ,"+
				" , ,4, , ,8, ,7, ,"+
				"9, , , , , ,2, , ,"+
				" ,4, ,3, , , , ,5,"+
				"8, ,7, , , ,4, ,6,"+
				"1, , , , ,6, ,9, ,"+
				" , ,2, , , , , ,1,"+
				" ,6, ,7, , ,8, , ,"+
				" ,1,9,8, , , , , ,";
		sudoku.initFromCsv(csvSudoku2);
		sudoku.solve(sudoku);
		System.out.println(sudoku.toString());
	}
}
