package solitaire;

abstract class Operation 
{
	private Solitaire solitaire;
	private Operation next;
	
	Operation(Solitaire solitaire)
	{
		this.solitaire = solitaire;
		setNext(null);
	}
	
	public Solitaire getSolitaire()
	{
		return solitaire;
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
