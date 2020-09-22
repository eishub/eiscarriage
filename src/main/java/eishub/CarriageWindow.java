package eishub;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class CarriageWindow extends JFrame {
	private BufferedImage carriageImage = null;
	private BufferedImage dirtImage = null;
	private int state = 0;

	class ViewPanel extends JPanel {
		public ViewPanel() {
			setSize(400, 400);
		}

		@Override
		public void paint(final Graphics g) {
			final Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.white);
			g2d.fillRect(0, 0, 400, 400);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(4, 0, 0));
			g2d.setColor(Color.gray);
			g2d.drawOval(50, 50, 300, 300);
			g2d.drawOval(200 - 5, 50 - 5, 10, 10);
			g2d.drawOval(70 - 5, 275 - 5, 10, 10);
			g2d.drawOval(330 - 5, 275 - 5, 10, 10);

			final AffineTransform transform = new AffineTransform();
			transform.translate(200, 200);
			transform.rotate(CarriageWindow.this.state * 2 * Math.PI / 3 + Math.PI);
			transform.translate(-25, 125);

			g2d.drawImage(CarriageWindow.this.carriageImage, transform, null);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
	}

	public CarriageWindow() {
		setSize(425, 450);
		setResizable(false);

		// carriage image
		this.carriageImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2D = (Graphics2D) this.carriageImage.getGraphics();
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setColor(Color.white);
		g2D.fillRect(0, 0, 50, 50);
		g2D.setColor(Color.lightGray);
		g2D.fillRect(10, 10, 30, 30);
		g2D.setColor(Color.black);
		g2D.drawRect(10, 10, 30, 30);
		g2D.setColor(Color.red);
		g2D.fillOval(0, 20, 10, 10);
		g2D.setColor(Color.blue);
		g2D.fillOval(40, 20, 10, 10);

		// dirt image
		this.dirtImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);

		g2D = (Graphics2D) this.dirtImage.getGraphics();

		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2D.setColor(Color.white);
		g2D.fillRect(0, 0, 400, 400);
		g2D.setColor(Color.orange.darker().darker());
		g2D.fillOval(175, 25, 50, 50);
		g2D.fillOval(210, 35, 75, 75);
		g2D.fillOval(250, 75, 100, 100);

		final JPanel panel1 = new ViewPanel();
		final JPanel panel2 = new JPanel();
		final JTabbedPane tabs = new JTabbedPane();
		tabs.add("View", panel1);
		tabs.add("Config", panel2);

		add(tabs);
		setVisible(true);
	}

	/**
	 * this can be called from outside (another thread) to change the state
	 */
	@Override
	public void setState(final int state) {
		this.state = state;
		SwingUtilities.invokeLater(() -> repaint());
	}
}
