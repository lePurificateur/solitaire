package solitaire;

abstract class Operation 
{
	private Grille grille;
	private Operation next;
	
	Operation(Grille grille)
	{
		this.grille = grille;
		setNext(null);
	}
	
	public Grille getGrille()
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

