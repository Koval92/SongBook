package java_xml.songbook.engine;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "artist")
public class ArtistData {
	private List<SongEntry> songEntries;
	private String description;

	@XmlElement(name = "description")
	public String getDescription() {
		return description.trim();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElementWrapper(name = "songs")
	@XmlElement(name = "song")
	public List<SongEntry> getSongEntries() {
		return songEntries;
	}

	public void setSongEntries(List<SongEntry> songEntries) {
		this.songEntries = songEntries;
	}

	@Override
	public String toString() {
		return "ArtistData [songEntries=" + songEntries + ", description=" + description + "]";
	}

	public SongEntry getSongEntry(String id) {
		for (SongEntry songEntry : songEntries) {
			if(songEntry.getId().equals(id)) {
				return songEntry;
			}
		}
		
		return null;
	}
}
