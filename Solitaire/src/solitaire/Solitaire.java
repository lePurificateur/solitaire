package solitaire;

import java.util.SortedSet;
import java.util.TreeSet;

public class Solitaire 
{
	private Historique historique;
	private Ligne[] lignes;
	private	int nbLignes;
	private Forme forme;
	private Selection selection;
	private boolean diagonaleAutorisee = false;
	private boolean modeTriche = false;
	private ChangeListener<Solitaire> listener;
	
	public Solitaire(Forme forme) 
	{
		this.forme = forme;
		nbLignes = forme.getNbLignes();
		lignes = new Ligne[nbLignes];
		for (int i = 0 ; i < nbLignes ; i++)
			lignes[i] = new Ligne(this, i);
		selection = new Selection();
		setSelection(getPremiereCase());
		historique = new Historique();
	}
	
	public SortedSet<Coordonnees> getCoordonnees()
	{
		SortedSet<Coordonnees> coordonnees = new TreeSet<Coordonnees>();
		for(int i = 0 ; i < nbLignes ; i++)
			coordonnees.addAll(lignes[i].getCoordonnees());
		return coordonnees;
	}

	public void setChangeListener(ChangeListener<Solitaire> listener)
	{
		this.listener = listener;
	}

	private void checkListener()
	{
		if (listener != null)
			listener.changePerformed(this);
	}
	
	private Case getPremiereCase()
	{
		Ligne ligne1 = lignes[0]; 
		return ligne1.getCase(ligne1.getPremiereColonne());
	}
	
	public String toString()
	{
		String res = "mode Triche = " + modeTriche();
		res += "\nmode Diagonale = " + diagonaleAutorisee();
		res += "\npartie terminee = " + estTerminee() + "\n\n";		
		for (Ligne l : lignes)
			res += l.toString();
		return res + "\n";
	}
	/**
	 * 
	 */

	public boolean diagonaleAutorisee() 
	{
		return diagonaleAutorisee;
	}

	public void setDiagonaleAutorisee(boolean diagonaleAutorisee) 
	{
		this.diagonaleAutorisee = diagonaleAutorisee;
	}

	public boolean modeTriche() {
		return modeTriche;
	}

	public void setModeTriche(boolean modeTriche) {
		this.modeTriche = modeTriche;
	}

	public Forme getForme() {
		return forme;
	}
	
	public Case getCase(int i, int j)
	{
		if (i < 0 || i >= nbLignes)
			return null;
		return lignes[i].getCase(j);
	}
	
	public Case getCase(Coordonnees c)
	{
		if (c != null && c.sontValides())
			return getCase(c.getI(), c.getJ());
		return null;
	}

	public boolean deplacementLegal(Direction direction)
	{
		Operation d = new Deplacement(this, getCaseSelectionnee(), direction);
		return d.estLegale();
	}

	public boolean deplacementLegal(Case destination)
	{
		Operation d = new Deplacement(this, getCaseSelectionnee(), destination);
		return d.estLegale();
	}

	private boolean executeOperation(Operation operation)
	{
		if (operation.estLegale())
		{
			boolean res = operation.execute();
			if (res)
			{
				ajouteHistorique(operation);
				checkListener();
			}
			return res;
		}
		return false; 		
	}
	
	public boolean detruitPionSelectionne()
	{
		return executeOperation(new Suppression(this, getCaseSelectionnee()));
	}
	
	public boolean deplacePion(Direction direction)
	{
		return deplacePion(getCaseSelectionnee().voisin(direction, 2).getCase());
	}

	public boolean deplacePion(Case destination)
	{
		return executeOperation(new Deplacement(this, getCaseSelectionnee(), destination)); 
	}
	
	public void setSelection(Case c)
	{
		selection.setCase(c);
	}
	
	public void setHistoriqueListener(ChangeListener<Historique> listener)
	{
		historique.setListener(listener);
	}
	
	public boolean deplaceSelection(Direction direction)
	{
		return selection.deplaceSelection(direction);
	}

	public Selection getSelection()
	{
		return selection;
	}
	
	public Case getCaseSelectionnee()
	{
		if (getSelection() == null)
			System.out.println("WARNING selection is null !");
		return getSelection().getCase();
	}
	
	public boolean estTerminee()
	{
		return nbPions() == 1;
	}

	private int nbPions()
	{
		int nb = 0;
		for (int i = 0 ; i < nbLignes ; i++)
			nb += lignes[i].nbPions();
		return nb;
	}

	public boolean historiqueVide()
	{
		return historiqueVide(true);
	}
	
	public boolean historiqueVide(boolean undo)
	{
		return historique.estVide(undo);
	}
	
	public boolean annule ()
	{
		boolean result = historique.annule();
		checkListener();
		return result;
	}

	public boolean retablit ()
	{
		boolean result = historique.retablit();
		checkListener();
		return result;
	}

	private void ajouteHistorique(Operation t)
	{
		historique.ajoute(t);
	}	
}
