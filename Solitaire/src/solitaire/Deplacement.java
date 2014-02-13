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
		getGrille().setSelection(caseDepart);
	}
	
	public boolean execute()
	{
		caseDepart.enlevePion();
		caseMilieu.enlevePion();
		caseArrivee.ajoutePion();
		getGrille().setSelection(caseArrivee);
		return true;
	}

	Deplacement(Solitaire grille, Case caseDepart, Case caseArrivee)
	{
		this(grille, caseDepart, (caseDepart != null) ? caseDepart.milieu(caseArrivee) : null, 
				caseArrivee);
	}
	
	Deplacement(Solitaire grille, Case caseDepart, Coordonnees coordArrivee)
	{
		this(grille, caseDepart, caseDepart.milieu(coordArrivee), coordArrivee.getCase());
	}
	
	Deplacement(Solitaire grille, Case caseDepart, Direction direction)
	{
		this(grille, caseDepart, caseDepart.voisin(direction, 2));
	}

	
	Deplacement(Solitaire grille, Case caseDepart, Case caseMilieu, Case caseArrivee)
	{
		super(grille);
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
		if (getGrille().diagonaleAutorisee() && di == 2 && dj == 2)
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
	
//	public boolean deplacementLegal(Case nouvelleCase)
//	{
//		if (nouvelleCase == null)
//			return false;
//		Case caseIntermediaire = getCase(getCoordonnees().milieu(nouvelleCase.getCoordonnees()));
//		if (caseIntermediaire == null)
//			return false;
//		if (!caseIntermediaire.estOccupee() || nouvelleCase.estOccupee())
//			return false;
//		return true;
//	}

//	public boolean deplacePion(Case nouvelleCase)
//	{
//		if (!deplacementLegal(nouvelleCase))
//			return false;
//		Case caseIntermediaire = getCase(getCoordonnees().milieu(nouvelleCase.getCoordonnees()));
//		//getGrille().ajouteHistorique(
//			//		new Deplacement(getGrille(), maCase, caseIntermediaire, nouvelleCase));
//		maCase.enlevePion();
//		caseIntermediaire.enlevePion();
//		nouvelleCase.ajoutePion();  
//		setCase(nouvelleCase);
//		return true;
//	}


}

