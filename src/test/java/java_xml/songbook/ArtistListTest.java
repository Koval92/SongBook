package java_xml.songbook;

import static org.junit.Assert.*;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Test;

public class ArtistListTest {
	private static JAXBContext jaxbContext;
	private static Marshaller marshaller;
	private static Unmarshaller unmarshaller;
	
	@BeforeClass
	public static void initialize() throws JAXBException {
		jaxbContext = JAXBContext.newInstance(ArtistList.class);
		marshaller = jaxbContext.createMarshaller();
		unmarshaller = jaxbContext.createUnmarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	}
	
	@Test
	public void shouldMarshalArtists() throws JAXBException {
		File file = new File("test_db//index3.xml");
		
		ArtistList artists = new ArtistList();
		artists.getArtists().add(new ArtistEntry("William Kidd", "William Kidd"));
		artists.getArtists().add(new ArtistEntry("Johnny Doe", "Johnny Doe"));
		
		marshaller.marshal(artists, file);
	}
	
	@Test
	public void shouldUnmarshalSingleFile() throws JAXBException {
		File file = new File("test_db//index2.xml");
		ArtistList artists = (ArtistList) unmarshaller.unmarshal(file);
		
		for(ArtistEntry artist : artists.getArtists()) {
			System.out.println(artist.getName() + " (" + artist.getId() + ")");
		}
	}

}
