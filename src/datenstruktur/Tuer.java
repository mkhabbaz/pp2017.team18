package datenstruktur;

public class Tuer extends Spielelement {
	private boolean offen;
	
	public Tuer(boolean offen){
		this.offen = offen;
	}
	
	public void setOffen(){
		offen = true;
	}
	
	public void setVerschlossen(){
		offen = false;
	}
	
	public boolean istOffen(){
		return offen;
	}
}
