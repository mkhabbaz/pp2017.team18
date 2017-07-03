package client.engine;

/**
 * Created by konstantinpilz on 03.07.17.
 */
public class TestumgebungClientEngine {

    public void gibAntwortWeiter(String antwort) {
        System.out.println(antwort);
    }

    public void serverAntwort(int nachrichtenTyp) {
        System.out.println("Neues Level erstellt");

        String nachrichtenBotschaft = "Warnung: Undefinierter String";
        switch (nachrichtenTyp) {
            case 0:
                //Fehlernachricht, uebergibt Fehlermeldung als Text
                nachrichtenBotschaft = "Fehlernachricht, uebergibt Fehlermeldung als Text";

            case 1:
                //Cheat
                nachrichtenBotschaft = "Cheat";

            case 2:
                //Antwort: Gibt Boolean-Werte auf Anfragen zurueck
                nachrichtenBotschaft = "Antwort: Gibt Boolean-Werte auf Anfragen zurueck";

            case 3:
                //LevelAendern
                nachrichtenBotschaft = "LevelAendern";

            case 4:
                //Login Nachricht
                nachrichtenBotschaft = "Login Nachricht";

            case 5:
                //SpielerBewegung
                nachrichtenBotschaft = "SpielerBewegung";

            case 6:
                //
                nachrichtenBotschaft = "";
        }
        System.out.println(nachrichtenBotschaft);
    }

}