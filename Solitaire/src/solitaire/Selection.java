package solitaire;

public class Selection 
{
 	
	private Case maCase = null;
	
	public Case getCase() 
	{
		return maCase;
	}

	public Case getCase(Coordonnees c) 
	{
		return getSolitaire().getCase(c);
	}


	public Coordonnees getCoordonnees() 
	{
		return maCase.getCoordonnees();
	}

	public void setCase(Case maCase) 
	{
		if (this.maCase != null && this.maCase.estSelectionnee())
			getCase().deselectionne();
		this.maCase = maCase;
		if (this.maCase != null && !this.maCase.estSelectionnee())
			getCase().selectionne();
	}
	
	public Solitaire getSolitaire() 
	{
		return maCase.getSolitaire();
	}
	
	Coordonnees voisin(Direction direction, int i)
	{
		return getCase().voisin(direction, i);
	}
	
	public boolean deplaceSelection(Direction direction)
	{
		Case nouvelleCase = voisin(direction, 1).getCase();
		if (nouvelleCase == null)
			return false;
		maCase.deselectionne();
		maCase = nouvelleCase;
		maCase.selectionne();
		return true;
	}
}
