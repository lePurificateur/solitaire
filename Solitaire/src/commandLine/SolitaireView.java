package commandLine;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import solitaire.*;

public class SolitaireView extends JFrame implements KeyListener
{
	private static final long serialVersionUID = 1L;
	private final int KEY_LEFT = 37, KEY_RIGHT = 39, KEY_UP = 38, KEY_DOWN = 40;
	private final char DEPLACER = 's', MODE_TRICHE = 't', MODE_DIAGONALE = 'g', RECOMMENCER = 'r', DETRUIRE = 'b', 
			ZOOM_IN = '+', ZOOM_OUT = '-', QUITTER = 'n', HAUT = 'z', GAUCHE = 'q', BAS = 'x', DROITE = 'd',
			HAUT_GAUCHE = 'a', HAUT_DROITE = 'e', BAS_GAUCHE = 'w', BAS_DROITE = 'c', ANNULER = 'u', RETABLIR = 'y';
	private static final int TAILLE_MIN = 2, TAILLE_MAX = 6;
	private int taille = 3;
	
	private Solitaire grille;
	private Case caseInitiale;
	private JTextArea text = new JTextArea ("test");
	private int forme = FormeGrille.CARRE; 
	
	public void init()
	{
		setTitle("Solitaire");
		setSize(800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1, 1));
		getContentPane().add(text);
		text.setEditable(false);
		text.addKeyListener(this);
		setVisible(true);
	}
	
	private boolean pionSelectionne()
	{
		return caseInitiale != null && caseInitiale.estOccupee();
	}
	
	private String getGridTop()
	{
		String str = "";
		if (!grille.estTerminee())
		{
			str += "\n" + DEPLACER ;
			if (pionSelectionne())
				str += " : deplacer le pion";
			else
				str += " : deselectionner le pion";
			str += "\n" + MODE_TRICHE + " : mode triche";
			str += "\n" + MODE_DIAGONALE + " : mode diagonale";
			if (grille.modeTriche())
				str += "\n" + DETRUIRE + " : detruire le pion";
		}
		return str;
	}
	
	private String getGridBottom()
	{
		String str = "";
		if (pionSelectionne())
		{
			str += "\n" + HAUT + " : haut"; 
			str += "\n" + GAUCHE + " : gauche"; 
			str += "\n" + DROITE + " : droite"; 
			str += "\n" + BAS + " : bas";
			str += "\n(vous pouvez aussi utiliser les flèches du pavé directionnel)";
			if (grille.diagonaleAutorisee())
				{
				str += "\n" + HAUT_GAUCHE + " : haut-gauche"; 
				str += "\n" + HAUT_DROITE + " : haut-droite"; 
				str += "\n" + BAS_GAUCHE + " : bas-gauche"; 
				str += "\n" + BAS_DROITE + " : bas-droite"; 
				}
		}
		str += "\n\n" + RECOMMENCER + " : recommencer (carré, croix, losange)";
		str += ((!grille.historiqueVide(true)) ? "\n" + ANNULER + " : annuler" : "");
		str += ((!grille.historiqueVide(false)) ? "\n" + RETABLIR + " : rétablir " : "");
		if (taille < TAILLE_MAX)
			str += "\n" + ZOOM_IN + " : augmenter la taille";
		if (taille > TAILLE_MIN)
			str += "\n" + ZOOM_OUT + " : diminiuer la taille";
		str += "\n" + QUITTER + " : quitter";
		return str;
	}
	
	private void printGrid()
	{
		String str = "";
		str += getGridTop();
		str += "\n\n" + grille.toString();
		str += getGridBottom();
		text.setText(str);
	}
	
	public SolitaireView()
	{
		grille = new Solitaire(new FormeGrille.Carre(taille));
		init();
	}

	public void keyPressed(KeyEvent e){}

	private void deplacePion(int direction)
	{
		grille.deplacePion(direction);		
		caseInitiale = null;
	}
	
	public void arrowReleased(int c)
	{
		if (!pionSelectionne())
			switch(c)
			{
				case KEY_LEFT : grille.deplaceSelection(Solitaire.GAUCHE);break;
				case KEY_UP : grille.deplaceSelection(Solitaire.HAUT);break;
				case KEY_RIGHT : grille.deplaceSelection(Solitaire.DROITE);break;
				case KEY_DOWN : grille.deplaceSelection(Solitaire.BAS);break;
				default: 
			}
		else
			switch(c)
			{
				case KEY_LEFT : deplacePion(Solitaire.GAUCHE);break;
				case KEY_UP : deplacePion(Solitaire.HAUT);break;
				case KEY_RIGHT : deplacePion(Solitaire.DROITE);break;
				case KEY_DOWN : deplacePion(Solitaire.BAS);break;
				default: 
			}
	}
	
	private void annuler()
	{
		if (!grille.historiqueVide())
			grille.annule();
	}
	
	private void retablir()
	{
		if (!grille.historiqueVide(false))
			grille.retablit();
	}

	private void deplacer()
	{
		if (!pionSelectionne())
			caseInitiale = grille.getCaseSelectionnee();
		else
			caseInitiale = null;
	}
	
	public void keyReleased(KeyEvent e) 
	{
		char key = e.getKeyChar();
		if (!grille.estTerminee())
		{
			switch (key)
			{
				case QUITTER : System.exit(0);break;
				case MODE_TRICHE : grille.setModeTriche(!grille.modeTriche());break;
				case ANNULER : annuler(); break;
				case RETABLIR : retablir(); break;
				case MODE_DIAGONALE : grille.setDiagonaleAutorisee(!grille.diagonaleAutorisee());break;
				case DETRUIRE : grille.detruitPionSelectionne();break;
				case BAS_GAUCHE : deplacePion(Solitaire.BAS_GAUCHE);break;
				case BAS : deplacePion(Solitaire.BAS);break;
				case BAS_DROITE : deplacePion(Solitaire.BAS_DROITE);break;
				case GAUCHE : deplacePion(Solitaire.GAUCHE);break;
				case DROITE : deplacePion(Solitaire.DROITE);break;
				case HAUT_GAUCHE : deplacePion(Solitaire.HAUT_GAUCHE);break;
				case HAUT : deplacePion(Solitaire.HAUT);break;
				case HAUT_DROITE : deplacePion(Solitaire.HAUT_DROITE);break;
				case DEPLACER : deplacer(); break;
				default : arrowReleased(e.getKeyCode());
			}
		}
		else
			if (key == ANNULER)
				annuler();
		printGrid();
		switch (key)
		{
			case 'r' : forme++ ; forme %= FormeGrille.NB_FORMES ; recommence(forme, taille); break;
			case '+' : if (taille < 6) taille++ ; recommence(forme, taille); break;
			case '-' : if (taille > 2) taille-- ; recommence(forme, taille); break;
			default: 
		}
	}
	
	private void recommence(int figure, int taille)
	{
		grille = FormeGrille.getGrille(figure, taille); 
		printGrid();
	}
	
	public void keyTyped(KeyEvent e) {}

	public static void main(String[] args) 
	{
		SolitaireView sv = new SolitaireView();
		sv.printGrid();
	}
}
