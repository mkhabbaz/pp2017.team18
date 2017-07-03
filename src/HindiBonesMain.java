import client.engine.ClientEngine;
import client.engine.TestumgebungClientEngine;
import gui.HindiBones;

public class HindiBonesMain {

	public static final int BOX = 32;
	public static final int WIDTH = 16, HEIGHT = 16;
	
	public static void main(String[] args){
		
		new HindiBones(BOX*WIDTH, BOX*HEIGHT, "Hindi Bones");

		ClientEngine cEngine = new ClientEngine();
		TestumgebungClientEngine testClientEngine = new TestumgebungClientEngine();

		cEngine.chatte("<#godmode");
		cEngine.chatte("Hallo");

		testClientEngine.serverAntwort(0, "Die Hindi Bones brennen");
		System.out.println("Hallo Test");
		System.out.flush();

	}
}
