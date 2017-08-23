import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import de.acwhadk.rz.DesktopBGChanger.picture.PictureCaption;
import de.acwhadk.rz.DesktopBGChanger.shutdown.ImageOrientationUtil;

public class CheckPictureCaption {

	@Test
	public void test() throws MalformedURLException, IOException {
		String filename = "D:\\Server\\Bilder\\Ralf\\Radgruppe Hahn\\2015 Pyrenäen\\Gerd\\IMG_1728.JPG";
		
		BufferedImage img;
		try {
			img = ImageOrientationUtil.correctOrientation(new File(filename));
		} catch (ImageProcessingException | MetadataException e) {
			return;
		}
		BufferedImage image = PictureCaption.convert(img, filename, false, false, 16);
		File outputfile = new File("d:/saved1.png");
	    ImageIO.write(image, "png", outputfile);
	    
		image = PictureCaption.convert(img, filename, false, true, 16);
		outputfile = new File("d:/saved2.png");
	    ImageIO.write(image, "png", outputfile);
	    
		image = PictureCaption.convert(img, filename, true, false, 16);
		outputfile = new File("d:/saved3.png");
	    ImageIO.write(image, "png", outputfile);
	    
		image = PictureCaption.convert(img, filename, true, true, 16);
		outputfile = new File("d:/saved4.png");
	    ImageIO.write(image, "png", outputfile);
	    
	    System.out.println("success.");
	}
}
