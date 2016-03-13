package bg.alexander.sudoku;

public class Cell {
	private Integer value;
	private int x;
	private int y;
	private boolean immutable;

	public Cell() {
	}
	
	public Cell(Integer value) {
		super();
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object anotherObject){
		Cell anotherCell = (Cell) anotherObject;
		if(this==anotherCell)
			return true;
		
		if(this.getValue().equals(anotherCell.getValue()))
			return true;
		
		return false;
	}
	
	@Override
	public String toString(){
		if(value==null)
			return "| |";
		return "|"+value.toString()+"|";
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isImmutable() {
		return immutable;
	}

	public void setImmutable(boolean immutable) {
		this.immutable = immutable;
	}
}
