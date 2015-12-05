package java_xml.songbook.engine;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

	public void mergeWith(ArtistList newArtistList) {
		artists.addAll(newArtistList.getArtists());
	}
}
