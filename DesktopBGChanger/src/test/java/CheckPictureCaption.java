import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.junit.Test;

import de.acwhadk.rz.DesktopBGChanger.picture.PictureCaption;

public class CheckPictureCaption {

	@Test
	public void test() throws MalformedURLException, IOException {
		String file = "D:/Server/Bilder/Ralf/Rennrad/2007 Alpencross MTB/Jojo/Alpencross 2007 157.jpg";
		
		BufferedImage image = PictureCaption.convert(file, file, false, false, 16);
		File outputfile = new File("d:/saved1.png");
	    ImageIO.write(image, "png", outputfile);
	    
		image = PictureCaption.convert(file, file, false, true, 16);
		outputfile = new File("d:/saved2.png");
	    ImageIO.write(image, "png", outputfile);
	    
		image = PictureCaption.convert(file, file, true, false, 16);
		outputfile = new File("d:/saved3.png");
	    ImageIO.write(image, "png", outputfile);
	    
		image = PictureCaption.convert(file, file, true, true, 16);
		outputfile = new File("d:/saved4.png");
	    ImageIO.write(image, "png", outputfile);
	    
	    System.out.println("success.");
	}
}
