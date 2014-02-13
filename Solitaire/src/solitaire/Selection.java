package solitaire;

public class Selection 
{
 	
	private Case maCase = null;
	
//	public Selection(Case premiereCase) 
//	{
//		this.maCase = premiereCase;
//		if (maCase != null)
//			maCase.selectionne();
//	}
	
	public Case getCase() 
	{
		return maCase;
	}

	public Case getCase(Coordonnees c) 
	{
		return getGrille().getCase(c);
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
	
	public Grille getGrille() 
	{
		return maCase.getGrille();
	}
	
//	private Coordonnees calculeNouvellesCoordonnees(Coordonnees positionActuelle, int direction)
//	{
//		switch(direction)
//		{
//		case HAUT : return new Coordonnees(positionActuelle.getI() - 1, positionActuelle.getJ());
//		case BAS : return new Coordonnees(positionActuelle.getI() + 1, positionActuelle.getJ());
//		case GAUCHE : return new Coordonnees(positionActuelle.getI(), positionActuelle.getJ() - 1);
//		case DROITE : return new Coordonnees(positionActuelle.getI(), positionActuelle.getJ() + 1);
//		case HAUT_DROITE : return calculeNouvellesCoordonnees(calculeNouvellesCoordonnees(positionActuelle, HAUT), DROITE);
//		case BAS_DROITE : return calculeNouvellesCoordonnees(calculeNouvellesCoordonnees(positionActuelle, BAS), DROITE);
//		case HAUT_GAUCHE : return calculeNouvellesCoordonnees(calculeNouvellesCoordonnees(positionActuelle, HAUT), GAUCHE);
//		case BAS_GAUCHE : return calculeNouvellesCoordonnees(calculeNouvellesCoordonnees(positionActuelle, BAS), GAUCHE);
//		default : System.out.println("Invalid Argument"); return null;
//		}
//	}

//	Coordonnees calculeNouvellesCoordonnees(int direction)
//	{
//		Coordonnees positionActuelle = getCase().getCoordonnees();
//		return calculeNouvellesCoordonnees(positionActuelle, direction);
//	}
//	
	
	Coordonnees voisin(int direction, int i)
	{
		return getCase().voisin(direction, i);
	}
	
//	public boolean deplacementLegal(int direction)
//	{
//		Case caseIntermediaire = getCase(calculeDeplacement(direction));
//		if (caseIntermediaire == null)
//			return false;
//		Case nouvelleCase = getCase(caseIntermediaire.calculeDeplacement(direction));
//		if (nouvelleCase == null)
//			return false;
//		if (caseIntermediaire.estOccupee() && !nouvelleCase.estOccupee())
//			return true;
//		return false;
//	}
	
//	public boolean deplacePion(int direction)
//	{
//		if (!deplacementLegal(direction))
//			return false;
//		Case caseIntermediaire = getCase(calculeDeplacement(direction));
//		Case nouvelleCase = getCase(caseIntermediaire.calculeDeplacement(direction));
//		getGrille().ajouteHistorique(
//					new Deplacement(maCase, caseIntermediaire, nouvelleCase));
//		maCase.enlevePion();
//		caseIntermediaire.enlevePion();
//		nouvelleCase.ajoutePion();
//		setCase(nouvelleCase);
//		return true;
//	}
	
	public boolean deplaceSelection(int direction)
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
