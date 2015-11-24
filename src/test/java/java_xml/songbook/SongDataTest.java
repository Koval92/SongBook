package java_xml.songbook;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class SongDataTest {
	private static JAXBContext jaxbContext;
	private static Marshaller marshaller;
	private static Unmarshaller unmarshaller;

	@BeforeClass
	public static void initialize() throws JAXBException {
		jaxbContext = JAXBContext.newInstance(SongData.class);
		marshaller = jaxbContext.createMarshaller();
		unmarshaller = jaxbContext.createUnmarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	}

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	@Test
	public void shouldMarshalAndUnmarshal() throws IOException, JAXBException {
		int id = 15;
		File file = testFolder.newFile("song" + id + ".xml");

		SongData data = createSong(id, 10, 5);

		marshaller.marshal(data, file);

		SongData unmarshalledData = (SongData) unmarshaller.unmarshal(file);

		assertEquals(data.getLyrics(), unmarshalledData.getLyrics());
		assertEquals(data.getChords(), unmarshalledData.getChords());
	}

	@Test
	public void shouldMerge() {
		int id = 3;
		SongData data = createSong(id, 7, 3);
		List<String> merged = data.mergeLyricsAndChords();

		int expectedSize = data.getChords().size() + data.getLyrics().size();
		int actualSize = merged.size();
		assertEquals(expectedSize, actualSize);

		assertEquals(data.getLyrics().get(0), merged.get(0));
		assertEquals(data.getChords().get(0), merged.get(1));
		assertEquals(data.getLyrics().get(1), merged.get(2));
		assertEquals(data.getChords().get(1), merged.get(3));
		assertEquals(data.getLyrics().get(2), merged.get(4));
		assertEquals(data.getChords().get(2), merged.get(5));
		assertEquals(data.getLyrics().get(3), merged.get(6));
		assertEquals(data.getLyrics().get(4), merged.get(7));
		assertEquals(data.getLyrics().get(5), merged.get(8));
		assertEquals(data.getLyrics().get(6), merged.get(9));
	}

	private SongData createSong(int id, int lyricsLines, int chordsLines) {
		SongData data1 = new SongData();

		List<String> lyrics = new ArrayList<>();
		List<String> chords = new ArrayList<>();

		for (int i = 0; i < lyricsLines; i++) {
			lyrics.add("Song " + id + ", lyrics line " + i);
		}

		for (int i = 0; i < chordsLines; i++) {
			chords.add("Song " + id + ", chords line " + i);
		}

		data1.setLyrics(lyrics);
		data1.setChords(chords);
		return data1;
	}
}
