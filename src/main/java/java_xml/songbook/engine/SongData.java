package java_xml.songbook.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SongData {
	private List<String> lyrics;
	private List<String> chords;

	public List<String> getMergedLyricsAndChords() {
		int c1 = 0, c2 = 0;
		List<String> merged = new ArrayList<>();

		while (c1 < lyrics.size() || c2 < chords.size()) {
			if (c2 < chords.size())
				merged.add(chords.get(c2++));
			if (c1 < lyrics.size())
				merged.add(lyrics.get(c1++));
		}
		return merged;
	}

	@XmlElementWrapper(name = "lyrics")
	@XmlElement(name = "line")
	public List<String> getLyrics() {
		return lyrics;
	}

	public void setLyrics(List<String> lyrics) {
		this.lyrics = lyrics;
	}

	@XmlElementWrapper(name = "chords")
	@XmlElement(name = "line")
	public List<String> getChords() {
		return chords;
	}

	public void setChords(List<String> chords) {
		this.chords = chords;
	}
	
	public static SongData getEmpty() {
		SongData songData = new SongData();
		songData.lyrics = Arrays.asList("*** No lyrics for this song ***");
		songData.chords = Arrays.asList("*** No chords for this song ***");
		
		return songData;
	}
}
