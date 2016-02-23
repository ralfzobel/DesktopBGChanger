package de.acwhadk.rz.DesktopBGChanger.shutdown;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class App {

	private static final String WINDOWSFOLDER = "D:\\MeineHintergrundBilder";
	private static final String SOURCEFOLDER = "D:\\Server\\Bilder\\Ralf\\Running\\2011-12-31 Silvesterlauf";

	public static void main(String[] args) {
		List<Path> windowsFolder = fileList(WINDOWSFOLDER);
		for(Path p : windowsFolder) {
			try {
				Files.delete(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List<Path> sourceFolder = fileList(SOURCEFOLDER);

		for(int i=0; i<Math.min(50, sourceFolder.size()); ++i) {
			Path sourcePath = sourceFolder.get(i);
			Path targetPath = Paths.get(WINDOWSFOLDER+"\\"+sourcePath.getFileName().toString());
			CopyOption options = StandardCopyOption.REPLACE_EXISTING;
			try {
				Files.copy(sourcePath, targetPath, options);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Runtime.getRuntime().exec("shutdown /h");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Path> fileList(String directory) {
		List<Path> files = new ArrayList<>();
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
			for (Path path : directoryStream) {
				if (path.toString().toLowerCase().endsWith(".jpg")) {
					files.add(path);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return files;
	}

}
