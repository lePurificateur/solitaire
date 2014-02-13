package solitaire;

abstract class Operation 
{
	private Solitaire grille;
	private Operation next;
	
	Operation(Solitaire grille)
	{
		this.grille = grille;
		setNext(null);
	}
	
	public Solitaire getGrille()
	{
		return grille;
	}
	
	public abstract boolean estLegale();
	
	public abstract void annule();

	public abstract boolean execute();

	public Operation getNext() 
	{
		return next;
	}

	public void setNext(Operation next) 
	{
		this.next = next;
	}
}

