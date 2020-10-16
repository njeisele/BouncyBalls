/********************************************************************************/
/*                                                                              */
/*              AnimationWindow.java                                            */
/*                                                                              */
/*      description of class                                                    */
/*                                                                              */
/*      Written by eiseleappes                                                  */
/*                                                                              */
/********************************************************************************/


import java.util.List;
import java.util.Random;


import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;



class AnimationWindow
{
	
    static List<Ball> balls = new ArrayList<Ball>();
    final static int STARTING_NUM_BALLS = 10; // TODO: check standards
    final static int MIN_BALLS = 5;
    final static int MAX_BALLS = 23;
    final static double DEFAULT_SPEED_RANGE = 0.005;
    final static double MAX_SPEED = 0.02;
    final static double MIN_SPEED = 0.002;
    final static double SPEED_FACTOR = 0.8;
    static int TICKING_INTERVAL = 20;
    static ColorTemplates.Template currentTemplate;

    public static void main(String[] args) {
    	
    	ColorTemplates templates = new ColorTemplates();
    	currentTemplate = templates.getNextTemplate();
    	initializeBalls();
    	
        
        Frame frame = new Frame();
        
        // Add a component with a custom paint method
        
        frame.add(new CustomPaintComponent());
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
                switch(e.getKeyChar()) {
                	// Spacebar to change colors
	                case ' ': {
	                	currentTemplate = templates.getNextTemplate();
	                	resetColors();
	                	return;
	                }
	                // 'a' to add a ball
	                case 'a': {
	                	addBall();
	                	return;
	                }
	                // 's' to subtract a ball
	                case 's': {
	                	subtractBall();
	                	return;
	                }
	                case 'q': {
	                	speedUp();
	                	return;
	                }
	                case 'w': {
	                	slowDown();
	                	return;
	                }
	                default: {}
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
       
        // Closes window
       frame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
        });
        
        // Display the frame
        
        int frameWidth = 300;
        
        int frameHeight = 300;
        
        
        frame.setSize(frameWidth, frameHeight);
        
        frame.setVisible(true);
        
        frame.setVisible(false);
        
        frame.repaint();
        frame.setVisible(true);
       
        
        long currentTime = System.currentTimeMillis();
        
