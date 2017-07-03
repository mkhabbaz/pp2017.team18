package client.engine;

//diverse imports vom Package shared
import datenstruktur.Spieler;
import gui.HindiBones;
import gui.Spielflaeche;
import shared.nachrichten.*;
import datenstruktur.*;
import gui.*;

/**
 * Klasse ClientEngine: Dies ist die Zentrale Klasse der clientseitigen Kommunikation
 * 
 * @author Pilz, Konstantin, 5957451
 *
 */
public class ClientEngine extends Thread {

	public Spielflaeche spielflaeche = new Spielflaeche();
	public Spieler spieler; //import aus shared.spieler, welcher alle Merkmale des Charakters beinhaltet
		String benutzername, passwort;
	HindiBones fenster;  //import von der gui; hier laeuft das Fenster des Spiels plus die Hauptschnittstelle des Clients drüber
	public boolean eingeloggt = false;
	public boolean login = false;
	public boolean neuesLevel = false;


	TestumgebungClientEngine testInstanz = new TestumgebungClientEngine();

	/**
	 * Methode nachrichtentypZuordnen: Verarbeitet die vom Server an Client versendete Nachricht
	 * je nach enthaltenem Nachrichtentyp
	 *
	 * @author Pilz, Konstantin, 5957451
	 * @param empfangenesPaket: Von Server empfangenes Paket, welches jetzt je nach Typ verarbeitet wird
	 */

	public void nachrichtentypZuordnen(Paket empfangenesPaket) {
		NachrichtMain empfangeneNachricht = empfangenesPaket.getNachricht();
		switch (empfangeneNachricht.getNachrichtentyp()) {             //Switch-Case Anweisung wird zur Unterscheidung eingehender Nachrichten verwendet
			case 0://LOGIN
				if (eingeloggt = true) {
					LoginAntwort daten = new LoginAntwort(true) ; //eingehendeNachricht
					spielflaeche.level = daten.karte;
					spielflaeche.levelzaehler = daten.levelzaehler;
					spieler.setName(daten.name);
					spieler.setPasswort(daten.passwort);
					this.eingeloggt = daten.eingeloggt;
					testInstanz.gibAntwortWeiter("Login wurde erfolgreich empfangen");

					testInstanz.serverAntwort(0);
					this.login = true;
				}
				break;
			case 1://anmelden
				/** Methode anmelden: Versucht den Spieler mit zugehörigem Benutzernamen und Passwort anzumelden
				 *
				 * @author Pilz, Konstantin, 5957451
				 * @param login: Loginnachricht, die an den Server gesendet wird
				 * @return Antwort vom Server, ob Benutzer angemeldet/registriert wurde
				 */
				Paket serverAntwort = sende(login);
				nachrichtentypZuordnen(serverAntwort);
				return serverAntwort.getNachricht().gueltig; //gueltig muss noch unter shared erstellt werden, dass Daten gueltig ist
				systemnachricht("Position des Spielers: " + empfangeneNachricht.getXPos() + ", " + empfangeneNachricht.getYPos());
				break;
			case 2:
				//
				testInstanz.gibAntwortWeiter("Trank an " + empfangeneNachricht.getXPos() + ", " + empfangeneNachricht.getYPos()
						+ " wurde aufgenommen");
				break;
			case 3:
				//
				testInstanz.gibAntwortWeiter("Schluessel an" + empfangeneNachricht.getXPos() + ", " + empfangeneNachricht.getYPos()
						+ " wurde aufgenommen");
				break;
			case 4:
				//
				systemnachricht("Das Level wurde abgeschlossen!");

				break;
			case 5:
				//
				systemnachricht(empfangeneNachricht.fehler);
				break;
			case 6:
				verarbeiteCheat(empfangeneNachricht);
				break;
			case 7: {
				for (int i = 0; i < empfangeneNachricht.leveldaten.length; i++) {
					alleLevel[i] = new Level(i, empfangeneNachricht.leveldaten[i]);
				}
			}
		}
	}

/**
 * Methode verarbeiteCheat: Verarbeitet von Server empfangene Cheat-Nachricht (führt aus)
 *
 * @author Pilz, Konstantin, 5957451
 * @param cheat: Zu verarbeitende Nachricht, welche dem Spiele Vorteile bringen sollen
 *
 */
	public void verarbeiteCheat(String cheat){

		if (cheat == "<#godmode") {
			testInstanz.serverAntwort(0);

		}
		if (cheat == "<#nebelWeg") {
			testInstanz.serverAntwort(1);
		}
		else {
			System.out.println("Error! Ungültiger Cheat!");
		}





		switch (cheat.cheattyp){

			case 1:
				fenster.nebelAn = false;
				systemnachricht("Nebel weg!");
				break;
			case 2:
				//weitere Cheats mit Spielweltverwaltung absprechen

		}
	}



    /**
     * Methode levelAnfordern: Empfaengt die Serverdaten des Levels
     * @author Pilz, Konstantin, 5957451
     *
     */
    public void levelAnfordern(){
        Paket serverAntwort = sende(new LevelAendern());  //diese Nachricht muss noch in shared erstellt werden
        nachrichtentypZuordnen(serverAntwort);  //mit dieser Methode aus ClienEngine wird der Antwort ein Nachrichtentyp
												// zugeordnet
        currentLevel = alleLevel[0];  //hier wird das aktuelle Level als Startlevel gesetzt

		testInstanz.serverAntwort();
    }

	/**
	 * Methode sende: Diese Methode schickt ein Paket mit einer Nachricht an den Server
	 * @param nachricht
	 * @return
	 */
	public Paket sende(NachrichtMain nachricht) {
		return socket.anServerSenden(new Paket(nachricht));
	}

