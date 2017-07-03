package shared.nachrichten;

/**
 * Klasse Cheat: Hier wird Cheat-Objekt beschrieben
 * @author Pilz, Konstantin, 5957451
 */
public class Cheat extends NachrichtMain {

    public static final long serialVersionUID = 1L;

    /**
     * Objekt Cheat: Erstellung eines Objekts Cheat
     * @param typ: Dieser Typ entscheidet ueber Faehigkeit des Cheats
     * @author Pilz, Konstantin, 5957451
     */

    public Cheat(int typ){
        super(3);
        this.cheattyp = typ;
        this.gueltig = true;
    }
}
