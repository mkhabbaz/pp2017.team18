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
     * 1: Fehlernachricht, uebergibt Fehlermeldung als Text
     * 2: Cheat:
     * 3: Antwort: Gibt Boolean-Werte auf Anfragen zurueck
     * 4: LevelAendern:
     * 5: Login Nachricht:
     * 6: SpielerBewegung:
     * 7:
     * 8:
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
