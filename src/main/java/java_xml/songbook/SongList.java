package java_xml.songbook;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "index")
public class SongList {
	private List<SongInfo> songInfos;

	public static void main(String[] args) throws JAXBException {
		File songListFile = new File("songs/index.xml");
		JAXBContext songListContext = JAXBContext.newInstance(SongList.class);
		Unmarshaller songListUnmarshaller = songListContext.createUnmarshaller();

		SongList songList = (SongList) songListUnmarshaller.unmarshal(songListFile);

		for (SongInfo info : songList.songInfos) {
			System.out.println(info.getAuthor() + " - " + info.getTitle() + ", " + info.getFilename());
		}
	}

	@XmlElementWrapper(name = "songs")
	@XmlElement(name = "song")
	public List<SongInfo> getSongInfos() {
		return songInfos;
	}

	public void setSongInfos(List<SongInfo> songInfos) {
		this.songInfos = songInfos;
	}

}
