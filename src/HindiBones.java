import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTextField;

import datenstruktur.Boden;
import datenstruktur.Heiltrank;
import datenstruktur.Monster;
import datenstruktur.Schluessel;
import datenstruktur.Spielelement;
import datenstruktur.Spieler;
import datenstruktur.Tuer;
import datenstruktur.Wand;

/**
 * Hindi Bones Fenster auf dem das Spiel laueft 
 * Schnittstelle zum Client
 * 
 * @author <Nazari, Mina, 5988551>
 *
 */

public class HindiBones extends JFrame implements KeyListener {
	
	
	
	private static final long serialVersionUID = 1L;


	Spielflaeche spielflaeche;
	private Leiste leiste;
	private Highscore highscore;
	private MenuLeiste menuLeiste;
	private Steuerung steuerung;
	private Minimap minimap;
	private Anmeldung anmeldung;


	public LinkedList<Monster> monsterListe;
	public Spieler spieler;
	public Spielelement[][] level;

	public int currentLevel = 0;
	public boolean spielende = false;
	public boolean verloren = false;
	public long startZeit;
	public int benoetigteZeit;
	public boolean nebelAn = true;

	private boolean spielerInHighscore = false;
	public boolean highscoreAngezeigt = false;

	public final int MAXLEVEL = 5;
	public final int WIDTH = 16;
	public final int HEIGHT = 16;
	public final int BOX = 60;
	public final int BOX2 = 15;

	
	/**
	 * Das Spiel Fenster wird erzeugt
	 * 
	 * @author <Nazari, Mina, 5988551>
	 * @param dicke
	 *            : Die Breite des Fensters
	 * @param hoehe
	 *            : Die Hoehe des Fensters
	 * @param titel:
	 *            Name des Fensters
	 */
	public HindiBones(int width, int height, String title) {
		initialisiereJFrame(width, height, title);
		
		starteNeuesSpiel();
		

	}

