import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
public class GameState extends State {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int windowHeight = (int) screenSize.getHeight();
	Main main;
	static Player character;
	Bullets boolet;
	int xPos = 10;
	int yPos = windowHeight - 100;
	static int gunxpos = 10;
	static int gunypos = windowHeight - 100;
	BufferedImage weapon = ImageLoader.loadImage("res/textures/weapon.png");
	BufferedImage weaponleft = ImageLoader.loadImage("res/textures/weaponleft.png");
	BufferedImage gunpic;
	boolean isJumping = false;
    int mouseX;
    BufferedImage leftcapn = ImageLoader.loadImage("res/textures/capnleft.png");
    BufferedImage capn = ImageLoader.loadImage("res/textures/capn.png");
    static boolean turnedLeft = false;
	
	public GameState(Main main) {
		super(main);
		character = new Player(main, xPos, yPos, 100, 100, ImageLoader.loadImage("res/textures/capn.png"), 4, 4, -15, 4, 4);
	}
	
	public void update() {
		if (KeyTracker.wpressed == true) {
			if (isJumping == false) {
				isJumping = true;
			}
		}
		if (KeyTracker.apressed == true) {
			xPos -= character.speed;
			gunxpos = xPos;
		}
		if (KeyTracker.spressed == true) {
		}
		if (KeyTracker.dpressed == true) {
			xPos += character.speed;
			gunxpos = xPos;
		}
		if (isJumping == true) {
			if (character.jump <= -15) {
				character.jump = 15;
				isJumping = false;
			}
			if (isJumping == true) {
				yPos -= character.jump;
			}
			if (character.jump >-15) {
				character.jump -= 1;
			}
		}
		mouseX = MouseInfo.getPointerInfo().getLocation().x;
		if (mouseX - xPos - 50 < 0) {
			turnedLeft = true;
		} else {
			turnedLeft = false;
		}
		if (turnedLeft == true) {
			character.image = leftcapn;
			gunxpos = xPos - 70;
			gunypos = yPos + 40;
			gunpic = weaponleft;
		} else {
			character.image = capn;
			gunxpos = xPos;
			gunypos = yPos;
			gunpic = weapon;
		}
		
		for (Bullets b: Bullets.bulletlist) {
			b.update();
		}
		for (Grenade g: Grenade.grenadelist) {
			g.update();
		}
		
		if ((Grenade.grenadecd < 3) && (Grenade.isCharging == false)) {
			Timer timer = new Timer();
			Grenade.isCharging = true;
	        timer.schedule(new TimerTask() {
	        	  public void run() {
	        		  Grenade.grenadecd += 1;
	        		  Grenade.isCharging = false;
	        	  }
			}, 3000);
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(character.image, xPos, yPos, character.width, character.height, null);
		ArmRotator.drawNewArm(g, gunxpos + 80, gunypos+30, gunpic);
		Iterator<Bullets> iter = Bullets.bulletlist.iterator();
		while(iter.hasNext()) {
		  iter.next().render(g);
		}
		
		Iterator<Grenade> giter = Grenade.grenadelist.iterator();
		while(giter.hasNext()) {
		  giter.next().render(g);
		}
		
		g.drawString("" + Grenade.grenadecd, 10, 10);
	}
}
