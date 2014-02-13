package solitaire;


public abstract class Forme
{
	public static final int CARRE = 0, CROIX = 1, LOSANGE = 2, TRIANGLE = 3, NB_FORMES = 4;
	private int taille;

	public Forme(int taille)
	{
		this.taille = taille;
	}
	
	public int getTaille()
	{
		return taille;
	}
	
	public static Solitaire createSolitaire(int forme, int taille)
	{
		switch (forme)
		{
		case CARRE : return new Solitaire(new Forme.Carre(taille));
		case CROIX : return new Solitaire(new Forme.Croix(taille));
		case LOSANGE : return new Solitaire(new Forme.Losange(taille));
		case TRIANGLE : return new Solitaire(new Forme.Triangle(taille));
		default : return null;
		}
	}
	
	public Solitaire createSolitaire(int taille)
	{
		return createSolitaire(getFormeIndex(), taille);
	}

	public abstract int getFormeIndex();
	
	public abstract int getNbLignes();

	public abstract int premiereColonne(int ligne);

	public abstract int derniereColonne(int ligne);

	public abstract boolean estOccupee(Coordonnees c);	

	public int getNbColonnes()
	{
		int max = 0;
		for (int i = 0 ; i < getNbLignes() ; i++)
		{
			int nbColonnes = derniereColonne(i); 
			max = (nbColonnes > max) ? nbColonnes : max ;
		}
		return max + 1;
	}
	
	public static class Carre extends Forme 
	{
		
		public Carre(int k)
		{
			super(k);
		}
		
		public boolean estOccupee(Coordonnees c)
		{
			return c.getI() != getTaille() || c.getJ() != getTaille();
		}
	
		public int derniereColonne(int ligne) 
		{
			return 2 * getTaille() ;
		} 
	
		public int getNbLignes() 
		{
			return 2 * getTaille() + 1;
		}
	
		public int premiereColonne(int ligne) 
		{
			return 0;
		}

		@Override
		public int getFormeIndex()
		{
			return CARRE;
		}
	}
	
	public static class Croix extends Forme 
	{
		public Croix(int taille)
		{
			super(taille);
		}
		
		@Override
		public int getFormeIndex()
		{
			return CROIX;
		}

		public int getNbLignes() 
		{
			return 3 * getTaille() ;
		}
	
		public int premiereColonne(int ligne) 
		{
			return (ligne >= getTaille() && ligne < 2 * getTaille()) ? 0 : getTaille();
		}
	
		public int derniereColonne(int ligne) 
		{
			return (ligne >= getTaille() && ligne < 2 * getTaille()) ? 3*getTaille() -1 : 2*getTaille() - 1;
		} 
	
		public boolean estOccupee(Coordonnees c)
		{
			return c.getI() != 3*getTaille()/2 || c.getJ() != 3*getTaille()/2;
		}
	
	}
	
	public static class Losange extends Forme 
	{
		public Losange(int taille)
		{
			super(taille);
		}
		
		@Override
		public int getFormeIndex()
		{
			return LOSANGE;
		}

		public int getNbLignes() 
		{
			return 3 * getTaille();
		}
	
		public int premiereColonne(int ligne) 
		{
			if (ligne < getTaille())
				return getTaille() - ligne;
			if (ligne >= 2 * getTaille())
				return ligne - (2 * getTaille() - 1);			
			return 0;
		}
	
		public int derniereColonne(int ligne) 
		{
			if (ligne < getTaille())
				return 2 * getTaille() - 1 + ligne;
			if (ligne >= 2 * getTaille())
				return (5 * getTaille() - 1) - (ligne + 1);			
			return 3 * getTaille() - 1 ;
		} 
	
		public boolean estOccupee(Coordonnees c)
		{
			return c.getI() != 3*getTaille()/2 || c.getJ() != 3*getTaille()/2;
		}
	}

	public static class Triangle extends Forme 
	{
		public Triangle(int taille)
		{
			super(taille);
		}
		
		@Override
		public int getFormeIndex()
		{
			return TRIANGLE;
		}

		public int getNbLignes() 
		{
			return 2*getTaille() + 1;
		}
	
		public int premiereColonne(int ligne) 
		{
			return 0;
		}
	
		public int derniereColonne(int ligne) 
		{
			return ligne;
		} 
	
		public boolean estOccupee(Coordonnees c)
		{
			return c.getI() != getTaille() || c.getJ() != getTaille();
		}
	}
}