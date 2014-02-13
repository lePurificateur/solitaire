package frame;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;

import solitaire.Case;

public class TextDrawer extends Drawable
{
	static final String EMPTY_CELL = ".", LOCKED_CELL = "", 
			PION = "X", SELECTED = "|X|", ACCESSIBLE = ":"; 
	private static final int SIZE = 40;

	public void draw(SolitaireLabel label, Graphics g)
	{
		label.setText(getText(label));
	}
	
	private String getText(SolitaireLabel label)
	{
		Case c = label.getCase();
		if (c == null)
			return LOCKED_CELL;
		if (c.estAccessible())
			return ACCESSIBLE;
		if (!c.estOccupee())
			return EMPTY_CELL;
		if (c.estSelectionnee())
			return SELECTED;
		else
			return PION;
	}

	@Override
	public void init(SolitaireLabel label)
	{
		label.setPreferredSize(new Dimension(SIZE, SIZE));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
	}
}
