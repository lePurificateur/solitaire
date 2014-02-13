package solitaire;

import java.util.SortedSet;
import java.util.TreeSet;


public class Ligne 
{
	private Solitaire grille;
	private int premiereColonne;
	private int derniereColonne;
	private Case[] cases;

	/**
	 * 
	 */
	public Ligne(Solitaire grille, int indice)
	{
		this.grille = grille;
		Forme formeGrille = grille.getFormeGrille();
		premiereColonne = formeGrille.premiereColonne(indice);
		derniereColonne = formeGrille.derniereColonne(indice);
		int n = plageIndices();
		cases = new Case[n];
		for (int i = 0 ; i < n ; i++)
		{
			cases[i] = new Case(this, indice, screenIndexOfTabIndex(i));
			if (formeGrille.estOccupee(cases[i].getCoordonnees()))
				cases[i].ajoutePion();
		}
	}
	
	private int tabIndexOfScreenIndex(int i)
	{
		return i - premiereColonne;
	}
	
	private int screenIndexOfTabIndex(int i)
	{
		return premiereColonne + i;
	}
	
	/**
	 * 
	 */
	public int getPremiereColonne() 
	{
		return premiereColonne;
	}

	/**
	 * 
	 */
	public int getDerniereColonne()
	{
		return derniereColonne;
	}

	/**
	 * 
	 */
	public int nbPions()
	{
		int nb = 0;
		for(Case c : cases)
			if (c.estOccupee())
				nb++;
		return nb;
	}
	
	/**
	 * 
	 */
	public Case getCase(int colonne)
	{
		int indice = tabIndexOfScreenIndex(colonne);
		if (indice < 0 || indice >= plageIndices())
			return null;
		return cases[indice];
	}
	
	/**
	 * 
	 */
	private int plageIndices()
	{
		return getDerniereColonne() - getPremiereColonne() + 1;
	}
	
	/**
	 * 
	 */

	public String toString()
	{
		String res = "";
		for (int i = 0 ; i < premiereColonne ; i++)
			res += " .. ";
		int n = plageIndices();
		for (int i = 0 ; i < n ; i++)
			res += cases[i].toString();
		return res + "\n\n";
	}

	/**
	 * 
	 */

	public Solitaire getGrille()
	{
		return grille;
	}
	
	public SortedSet<Coordonnees> getCoordonnees()
	{
		SortedSet<Coordonnees> coordonnees = new TreeSet<Coordonnees>();
		for(int i = premiereColonne ; i <= derniereColonne  ; i++)
			coordonnees.add(cases[tabIndexOfScreenIndex(i)].getCoordonnees());
		return coordonnees;
	}

}
