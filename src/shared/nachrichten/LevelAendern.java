package shared.nachrichten;

/**
 * Klasse LevelAendern:
 * @author Pilz, Konstantin, 5957451
 */
public class LevelAendern extends NachrichtMain {

    public static final long serialVersionUID = 1L;


    public int[][] level = new int[5][5];
    public int levelzaehler = 0;

    public LevelAendern(){
        super(4);
    }
}
