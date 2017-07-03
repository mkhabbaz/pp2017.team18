package shared.nachrichten;

import java.io.Serializable;

/**
 * Klasse NachrichtMain: Dies ist die Hauptklasse der Nachrichten, wo die grundlegenden Merkmale und Eigenschaften der
 * Nachrichten definiert sind und dann auf andere Klassen vererbt werden können
 */
public class NachrichtMain implements Serializable {

    private static final long serialVersionUID = 1L;

    int nachrichtentyp, XPos, YPos;
    public int cheattyp, spielerID;
    public boolean aufgenommen, gueltig, eingeloggt;
    public String fehler, benutzername, passwort, nachricht;
    public int [][][] leveldaten;



    /**
     * Objekt Nachricht: Erstellt eine Nachricht, welche in der Client-Server-Kommunikation in Form von verschiedenen
     * Typen versendet wird
     * @param nachrichtentyp: Je nach Nachrichtentyp wird die Nachricht anders behandelt. Typ wird je nach Ereignis
     *                      der Nachricht zugewiesen.
     * Nachrichtentypen:
     * 0: Fehlernachricht, uebergibt Fehlermeldung als Text
     * 1: Cheat:
     * 2: Antwort: Gibt Boolean-Werte auf Anfragen zurueck
     * 3: LevelAendern:
     * 4: Login Nachricht:
     * 5: SpielerBewegung:
     * 6:
     * 7:
     * @author Pilz, Konstantin, 5957451
     *
     */
    public NachrichtMain(int nachrichtentyp){
        this.nachrichtentyp = nachrichtentyp;
    }

    public int getNachrichtentyp(){
        return this.nachrichtentyp;
    }


    public int getXPos(){
        return this.XPos;
    }


    public int getYPos(){
        return this.YPos;
    }


    public int getID(){
        return this.spielerID;
    }
}
