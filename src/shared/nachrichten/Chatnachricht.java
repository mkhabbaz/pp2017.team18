package shared.nachrichten;

/**
 * Klasse Chatnachricht:
 * @author Pilz, Konstantin, 5957451
 */
public class Chatnachricht extends NachrichtMain {

    private static final long serialVersionUID = 1L;


    /**
     * Objekt Chatnachricht:
     * @param nachricht
     */
    public Chatnachricht(String nachricht){
        super(5);
        nachricht = nachricht;
    }

    /**
     * Methode istCheat:
     * @return
     */
    public boolean istCheat(){
        boolean istCheat = false;

        switch (nachricht){

            case("++volles Leben"):
                istCheat = true;
                break;
// hier koennen noch mehr Cheats stehen
        }
        return istCheat;
    }

    public int getCheattyp(){
        int cheattyp = 0;
        if(istCheat()){
            switch (nachricht){
                case("++volles Leben"):
                    cheattyp = 1;
                    break;
                //hier koennen analog zu istCheats noch weitere hinzugefuegt werden
            }
        }
        return cheattyp;
    }
}
