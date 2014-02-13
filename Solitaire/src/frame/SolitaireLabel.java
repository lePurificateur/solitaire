package frame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import solitaire.*;
import javax.swing.JLabel;

public class SolitaireLabel extends JLabel implements 
	MouseListener, ChangeListener<Case>
{
	private static final long serialVersionUID = 1L;
	private Case c;
	private SolitaireFrame frame;
	
	public SolitaireLabel(SolitaireFrame frame, Case c)
	{
		this.c = c;
		this.frame = frame;
		this.addMouseListener(this);
		initDrawer();
		if (c != null)
			c.setChangeListener(this);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		frame.getCaseDrawer().draw(this, g);
	}
	
	private void initDrawer()
	{
		frame.getCaseDrawer().init(this);
	}
	
	public Case getCase()
	{
		return c;
	}
	
	@Override
	public String toString()
	{
		if (c != null)
			return "" + c.getI() + ", " + c.getJ();
		else
			return "empty";
	}

	@Override
	public void changePerformed(Case c)
	{
		frame.getCaseDrawer().draw(this);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		frame.setSource(this);
		frame.detruitSource();
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		frame.setDest(this);
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		frame.setDest(null);
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		frame.setSource(this);
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		frame.movePion();
	}
}