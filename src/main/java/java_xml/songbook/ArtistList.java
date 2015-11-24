package java_xml.songbook;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "artists")
public class ArtistList {
	private List<ArtistEntry> artists = new ArrayList<>();

	@XmlElement(name = "artist")
	public List<ArtistEntry> getArtists() {
		return artists;
	}

	public void setArtists(List<ArtistEntry> artists) {
		this.artists = artists;
	}
}

class ArtistEntry {
	private String id;
	private String name;

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

	public ArtistEntry(String name, String id) {
		setName(name);
		setId(id);
	}
	
	public ArtistEntry() {
		
	}
}
