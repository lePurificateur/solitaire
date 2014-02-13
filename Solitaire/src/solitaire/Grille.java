package solitaire;

import java.util.SortedSet;
import java.util.TreeSet;

public class Grille 
{
	private Historique historique;
	private LigneGrille[] lignes;
	private	int nbLignes;
	private FormeGrille formeGrille;
	private Selection selection;
	private boolean diagonaleAutorisee = false; 
	private boolean modeTriche = false;
	private ChangeListener<Grille> listener;
	
 	public static final int BAS_GAUCHE= 1;
 	public static final int BAS= 2;
 	public static final int BAS_DROITE = 3;
 	public static final int DROITE = 4;
 	public static final int HAUT_DROITE = 5;
 	public static final int HAUT = 6;
 	public static final int HAUT_GAUCHE = 7;
 	public static final int GAUCHE = 8;
 	public static final int MIN_DIRECTION = 1, MAX_DIRECTION = 8;
 	
	public Grille(FormeGrille formeGrille) 
	{
		this.formeGrille = formeGrille;
		nbLignes = formeGrille.getNbLignes();
		lignes = new LigneGrille[nbLignes];
		for (int i = 0 ; i < nbLignes ; i++)
			lignes[i] = new LigneGrille(this, i);
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

	public void setChangeListener(ChangeListener<Grille> listener)
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
		LigneGrille ligne1 = lignes[0]; 
		return ligne1.getCase(ligne1.getPremiereColonne());
	}
	
	public String toString()
	{
		String res = "mode Triche = " + modeTriche();
		res += "\nmode Diagonale = " + diagonaleAutorisee();
		res += "\npartie terminee = " + estTerminee() + "\n\n";		
		for (LigneGrille l : lignes)
			res += l.toString();
		return res + "\n";
	}

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

	public FormeGrille getFormeGrille() {
		return formeGrille;
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
	
//	private boolean directionLegale(int direction)
//	{
//		return (diagonaleAutorisee() || direction == BAS || direction == HAUT
//			|| direction == DROITE || direction == GAUCHE);
//	}

	public boolean deplacementLegal(int direction)
	{
		Operation d = new Deplacement(this, getCaseSelectionnee(), direction);
		return d.estLegale();
		
		
//		if (!directionLegale(direction))
//			return false;
//		Selection s = getSelection();
//		if (s == null)
//			return false;
//		Coordonnees c = s.voisin(direction, 2);
//		Case destination = getCase(c);
//		if (destination == null)
//			return false;
//		return deplacementLegal(destination);
	}

	// TODO vérifier que les cases ne sont pas occupées
	
	public boolean deplacementLegal(Case destination)
	{
		Operation d = new Deplacement(this, getCaseSelectionnee(), destination);
		return d.estLegale();

//		if (destination == null || selection.getCase() == null)
//			return false;
//		int di = Math.abs(destination.getI() - selection.getCase().getI()),
//				dj = Math.abs(destination.getJ() - selection.getCase().getJ());
//		if (di == 0 && dj == 2 || di == 2 && dj == 0)
//			return true;
//		if (diagonaleAutorisee() && di == 2 && dj == 2)
//			return true;
//		return false;
	}

	private boolean executeOperation(Operation o)
	{
		if (o.estLegale())
		{
			boolean res = o.execute();
			if (res)
			{
				ajouteHistorique(o);
				checkListener();
			}
			return res;
		}
		return false; 		
	}
	
	public boolean detruitPionSelectionne()
	{
		return executeOperation(new Suppression(this, getCaseSelectionnee()));
				// ancienne version
		//getCaseSelectionnee().enlevePion();
		//ajouteHistorique(new Suppression(this, getCaseSelectionnee()));
		//setSelection(null);
//		checkListener();
//		return true;
	}
	
	public boolean deplacePion(int direction)
	{
		return deplacePion(getCaseSelectionnee().voisin(direction, 2).getCase());
//		Selection s = getSelection();
//		if (s == null)
//			return false;
//		Coordonnees c = s.voisin(direction, 2);
//		Case destination = getCase(c);
//		if (destination == null)
//			return false;
//		return deplacePion(direction);
//		if (!deplacementLegal(direction))
//			return false;
//		boolean result = selection.deplacePion(direction);
//		checkListener();
//		return result;
	}

	public boolean deplacePion(Case destination)
	{
		return executeOperation(new Deplacement(this, getCaseSelectionnee(), destination)); 
//		if (!deplacementLegal(destination))
//			return false;
//		boolean result = selection.deplacePion(destination);
//		checkListener();
//		return result;
	}

	
	public void setSelection(Case c)
	{
		selection.setCase(c);
	}
	
	public void setHistoriqueListener(ChangeListener<Historique> listener)
	{
		historique.setListener(listener);
	}
	
	public boolean deplaceSelection(int direction)
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