    /**
     * Methode nimmHeiltrank: In dieser Methode kann der Spieler an einer bestimmten Position einen Trank aufnehmen.
	 * Nachricht an Server bestaetigt, ob an dieser Stelle an Item liegt und welche Art Item und aendert nach Aufnahme
	 * den Status dieses Feldes.
	 * @author Pilz, Konstantin, 5957451
     */
	public void nimmHeiltrank(){
		spieler = fenster.spieler;
		spieler.nimmHeiltrank();  //aufnehmenHeiltrank ist in Shared.Spieler eine Methode und muss noch erstellt werden
		anServerSenden(new Itemnachricht(spieler.getXPos(), spieler.getYPos(),2));  //ItemNachricht in Shared mit (XPos, YPos, 2 = Art des Items)
	}

	/**
     * Methode aufnehmenSchluessel: Hierdurch kann der Spieler an einer festgelegten Position einen Schluessel aufnehmen
	 * @author Pilz, Konstantin, 5957451
     */
    public void aufnehmenSchluessel(){
    	spieler = fenster.spieler;
    	spieler.nimmSchluessel();
    	anServerSenden(new Itemnachricht(spieler.getXPos(), spieler.getYPos(),1)); //1 = Art des Items
	}

    /**
     * Methode benutzeHeiltrank:
	 * @author Pilz, Konstantin, 5957451
	 * Methode aus gui-Komponente uebernommen
     */
	public void benutzeHeiltrank(){
		int change = spieler.benutzeHeiltrank();
		// Heilungseffekt wird verbessert, falls neue Monster durch das Aufheben des Schluessels ausgeloest wurden
		if (spieler.hatSchluessel())
			spieler.changeHealth((int)(change*1.5));
		else
			spieler.changeHealth((int)(change*0.5));
	}


    /**
     * Methode: verwendeSchluessel: Wenn der Schluessel aus diesem Level aufgenommen wurde, kann er verwendet werden
	 * und geht nach Gebrauch verloren. Entsprechend wird Nachricht an Server geschickt.
	 * @author Pilz, Konstantin, 5957451
     */
    public void verwendeSchluessel(){
    	spieler = fenster.spieler;
    	if(spieler.hatSchluessel()) {  //schluesselAufgenommen muss noch in shared.Spieler erstellt werden
			aktuellesLevel.setLevelInhalt(spieler.getXPos(), spieler.getYPos(), 1);  //1 = neuer Inhalt des des zu veraendernden Ortes
			spieler.entferneSchluessel();  //Schluessel geht nach Gebrauch verloren
		}
	}

	/**
	 * Methode chatte
	 * @param nachricht
	 * @return
	 */
	public boolean chatte(Chatnachricht nachricht) {
		Paket serverAntwort = new Paket(new Fehlernachricht("Konnte keine Nachricht senden!"));
		// Wenn die Chatnachricht ein Cheat ist, wird dieser ausgefuehrt
		if (nachricht.istCheat()) {
			serverAntwort = sende(new Cheat(nachricht.getCheattyp()));
		} else {						// Wenn kein Cheat, dann wird die Nachricht als Chatnachricht weiterverarbeitet
			serverAntwort = sende(nachricht);
		}
		// Verarbeitet die Serverantwort
		nachrichtentypZuordnen(serverAntwort);

		return serverAntwort.getNachricht().gueltig;
	}

	/**
	 * Methode run: Fragt kontinuierlich während der Client geoeffnet ist, ob neue Nachrichten von Server kommen
	 */
	/*public void run() {
		while (client.aktiv) {									//aktiv: ClientServer Verbindung aktiv?
			NachrichtMain m = client.anClientWeitergeben();
			if (m == null) {
				System.out.println("Test");
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					this.nachrichtenVerarbeitung(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		this.interrupt();
	}*/

	/**
	 * Methode nachrichtenVerarbeitung:
	 * @param eingehendeNachricht
	 * @throws Exception
	 */
	void nachrichtenVerarbeitung(NachrichtMain eingehendeNachricht)
			throws Exception {
		// Login
		if (eingehendeNachricht instanceof LoginAntwort) {
			LoginAntwort daten = (LoginAntwort) eingehendeNachricht;
			spielflaeche.level = daten.karte;
			spielflaeche.levelzaehler = daten.levelzaehler;
			spieler.setName(daten.name);
			spieler.setPasswort(daten.passwort);
			this.eingeloggt = daten.eingeloggt;

			System.out.println("Login empfangen");

			this.login = true;

			// Wechseln des Levels/Neues Spielfeld
		} else if (eingehendeNachricht instanceof LevelAendern) {
			LevelAendern daten = (LevelAendern) eingehendeNachricht;
			spielflaeche.breite = konstante.WIDTH;
			spielflaeche.hoehe = konstante.HEIGHT;
			while (!monsterListe.isEmpty()) {
				monsterListe.remove();
			}
			// Wechseln des neuen Levels/ Spielfelds
		} else if (eingehendeNachricht instanceof LevelAendern) {
			LevelAendern daten = (LevelAendern) eingehendeNachricht;
			spielfeld.breite = konstante.WIDTH;
			spielfeld.hoehe = konstante.HEIGHT;
			while (!monsterListe.isEmpty()) {
				monsterListe.remove();
			}

			spielflaeche.levelzaehler = daten.levelzaehler;
			this.neuesLevel = true;
			System.out.println("Neues Level gespeichert");

			// Spieler-Bewegung
		} else if (eingehendeNachricht instanceof SpielerBewegung) {
			System.out.println("Neue Position");
			SpielerBewegung daten = (SpielerBewegung) eingehendeNachricht;
			this.spieler.setPos(daten.neuXPos, daten.neuYPos);
		}
	}

}
