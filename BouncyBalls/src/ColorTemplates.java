

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorTemplates {
	
	static List<Template> templates = new ArrayList<Template>();
	int currentIndex = 0; // For the user to cycle through
	
	public ColorTemplates() {
		initializeTemplates();
	}
	
	public Template getNextTemplate() {
		Template result = templates.get(currentIndex);
		currentIndex = (currentIndex + 1) % templates.size();
		return result;
	}
	
	// Based on templates found here: https://visme.co/blog/website-color-schemes/
	private void initializeTemplates() {
		List<Color> temp1Colors = new ArrayList<Color>();
		temp1Colors.add(new Color(226, 125, 96, 200));
		temp1Colors.add(new Color(232, 168, 124, 200));
		temp1Colors.add(new Color(195, 141, 158, 200));
		temp1Colors.add(new Color(143, 195, 195, 200));
		templates.add(new Template(temp1Colors, new Color(65, 179, 163)));
		
		List<Color> temp9Colors = new ArrayList<Color>();
		temp9Colors.add(new Color(233, 75, 247, 200));
		temp9Colors.add(new Color(81, 187, 225, 200));
		temp9Colors.add(new Color(26, 15, 138, 200));
		temp9Colors.add(new Color(40, 27, 204, 200));
		templates.add(new Template(temp9Colors, new Color(0, 0, 0)));
		
		List<Color> temp30Colors = new ArrayList<Color>();
		temp30Colors.add(new Color(108,120, 103, 200));
		temp30Colors.add(new Color(63, 110, 138, 200));
		temp30Colors.add(new Color(90, 131, 162, 200));
		temp30Colors.add(new Color(151, 192, 220, 200));
		templates.add(new Template(temp30Colors, new Color(255, 255, 255)));
	
		List<Color> temp31Colors = new ArrayList<Color>();
		temp31Colors.add(new Color(184, 38, 64, 200));
		temp31Colors.add(new Color(228, 120, 57, 200));
		temp31Colors.add(new Color(246, 190, 120, 200));
		temp31Colors.add(new Color(15, 30, 100, 200));
		templates.add(new Template(temp31Colors, new Color(0, 0, 0)));
	}
	

	public static class Template {
		
		List<Color> ballColors = new ArrayList<Color>();
		Color background;
		
		public Template(List<Color> colors, Color background){
			this.ballColors = colors;
			this.background = background;
		}
		
		Color getRandomBallColor() {
			Random rand = new Random();
		    return ballColors.get(rand.nextInt(ballColors.size()));
		}
		
		Color getBackground() {
			return background;
		}
		
	}

}
