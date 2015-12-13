package java_xml.songbook.engine;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

public class ArtistEntry {
	@XmlTransient
	Logger log = Logger.getLogger(ArtistEntry.class.getName());
	
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
		this.id = id.toLowerCase().replaceAll(" ", "_");
	}

	@Override
	public String toString() {
		// return "ArtistEntry [name=" + name + ", id=" + id + "]";
		return name;
	}

	public ArtistData getArtistData() {
		String filename = "test_db//artists//" + id + ".xml";
		File file = new File(filename);
		ArtistData artistData = null;

		if (file.exists()) {
			JAXBContext artistDataContext;
			try {
				artistDataContext = JAXBContext.newInstance(ArtistData.class);
				Unmarshaller artistDataUnmarshaller = artistDataContext.createUnmarshaller();
				artistData = (ArtistData) artistDataUnmarshaller.unmarshal(file);
				
				artistData.getSongEntries().sort(new Comparator<SongEntry>() {
					@Override
					public int compare(SongEntry entry1, SongEntry entry2) {
						return entry1.getName().compareTo(entry2.getName());
					}
				});
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		} else {
			log.warning("No file for this artist!");
		}

		return artistData;
	}

	public void display() {
		System.out.println("Name:\t" + this.getName());
		System.out.println("Id:\t" + this.getId());

		ArtistData artistData = getArtistData();

		if (artistData != null) {
			System.out.println("Description:");
			System.out.println(artistData.getDescription());
			System.out.println("Songs:");
			for (SongEntry songEntry : artistData.getSongEntries()) {
				System.out.println("\t" + songEntry.getName() + " (" + songEntry.getId() + ")");
			}
		}
		System.out.println();
	}

	public SongEntry getSongEntry(String id) {
		return getArtistData().getSongEntry(id);
	}

	public List<SongEntry> getSongEntries() {
		ArtistData artistData = getArtistData();
		if (artistData == null) {
			log.warning("return null!");
			return null;			
		}
		return artistData.getSongEntries();
	}
}