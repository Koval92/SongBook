package java_xml.songbook;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SongInfo {
	private String title;
	private String author;
	private String fileName;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
