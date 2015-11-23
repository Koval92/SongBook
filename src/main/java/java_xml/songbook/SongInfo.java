package java_xml.songbook;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SongInfo {
	private String title;
	private String author;
	private String filename;

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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
