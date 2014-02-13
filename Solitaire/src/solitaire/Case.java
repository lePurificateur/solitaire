package solitaire;

public class Case
{
	private final static String SELECTIONNEE = "|", VIDE = "::", OCCUPEE = "X", ACCESSIBLE = ";;", RIEN = " ";
	private final Coordonnees coordonnees;
	private final LigneGrille ligneGrille;
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
	
	public Case(LigneGrille ligneGrille, Coordonnees coordonnees) 
	{
		this.ligneGrille = ligneGrille ;
		this.coordonnees = coordonnees;
		this.pion = false;
		this.selectionnee = false;
		changePerformed();
	}

	public Case(LigneGrille ligneGrille, int i, int j) 
	{
		this(ligneGrille, new Coordonnees(i, j, ligneGrille.getGrille()));
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
	
	public Solitaire getGrille()
	{
		return ligneGrille.getGrille();
	}
	
	public boolean estSelectionnee()
	{
		return selectionnee;
	}
	 
	private void mouvementsLegaux(boolean enable)
	{
		for (int direction = Solitaire.MIN_DIRECTION ; direction <= Solitaire.MAX_DIRECTION ; 
				direction++)
		{
			if (enable)
			{
				if (getGrille().deplacementLegal(direction))
				{
					Case c = getGrille().getCase(voisin(direction, 2));
					c.setAccessible(enable);
				}
			}
			else
			{
				Case c = getGrille().getCase(voisin(direction, 2));
				if (c != null)
					c.setAccessible(enable);
			}
		}
	}
	
	/**
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

//	Coordonnees calculeDeplacement(int direction)
//	{
//		return getCoordonnees().deplacement(direction);
//	}
//
//	Coordonnees calculeDeplacement(int direction, int i)
//	{
//		if (i == 0)
//			return getCoordonnees();
//		return calculeDeplacement(direction, i - 1).deplacement(direction); 
//	}

	Coordonnees voisin(int direction, int i)
	{
		return getCoordonnees().voisin(direction, i);
	}
}