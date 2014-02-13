package solitaire;

class Suppression extends Operation
{
	private Case caseSupprimee;
	
	Suppression(Solitaire grille, Case caseSupprimee)
	{
		super(grille);
		this.caseSupprimee = caseSupprimee;
	}

	Suppression(Solitaire grille, Coordonnees c)
	{
		this(grille, c.getCase());
	}


	public Case getCase()
	{
		return caseSupprimee;
	}
	
	public void annule() 
	{
		//caseSupprimee.getGrille().getSelection().setCase(caseSupprimee);
		caseSupprimee.ajoutePion();
	}
	
	public boolean execute() 
	{
		//caseSupprimee.getGrille().getSelection().setCase(caseSupprimee);
		caseSupprimee.enlevePion();
		return true;
	}

	public String toString()
	{
		return "supprimée = " + caseSupprimee.getCoordonnees().toString() 
			+ "\n" + ((getNext() != null) ? getNext().toString() : "");
	}

	@Override
	public boolean estLegale()
	{
		if (!getGrille().modeTriche())
			return false;
		if (!getGrille().getCaseSelectionnee().estOccupee())
			return false;
		return true;
	}
}
