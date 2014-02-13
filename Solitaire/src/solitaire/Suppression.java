package solitaire;

class Suppression extends Operation
{
	private Case caseSupprimee;
	
	Suppression(Solitaire solitaire, Case caseSupprimee)
	{
		super(solitaire);
		this.caseSupprimee = caseSupprimee;
	}

	Suppression(Solitaire solitaire, Coordonnees c)
	{
		this(solitaire, c.getCase());
	}


	public Case getCase()
	{
		return caseSupprimee;
	}
	
	public void annule() 
	{
		caseSupprimee.ajoutePion();
	}
	
	public boolean execute() 
	{
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
		if (!getSolitaire().modeTriche())
			return false;
		if (!getSolitaire().getCaseSelectionnee().estOccupee())
			return false;
		return true;
	}
}