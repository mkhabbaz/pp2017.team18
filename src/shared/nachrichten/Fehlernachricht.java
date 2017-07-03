package shared.nachrichten;

/**
 * Klasse Fehlernachricht: Hier wird die Fehlernachricht beschrieben, welche die Fehlermeldung als String enthaelt
 * @author Pilz, Konstantin, 5957451
 */
public class Fehlernachricht extends NachrichtMain {

    public static final long serialVersionUID = 1L;

    /**
     * Objekt fehlernachricht: Erzeugt das Objekt Fehlernachricht, welche bei Fehlermeldungen als Text ausgegeben wird
     * @param fehlermeldung: Text der Fehlermeldung
     * @author Pilz, Konstantin, 5957451
     */
    public Fehlernachricht(String fehlermeldung){
        super(1);                       //Fehlernachricht = Nachrichtentyp 1 aus NachrichtMain
        fehler = fehlermeldung;
    }
}
