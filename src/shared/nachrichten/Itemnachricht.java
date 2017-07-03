package shared.nachrichten;

/**
 * Klasse Itemnachricht:
 * @author Pilz, Konstantin, 5957451
 */
public class Itemnachricht extends NachrichtMain {
    public static final long serialVersionUID = 1L;

    /**
     * Objekt Fehlernachricht: Hier wird das Objekt der Itemnachricht erstellt, welche bei Aufnahme und Verwendung des Items genutzt wird.
     * Durch Typ wird die Faehigkeit des Items bestimmt
     * @author Pilz, Konstantin, 5957451
     */

    public Itemnachricht(int a, int b, int typ){
        super(typ);     //in Spielweltverwaltung wird durch Auflistung der Typen das Item gewaehlt
        XPos = a;
        YPos = b;
    }

    public int getXPos(){
        return XPos;
    }

    public int getYPos(){
        return YPos;
    }


}
