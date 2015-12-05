package java_xml.songbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class SongBook {
	private ArtistList artistList = new ArtistList();
	
	public static void main(String args[]) throws JAXBException {
		String dirName = "./test_db";
		
		SongBook book = createSongBookFromDir(dirName);
		
		ArtistEntry artistEntry = book.getArtistEntry("jenny_donelly");
		SongEntry songEntry = artistEntry.getSongEntry("song_15");
		
		List<String> lyricsAndChords = book.getArtistEntry("jenny_donelly").getSongEntry("song_15").getMergedLyricsAndChords();
		for (String string : lyricsAndChords) {
			System.out.println(string);
		}
		
		book.getArtistEntry("aaa").getSongEntries();
	}

	private static SongBook createSongBookFromDir(String dirName) throws JAXBException {
		SongBook songBook = new SongBook();
		JAXBContext artistListContext = JAXBContext.newInstance(ArtistList.class);
		Unmarshaller artistListUnmarshaller = artistListContext.createUnmarshaller();

		for (File file : getFileList(new File(dirName))) {
			songBook.getArtistList().mergeWith((ArtistList) artistListUnmarshaller.unmarshal(file));
		}
		return songBook;
	}
	
	private ArtistEntry getArtistEntry(String id) {
		for (ArtistEntry artistEntry : getArtists()) {
			if(artistEntry.getId().equals(id)) {
				return artistEntry;
			}
		}
		
		return null;
	}

	public static List<File> getFileList(final File folder) {
		List<File> files = new ArrayList<>();
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	            files.add(fileEntry);
	        }
	    }
	    return files;
	}
	
	public List<ArtistEntry> getArtists() {
		return artistList.getArtists();
	}

	public ArtistList getArtistList() {
		return artistList;
	}

	public void setArtistList(ArtistList artistList) {
		this.artistList = artistList;
	}
}
