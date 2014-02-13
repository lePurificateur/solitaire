package solitaire;

public class Case
{
	private final static String SELECTIONNEE = "|", VIDE = "::", OCCUPEE = "X", ACCESSIBLE = ";;", RIEN = " ";
	private final Coordonnees coordonnees;
	private final Ligne ligne;
	private boolean pion;
	private boolean selectionnee;
	private boolean accessible = false;
	private ChangeListener<Case> listener;

	public void setChangeListener(ChangeListener<Case> listener)
	{
		this.listener = listener;
	}
	
	private void changePerformed()
	{
		if (listener != null)
			listener.changePerformed(this);
	}
	
	public Case(Ligne ligne, Coordonnees coordonnees) 
	{
		this.ligne = ligne ;
		this.coordonnees = coordonnees;
		this.pion = false;
		this.selectionnee = false;
		changePerformed();
	}

	public Case(Ligne ligne, int i, int j) 
	{
		this(ligne, new Coordonnees(i, j, ligne.getSolitaire()));
	}
	
	public boolean estAccessible() 
	{
		return accessible;
	}

	public void setAccessible(boolean accessible) 
	{
		this.accessible = accessible;
		changePerformed();
	}
	
	public int getI() 
	{
		return coordonnees.getI();
	}

	public int getJ() 
	{
		return coordonnees.getJ();
	}
	
	public boolean estOccupee()
	{
		return pion;
	}

	public void ajoutePion() 
	{
		this.pion = true;
		changePerformed();
	}
	
	public void enlevePion() 
	{
		this.pion = false;
		changePerformed();
	}

	public Coordonnees getCoordonnees() 
	{
		return coordonnees;
	}
	
	public Case milieu(Case autre)
	{
		if (autre == null)
			return null;
		return getCoordonnees().milieu(autre.getCoordonnees()).getCase();
	}
	
	public Case milieu(Coordonnees autre)
	{
		return getCoordonnees().milieu(autre).getCase();
	}
	

	private String cotes()
	{
		String res = "";
		if (estSelectionnee())
			res += SELECTIONNEE;
		else
		{
			if (estAccessible())
				res += RIEN;
			else
				res += RIEN;
		}
		return res;
	}
	
	public String toString()
	{
		String res = "";
		res += cotes();
		if (estOccupee()) 
			res += OCCUPEE;
		else
		{
			if (estAccessible())
				res += ACCESSIBLE;
			else
				res += VIDE;
		}
		res += cotes();
		return res;
	}
	
	public Solitaire getSolitaire()
	{
		return ligne.getSolitaire();
	}
	
	public boolean estSelectionnee()
	{
		return selectionnee;
	}
	 
	private void mouvementsLegaux(boolean enable)
	{
		for (Direction direction : Direction.DIRECTIONS)
		{
			if (enable)
			{
				if (getSolitaire().deplacementLegal(direction))
				{
					Case c = getSolitaire().getCase(voisin(direction, 2));
					c.setAccessible(enable);
				}
			}
			else
			{
				Case c = getSolitaire().getCase(voisin(direction, 2));
				if (c != null)
					c.setAccessible(enable);
			}
		}
	}
	
	/*
	 * !!!!!!! Ne déselectionne pas l'autre case
	 */

	public void selectionne()
	{
		selectionnee = true;
		mouvementsLegaux(true);
		changePerformed();
	}
	
	public void deselectionne()
	{
		selectionnee = false;
		mouvementsLegaux(false);
		changePerformed();
	}

	Coordonnees voisin(Direction direction, int i)
	{
		return getCoordonnees().voisin(direction, i);
	}
}