	public void initialisiereJFrame(int width, int height, String title) {
		// Layout fuer unser Fenster
		
			this.setLayout(new BorderLayout());
		// Erzeuge Objekte der Panels
	
		this.spielflaeche = new Spielflaeche(this);
		this.minimap = new Minimap(this);
		this.leiste = new Leiste(this);
		this.steuerung = new Steuerung();
		this.highscore = new Highscore();
		
		// Erzeuge Menuleiste
		this.menuLeiste = new MenuLeiste(this);
		this.anmeldung = new Anmeldung(this);

		
		// Es wird die gewuenschte Groesse angegeben
		spielflaeche.setPreferredSize(new Dimension(width, height));
		minimap.setPreferredSize(new Dimension(width/2, height/2));
		//chatfenster.setPreferredSize(new Dimension(width/2, height/2));
		leiste.setPreferredSize(new Dimension(width, BOX));
		steuerung.setPreferredSize(new Dimension(width, height + BOX));
		highscore.setPreferredSize(new Dimension(width, height + BOX));
		anmeldung.setPreferredSize(new Dimension(width, height + BOX));
		// Erstelle das Spielfeld
		zeigeSpielfeld();
		zeigeAnmeldung();
		// Zentriere das Fenster auf dem Bildschirm
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2),
				(int) ((d.getHeight() - this.getHeight()) / 2));
		// Standardsetup
		

		this.addKeyListener(this);
		this.setResizable(false);
		this.setTitle(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Methode zum Anzeigen des Anmelde Panels +
	 * Verbindung zum Client wird aufgebaut
	 * 
	 * @author Nazari, Mina, 5988551
	 */

	public void zeigeAnmeldung() {
		//steuerungAngezeigt = false;
		highscoreAngezeigt = false;
		this.remove(minimap);
		this.remove(leiste);
		this.remove(spielflaeche);
		this.remove(highscore);
		this.remove(steuerung);
		try {
			Thread.sleep(50);
			this.add(anmeldung, BorderLayout.CENTER);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.requestFocus();
		this.pack();
		anmeldung.repaint();

	}

	/**
	 * Methoden zum Anzeigen des Spielfelds 
	 * 
	 * @author Nazari, Mina, 5988551
	 */
	
	public void zeigeSpielfeld() {
		// entferne alles
		highscoreAngezeigt = false;
		this.remove(highscore);
		this.remove(steuerung);
		this.remove(anmeldung);
		// erstelle das Spielfeld

		this.add(spielflaeche, BorderLayout.CENTER);
		this.add(leiste, BorderLayout.SOUTH);
		this.add(menuLeiste, BorderLayout.NORTH);
		this.add(minimap, BorderLayout.EAST);
		
		
		// aktiviere das fertige Spielfeld
		this.requestFocus();
		this.pack();
	}

	/**
	 * Ergaenzt um einige Elemente Anzeigen des Highscores
	 * 
	 * @author <Nazari, Mina, 5988551>
	 */
	public void zeigeHighscore() {
		// entferne alles
		highscoreAngezeigt = true;
		this.remove(spielflaeche);
		this.remove(leiste);
		this.remove(steuerung);
		
		// erstelle die Highscoreanzeige
		this.add(highscore, BorderLayout.CENTER);
		
		// aktiviere die Highscoreanzeige
		this.requestFocus();
		this.pack();
		highscore.repaint();
	}

	/**
	 * Methode fuer Steuerungsanzeige
	 * 
	 * @author <Nazari, Mina, 5988551>
	 */
	public void zeigeSteuerung() {
		// entferne alles
		highscoreAngezeigt = false;
		this.remove(spielflaeche);
		this.remove(leiste);
		this.remove(highscore);
		// erstelle die Steuerungsanzeige
		this.add(steuerung, BorderLayout.CENTER);
		// aktiviere die Steuerungsanzeige
		this.requestFocus();
		this.pack();
		steuerung.repaint();
	}

	/**
	 * Getter- Methode fuer Highscore, leiste und Minimap
	 * 
	 * @author <Nazari, Mina, 5988551>
	 */
	public Spielflaeche getSpielflaeche() {return spielflaeche;}
	public Leiste getLeiste() {return leiste;}
	public Highscore getHighscore() {return highscore;}
	public Minimap getMinimap() {return minimap;}
	
	

	// Methoden der Schnittstelle KeyListener

	public void keyPressed(KeyEvent e) {
		// Aktuelle Position des Spielers
		int xPos = spieler.getXPos();
		int yPos = spieler.getYPos();

		// Frage Tastatureingaben auf den Pfeiltasten ab.
		// Es wird geprueft, ob der naechste Schritt zulaessig ist.
		// Bleibt die Figur innerhalb der Grenzen des Arrays?
		// Wenn ja, ist das naechste Feld begehbar?
		// Falls beides "wahr" ist, dann gehe den naechsten Schritt
		if (!spielende) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (yPos > 0 && !(level[xPos][yPos - 1] instanceof Wand))
					spieler.hoch();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (yPos < HEIGHT - 1 && !(level[xPos][yPos + 1] instanceof Wand))
					spieler.runter();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (xPos > 0 && !(level[xPos - 1][yPos] instanceof Wand))
					spieler.links();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (xPos < WIDTH - 1 && !(level[xPos + 1][yPos] instanceof Wand))
					spieler.rechts();
			} else if (e.getKeyCode() == KeyEvent.VK_Q) {
				Monster m = spieler.angriffsMonster();
				if (m != null)
					m.changeHealth(-BOX / 4);
			// B für 'Heiltrank benutzen'
			} else if (e.getKeyCode() == KeyEvent.VK_B){
				int change = spieler.benutzeHeiltrank();
				// Heilungseffekt wird verbessert, falls neue Monster durch das Aufheben des Schluessels ausgeloest wurden
				if (spieler.hatSchluessel())
					spieler.changeHealth((int)(change*1.5));
				else
					spieler.changeHealth((int)(change*0.5));
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// Schluessel aufnehmen
			if (level[spieler.getXPos()][spieler.getYPos()] instanceof Schluessel) {
				spieler.nimmSchluessel();
				level[spieler.getXPos()][spieler.getYPos()] = new Boden();
			}
			// Heiltrank aufnehmen
			else if (level[spieler.getXPos()][spieler.getYPos()] instanceof Heiltrank) {
				spieler.nimmHeiltrank((Heiltrank) level[spieler.getXPos()][spieler.getYPos()]);		
				level[spieler.getXPos()][spieler.getYPos()] = new Boden();
			}
			// Schluessel benutzen
			if (level[spieler.getXPos()][spieler.getYPos()] instanceof Tuer) {
				if (!((Tuer) level[spieler.getXPos()][spieler.getYPos()]).istOffen() && spieler.hatSchluessel()) {
					((Tuer) level[spieler.getXPos()][spieler.getYPos()]).setOffen();
					// Nach dem Oeffnen der Tuer ist der Schluessel wieder weg
					spieler.entferneSchluessel();
					if (currentLevel < MAXLEVEL)
						nextLevel();
					else {
						spielende = true;
					}
				}
			}
		}

	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}


	/**
	 * Spiel wird auf den Anfangszustand zurueckgesetzt
	 * 
	 * 
	 * @author <unbekannt>
	 * @author <Nazari, Mina, 5988551>
	 */
	public void spielZuruecksetzen() {

		spieler = new Spieler("img//spieler.png", this);
		monsterListe = new LinkedList<Monster>();
		level = new Spielelement[WIDTH][HEIGHT];

		currentLevel = 0;
		spielende = false;
		verloren = false;
		nebelAn = true;
		nextLevel();
		spielerInHighscore = false;
		startZeit = System.currentTimeMillis();
	}

	/**
	 * Spielschleife wurde ergaenzt
	 * 
	 * @author <Nazari, Mina, 5988551>
	 * @author <unbekannt>
	 */
	public void starteNeuesSpiel() {
		spielZuruecksetzen();

		do {

			if (!spielende) {
				// Hier wird alle 50ms neu gezeichnet
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {}

				getSpielflaeche().repaint();
				getLeiste().repaint();
				getMinimap().repaint();

				if (spieler.getHealth() <= 0) {
					spielende = true;
					verloren = true;
				}
			} else {
				benoetigteZeit = (int) ((System.currentTimeMillis() - startZeit) / 1000);

				if (!verloren && !spielerInHighscore) {
					getHighscore().repaint();
					spielerInHighscore = true;
				} else {
					getSpielflaeche().repaint();
				}
			}

		} while (true);

	}

	/**
	 * Wechsel in die naechste Klasse
	 * 
	 * @author <Nazari, Mina, 5988551>
	 * 
	 */
	public void nextLevel() {
		currentLevel++;

		Leser leser = new Leser("lvl//level" + currentLevel + ".txt", this);
		level = leser.getLevel();

	}

}
