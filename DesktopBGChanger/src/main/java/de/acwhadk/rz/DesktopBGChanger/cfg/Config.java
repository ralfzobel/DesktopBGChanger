package de.acwhadk.rz.DesktopBGChanger.cfg;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Config")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Config", namespace = "http://rz.acwhadk.de/DesktopBGChanger/cfg", propOrder = {
	    "includes",
	    "excludes",
	    "desktopFolder",
	    "minFiles",
	    "maxFiles",
	    "shutdownMode",
	    "currentFile",
	    "caption",
	    "left",
	    "top",
	    "fontSize"
})
public class Config {
	private List<String> includes;
	private List<String> excludes;
	private String desktopFolder;
	private int minFiles;
	private int maxFiles;
	private String shutdownMode;
	private String currentFile;
	private Boolean caption;
	private Boolean left;
	private Boolean top;
	private Integer fontSize;
	
	public List<String> getIncludes() {
		if (includes == null) {
			includes = new ArrayList<>();
		}
		return includes;
	}
	public void setIncludes(List<String> includes) {
		this.includes = includes;
	}
	public List<String> getExcludes() {
		if (excludes == null) {
			excludes = new ArrayList<>();
		}
		return excludes;
	}
	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}
	public int getMinFiles() {
		return minFiles;
	}
	public void setMinFiles(int minFiles) {
		this.minFiles = minFiles;
	}
	public int getMaxFiles() {
		return maxFiles;
	}
	public void setMaxFiles(int maxFiles) {
		this.maxFiles = maxFiles;
	}
	public String getCurrentFile() {
		return currentFile;
	}
	public void setCurrentFile(String currentFile) {
		this.currentFile = currentFile;
	}
	public String getShutdownMode() {
		return shutdownMode;
	}
	public void setShutdownMode(String shutdownMode) {
		this.shutdownMode = shutdownMode;
	}
	public String getDesktopFolder() {
		return desktopFolder;
	}
	public void setDesktopFolder(String desktopFolder) {
		this.desktopFolder = desktopFolder;
	}
	public Boolean getCaption() {
		return caption;
	}
	public void setCaption(Boolean caption) {
		this.caption = caption;
	}
	public Boolean getLeft() {
		return left;
	}
	public void setLeft(Boolean left) {
		this.left = left;
	}
	public Boolean getTop() {
		return top;
	}
	public void setTop(Boolean top) {
		this.top = top;
	}
	public Integer getFontSize() {
		return fontSize;
	}
	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

}
