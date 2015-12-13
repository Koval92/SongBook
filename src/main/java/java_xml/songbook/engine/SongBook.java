package java_xml.songbook.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class SongBook {
	private ArtistList artistList = new ArtistList();

	public static SongBook createSongBookFromDir(String dirName) throws JAXBException {
		SongBook songBook = new SongBook();
		JAXBContext artistListContext = JAXBContext.newInstance(ArtistList.class);
		Unmarshaller artistListUnmarshaller = artistListContext.createUnmarshaller();

		for (File file : getFileList(new File(dirName))) {
			songBook.getArtistList().mergeWith((ArtistList) artistListUnmarshaller.unmarshal(file));
		}
		
		songBook.getArtists().sort(new Comparator<ArtistEntry>() {
			@Override
			public int compare(ArtistEntry entry1, ArtistEntry entry2) {
				return entry1.getName().compareTo(entry2.getName());
			}
		});
		
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

	private static List<File> getFileList(final File folder) {
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
