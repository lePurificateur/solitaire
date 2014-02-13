package solitaire;

public class Direction implements Comparable<Direction>
{
	private final int i;
	private final int j;

	public Direction(int i, int j) 
	{
		this.i = i;
		this.j = j;
	}

	public int getI() 
	{
		return i;
	}

	public int getJ() 
	{
		return j;
	}
	
	public String toString()
	{
		return "(" + i + "; " + j + ")"; 
	}

	@Override
	public int compareTo(Direction autre)
	{
		int res = ((Integer)getI()).compareTo(autre.getI());
		if (res != 0)
			return res;
		else
			return ((Integer)getJ()).compareTo(autre.getJ());
	}
	
}
