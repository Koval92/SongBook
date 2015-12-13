package java_xml.songbook.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.xml.bind.JAXBException;

import java_xml.songbook.engine.ArtistEntry;
import java_xml.songbook.engine.SongBook;
import java_xml.songbook.engine.SongEntry;

public class App {
	Logger log = Logger.getLogger(App.class.getName());

	private JFrame frame;
	SongBook book;

	private JComboBox<ArtistEntry> artistComboBox;
	private JComboBox<SongEntry> songComboBox;
	private JTextArea mergedTextArea;
	private JScrollPane mergedPane;

	private Container contentPane;
	private JTabbedPane tabbedPane;

	private JTextArea lyricsTextArea;

	private JTextArea chordsTextArea;
	private JPanel panel_1;
	private JPanel panel_3;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App app = new App();
					app.frame.setVisible(true);
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
		initializeFrame();
		contentPane = frame.getContentPane();
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
																		panel.setLayout(new GridLayout(0, 1, 10, 5));
																		
																		panel_1 = new JPanel();
																		panel.add(panel_1);
																				panel_1.setLayout(new GridLayout(0, 2, 5, 0));
																		
																				JLabel lblArtist = new JLabel("Artist:");
																				panel_1.add(lblArtist);
																				lblArtist.setHorizontalAlignment(SwingConstants.RIGHT);
																				
																						artistComboBox = new JComboBox<ArtistEntry>();
																						panel_1.add(artistComboBox);
																						
																								artistComboBox.setSelectedIndex(-1);
																		
																		panel_3 = new JPanel();
																		panel.add(panel_3);
																				panel_3.setLayout(new GridLayout(0, 2, 5, 0));
																		
																				JLabel lblSong = new JLabel("Song:");
																				panel_3.add(lblSong);
																				lblSong.setHorizontalAlignment(SwingConstants.RIGHT);
																				
																						songComboBox = new JComboBox<SongEntry>();
																						panel_3.add(songComboBox);

		addItemListenerForArtistComboBox();
		addItemListenerForSongComboBox();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		mergedPane = new JScrollPane();
		tabbedPane.addTab("Merged", null, mergedPane, null);

		mergedTextArea = new JTextArea();
		mergedPane.setViewportView(mergedTextArea);
		mergedTextArea.setEditable(false);
		
		JScrollPane separatePane = new JScrollPane();
		tabbedPane.addTab("Separate", null, separatePane, null);
		
		JPanel panel_2 = new JPanel();
		separatePane.setViewportView(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 15, 0));
		
		lyricsTextArea = new JTextArea();
		lyricsTextArea.setEditable(false);
		panel_2.add(lyricsTextArea);
		
		chordsTextArea = new JTextArea();
		chordsTextArea.setEditable(false);
		panel_2.add(chordsTextArea);

		for (ArtistEntry artistEntry : book.getArtists()) {
			artistComboBox.addItem(artistEntry);
		}
	}

	private void addItemListenerForArtistComboBox() {
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
						mergedTextArea.setText("No songs for this author");
					}
				}
			}
		});
	}

	private void addItemListenerForSongComboBox() {
		songComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					SongEntry songEntry = (SongEntry) e.getItem();
					log.info("Chosen song: " + songEntry);

					List<String> mergedLyricsAndChords = songEntry.getMergedLyricsAndChords();
					String mergedAsString = "";
					if (mergedLyricsAndChords != null) {
						for (String line : mergedLyricsAndChords) {
							mergedAsString = mergedAsString + line + "\n";
						}
						mergedTextArea.setText(mergedAsString.trim());
					} else {
						mergedTextArea.setText("No file for this song!");
					}
					
					List<String> lyrics = songEntry.getLyrics();
					String lyricsAsString = "";
					if (lyrics != null) {
						for (String line : lyrics) {
							lyricsAsString = lyricsAsString + line + "\n";
						}
						lyricsTextArea.setText(lyricsAsString.trim());
					}
					
					List<String> chords = songEntry.getChords();
					String chordsAsString = "";
					if (lyrics != null) {
						for (String line : chords) {
							chordsAsString = chordsAsString + line + "\n";
						}
						chordsTextArea.setText(chordsAsString.trim());
					}
				}
			}
		});
	}

	private void initializeFrame() {
		frame = new JFrame();
		frame.setTitle("SongBook");
		frame.setBounds(100, 100, 474, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
