package de.acwhadk.rz.DesktopBGChanger.shutdown;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import de.acwhadk.rz.DesktopBGChanger.cfg.Config;
import de.acwhadk.rz.DesktopBGChanger.cfg.ConfigToXML;
import de.acwhadk.rz.DesktopBGChanger.picture.PictureCaption;


public class App {
	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		try {
			new App();
		} catch (IOException | JAXBException e1) {
			logger.fatal("Exception: ", e1);
		}
	}

	private App() throws IOException, JAXBException {
		logger.info("DesktopBGChanger started");
		Config config = loadConfig();
		List<Path> fileList = loadFileList(config);
		deleteOldFiles(config);	
		copyFiles(fileList, config);
		shutdown(config);
		saveConfig(config);
		logger.info("DesktopBGChanger successful");
	}
	
	private Config loadConfig() throws IOException, JAXBException {
		File file = ConfigToXML.getConfigFile();
		return ConfigToXML.load(file);
	}
	
	private List<Path> loadFileList(Config config) throws IOException {
		List<Path> allFolders = new ArrayList<>();
		for(String pathName : config.getIncludes()) {
			allFolders.addAll(fileList(pathName));
		}				
		return allFolders;
	}

	private void deleteOldFiles(Config config) throws IOException {
		List<Path> windowsFolder = fileList(config.getDesktopFolder());
		for(Path p : windowsFolder) {
			Files.delete(p);
		}
	}
	
	private void copyFiles(List<Path> fileList, Config config) throws IOException {
		Iterator<Path> it = fileList.iterator();
		while (it.hasNext()) {
			Path path = it.next();
			if (path.toString().equals(config.getCurrentFile())) {
				break;
			}
		}
		int cnt = 1;
		Path lastPath = null;
		while(true) {
			// at end, restart loop
			if (!it.hasNext()) {
				it = fileList.iterator();
			}		
			// get next path
			Path sourcePath = it.next();
			
			// if min files are copied and the folder changes, break
			if (cnt >= config.getMinFiles() && pathesDiffer(sourcePath, lastPath)) {
				break;
			}
			
			// copy file
			copyFile(config, sourcePath);

			// check if maximum is reached
			if (cnt >= config.getMaxFiles()) {
				break;
			}
			
			// init next loop
			lastPath = sourcePath;
			++cnt;
		}
		// save last copied file for next execution
		config.setCurrentFile(lastPath.toString());
	}

	private void copyFile(Config config, Path sourcePath) throws IOException {
		boolean subscribe = config.getCaption();
		boolean left = config.getLeft();
		boolean top = config.getTop();
		int fontSize = config.getFontSize();
		
		if (!subscribe || fontSize <= 0) {
			Path targetPath = Paths.get(config.getDesktopFolder()+"\\"+sourcePath.getFileName().toString());
			CopyOption options = StandardCopyOption.REPLACE_EXISTING;
			Files.copy(sourcePath, targetPath, options);
		} else {
			logger.info("write path to " + sourcePath.toString());
			BufferedImage image = PictureCaption.convert(sourcePath.toString(), sourcePath.toString(), left, top, fontSize);
			String filename = sourcePath.getFileName().toString();
			filename = filename.toLowerCase().replace("jpg", "png");
			File outputfile = new File(config.getDesktopFolder()+"\\"+filename);
		    ImageIO.write(image, "png", outputfile);
		}
	}

	private void shutdown(Config config) throws IOException {
		String cmd = null;
		switch(config.getShutdownMode()) {
		case "Hibernate":
			cmd = "shutdown /h";
			break;
		case "Shutdown":
			cmd = "shutdown /s";
			break;
		case "Sign off":
			cmd = "shutdown /l";
			break;
		default:
			break;
		}
		if (cmd != null) {
			Runtime.getRuntime().exec(cmd);
		}
	}

	private void saveConfig(Config config) throws IOException, JAXBException {
		File file = ConfigToXML.getConfigFile();
		ConfigToXML.save(file, config);
	}

	private List<Path> fileList(String directory) throws IOException {
		List<Path> files = new ArrayList<>();
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
			for (Path path : directoryStream) {
				if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
					files.addAll(fileList(path.toString()));
				} else if (path.toString().toLowerCase().endsWith(".jpg") || path.toString().toLowerCase().endsWith(".png")) {
					files.add(path);
				}
			}
		}
		return files;
	}

	private boolean pathesDiffer(Path path1, Path path2) throws IOException {
		if (path1 == null || path2 == null) {
			return false;
		}
		return !Files.isSameFile(path1.getParent(), path2.getParent());
	}

}
