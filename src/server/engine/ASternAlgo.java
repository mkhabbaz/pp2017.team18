package server.engine;

import java.util.LinkedList;

/**
 * Die Klasse "ASternAlgo" fuehrt den Suchalgorithmus AStern-Algorithmus aus.
 * Dieser berechnet den kuerzesten Weg zwischen einem Start- und einem
 * Zielknoten (= Weg mit den geringsten Kosten). Des Weiteren wird zwischen
 * begehbaren und nicht-begehbaren Knoten unterschieden. Begehbare
 * Spielelemente: Boden, Heiltrank, Schluessel. Nicht-begehbare Spielelemente:
 * Tuer, Wand
 * 
 * @author Khabbaz, Marwa, 5967813
 */
public class ASternAlgo {

	LinkedList<Knoten> offeneKnoten;
	LinkedList<Knoten> geschlosseneKnoten;

	private int startPosX;
	private int startPosY;
	private int zielPosX;
	private int zielPosY;

	private Spielelement[][] karte = new Spielelement[16][16];
	private boolean[][] spielFeld; // Wenn wahr, dann ist das Feld begehbar

	private Knoten zielKnoten;

	/**
	 * Beim Konstruieren eines ASternAlgo werden die Postion des Monsters (=
	 * Startposition) und die Postion des Zielknotens festgelegt. Ausserdem
	 * werden die begehbaren bzw. nicht-begehbaren Knoten definiert.
	 * 
	 * @author Khabbaz, Marwa, 5967813
	 * 
	 * @param monPosX:
	 *            x-Koordinate eines Monsters
	 * @param monPosY:
	 *            y-Koordinate eines Monsters
	 * @param zielPosX:
	 *            x Koordinate des Zielknotens
	 * @param zielPosY:
	 *            y-Koordinate des Zielknotens
	 * @param karte:
	 *            die zu ueberpruefende Karte auf begehbare bzw. nicht-begehbare
	 *            Knoten
	 */
	public ASternAlgo(int startPosX, int startPosY, int zielPosX, int zielPosY, Spielelement[][] karte) {
		this.setKarte(karte);
		this.startPosX = startPosX;
		this.startPosY = startPosY;
		this.zielPosX = zielPosX;
		this.zielPosY = zielPosY;

		this.spielFeld = new boolean[16][16];

		// Pruefe jeden Knoten auf der Karte auf Begehbarkeit. Wenn begehbar,
		// dann setze den Wert in der boolean-Matrix spielFeld auf true, wenn
		// nicht dann entsprechend auf false.
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (karte[i][j] instanceof Boden || karte[i][j] instanceof Heiltrank
						|| karte[i][j] instanceof Schluessel) {
					spielFeld[i][j] = true;
				} else
					spielFeld[i][j] = false;
			}
		}

		// Start- und Zielknoten werden auf true gesetzt.
		this.spielFeld[this.startPosX][this.startPosY] = true;
		this.spielFeld[this.zielPosX][this.zielPosY] = true;

		// Erstelle die Listen der offenen und geschlossenen Knoten. Fuege den
		// Startknoten in die Liste der offenen Knoten (ohne Vorgaenger, deshalb
		// vorgaenger gleich NULL).
		this.offeneKnoten = new LinkedList<Knoten>();
		this.offeneKnoten
				.add(new Knoten(this.startPosX, this.startPosY, 0, this.hKosten(this.startPosX, this.startPosY), null));
		this.geschlosseneKnoten = new LinkedList<Knoten>();

	}

	/**
	 * Diese Methode soll uns den ersten Knoten nach dem Startknoten ausgeben.
	 * Wir betrachten solange die Nachbarn der Knoten, bis wir beim Zielknoten
	 * angekommen sind. Wir beginnen beim Zielknoten und gehen solange zur�ck
	 * (indem wir die Vorgaenger betrachten), bis wir beim Knoten angekommen
	 * sind, der direkt nach dem Startknoten kommt.
	 * 
	 * @author Khabbaz, Marwa, 5967813
	 * 
	 * @return der naechste Schritt im Algo
	 */
	public Knoten suchAlgo() {
		boolean sucheAktiv = true;
		Knoten naechsterKnoten = null;

		// Solange wir den Startknoten nicht erreicht haben, suche weiter.
		while (sucheAktiv) {
			naechsterKnoten = null;
			if (this.zielKnoten != null) {
				naechsterKnoten = this.zielKnoten;
				// Gehe solange zurueck, bis wir beim ersten Knoten nach dem
				// Startknoten angekommen sind.
				if (naechsterKnoten.vorgaenger != null) {
					while (naechsterKnoten.vorgaenger.vorgaenger != null) {
						naechsterKnoten = naechsterKnoten.vorgaenger;
					}
				}
				sucheAktiv = false;
			} else if (this.offeneKnoten.isEmpty() == false && sucheAktiv == true) {
				naechsterKnoten = this.betrachteNachbarn();
			}
		}

		return naechsterKnoten;
	}

	/**
	 * Diese Methode betrachtet alle Nachbarn des naechsten Knoten mit den
	 * geringsten Kosten (also: noerdlich, oestlich, suedlich, westlich vom
	 * Knoten) und fuegt diese der Liste der offenen Knoten hinzu.
	 * 
	 * @author Khabbaz, Marwa, 5967813
	 * 
	 * @return naechstkuerzester Knoten
	 */
	public Knoten betrachteNachbarn() {

		Knoten kuerzesterKnoten;

		// Suche nach dem naechstkuerzesten Knoten in der Liste der offenen
		// Knoten, entferne ihn aus dieser Liste und fuege ihn in die Liste der
		// geschlossenen Knoten ein.
		kuerzesterKnoten = offeneKnoten.get(this.getKuerzestenKnoten(offeneKnoten));
		geschlosseneKnoten.add(offeneKnoten.remove(this.getKuerzestenKnoten(offeneKnoten)));

		// Wenn der naechstkuerzeste Knoten der Zielknoten ist, dann muessen die
		// Nachbarn nicht mehr betrachtet werden und das Ziel ist erreicht.
		if (kuerzesterKnoten.posX == this.zielPosX && kuerzesterKnoten.posY == this.zielPosY) {
			this.zielKnoten = kuerzesterKnoten;
			return kuerzesterKnoten;
		}

		// Fuege alle Nachbarn in die Liste der offenen Knoten.
		this.fuegeInOffeneKnoten(kuerzesterKnoten.posX, kuerzesterKnoten.posY - 1, offeneKnoten, geschlosseneKnoten,
				kuerzesterKnoten);
		this.fuegeInOffeneKnoten(kuerzesterKnoten.posX + 1, kuerzesterKnoten.posY, offeneKnoten, geschlosseneKnoten,
				kuerzesterKnoten);
		this.fuegeInOffeneKnoten(kuerzesterKnoten.posX, kuerzesterKnoten.posY + 1, offeneKnoten, geschlosseneKnoten,
				kuerzesterKnoten);
		this.fuegeInOffeneKnoten(kuerzesterKnoten.posX - 1, kuerzesterKnoten.posY, offeneKnoten, geschlosseneKnoten,
				kuerzesterKnoten);

		return kuerzesterKnoten;
	}

	/**
	 * Diese Methode fuegt einen Knoten in die Liste der offenen Knoten (=
	 * offeneKnoten).
	 * 
	 * @author Khabbaz, Marwa, 5967813
	 * 
	 * @param posX:
	 *            x-Koordinate des Knotens
	 * @param posY:
	 *            y-Koordinate des Knotens
	 * @param offeneKnoten:
	 *            Liste der offenen Knoten
	 * @param geschlosseneKnoten:
	 *            Liste der geschlossenen Knoten
	 * @param vorgaenger:
	 *            vorgaenger des Knotens
	 */
	private void fuegeInOffeneKnoten(int posX, int posY, LinkedList<Knoten> offeneKnoten,
			LinkedList<Knoten> geschlosseneKnoten, Knoten vorgaenger) {
		boolean existenzGeschlossen = this.pruefeKnoten(posX, posY, geschlosseneKnoten);
		boolean existenzOffen = this.pruefeKnoten(posX, posY, offeneKnoten);

		// Negative Koordinaten und Koordinaten groesser 16 existieren nicht, da
		// das Spielfeld 16 x 16 gross ist
		if (posX < 0 || posY < 0 || posX >= 16 || posY >= 16) {
			System.out.print("Fehler! Dieser Knoten existiert nicht!");
		}
		// Damit ein Knoten in die Liste der offenen Knoten eingefuegt werden
		// kann, darf er nicht in der Liste der geschlossenen Knoten sein, muss
		// begehbar sein und noch nicht bereits eingefuegt sein
		else if (existenzGeschlossen == false && this.spielFeld[posX][posY] == true && existenzOffen == false) {
			offeneKnoten.add(new Knoten(posX, posY, 1, this.hKosten(posX, posY), vorgaenger));
		}
	}

	/**
	 * Diese Methode prueft, ob ein Knoten in einer bestimmten Knotenliste
	 * existiert.
	 * 
	 * @author Khabbaz, Marwa, 5967813
	 * 
	 * @param posX:
	 *            x-Koordinate des Knotens
	 * @param posY:
	 *            y-Koordinate des Knotens
	 * @param knotenListe:
	 *            Eingabeliste, in der die Existenz eines Knotens geprueft wird
	 * 
	 * @return Wahrheitswert ueber die Existenz eines Knotens
	 */
	private boolean pruefeKnoten(int x, int y, LinkedList<Knoten> knotenListe) {
		// Durchsuche die gesamte Liste und pruefe ob die eingegebenen
		// Koordinaten, auf ein Knoten in der Liste zutreffen
		for (int i = 0; i < knotenListe.size(); i++) {
			if (knotenListe.get(i).posX == x && knotenListe.get(i).posY == y) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Bei jedem "Schritt" veraendert sich der Wert der x- bzw. der y-Koordinate
	 * um 1. Durch Berechnung der Differenz zwischen der x- bzw. y-Koordinaten
	 * des aktuellen Knoten und des Zielknotens, erhaelt man die wertmaessige
	 * Veraenderung. Die Summe dieser Veraenderungen ist somit die Anzahl der
	 * Schritte vom aktuellen Knoten zum Zielknoten (= hKosten).
	 * 
	 * @author Khabbaz, Marwa, 5967813
	 * 
	 * @param posX:
	 *            x-Koordinate des Knotens
	 * @param posY:
	 *            y-Koordinate des Knotens
	 * 
	 * @return Anzahl der Schritte vom betrachteten Knoten bis zum Zielknoten
	 */
	private int hKosten(int posX, int posY) {
		int diffPosX = posX - this.zielPosX;
		int diffPosY = posY - this.zielPosY;
		int anzahlSchritte = Math.abs(diffPosX) + Math.abs(diffPosY);

		return anzahlSchritte;
	}

	/**
	 * Diese Methode durchsucht eine Liste, die Objekte des Typs Knoten enhaelt,
	 * nach dem Knoten mit den geringsten Kosten (= kuerzeste Distanz).
	 * 
	 * @author Khabbaz, Marwa, 5967813
	 * 
	 * @param knotenListe:
	 *            Eingabeliste (i.d.R. offeneKnoten), in der nach dem kuerzesten
	 *            Knoten gesucht wird
	 * 
	 * @return Position des kuerzesten Knotens in der Eingabeliste
	 */
	private int getKuerzestenKnoten(LinkedList<Knoten> knotenListe) {
		// Kosten des ersten Knotens in der Knotenliste
		int kuerzesterKnoten = knotenListe.get(0).getGesamtkosten();

		// Suche nach dem Knoten mit den geringsten Kosten
		for (int i = 1; i < knotenListe.size(); i++) {
			if (knotenListe.get(i).getGesamtkosten() < kuerzesterKnoten) {
				kuerzesterKnoten = knotenListe.get(i).getGesamtkosten();
			}
		}

		// Suche nach der Postion in der Knotenliste des Knotens mit den
		// geringsten Kosten
		for (int i = 0; i < knotenListe.size(); i++) {
			if (knotenListe.get(i).getGesamtkosten() == kuerzesterKnoten) {
				return i;
			}
		}
		System.out.println("In der Methode getKuerzestenKnoten() ist ein Fehler aufgetreten.");
		return -1;
	}

	public Spielelement[][] getKarte() {
		return karte;
	}

	public void setKarte(Spielelement[][] karte) {
		this.karte = karte;
	}

}
