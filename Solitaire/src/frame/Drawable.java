package frame;

import java.awt.Graphics;

public abstract class Drawable
{
	public void draw(SolitaireLabel label)
	{
		draw(label, label.getGraphics());
	}

	public abstract void draw(SolitaireLabel label, Graphics g);
	public abstract void init(SolitaireLabel label);
}
