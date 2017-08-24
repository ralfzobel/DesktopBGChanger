package de.acwhadk.rz.DesktopBGChanger.cfg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;


/**
 * Read and write an index xml file for all activities referencing the activity files
 * and containing some important data.
 * 
 * @author Ralf
 *
 */
public class ConfigToXML {

	private static final String JAXB_PACKAGES = "de.acwhadk.rz.DesktopBGChanger.cfg";
	private static final String APP_NAME = "DesktopBGChanger";
	private static final String CFG_FILE = "config.xml";
	private static final String sep = File.separator;
	
	public static Config load(File file) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(JAXB_PACKAGES);

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		@SuppressWarnings("unchecked")
		JAXBElement<Config> obj = (JAXBElement<Config>) unmarshaller.unmarshal(file);
		return obj.getValue();
	}

	public static void save(File file, Config cfg) throws IOException, JAXBException {
		JAXBContext jc;
		jc = JAXBContext.newInstance(JAXB_PACKAGES);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		QName name = new QName("http://rz.acwhadk.de/DesktopBGChanger/cfg", cfg.getClass().getSimpleName());

		try (FileOutputStream fos = new FileOutputStream(file)) {
			JAXBElement<Object> element = new JAXBElement<Object>(name,
					Object.class, null, cfg);
			m.marshal(element, fos);
		}
	}
	
	public static File getConfigFile() throws IOException {
		String baseFolder = System.getenv("LOCALAPPDATA");
		String appFolderName = baseFolder + sep + APP_NAME;
		File appFolder = new File(appFolderName);
		if (!appFolder.exists()) {
			if (!appFolder.mkdir()) {
				throw new IOException("cannot create directory " + appFolderName);
			}
		}
		String fileName = baseFolder + sep + APP_NAME + sep + CFG_FILE;
		return new File(fileName);
	}
	

}
