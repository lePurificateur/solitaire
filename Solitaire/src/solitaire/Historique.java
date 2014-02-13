package solitaire;

public class Historique
{
	private Operation undo, redo;
	
	private ChangeListener<Historique> listener;

	public void setListener(ChangeListener<Historique> listener)
	{
		this.listener = listener;
		listener.changePerformed(this);
	}
	
	private void onChange()
	{
		if (listener != null)
			listener.changePerformed(this);
	}
	
	public Historique()
	{
		redo = null;
		undo = null;
	}
	
	public void ajoute(Operation tour)
	{
		tour.setNext(undo);
		undo = tour;
		redo = null;
		onChange();
	}
	
	public boolean estVide()
	{
		return estVide(true); 
	}
	
	public boolean estVide(boolean undo)
	{
		return (undo && this.undo == null) || (!undo && redo==null); 
	}
	
	public boolean annule()
	{
		if (estVide(true))
			return false;
		Operation premier = undo;
		undo.annule();
		undo = undo.getNext();
		premier.setNext(redo);
		redo = premier;
		onChange();
		return true;
	}

	public boolean retablit()
	{
		if (estVide(false))
			return false;
		Operation premier = redo;
		redo.execute();
		redo = redo.getNext();
		premier.setNext(undo);
		undo = premier;
		onChange();
		return true;
	}

	
	public String toString()
	{
		return (undo != null) ? undo.toString() : "";
	}
}
