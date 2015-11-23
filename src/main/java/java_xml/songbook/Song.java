package java_xml.songbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Song {
	public static void main(String args[]) {
		Song song = new Song();
		song.setTitle("Tytul piosenki");
		song.setAuthor("Imie nazwisko");

		List<String> lyrics = new ArrayList<>();
		List<String> chords = new ArrayList<>();

		lyrics.add("First line");
		lyrics.add("Second line");

		chords.add("E A E");
		chords.add("G H D");

		song.setLyrics(lyrics);
		song.setChords(chords);

		System.out.println(song.toString());
		
		String fileName = (song.getAuthor() + " " + song.getTitle()).replaceAll(" ", "_");

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Song.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(song, new File("songs/" + fileName + ".xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	private String title;
	private String author;
	private List<String> lyrics;
	private List<String> chords;

	@Override
	public String toString() {
		return "Song [title=" + title + ", author=" + author + ", lyrics=" + lyrics + ", chords=" + chords + "]";
	}

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

	@XmlElement(name = "line")
	@XmlElementWrapper(name = "lyrics")
	public List<String> getLyrics() {
		return lyrics;
	}

	public void setLyrics(List<String> lyrics) {
		this.lyrics = lyrics;
	}

	@XmlElement(name = "line")
	@XmlElementWrapper(name = "chords")
	public List<String> getChords() {
		return chords;
	}

	public void setChords(List<String> chords) {
		this.chords = chords;
	}

}
