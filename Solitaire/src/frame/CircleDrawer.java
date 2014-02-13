package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import solitaire.Case;

public class CircleDrawer extends Drawable
{
	private static final int SIZE = 40;


	public void draw(SolitaireLabel label, Graphics g)
	{
		label.setBackground(getBackgroundColor(label));
		Color color = getCircleColor(label);
		if (color != null)
		{
			g.setColor(color);
			g.fillOval(0, 0, SIZE, SIZE);
		}
	}

	private Color getBackgroundColor(SolitaireLabel label)
	{
		return Color.LIGHT_GRAY;
	}

	private Color getCircleColor(SolitaireLabel label)
	{
		Case c = label.getCase();
		if (c == null)
			return null;
		if (c.estAccessible())
			return Color.RED;
		if (!c.estOccupee())
			return Color.GRAY;
		if (c.estSelectionnee())
			return Color.MAGENTA;
		else
			return Color.BLUE;
	}

	@Override
	public void init(SolitaireLabel label)
	{
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(SIZE, SIZE));
	}
}
