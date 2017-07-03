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
                nachrichtenBotschaft = "Fehlernachricht, übergibt Fehlermeldung als Text" + nachrichtenInhalt;

                break;

            case 1:
                //Cheat
                nachrichtenBotschaft = "Cheat: " + nachrichtenInhalt;

                break;

            case 2:
                //Antwort: Gibt Boolean-Werte auf Anfragen zurueck
                nachrichtenBotschaft = "Antwort: Gibt Boolean-Werte auf Anfragen zurueck" + nachrichtenInhalt;

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




}