        while(true) {
        	if (System.currentTimeMillis() > currentTime + TICKING_INTERVAL) {
        		move();
        		checkForBounces();
        		//System.out.println("size: " + balls.size());
        		frame.paint(frame.getGraphics());
        		currentTime = System.currentTimeMillis();
        	}
        }
    }
    
    public static void initializeBalls() {
    	for (int i = 0; i < STARTING_NUM_BALLS; i++) {
    		addBall();
    	}
    }
    
    public static void resetColors() {
    	for (int i = 0; i < balls.size(); i++) {
    		Color c = currentTemplate.getRandomBallColor();
    		balls.get(i).setColor(c);
    	}
    }
    
    public static double getRandomSpeed() {
    	boolean isPositive = Math.random() < 0.5;
    	if (isPositive) {
    		return Math.random() * DEFAULT_SPEED_RANGE + MIN_SPEED;
    	} else {
    		return -Math.random() * DEFAULT_SPEED_RANGE - MIN_SPEED;
    	}
    }
    
    public static void addBall() {
    	if (balls.size() > MAX_BALLS) {
    		return;
    	}
    	//System.out.println(balls.size());
    	Color c = currentTemplate.getRandomBallColor();
		// .01 increments are ok
		//double velocityRange = 0.005;
		//balls.add(new Ball(Math.random(), Math.random(), 0.05, (Math.random() * 2.0 - 1) * velocityRange, 
		//		(Math.random() * 2.0 - 1) * velocityRange, c));
    	balls.add(new Ball(Math.random(), Math.random(), 0.05, getRandomSpeed(), getRandomSpeed(), c));
		//System.out.println(balls.size());
    }
    
    public static void subtractBall() {
    	if (balls.size() < MIN_BALLS) {
    		return;
    	}
    	Random rand = new Random();
	    balls.remove(rand.nextInt(balls.size()));
    }
    
    // TODO: this should be done inside ball itself
    public static void speedUp() {
    	for (int i = 0; i < balls.size(); i++) {
    		Ball b = balls.get(i);
    		if (Math.abs(b.getVelX()) > MAX_SPEED) {
    			//System.out.println("MAX");
    		} else {
    			b.setVelX(b.getVelX() * (1/SPEED_FACTOR));
    		}
    		
    		if (Math.abs(b.getVelY()) > MAX_SPEED) {
    			//System.out.println("MAX");
    		} else {
    			b.setVelY(b.getVelY() * (1/SPEED_FACTOR));
    		}
    	}
    }
    
    public static void slowDown() {
    	for (int i = 0; i < balls.size(); i++) {
    		Ball b = balls.get(i);
    		if (Math.abs(b.getVelX()) < MIN_SPEED) {
    			//System.out.println("MIN");
    		} else {
    			b.setVelX(b.getVelX() * SPEED_FACTOR);
    		}
    		
    		if (Math.abs(b.getVelY()) < MIN_SPEED) {
    			//System.out.println("MIN");
    		} else {
    			b.setVelY(b.getVelY() * SPEED_FACTOR);
    		}
    	}
    }
    
    // Could base it off 0 to 1 but then radius is weird. maybe also make radius like this
    public static void checkForBounces() {
    	for (int i = 0; i < balls.size(); i++) {
    		checkForCollisionAndBounce(balls.get(i));
    	}
    }
    
    public static void move() {
    	for (int i = 0; i < balls.size(); i++) {
    		Ball b = balls.get(i);
    		b.setPosX(b.getPosX() + b.getVelX());
        	b.setPosY(b.getPosY() + b.getVelY());
    	}
    }
    
    static void checkForCollisionAndBounce(Ball b) {
    	// draw is from top left I think so center is really at posX + radius
    	// so left side is just posX, right side is (posX + radius + radius)
    	double leftX = b.getPosX();
    	double rightX = b.getPosX() + 2.0 * b.getRadius();
    	double topY = b.getPosY();
    	double bottomY = b.getPosY() + 2.0 * b.getRadius();
    	double epsilon = 0.001; // Used to move ball slightly away from wall
    	if (leftX < 0.0) { 
    		// Hit left wall
    		b.setPosX(epsilon);
    		b.setVelX(-b.getVelX());
    	}
    	if (topY < 0.0) {
    		// Hit top wall
    		b.setPosY(epsilon);
    		b.setVelY(-b.getVelY());
    		
    	}
    	if (rightX > 1.0) {
    		// Hit right wall
    		b.setPosX(1.0 - epsilon - 2.0 * b.getRadius());
    		b.setVelX(-b.getVelX());
    	}
    	if (bottomY > 1.0) {
    		// Hit bottom wall
    		b.setPosY(1.0 - epsilon - 2.0 * b.getRadius());
    		b.setVelY(-b.getVelY());
    	}
    }
    

    static class CustomPaintComponent extends Component {
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Prevents need to resize buffer
    	BufferedImage  bf = new BufferedImage( screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
        
        public void paint(Graphics g) {
            
            Graphics2D g2d = (Graphics2D)g;
            
            Graphics gBuffered = bf.getGraphics();            
            
            int x = 0;
            
            int y = 0;
            
            int w = getSize().width;
            
            int h = getSize().height;
       
            
            // change background
            gBuffered.setColor(currentTemplate.getBackground());
            gBuffered.fillRect(0, 0, w, h);
            
            //System.out.println("here: " + balls.size());
            for (int i = 0; i < balls.size(); i++) {
            
            	Ball b = balls.get(i);
            	x = (int) (b.getPosX() * w);
            	y = (int) (b.getPosY() * h);
            	int rad = (int) (b.getRadius() * w); 
            	gBuffered.setColor(b.getColor());
            	gBuffered.fillOval(x,  y, rad * 2, rad * 2);
        	}
            g.drawImage(bf,0,0,null);
        }
        
    }
  

}       // end of class AnimationWindow
    




/* end of AnimationWindow.java */
