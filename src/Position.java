import static java.lang.System.arraycopy;
import static java.lang.System.out;

class Position
{

	static final int numRows = 3; // number of rows on board
	
	static final int numCols = 10; // number of columns on board

	static final Position end1 = new Position(1, 2); // possible end positions
														// (start is (0,0)
	static final Position end2 = new Position(2, 1); // other possible end
														// position
	private int row; // row number, o<=i<numRows
	
	private int col; // column number, o<=j<numCols

	Position(int row0, int col0)
	{
		row = row0;
		
		col = col0;
	}

	public boolean equals(Object obj)
	{ 
		// compare with another position
		if (obj == null)
		{
			return false;
		}
			
		Position p = (Position) obj;
		
		return (p.row == row && p.col == col);
	}

	Position[] knightMoves()
	{ 
		// list of knight's possible moves from here
		Position[] temp = new Position[8];
		
		int count = 0; // temp[0..count-1] holds results
		
		int[] xStep = { 2, 2, -2, -2, 1, 1, -1, -1 }; // potential moves are
														// (row+yStep[k],col+xStep[k])
														// for each index k
		int[] yStep = { 1, -1, 1, -1, 2, -2, 2, -2 };
		
		for (int k = 0; k < xStep.length; k++)
		{ 
			// check that each potential move stays on board
			int x = row + yStep[k];
			
			int y = col + xStep[k];
			
			if (0 <= x && x < numCols && 0 <= y && y < numRows)
			{
				temp[count] = new Position(x, y);
				
				count++;
			}
		}
		
		Position[] result = new Position[count];

		arraycopy(temp, 0, result, 0, count);
			
		return result;
	}

	void putPosition()
	{ // print position
		out.print("(" + row + "," + col + ")");
	}
}