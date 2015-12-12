package java_xml.songbook.engine;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

public class SongEntry {
	@XmlTransient
	Logger log = Logger.getLogger(SongEntry.class.getName());

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

		if (!file.exists()) {
			log.warning("No file for this song! Return null!");
			return null;
		}

		JAXBContext songDataContext;
		try {
			songDataContext = JAXBContext.newInstance(SongData.class);
			Unmarshaller songDataUnmarshaller = songDataContext.createUnmarshaller();
			songData = (SongData) songDataUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
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
		SongData songData = getSongData();
		if (songData == null) {
			log.warning("return null!");
			return null;
		}
		return songData.getMergedLyricsAndChords();
	}

	@Override
	public String toString() {
		return name;
	}
}
