package solitaire;

import static solitaire.Grille.*;

public class Coordonnees implements Comparable<Coordonnees>
{

	private final int i;
	private final int j;
	private Grille grille;
	
//	public Coordonnees(int i, int j) 
//	{
//		this.i = i;
//		this.j = j;
//	}

	public Coordonnees(int i, int j, Grille grille) 
	{
//		this(i, j);
		this.i = i;
		this.j = j;
		this.grille = grille;
	}

	public int getI() 
	{
		return i;
	}

	public int getJ() 
	{
		return j;
	}
	
	public Grille getGrille()
	{
		return grille;
	}

	public boolean sontValides()
	{
		FormeGrille formeGrille = getFormeGrille();
		if (formeGrille== null)
			return false;
		return getI()>=0 && getI() < formeGrille.getNbLignes() 
			&& getJ()>=formeGrille.premiereColonne(getI()) && getJ()<=formeGrille.derniereColonne(getI()) ;
	}

	public FormeGrille getFormeGrille() 
	{
		return getGrille().getFormeGrille();
	}
	
	public Case getCase()
	{
		return getGrille().getCase(this);
	}
	
	public String toString()
	{
		return "(" + i + "; " + j + ")"; 
	}

	@Override
	public int compareTo(Coordonnees autre)
	{
		int res = ((Integer)getI()).compareTo(autre.getI());
		if (res != 0)
			return res;
		else
			return ((Integer)getJ()).compareTo(autre.getJ());
	}
	
	public Coordonnees milieu(Coordonnees autre)
	{
		
		return new Coordonnees((i + autre.getI())/2, (j + autre.getJ())/2, getGrille());
	}

	public Coordonnees voisin(int direction)
	{
		switch(direction)
		{
		case HAUT : return new Coordonnees(getI() - 1, getJ(), getGrille());
		case BAS : return new Coordonnees(getI() + 1, getJ(), getGrille());
		case GAUCHE : return new Coordonnees(getI(), getJ() - 1, getGrille());
		case DROITE : return new Coordonnees(getI(), getJ() + 1, getGrille());
		case HAUT_DROITE : return voisin(DROITE).voisin(HAUT);
		case BAS_DROITE : return voisin(BAS).voisin(DROITE);
		case HAUT_GAUCHE : return voisin(HAUT).voisin(GAUCHE);
		case BAS_GAUCHE : return voisin(BAS).voisin(GAUCHE);
		default : System.out.println("Invalid Argument"); return null;
		}
	}
	
	Coordonnees voisin(int direction, int i)
	{
		if (i == 0)
			return this;
		return voisin(direction, i - 1).voisin(direction);
	}

}
