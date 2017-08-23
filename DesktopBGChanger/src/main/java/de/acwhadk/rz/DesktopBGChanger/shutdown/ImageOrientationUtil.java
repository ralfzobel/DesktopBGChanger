package de.acwhadk.rz.DesktopBGChanger.shutdown;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

public class ImageOrientationUtil {

	final static Logger logger = Logger.getLogger(ImageOrientationUtil.class);

/**
 * Checks the orientation of the image and corrects it if necessary.
 * <p>If the orientation of the image does not need to be corrected, no operation will be performed.</p>
 * @param inputStream
 * @return
 * @throws ImageProcessingException
 * @throws IOException
 * @throws MetadataException
 */
public static BufferedImage correctOrientation(File file) throws ImageProcessingException, IOException, MetadataException {
    Metadata metadata = ImageMetadataReader.readMetadata(file);
    if(metadata != null) {
        if(metadata.containsDirectoryOfType(ExifIFD0Directory.class)) {
            // Get the current orientation of the image
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);

            // Create a buffered image from the input stream
            BufferedImage bimg = ImageIO.read(file);


            // Get the current width and height of the image
            int[] imageSize = {bimg.getWidth(), bimg.getHeight()};
            int width = imageSize[0];
            int height = imageSize[1];

            // Determine which correction is needed
            AffineTransform t = new AffineTransform();
            if (orientation > 1) {
            	logger.info("rotate image with orientation="+orientation);
            }
            switch(orientation) {
            case 1:
                // no correction necessary skip and return the image
                return bimg;
            case 2: // Flip X
                t.scale(-1.0, 1.0);
                t.translate(-width, 0);
                return transform(bimg, t);
            case 3: // PI rotation 
                t.translate(width, height);
                t.rotate(Math.PI);
                return transform(bimg, t);
            case 4: // Flip Y
                t.scale(1.0, -1.0);
                t.translate(0, -height);
                return transform(bimg, t);
            case 5: // - PI/2 and Flip X
                t.rotate(-Math.PI / 2);
                t.scale(-1.0, 1.0);
                return transform(bimg, t);
            case 6: // -PI/2 and -width
            {
                int centerX = Math.min(width, height)/2;
                int centerY = centerX;

                int w1 = height;
                int h1 = width;

            	t.setToQuadrantRotation(1, centerX, centerY);
                AffineTransformOp opRotated = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);
                BufferedImage transformedImage = new BufferedImage(w1, h1, bimg.getType());

                opRotated.filter(bimg, transformedImage);
                return transformedImage;
            }
            case 7: // PI/2 and Flip
                t.scale(-1.0, 1.0);
                t.translate(height, 0);
                t.translate(0, width);
                t.rotate(  3 * Math.PI / 2);
                return transform(bimg, t);
            case 8: // PI / 2
            {
                int centerX = Math.max(width, height)/2;
                int centerY = centerX;

                int w1 = height;
                int h1 = width;

            	t.setToQuadrantRotation(3, centerX, centerY);
                AffineTransformOp opRotated = new AffineTransformOp(t, AffineTransformOp.TYPE_BILINEAR);
                BufferedImage transformedImage = new BufferedImage(w1, h1, bimg.getType());

                opRotated.filter(bimg, transformedImage);
                return transformedImage;     
            }
            default:
            	return null;
            }
        }
    }

    return null;
}

/**
 * Performs the tranformation
 * @param bimage
 * @param transform
 * @return
 * @throws IOException
 */
private static BufferedImage transform(BufferedImage bimage, AffineTransform transform) throws IOException {
    // Create an transformation operation
    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);

    // Create an instance of the resulting image, with the same width, height and image type than the referenced one
    BufferedImage destinationImage = new BufferedImage( bimage.getWidth(), bimage.getHeight(), bimage.getType() );
    op.filter(bimage, destinationImage);

   return destinationImage;
}
}