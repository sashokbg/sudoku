package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Sudoku {
	private List<List<Cell>> cells;
	private static final String NEW_LINE = "\n* * * * * * * * * * * * * * * * * *\n";
	
	public List<List<Cell>> getCells() {
		return cells;
	}

	public void setCells(List<List<Cell>> cells) {
		this.cells = cells;
	}

	public List<List<Cell>> getTransposedCells() {
		return transposedCells;
	}

	public void setTransposedCells(List<List<Cell>> transposedCells) {
		this.transposedCells = transposedCells;
	}

	private List<List<Cell>> transposedCells;

	public Sudoku() {
		cells = new ArrayList<>();
		for(int i=0; i<9; i++){
			List<Cell> row = new ArrayList<>();
			for(int j=0; j<9; j++){
				Cell newCell = new Cell();
				newCell.setY(i);
				newCell.setX(j);
				row.add(newCell);
			}
			cells.add(row);
		}
		transposedCells = new ArrayList<>();
		
		for(int i=0; i<9; i++){
			List<Cell> row = new ArrayList<>();
			for(int j=0; j<9; j++){
				row.add(cells.get(j).get(i));
			}
			transposedCells.add(row);
		}
	}
	
	public void initWithSequential(){
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				cells.get(i).get(j).setValue(j+1);;
			}
		}
	}

	public void initWithRandoms(){
		Random rand = new Random(new Date().getTime());
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				cells.get(i).get(j).setValue(rand.nextInt(9)+1);
			}
		}
	}
	
	public void initFromCsv(String csv){
		String[] numbers = csv.split(",");
		
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(numbers[i*9+j]==null || numbers[i*9+j].equals(" "))
					cells.get(i).get(j).setValue(null);
				else{
					cells.get(i).get(j).setValue(Integer.valueOf(numbers[i*9+j]));
					cells.get(i).get(j).setImmutable(true);
				}
			}
		}
	}
	
	public List<Integer> getAllowedNumbers(int x, int y){
		List<Integer> allowedNumbers = new ArrayList<>();
		for(int i=1;i<10;i++){
			if(isAllowed(i,x,y)){
				allowedNumbers.add(i);
			}
		}
		
		return allowedNumbers;
	}

	/**
	 * 
	 * @param i the number to check
	 * @param x position
	 * @param y position
	 * @return
	 */
	public boolean isAllowed(int i, int x, int y) {
		//check in row
		if(cells.get(x).contains(new Cell(i)))
			return false;
		//check in cols
		if(transposedCells.get(y).contains(new Cell(i)))
			return false;
		//check in square
		if(getSquare(x,y).contains(new Cell(i)))
			return false;
		
		return true;
	}

	public List<Cell> getSquare(int x, int y) {
		//define quadrant
		int x_quad = x/3;
		int y_quad = y/3;
		
		List<Cell> square = new ArrayList<>();
		for(int i=x_quad*3; i<x_quad*3+3; i++){
			for(int j=y_quad*3; j<y_quad*3+3; j++){
				square.add(cells.get(i).get(j));
			}
		}
		
		return square;
	}

	public boolean solve(Sudoku sudoku) {
		//pick first empty cell
		Cell emptyCell = null;
		for(int i=0; i<9; i++){
			if(emptyCell!=null)
				break;
			for(int j=0; j<9; j++){
				if(cells.get(i).get(j).getValue()==null){
					emptyCell = cells.get(i).get(j);
					break;
				}
			}
		}
		
		if(emptyCell!=null && emptyCell.getValue()==null){
			List<Integer> allowedNumbers = getAllowedNumbers(emptyCell.getY(),emptyCell.getX());
			Collections.shuffle(allowedNumbers);
			if(!allowedNumbers.isEmpty()){
				boolean solved = false;
				for(Integer allowedNumber: allowedNumbers){
					emptyCell.setValue(allowedNumber);
					solved = solve(sudoku);
					if(!solved){
						emptyCell.setValue(null);
						for(int i=emptyCell.getY(); i<9; i++){
							for(int j=emptyCell.getX(); j<9; j++){
								Cell cell = cells.get(i).get(j);
								if(!cell.isImmutable())
									cells.get(i).get(j).setValue(null);
							}
						}
					}
					else{
						break;
					}
				}
				return solved;
			}
			else{
				//OOPS Wrong sudoku
				return false;
			}
		}
		return true;
	}
	
	public String toStringTransposed(){
		String result = "";
		
		for(int i=0; i<9; i++){
			if(i%3==0){
				result+=NEW_LINE;
			}
			else{
				result+="\n";
			}
			result+="*";
			for(int j=0; j<9; j++){
				if(j%3==0 && j>0)
					result+=" * ";
				result+=transposedCells.get(i).get(j);
			}
			result+="* ";
		}
		result+=NEW_LINE;
		
		return result;
	}
	
	@Override
	public String toString(){
		String result = "";
		
		for(int i=0; i<9; i++){
			if(i%3==0){
				result+=NEW_LINE;
			}
			else{
				if(i>0)
					result+="\n";
			}
			result+="*";
			for(int j=0; j<9; j++){
				if(j%3==0 && j>0)
					result+=" * ";
				result+=cells.get(i).get(j);
			}
			result+="* ";
		}
		result+=NEW_LINE;
		
		return result;
	}
}
