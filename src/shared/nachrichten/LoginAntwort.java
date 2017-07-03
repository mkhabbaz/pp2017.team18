package shared.nachrichten;

/**
 * Created by konstantinpilz on 03.07.17.
 */
public class LoginAntwort extends NachrichtMain {

    public static final long serialVersionUID = 1L;

    public int[][] karte;
    public int levelzaehler;
    public String name;
    public String passwort;
    public boolean eingeloggt;



    public LoginAntwort(boolean loginAntwort) {
        super(4);
        this.eingeloggt = loginAntwort;
    }
}
