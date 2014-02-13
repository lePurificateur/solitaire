package solitaire;


class Deplacement extends Operation
{
	private Case caseDepart;
	private Case caseMilieu;
	private Case caseArrivee;
	
	public void annule()
	{
		caseDepart.ajoutePion();
		caseMilieu.ajoutePion();
		caseArrivee.enlevePion();
		getSolitaire().setSelection(caseDepart);
	}
	
	public boolean execute()
	{
		caseDepart.enlevePion();
		caseMilieu.enlevePion();
		caseArrivee.ajoutePion();
		getSolitaire().setSelection(caseArrivee);
		return true;
	}

	Deplacement(Solitaire solitaire, Case caseDepart, Case caseArrivee)
	{
		this(solitaire, caseDepart, (caseDepart != null) ? caseDepart.milieu(caseArrivee) : null, 
				caseArrivee);
	}
	
	Deplacement(Solitaire solitaire, Case caseDepart, Coordonnees coordArrivee)
	{
		this(solitaire, caseDepart, caseDepart.milieu(coordArrivee), coordArrivee.getCase());
	}
	
	Deplacement(Solitaire solitaire, Case caseDepart, Direction direction)
	{
		this(solitaire, caseDepart, caseDepart.voisin(direction, 2));
	}

	
	Deplacement(Solitaire solitaire, Case caseDepart, Case caseMilieu, Case caseArrivee)
	{
		super(solitaire);
		this.caseDepart = caseDepart;
		this.caseMilieu = caseMilieu;
		this.caseArrivee = caseArrivee;
	}
	
	public String toString()
	{
		return "deplacée = " + caseDepart.getCoordonnees().toString() + " -> "
			+ caseMilieu.getCoordonnees().toString() + " -> " 
			+ caseArrivee.getCoordonnees().toString() + "\n"
			+ ((getNext() != null) ? getNext().toString() : "");
	}

	@Override
	public boolean estLegale()
	{
		return caseDepart!= null && caseMilieu != null && caseArrivee != null
				&& directionLegale()
				&& caseDepart.estOccupee() && caseMilieu.estOccupee() && !caseArrivee.estOccupee();
	}
	
	private boolean directionLegale()
	{
		int di = Math.abs(caseArrivee.getI() - caseDepart.getI()),
				dj = Math.abs(caseArrivee.getJ() - caseDepart.getJ());
		if (di == 0 && dj == 2 || di == 2 && dj == 0)
			return true;
		if (getSolitaire().diagonaleAutorisee() && di == 2 && dj == 2)
			return true;
		return false;
		}

	protected Case getCaseDepart()
	{
		return caseDepart;
	}

	protected Case getCaseMilieu()
	{
		return caseMilieu;
	}

	protected Case getCaseArrivee()
	{
		return caseArrivee;
	}
}
