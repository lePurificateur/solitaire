package solitaire;

public class Coordonnees extends Direction
{
	private Solitaire grille;

	public Coordonnees(int i, int j, Solitaire grille) 
	{
		super(i, j);
		this.grille = grille;
	}

	public Coordonnees(Direction direction, Solitaire grille) 
	{
		super(direction);
		this.grille = grille;
	}

	public Solitaire getGrille()
	{
		return grille;
	}

	public boolean sontValides()
	{
		Forme formeGrille = getFormeGrille();
		if (formeGrille== null)
			return false;
		return getI()>=0 && getI() < formeGrille.getNbLignes() 
			&& getJ()>=formeGrille.premiereColonne(getI()) && getJ()<=formeGrille.derniereColonne(getI()) ;
	}

	public Forme getFormeGrille() 
	{
		return getGrille().getFormeGrille();
	}
	
	public Case getCase()
	{
		return getGrille().getCase(this);
	}
	
	public Coordonnees milieu(Coordonnees autre)
	{
		return new Coordonnees((getI() + autre.getI())/2, (getJ() + autre.getJ())/2, getGrille());
	}

	public Coordonnees voisin(Direction direction)
	{
		return new Coordonnees(this.additionne(direction), getGrille());
//		switch(direction)
//		{
//		case HAUT : return new Coordonnees(getI() - 1, getJ(), getGrille());
//		case BAS : return new Coordonnees(getI() + 1, getJ(), getGrille());
//		case GAUCHE : return new Coordonnees(getI(), getJ() - 1, getGrille());
//		case DROITE : return new Coordonnees(getI(), getJ() + 1, getGrille());
//		case HAUT_DROITE : return voisin(DROITE).voisin(HAUT);
//		case BAS_DROITE : return voisin(BAS).voisin(DROITE);
//		case HAUT_GAUCHE : return voisin(HAUT).voisin(GAUCHE);
//		case BAS_GAUCHE : return voisin(BAS).voisin(GAUCHE);
//		default : System.out.println("Invalid Argument"); return null;
//		}
	}
	
	Coordonnees voisin(Direction direction, int i)
	{
		if (i == 0)
			return this;
		return voisin(direction, i - 1).voisin(direction);
	}

}
