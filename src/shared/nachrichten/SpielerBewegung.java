package shared.nachrichten;

/**
 * @author Pilz, Konstantin, 5957451
 */
public class SpielerBewegung extends NachrichtMain {

    public static final long serialVersionUID = 1L;


    public int neuXPos;
    public int neuYPos;


    public SpielerBewegung(int nachrichtentyp) {
        super(6);
    }
}
