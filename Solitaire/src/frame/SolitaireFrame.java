package frame;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import solitaire.*;

public class SolitaireFrame
{
	private JFrame frame;
	private Grille grille;
	private Drawable caseDrawer = new CircleDrawer();
	private List<SolitaireLabel> labels;
	private int nbLignes, nbColonnes;
	static final String 
			DIAG_AUTORISE = "Autoriser les déplacements en diagonale",
			DIAG_INTERDIT = "Interdire les déplacements en diagonale",
			SUPPR_AUTORISE = "Autoriser la suppression d'une pièce",
			SUPPR_INTERDIT = "Interdire la suppression d'une pièce";
	private MenuItem annuler, retablir, modeTriche, diagonale, agrandir, retrecir, reinitialiser;
	private SolitaireLabel sourceLabel = null, destLabel = null;
	private int taille = 3;
	private static final int TAILLE_MIN = 1, TAILLE_MAX = 6;

	private boolean estTerminee()
	{
		return grille.estTerminee();
	}
	
	void setSource(SolitaireLabel p)
	{
		if (!estTerminee())
		{
			if (sourceLabel != null && sourceLabel.getCase() != null)	
			{
				grille.setSelection(null);
				//sourceLabel.updateText();
			}
			sourceLabel = p;
			if (sourceLabel != null && sourceLabel.getCase() != null)	
			{
				grille.setSelection(sourceLabel.getCase());
				//sourceLabel.updateText();
			}
			else
			{
				grille.setSelection(null);
				//sourceLabel.updateText();				
			}
		}
	}
	
	void setDest(SolitaireLabel p)
	{
		if (! estTerminee())
			destLabel = p;
	}

	void movePion()
	{
		if (!estTerminee() && sourceLabel!=null && destLabel!=null)
		{
			grille.deplacePion(destLabel.getCase());
			setSource(null);
			setDest(null);
		}
	}
	
	void detruitSource()
	{
		if (!estTerminee() && sourceLabel!=null && sourceLabel.getCase() != null)
		{
			grille.detruitPionSelectionne();
			setSource(null);
		}
	}

	SolitaireLabel getSouce()
	{
		return sourceLabel;
	}
	
	SolitaireLabel getDest()
	{
		return destLabel;
	}
	
	Drawable getCaseDrawer()
	{
		return caseDrawer;
	}
	
	private void fillGrid(JPanel grillePanel)
	{
		labels = new ArrayList<SolitaireLabel>();
		for (int i = 0 ; i < nbLignes ; i++)
			for (int j = 0 ; j < nbColonnes ; j++)
			{
				SolitaireLabel pl = new SolitaireLabel(this, grille.getCase(i, j)); 
				labels.add(pl);
				grillePanel.add(pl);
			}
	}
	
//	private void drawLabels()
//	{
//		for (SolitaireLabel label : labels)
//			label.update();
//	}
	
	private JPanel getGrillePanel()
	{
		JPanel grillePanel = new JPanel();
		grillePanel.setLayout(new GridLayout(grille.getFormeGrille().getNbLignes(), 
				grille.getFormeGrille().getNbColonnes()));
		fillGrid(grillePanel);
		return grillePanel;
	}

