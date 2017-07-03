import client.engine.ClientEngine;
import gui.HindiBones;

public class HindiBonesMain {

	public static final int BOX = 32;
	public static final int WIDTH = 16, HEIGHT = 16;
	
	public static void main(String[] args){
		
		new HindiBones(BOX*WIDTH, BOX*HEIGHT, "Hindi Bones");

		ClientEngine cEngine = new ClientEngine();
		cEngine.chatte("<#godmode");
		cEngine.chatte("Hallo");
		System.out.println("Hallo Test");
	}
}
