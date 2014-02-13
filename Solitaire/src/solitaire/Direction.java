package solitaire;

public class Direction implements Comparable<Direction>
{
 	public static final Direction 
		HAUT = new Direction(-1, 0),
 		BAS = new Direction(1, 0),
 		GAUCHE = new Direction(0, -1),
 		DROITE = new Direction(0, 1),
 		HAUT_DROITE = HAUT.additionne(DROITE),
 		HAUT_GAUCHE = HAUT.additionne(GAUCHE),
 		BAS_DROITE = BAS.additionne(DROITE),
 		BAS_GAUCHE = BAS.additionne(GAUCHE);
 	public static final Direction[] DIRECTIONS = {HAUT, BAS, GAUCHE, DROITE, HAUT_GAUCHE, HAUT_DROITE, BAS_GAUCHE, BAS_DROITE};
// 	public final static int MIN_DIRECTION = 1, MAX_DIRECTION = 8;
 	
	private final int i;
	private final int j;

	public Direction(int i, int j)
	{
		this.i = i;
		this.j = j;
	}

	public Direction(Direction direction)
	{
		this(direction.getI(), direction.getJ());
	}

	public Direction additionne(Direction direction)
	{
		return new Direction(getI() + direction.getI(), getJ() + direction.getJ());
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
