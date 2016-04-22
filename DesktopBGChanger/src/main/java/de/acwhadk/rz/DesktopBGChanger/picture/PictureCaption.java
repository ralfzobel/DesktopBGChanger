package de.acwhadk.rz.DesktopBGChanger.picture;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class PictureCaption {

	private PictureCaption() {
	}

	public static BufferedImage convert(String file, String text, boolean left, boolean top, int fontSize) throws MalformedURLException, IOException {
		BufferedImage image = ImageIO.read(new URL("file:"+file));
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage img = new BufferedImage(
				w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.setPaint(Color.black);
		g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize * img.getHeight() / 1024));
		FontMetrics fm = g2d.getFontMetrics();
		int x = left ? 10 : img.getWidth() - fm.stringWidth(text) - 10;
		int y = top ? fm.getHeight() : img.getHeight() - fm.getHeight();
		g2d.drawString(text, x, y);
		g2d.dispose();
		return img;
	}

}
