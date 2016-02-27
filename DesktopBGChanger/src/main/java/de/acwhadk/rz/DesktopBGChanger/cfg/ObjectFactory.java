package de.acwhadk.rz.DesktopBGChanger.cfg;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * Object factory needed for jaxb.
 * 
 * @author Ralf
 *
 */
@XmlRegistry
public class ObjectFactory {

   private final static QName _Config_QNAME = new QName("http://rz.acwhadk.de/DesktopBGChanger/cfg", "Config");
   
   public ObjectFactory() {
    }
    
	public Config createConfig() {
		return new Config();
	}
	
    @XmlElementDecl(namespace = "http://rz.acwhadk.de/DesktopBGChanger/cfg", name = "Config")
    public JAXBElement<Config> createTrainingFileContainer(Config value) {
        return new JAXBElement<Config>(_Config_QNAME, Config.class, null, value);
    }
}
