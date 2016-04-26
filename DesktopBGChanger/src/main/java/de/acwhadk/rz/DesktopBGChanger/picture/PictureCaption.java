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
		g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize * img.getHeight() / 1024));
		FontMetrics fm = g2d.getFontMetrics();
		int x = left ? 10 : img.getWidth() - fm.stringWidth(text) - 10;
		int y = top ? fm.getHeight() : img.getHeight() - fm.getHeight();
		float luminance = getAverageLuminance(image, x, y, fm.stringWidth(text), fm.getHeight());
		if (luminance >= 0.5f) {
			g2d.setPaint(Color.black);
		} else {
			g2d.setPaint(Color.white);
		}
		g2d.drawString(text, x, y);
		g2d.dispose();
		return img;
	}

	private static float getAverageLuminance(BufferedImage image, int x0, int y0, int width, int height) {
		int cnt=0;
		float luminance = 0.0F;
		for(int x=x0; x<x0+width; x+=10) {
			for(int y=y0-height; y<y0; y+=10) {
				luminance += getLuminance(image, x, y);
				++cnt;
			}
		}
		return luminance / cnt;
	}
	
	private static float getLuminance(BufferedImage image, int x, int y) {
		int color = image.getRGB(x, y);

		// extract each color component
		int red   = (color >>> 16) & 0xFF;
		int green = (color >>>  8) & 0xFF;
		int blue  = (color >>>  0) & 0xFF;

		// calc luminance in range 0.0 to 1.0; using SRGB luminance constants
		return (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;

	}
}
