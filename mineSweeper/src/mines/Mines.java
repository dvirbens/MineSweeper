package mines;

import java.util.ArrayList;
import java.util.Random;

public class Mines {
	private CellInBoard[][] board;
	private int height, width, numMines;
	private boolean showAll = false;

	public Mines(int height, int width, int numMines) {
		this.height = height;
		this.width = width;
		this.numMines = numMines;
		board = new CellInBoard[height][width];
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
				board[i][j]=new CellInBoard();
		Random rand=new Random();
		for(int i=0;i<numMines;i++)
		{
			if(addMine(rand.nextInt(height), rand.nextInt(width))==false)
			{
				i--;
			}
		}
		
	}
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}

	// a class that describe a cell in the game board.
	public class CellInBoard {
		private int mine = 0;
		private int isOpen = 0;
		private int flag = 0;
		private int nearMines = 0;
	

	}

	// class for a cell.
	public class Cell {
		private int row;
		private int col;

		public Cell(int i, int j) {
			row = i;
			col = j;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}

	}

	// method that returns a list of all the Cell neighbors
	public ArrayList<Cell> neighborHood(int i, int j) {
		ArrayList<Cell> list = new ArrayList<Cell>();
		if (i - 1 >= 0) {
			list.add(new Cell(i - 1, j)); // add the neighbor above
			if (j - 1 >= 0)
				list.add(new Cell(i - 1, j - 1));// add the neighbor above left
			if (j + 1 < width)
				list.add(new Cell(i - 1, j + 1));// add the neighbor above right
		}
		if (i + 1 < height) {
			list.add(new Cell(i + 1, j));// add the neighbor below
			if (j - 1 >= 0)
				list.add(new Cell(i + 1, j - 1));// add the neighbor below left
			if (j + 1 < width)
				list.add(new Cell(i + 1, j + 1));// add the neighbor below right
		}

		if (j - 1 >= 0)
			list.add(new Cell(i, j - 1));// add the neighbor left
		if (j + 1 < width)
			list.add(new Cell(i, j + 1));// add the neighbor right

		return list;
	}

	// method that add a mine in cell and update the neighbors nearMines counter.
	public boolean addMine(int i, int j) {
		if (board[i][j].mine == 1)
			return false;
		board[i][j].mine = 1;
		ArrayList<Cell> list = new ArrayList<Cell>();
		list.addAll(neighborHood(i, j));
		for (Cell c : list)
			board[c.row][c.col].nearMines++;
		return true;
	}

	// method that opens a cell and all its neighbors if there are no mines around
	public boolean open(int i, int j) {
		if (board[i][j].mine == 1) // if this Cell contains a mine returns false.
			return false;
		if (board[i][j].isOpen == 1) // if this Cell is open return true.
			return true;
		board[i][j].isOpen = 1;
		if (board[i][j].nearMines != 0) // if there is mine near return true.
			return true;
		ArrayList<Cell> list = new ArrayList<Cell>();
		list.addAll(neighborHood(i, j));
		for (Cell c : list) // if there is no mines near open all neighbors.
			open(c.row, c.col);

		return true;
	}

	// method that place or remove a flag from a cell
	public boolean toggleFlag(int x, int y) {
		if (board[x][y].flag == 0)
		{
			board[x][y].flag = 1;
			return true;
		}
		else
			board[x][y].flag = 0;
		return false;

	}

	// method that check if all the cell that are not mines are open.
	public boolean isDone() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				if (board[i][j].mine == 0)
					if (board[i][j].isOpen == 0)
						return false;
		return true;
	}

	public String get(int i, int j) {
		String s = "";
		if (board[i][j].isOpen == 0 && showAll == false)
			if (board[i][j].flag == 1)
				return s + "F";
			else
				return s + ".";

		else if (board[i][j].mine == 1 && board[i][j].flag == 1)
			return s + "XX";
		else if (board[i][j].mine == 1)
			return s + "X";
		else if (board[i][j].flag == 1)
			return s + "F";
		else if (board[i][j].nearMines == 0)
			return s+" ";
		else
			return s + board[i][j].nearMines;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				s = s + get(i, j);
			s = s + "\n";

		}
		return s;

	}


}
