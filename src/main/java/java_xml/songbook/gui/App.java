package java_xml.songbook.gui;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.xml.bind.JAXBException;

import java_xml.songbook.engine.ArtistEntry;
import java_xml.songbook.engine.SongBook;
import java_xml.songbook.engine.SongEntry;

public class App {
	Logger log = Logger.getLogger(App.class.getName());

	private JFrame frmSongbook;
	SongBook book;

	private JComboBox<ArtistEntry> artistComboBox;
	private JComboBox<SongEntry> songComboBox;
	private JTextArea textArea;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmSongbook.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public App() {
		try {
			book = SongBook.createSongBookFromDir("./test_db");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {
		frmSongbook = new JFrame();
		frmSongbook.setTitle("SongBook");
		frmSongbook.setResizable(false);
		frmSongbook.setBounds(100, 100, 400, 400);
		frmSongbook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSongbook.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(139, 11, 246, 70);
		frmSongbook.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblArtist = new JLabel("Artist");
		lblArtist.setHorizontalAlignment(SwingConstants.RIGHT);
		lblArtist.setBounds(7, 15, 63, 14);
		panel.add(lblArtist);

		artistComboBox = new JComboBox<ArtistEntry>();
		artistComboBox.setBounds(80, 12, 156, 20);
		panel.add(artistComboBox);

		artistComboBox.setSelectedIndex(-1);

		artistComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					ArtistEntry artistEntry = (ArtistEntry) e.getItem();
					log.info("Chosen author: " + artistEntry);
					
					songComboBox.removeAllItems();
					songComboBox.setSelectedIndex(-1);

					List<SongEntry> songEntries = artistEntry.getSongEntries();
					if (songEntries != null) {
						for (SongEntry songEntry : songEntries) {
							songComboBox.addItem(songEntry);
						}
					} else {
						textArea.setText("No songs for this author");
					}
				}
			}
		});

		JLabel lblSong = new JLabel("Song:");
		lblSong.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSong.setBounds(7, 44, 63, 14);
		panel.add(lblSong);

		songComboBox = new JComboBox<SongEntry>();
		songComboBox.setBounds(80, 41, 156, 20);
		panel.add(songComboBox);

		songComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					SongEntry songEntry = (SongEntry) e.getItem();
					log.info("Chosen song: " + songEntry);

					List<String> mergedLyricsAndChords = songEntry.getMergedLyricsAndChords();
					String str = "";
					if (mergedLyricsAndChords != null) {
						for (String line : mergedLyricsAndChords) {
							str = str + line + "\n";
						}
						textArea.setText(str.trim());
					} else {
						textArea.setText("No file for this song!");
					}
				}
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 92, 375, 268);
		frmSongbook.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);

		for (ArtistEntry artistEntry : book.getArtists()) {
			artistComboBox.addItem(artistEntry);
		}
	}
}
