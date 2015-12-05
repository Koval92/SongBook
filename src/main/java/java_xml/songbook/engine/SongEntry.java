package java_xml.songbook.engine;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class SongEntry {
	private String name;
	private String id;

	@XmlValue
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SongData getSongData() {
		String filename = "test_db//songs//" + id + ".xml";
		File file = new File(filename);
		SongData songData = null;

		if (file.exists()) {
			JAXBContext songDataContext;
			try {
				songDataContext = JAXBContext.newInstance(SongData.class);
				Unmarshaller songDataUnmarshaller = songDataContext.createUnmarshaller();
				songData = (SongData) songDataUnmarshaller.unmarshal(file);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No file for this song!");
		}

		return songData;
	}

	public List<String> getLyrics() {
		return getSongData().getLyrics();
	}
	
	public List<String> getChords() {
		return getSongData().getChords();
	}
	
	public List<String> getMergedLyricsAndChords() {
		return getSongData().getMergedLyricsAndChords();
	}

	@Override
	public String toString() {
		return name;
	}
}
