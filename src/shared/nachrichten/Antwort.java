package shared.nachrichten;

import shared.nachrichten.NachrichtMain;

/**
 * Klasse Antwort:
 * @author Pilz, Konstantin, 5957451
 */
public class Antwort extends NachrichtMain {
    public static final long serialVersionUID = 1L;

    /**
     * Objekt Antwort: Gibt Antwort in Form von Booleanwerten auf Anfragen zurueck
     * @param antwort
     * @author Pilz, Konstantin, 5957451
     */
    public Antwort(boolean antwort){
        super(3);
        this.gueltig = antwort;
    }


}