	private JPanel getMainPanel()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(getGrillePanel());
		return mainPanel;
	}

	private void setGrille(Grille grille)
	{
		this.grille = grille;
		grille.setSelection(null);
		nbLignes = grille.getFormeGrille().getNbLignes();
		nbColonnes = grille.getFormeGrille().getNbColonnes();
		makeMenu();
		grille.setHistoriqueListener(getHistoriqueListener());
		grille.setChangeListener(getGrilleListener());
		frame.setContentPane(getMainPanel());
		frame.pack();
//		drawLabels();
	}
	
	private Menu getMenuJeu()
	{
		Menu menu= new Menu("Jeu");
		// option annuler
		annuler = new MenuItem("Annuler");
		menu.add(annuler);
		annuler.addActionListener(getAnnuler());
		MenuShortcut controlZ = new MenuShortcut('Z');
		annuler.setShortcut(controlZ);
		annuler.setEnabled(false);
		// option Retablir
		retablir = new MenuItem("Rétablir");
		menu.add(retablir);
		retablir.addActionListener(getRetablir());
		MenuShortcut controlY = new MenuShortcut('Y');
		retablir.setShortcut(controlY);
		retablir.setEnabled(false);
		// réinitialiser
		reinitialiser = new MenuItem("Réinitialiser");
		menu.add(reinitialiser);
		reinitialiser.addActionListener(getReinitialiser());
		annuler.setEnabled(false);
		// option quitter
		MenuItem quitter = new MenuItem("Quitter");
		quitter.addActionListener(getQuitter());
		menu.add(quitter);
		MenuShortcut esc = new MenuShortcut('q');
		quitter.setShortcut(esc);
		return menu;
	}
	
	private Menu getMenuOptions()
	{
		Menu menu = new Menu("Options");
		// option mode triche
		modeTriche = new MenuItem(SUPPR_AUTORISE);
		menu.add(modeTriche);
		modeTriche.addActionListener(getModeTriche());
		// option mode diagonale
		diagonale = new MenuItem(DIAG_AUTORISE);
		menu.add(diagonale);
		diagonale.addActionListener(getDiagonale());
		return menu;
	}

	private Menu getMenuPlateau()
	{
		Menu menu= new Menu("Plateau");
		// option agrandir
		agrandir = new MenuItem("Agrandir");
		agrandir.addActionListener(getAgrandir());
		menu.add(agrandir);
		// option agrandir
		retrecir = new MenuItem("Retrécir");	
		retrecir.addActionListener(getRetrecir());
		menu.add(retrecir);
		// options de formes 
		MenuItem carre = new MenuItem("Carre"), croix = new MenuItem("Croix"), 
				losange = new MenuItem("Losange"), triangle = new MenuItem("Triangle");
		carre.addActionListener(getChangeForme(FormeGrille.CARRE));
		croix.addActionListener(getChangeForme(FormeGrille.CROIX));
		losange.addActionListener(getChangeForme(FormeGrille.LOSANGE));
		triangle.addActionListener(getChangeForme(FormeGrille.TRIANGLE));
		menu.add(carre);
		menu.add(croix);
		menu.add(losange);
		menu.add(triangle);
		return menu;
	}
	
	private void makeMenu()
	{
		MenuBar mb = new MenuBar();
		mb.add(getMenuJeu());
		mb.add(getMenuOptions());
		mb.add(getMenuPlateau());
		frame.setMenuBar(mb);
	}
	

	private ChangeListener<Historique> getHistoriqueListener()
	{
		return new ChangeListener<Historique>()
		{
			@Override
			public void changePerformed(Historique source)
			{
				annuler.setEnabled(!source.estVide(true));
				retablir.setEnabled(!source.estVide(false));
				reinitialiser.setEnabled(!source.estVide());
			}
		};
	}

	private ChangeListener<Grille> getGrilleListener()
	{
		return new ChangeListener<Grille>()
		{
			@Override
			public void changePerformed(Grille source)
			{
				modeTriche.setEnabled(!source.estTerminee());
				diagonale.setEnabled(!source.estTerminee());
			}
		};
	}

	private ActionListener getAnnuler()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				grille.annule();
				setSource(null);
			}
		};
	}

	private ActionListener getRetablir()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				grille.retablit();
				setSource(null);
			}
		};
	}


	private ActionListener getReinitialiser()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				FormeGrille fg = grille.getFormeGrille();
				setGrille(FormeGrille.getGrille(fg.getFormeIndex(), fg.getTaille()));
			}
		};
	}

	private ActionListener getAgrandir()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (taille < TAILLE_MAX)
				{
					taille++;
					setGrille(grille.getFormeGrille().getGrille(taille));
					agrandir.setEnabled(taille != TAILLE_MAX);
				}
			}
		};
	}

	private ActionListener getRetrecir()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (taille > TAILLE_MIN)
				{
					taille--;
					setGrille(grille.getFormeGrille().getGrille(taille));
					retrecir.setEnabled(taille != TAILLE_MIN);
				}
			}
		};
	}

	private ActionListener getQuitter()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);				
			}
		};
	}
	
	private ActionListener getDiagonale()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				grille.setDiagonaleAutorisee(!grille.diagonaleAutorisee());
				diagonale.setLabel((grille.diagonaleAutorisee()) ? DIAG_INTERDIT: DIAG_AUTORISE);
			}
		};
	}

	private ActionListener getModeTriche()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				grille.setModeTriche(!grille.modeTriche());
				modeTriche.setLabel((grille.modeTriche()) ? SUPPR_INTERDIT: SUPPR_AUTORISE);
			}
		};
	}

	private ActionListener getChangeForme(final int forme)
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setGrille(FormeGrille.getGrille(forme, taille));
			}
		};
	}

	public SolitaireFrame(Grille grille)
	{
		frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("Solitaire");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setGrille(grille);
	}
	
	public static void main(String[] args)
	{
		new SolitaireFrame(new Grille(new FormeGrille.Losange(3)));
	}
}
