package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuLeiste extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;

    private JMenu spiel;
    private JMenu anzeige;
	private JMenu hilfe;
    	
    private JMenuItem neuesSpiel;
    private JMenuItem highscore;
    private JMenuItem beenden;
    private JMenuItem karteaufdecken;
    private JMenuItem steuerung;

    private HindiBones fenster;
    
	public MenuLeiste(HindiBones fenster){
		this.fenster = fenster;
		
		spiel = new JMenu("Spiel");
		anzeige = new JMenu("Anzeige");
		hilfe = new JMenu("Hilfe");
        
        neuesSpiel = new JMenuItem("Neues Spiel starten");
        highscore = new JMenuItem("Highscore anzeigen");
        beenden = new JMenuItem("Beenden");
        karteaufdecken = new JMenuItem("Karte aufdecken");
        steuerung = new JMenuItem("Steuerung");
        
        neuesSpiel.addActionListener(this);
        highscore.addActionListener(this);
        beenden.addActionListener(this);
        karteaufdecken.addActionListener(this);
        steuerung.addActionListener(this);
        hilfe.addActionListener(this);
        
        
        spiel.add(neuesSpiel);
        spiel.add(beenden);
        anzeige.add(highscore);
        anzeige.add(karteaufdecken);
        hilfe.add(steuerung);
        
        this.add(spiel);
        this.add(anzeige);
        this.add(hilfe);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == neuesSpiel){
			fenster.spielZuruecksetzen();
			fenster.zeigeSpielfeld();
		}else if(e.getSource() == highscore){
			if(fenster.highscoreAngezeigt){
				fenster.zeigeSpielfeld();
				highscore.setText("Highscore anzeigen");
			}else{
				fenster.zeigeHighscore();
				highscore.setText("Spielfeld anzeigen");
			}
			
		}else if(e.getSource() == karteaufdecken){
			if(fenster.nebelAn){
				fenster.nebelAn = false;
				karteaufdecken.setText("Karte verdecken");
			}else{
				fenster.nebelAn = true;
				karteaufdecken.setText("Karte aufdecken");
			}		
		}else if(e.getSource() == beenden){
			System.exit(0);
		}else if(e.getSource() == steuerung){
			fenster.zeigeSteuerung();
		}
	}
	
	public JMenuItem getHighscore(){
		return highscore;
	}
	
}
