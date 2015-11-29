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
		SongBook book = new SongBook();
		
		JAXBContext artistListContext = JAXBContext.newInstance(ArtistList.class);
		Unmarshaller artistListUnmarshaller = artistListContext.createUnmarshaller();

		for (File file : getFileList(new File("./test_db"))) {
			book.getArtistList().mergeWith((ArtistList) artistListUnmarshaller.unmarshal(file));
		}
		
		List<ArtistEntry> artists = book.getArtists();
		for (ArtistEntry artistEntry : artists) {
			artistEntry.display();
		}
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
