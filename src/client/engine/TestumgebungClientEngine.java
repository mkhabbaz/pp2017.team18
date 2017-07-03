package client.engine;

import gui.HindiBones;
import gui.Spielflaeche;

/**
 * Created by konstantinpilz on 03.07.17.
 */
public class TestumgebungClientEngine {

    HindiBones fenster;

    public void serverAntwort(String antwort) {
        System.out.println(antwort);
    }

    public void serverAntwort(int antwort) {
        System.out.println("" + antwort);

        switch (antwort) {
            case 0:
                //godmode cheat aktiviert
                System.out.println("godmode Cheat wurde erfolgreich eingesetzt");
                break;

            case 1:
                //nebelWeg cheat aktiviert
                System.out.println("nebelWeg Cheat wurde erfolgreich eingesetzt");
                fenster.nebelAn = false;
                break;


        }

    }

    public void serverAntwort(int nachrichtenTyp, String nachrichtenInhalt) {
        System.out.println("Neues Level erstellt");

        String nachrichtenBotschaft = "Warnung: Undefinierter String";
        switch (nachrichtenTyp) {
            case 0:
                //Fehlernachricht, uebergibt Fehlermeldung als Text
                nachrichtenBotschaft = "Fehlernachricht, übergibt Fehlermeldung als Text";

                break;

            case 1:
                //Cheat
                nachrichtenBotschaft = "Cheat: " + nachrichtenInhalt;

                break;

            case 2:
                //Antwort: Gibt Boolean-Werte auf Anfragen zurueck
                nachrichtenBotschaft = "Antwort: Gibt Boolean-Werte auf Anfragen zurueck";

                break;

            case 3:
                //LevelAendern
                nachrichtenBotschaft = "LevelAendern: " + nachrichtenInhalt;

                //nachrichtenInhalt und Typ muessen zum Server weitergegeben werden

                break;

            case 4:
                //Login Nachricht
                nachrichtenBotschaft = "Login Nachricht: " + nachrichtenInhalt;

                break;

            case 5:
                //SpielerBewegung
                nachrichtenBotschaft = "SpielerBewegung: " + nachrichtenInhalt;

                break;;

            case 6:
                //
                nachrichtenBotschaft = "";

                break;
        }
        System.out.println(nachrichtenBotschaft);
    }

    public void serverAntwort(int positionX, int positionY, int itemID) {
        //Server handling/engine hier implementieren!!

        System.out.println("PositionX: " + positionX + " PositionY: " + positionY + " itemID: " + itemID);
    }




    public void neuesLevelAnfordern(int levelNummer) {

        //Eigentlich auf dem Server ausführen
        HindiBones hindiObjekt = new HindiBones(110, 110, "Hindi");
        Spielflaeche spfla = new Spielflaeche(hindiObjekt);


    }



    public Level levelWechseln() {
        // Wenn das Level nicht das letzte war, wird das Level um eins erhoeht.
        if (fenster.level.getLevelID() < fenster.MAXLEVEL - 1) {
            aktuellesLevel = alleLevel[aktuellesLevel.levelID + 1];
            // Dem Spieler wird der Schluessel abgenommen
            fenster.spieler.entferneSchluessel();
            fenster.levelnummer = aktuellesLevel.levelID;
            systemnachricht("Level wurde gewechselt!");
            return aktuellesLevel;
        } else {
            // Wenn es das letzte Level war, wird ein Level mit ID = 6
            // zurueckgegeben, damit
            return new Level(6, aktuellesLevel.levelInhalt);
        }
    }


    public void spielerBewegung(int richtung) {
        spieler = fenster.spieler;
        switch (richtung) {
            case 0:
			/*
			 * Testet, ob eine Bewegung in die angegebene Richtung moeglich ist.
			 * Fuehrt die Bewegung aus und sendet eine entsprechende Nachricht
			 * an den Server
			 */
                if (spieler.getYPos() < aktuellesLevel.getLaengeY() - 1
                        && fenster.level.getBestimmtenLevelInhalt(spieler.getXPos(), spieler.getYPos() + 1) != 0) {
                    spieler.runter();
                    sende(new BewegungsNachricht(spieler.getID(), spieler.getXPos(), spieler.getYPos()));
                }
                break;

            case 1:
			/*
			 * Analog zu case 0
			 */
                if (spieler.getYPos() > 0
                        && fenster.level.getBestimmtenLevelInhalt(spieler.getXPos(), spieler.getYPos() - 1) != 0) {
                    spieler.hoch();
                    sende(new BewegungsNachricht(spieler.getID(), spieler.getXPos(), spieler.getYPos()));
                }
                break;

            case 2:
			/*
			 * Analog zu case 0
			 */
                if (spieler.getXPos() > 0
                        && fenster.level.getBestimmtenLevelInhalt(spieler.getXPos() - 1, spieler.getYPos()) != 0) {
                    spieler.links();
                    sende(new BewegungsNachricht(spieler.getID(), spieler.getXPos(), spieler.getYPos()));
                }
                break;

            case 3:
			/*
			 * Analog zu case 0
			 */
                if (spieler.getXPos() < aktuellesLevel.getLaengeX() - 1
                        && fenster.level.getBestimmtenLevelInhalt(spieler.getXPos() + 1, spieler.getYPos()) != 0) {
                    spieler.rechts();
                    sende(new BewegungsNachricht(spieler.getID(), spieler.getXPos(), spieler.getYPos()));
                }
                break;

        }
    }

}
