import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Protection extends Ability {
	private boolean forcefieldUp = false;
	public Protection(BufferedImage icon, int cooldown, int uses, String type) {
		super(icon, uses, uses, type);

	}
	
	@Override
	public void update() {
		if (KeyTracker.onepressed) {
			if (forcefieldUp == true) {
			} else {
				Forcefield.makeForcefield(GameState.ladycharacter.xPos, GameState.ladycharacter.yPos);
				forcefieldUp = true;
			}
		} else {
			forcefieldUp = false;
		}
	}
	
	@Override
	public void render(Graphics g) {
		
	}

}