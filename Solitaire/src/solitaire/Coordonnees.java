package solitaire;

public class Coordonnees extends Direction
{
	private Solitaire solitaire;

	public Coordonnees(int i, int j, Solitaire grille) 
	{
		super(i, j);
		this.solitaire = grille;
	}

	public Coordonnees(Direction direction, Solitaire grille) 
	{
		super(direction);
		this.solitaire = grille;
	}

	public Solitaire getGrille()
	{
		return solitaire;
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
		return getGrille().getForme();
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
	}
	
	Coordonnees voisin(Direction direction, int i)
	{
		if (i == 0)
			return this;
		return voisin(direction, i - 1).voisin(direction);
	}
}
