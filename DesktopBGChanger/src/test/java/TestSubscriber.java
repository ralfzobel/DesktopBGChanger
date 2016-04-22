import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.junit.Test;

import de.acwhadk.rz.DesktopBGChanger.picture.PictureCaption;

public class TestSubscriber {

	@Test
	public void test() throws MalformedURLException, IOException {
		String file = "D:/Server/Bilder/Ralf/Rennrad/2004_06_06 Alpentour Schweiz/Bilder Jojo/2004_0604_235145.JPG";
		
		BufferedImage image = PictureCaption.convert(file, "Hello picture", false, false, 24);
		File outputfile = new File("d:/saved1.png");
	    ImageIO.write(image, "png", outputfile);
	    
		image = PictureCaption.convert(file, "Hello picture", false, true, 24);
		outputfile = new File("d:/saved2.png");
	    ImageIO.write(image, "png", outputfile);
	    
		image = PictureCaption.convert(file, "Hello picture", true, false, 24);
		outputfile = new File("d:/saved3.png");
	    ImageIO.write(image, "png", outputfile);
	    
		image = PictureCaption.convert(file, "Hello picture", true, true, 24);
		outputfile = new File("d:/saved4.png");
	    ImageIO.write(image, "png", outputfile);
	    
	    System.out.println("success.");
	}
}
