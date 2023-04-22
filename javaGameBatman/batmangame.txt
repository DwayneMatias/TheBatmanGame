package javaGameBatman;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

import org.w3c.dom.Text;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


//Name: Dwayne Matias
//Course: ICS3U1
//Project: ISU - Game project
//Description: Play as Batman as he solves riddles set out by the Riddler.

@SuppressWarnings("serial")
public class theBatman extends JPanel implements Runnable, KeyListener {

	//Rectangle Variables
	//Player and NPCS
	Rectangle player = new Rectangle(0, 420, 40, 40);
	Rectangle npc = new Rectangle(5, 175, 40, 40);

	//Rectangle Arrays
	///// Collision walls for levels 1, 2, 3
	Rectangle[] walls = new Rectangle[20];
	Rectangle[] walls2 = new Rectangle[6];
	Rectangle[] walls3 = new Rectangle[11];

	/////////////////////Object rectangles
	Rectangle[] box = new Rectangle[5];
	Rectangle[] batarang =  new Rectangle[6];

	/////////////////////////
	Rectangle comp = new Rectangle(494,240, 40, 40);
	Rectangle battery = new Rectangle(576, 750, 50, 80);

	///////////////////////Collision rectangles for 
	Rectangle door = new Rectangle(576, 527, 100, 90);
	Rectangle key = new Rectangle(406, 387, 40, 60);
	Rectangle hint1 = new Rectangle(590, 482, 80, 80);
	Rectangle hint2 = new Rectangle(7, 5, 80, 80);
	Rectangle doorFinal = new Rectangle(473, 70, 40, 40);
	//////////////////
	Rectangle answercall = new Rectangle(0, 320, 80, 80);
	Rectangle breakBox = new Rectangle(508, 492, 40, 40);
	Rectangle gate = new Rectangle(508, 432, 40, 400);
	Rectangle finalDoor = new Rectangle(691, 718, 40, 40);

	////////////////////// Level 3 turrets
	Rectangle turret =  new Rectangle(48, 644, 40, 40);
	Rectangle turret2 =  new Rectangle(44, 388, 40, 40);
	Rectangle turret3 =  new Rectangle(48, 344, 40, 40);
	Rectangle turret4 =  new Rectangle(52, 230, 40, 40);

	/////////////////////Turret Bullets
	Rectangle bullet =  new Rectangle(48, 644, 5, 5);
	Rectangle bullet2 =  new Rectangle(44, 388, 5, 5);
	Rectangle bullet3 =  new Rectangle(48, 344, 5, 5);
	Rectangle bullet4 =  new Rectangle(52, 230, 5, 5);

	//////////////////////// Level 3 Collision areas for turrets (when plater steps on area, turrets shoot)
	Rectangle dangerArea = new Rectangle(607, 644, 40, 40);
	Rectangle dangerArea2 = new Rectangle(416, 380, 40, 40);
	Rectangle dangerArea3 = new Rectangle(416, 340, 40, 40);
	Rectangle dangerArea4 = new Rectangle(405, 214, 40, 40);

	//////////////////////Level 3 keypad Collision rectangles
	Rectangle keyPad = new Rectangle(425, 70, 40, 40);
	Rectangle keyMark = new Rectangle(695, 473, 40, 40);

	/////////////////////Level 3 trap Collision rectangles
	Rectangle trap = new Rectangle(604, 595, 30, 30);
	Rectangle trap2 = new Rectangle(380, 480, 30, 30);
	Rectangle trap3 = new Rectangle(621, 469, 30, 30);
	Rectangle trap4 = new Rectangle(695, 473, 40, 40);
	Rectangle trap5 = new Rectangle(724, 292, 40, 40);

	///////////////////////////////////////////////////////////////////////////////	
	//Boolean Variables
	boolean up, down, left, right;
	public boolean state;
	public boolean collision;
	public boolean doorCollision;
	public boolean answer;
	public boolean batproj;
	public boolean attack;
	public boolean touch;
	public boolean death;
	public boolean gateCollision;
	public boolean shoot;
	public boolean shoot2;
	public boolean shoot3;
	public boolean shoot4;
	public boolean cheats;
	public boolean gameEnd;
	/////////////////////////////////////////////////////

	//Integer Variables
	int speed = 4;
	int screenWidth = 800;
	int screenHeight = 800;
	Thread thread;
	int FPS = 60;
	int rl = walls2.length;

	/////////////////////////////////////////////////////

	//Public Variables
	public int batteryCount = 0;
	public int keyCount = 0;
	public int gameState = 1;
	public int playState = 2;
	public int pauseState = 3;
	public int menuNum = 0;
	public int level = 0;
	public int code;
	public int numProjectile = 0;
	public int numProjectile2 = 0;
	public int batSpeed = 5;
	public int bulletSpeed = 14;
	public int j = 0;
	public int hitCount = 0;
	public static Clip hello;
	public String input = " ";
	public String keycode = " ";

	/////////////////////////////////////////////////////

	//Sound Effects
	Clip unlock, grab, ability, computer, bgm2, bgmRiddler, punch, batarangSFX, electricity; {

		try {

			AudioInputStream sound = AudioSystem.getAudioInputStream(new File ("unlock.wav"));
			unlock = AudioSystem.getClip();
			unlock.open(sound);
			
			AudioInputStream sound1 = AudioSystem.getAudioInputStream(new File ("Coin_3.wav"));
			grab = AudioSystem.getClip();
			grab.open(sound1);
			
			AudioInputStream sound2 = AudioSystem.getAudioInputStream(new File ("detect.wav"));
			ability = AudioSystem.getClip();
			ability.open(sound2);
			
			AudioInputStream sound3 = AudioSystem.getAudioInputStream(new File ("comp.wav"));
			computer = AudioSystem.getClip();
			computer.open(sound3);
			
			AudioInputStream sound4 = AudioSystem.getAudioInputStream(new File ("theBatman2.wav"));
			bgm2 = AudioSystem.getClip();
			bgm2.open(sound4);
			
			AudioInputStream sound5 = AudioSystem.getAudioInputStream(new File ("theRiddler.wav"));
			bgmRiddler = AudioSystem.getClip();
			bgmRiddler.open(sound5);
			
			AudioInputStream sound6 = AudioSystem.getAudioInputStream(new File ("punch.wav"));
			punch = AudioSystem.getClip();
			punch.open(sound6);
			
			AudioInputStream sound7 = AudioSystem.getAudioInputStream(new File ("batarang.wav"));
			batarangSFX = AudioSystem.getClip();
			batarangSFX.open(sound7);
			
			AudioInputStream sound9 = AudioSystem.getAudioInputStream(new File ("Electric Sound Effect.wav"));
			electricity = AudioSystem.getClip();
			electricity.open(sound9);
		} 

		catch (Exception e) {

		}

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Images
	Image img = Toolkit.getDefaultToolkit().getImage("New Piskel.gif");

	Image img2 = Toolkit.getDefaultToolkit().getImage("New Piskel30.gif");

	Image img3 = Toolkit.getDefaultToolkit().getImage("bhgsure.gif");

	Image img4 = Toolkit.getDefaultToolkit().getImage("qmark2.gif");


	Image img8 = Toolkit.getDefaultToolkit().getImage("riddle1.gif");

	Image img9 = Toolkit.getDefaultToolkit().getImage("doorandroof.png");

	Image img11 = Toolkit.getDefaultToolkit().getImage("dt.gif");

	Image img12 = Toolkit.getDefaultToolkit().getImage("New Piskel.gif");

	Image img13 = Toolkit.getDefaultToolkit().getImage("New Piskel8.gif");

	Image img14 = Toolkit.getDefaultToolkit().getImage("New Piskel7.png");

	Image img15 = Toolkit.getDefaultToolkit().getImage("New Piskel.gif");

	Image img16 = Toolkit.getDefaultToolkit().getImage("New Piskel8.gif");

	Image img17 = Toolkit.getDefaultToolkit().getImage("rwalk.gif");

	Image img18 = Toolkit.getDefaultToolkit().getImage("lwalk.gif");

	Image img19 = Toolkit.getDefaultToolkit().getImage("compdia.png");

	Image img20 = Toolkit.getDefaultToolkit().getImage("key.gif");

	Image img21 = Toolkit.getDefaultToolkit().getImage("opendoor.png");

	Image img22 = Toolkit.getDefaultToolkit().getImage("doorclose.png");

	Image img23 = Toolkit.getDefaultToolkit().getImage("doorclose.png");

	Image img24 = Toolkit.getDefaultToolkit().getImage("menu.gif");

	Image img25 = Toolkit.getDefaultToolkit().getImage("hint1.gif");

	Image img26 = Toolkit.getDefaultToolkit().getImage("qmark2.gif");

	Image img27 = Toolkit.getDefaultToolkit().getImage("hint2.gif");

	Image img28 = Toolkit.getDefaultToolkit().getImage("riddleCorrect.gif");

	Image img29 = Toolkit.getDefaultToolkit().getImage("lvl2bg.gif");

	Image img30 = Toolkit.getDefaultToolkit().getImage("batproj.gif");

	Image img31 = Toolkit.getDefaultToolkit().getImage("attack.png");

	Image img32 = Toolkit.getDefaultToolkit().getImage("hudd.png");

	Image img33 = Toolkit.getDefaultToolkit().getImage("NPC.gif");

	Image img35 = Toolkit.getDefaultToolkit().getImage("DIA.png");

	Image img36 = Toolkit.getDefaultToolkit().getImage("gateOpen.gif");

	Image img37 = Toolkit.getDefaultToolkit().getImage("finalLvl.gif");

	Image img38 = Toolkit.getDefaultToolkit().getImage("health2.png");

	Image img39 = Toolkit.getDefaultToolkit().getImage("gameOver.gif");

	Image img40 = Toolkit.getDefaultToolkit().getImage("objects.png");

	Image img41 = Toolkit.getDefaultToolkit().getImage("fencing.png");

	Image img42 = Toolkit.getDefaultToolkit().getImage("bullet.png");

	Image img43 = Toolkit.getDefaultToolkit().getImage("box.png");

	Image img44 = Toolkit.getDefaultToolkit().getImage("box2.png");

	Image img45 = Toolkit.getDefaultToolkit().getImage("box3.png");

	Image img46 = Toolkit.getDefaultToolkit().getImage("box4.png");

	Image img47 = Toolkit.getDefaultToolkit().getImage("box5.png");

	Image img48 = Toolkit.getDefaultToolkit().getImage("keypad.png");

	Image img49 = Toolkit.getDefaultToolkit().getImage("CorrectCode.gif");

	Image img50 = Toolkit.getDefaultToolkit().getImage("keyHint.gif");

	Image img51 = Toolkit.getDefaultToolkit().getImage("cheats.gif");

	Image img52 = Toolkit.getDefaultToolkit().getImage("health1.png");
	
	Image img53 = Toolkit.getDefaultToolkit().getImage("Controls.png");
	
	Image img54 = Toolkit.getDefaultToolkit().getImage("gameEnd.gif");

	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Main game methods
	public theBatman() {

		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);

		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		initialize();
		while(true) {
			//main game loop
			update();
			this.repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}


	//Initialize rectangle arrays
	public void initialize() {
		//setups before the game starts running

		//Level 1 walls
		walls[0] = new Rectangle(380, 190, 218, 50);

		walls[1] = new Rectangle(563, 450, 130, 80);

		walls[2] = new Rectangle(80, 690, 50, 75);

		walls[3] = new Rectangle(0, 690, 150, 250);

		walls[4] = new Rectangle(700, 530, 200, 280);

		walls[5] = new Rectangle(0, 90, 128, 290);

		walls[6] = new Rectangle(0, 0, 0, 0);

		walls[7] = new Rectangle(82, 90, 232, 154);

		walls[8] = new Rectangle(276, 324, 15, 164);

		walls[9] = new Rectangle(0, 540, 10, 0);

		walls[10] = new Rectangle(0, 560, 160, 560);

		walls[11] = new Rectangle(95, 360, 60, 60);

		walls[12] = new Rectangle(0, 350, 24, 65);

		walls[13] = new Rectangle(516, 527, 50, 90);

		walls[14] = new Rectangle(681, 527, 20, 90);

		walls[15] = new Rectangle(695, 176, 20, 500);

		walls[16] = new Rectangle(276, 314, 119, 16);

		walls[17] = new Rectangle(378, 194, 16, 126);

		walls[18] = new Rectangle(446, 345, 16, 144);

		walls[19] = new Rectangle(116, 689, 450, 144);

		//Level 2 walls
		walls2[0] = new Rectangle(0, 220, 265, 144);

		walls2[1] = new Rectangle(0, 570, 240, 444);

		walls2[2] = new Rectangle(540, 400, 700, 324);

		walls2[3] = new Rectangle(2, 144, 150, 15);

		walls2[4] = new Rectangle(290, 148, 130, 33);

		walls2[5] = new Rectangle(0, 64, 800, 20);

		//Level 3 walls

		walls3[0] = new Rectangle(0, 0, 800, 70);

		walls3[1] = new Rectangle(100, 720, 500, 324);

		walls3[2] = new Rectangle(90, 460, 290, 124);

		walls3[3] = new Rectangle(460, 450, 15, 124);

		walls3[4] = new Rectangle(460, 580, 400, 20);

		walls3[5] = new Rectangle(460, 440, 90, 20);

		walls3[6] = new Rectangle(550, 340, 25, 120);

		walls3[7] = new Rectangle(460, 330, 115, 20);

		walls3[8] = new Rectangle(90, 325, 280, 20);

		walls3[9] = new Rectangle(0, 170, 405, 60);

		walls3[10] = new Rectangle(450, 170, 360, 60);

		//Boxes

		box[0] = new Rectangle(603, 340, 40, 80);

		box[1] = new Rectangle(695, 344, 40, 40);

		box[2] = new Rectangle(651, 465, 40, 40);

		box[3] = new Rectangle(707, 421, 80, 40);

		box[4] = new Rectangle(511, 477, 40, 40);

	}

	//Update Method for all the methods
	public void update() {

		if(gameState == 2) {
			move();
			keepInBound();

			for(int i = 0; i < walls.length; i++)
				checkCollision(walls[i]);

			for(int c = 0; c < walls2.length; c++)
				checkCollision2(walls2[c]);

			for(int k = 0; k < walls3.length; k++)
				checkCollision3(walls3[k]);

			for(int l = 0; l < box.length; l++)
				boxCollision(box[l]);

			keyMark();
			gateCollision();
			objCollision(comp);
			doorCollision(door);
			moveProjectile();
			finalDoor();
			dangerArea();
			trap();
			cheats();
			health();
			level3door();
		}

		else if(gameState == 3) {
			hello.stop();

		}
	}

	//Paint component for graphics for all levels
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		//////////////////		
		//Menu screen
		
		if(gameState == 1) {

			//Menu buttons
			g2.drawImage(img24, 0, 0, screenWidth, screenHeight, null);

			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			g2.drawString("NEW GAME", 50, 400);

			if(menuNum == 0) {

				g2.setColor(Color.WHITE);
				g2.drawString(">", 20, 400);
			}
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			g2.drawString("CONTROLS", 43, 480);

			if(menuNum == 1) {

				g2.setColor(Color.WHITE);
				g2.drawString(">", 10, 480);
			}
			
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			g2.drawString("EXIT", 115, 550);

			if(menuNum == 2) {

				g2.setColor(Color.WHITE);
				g2.drawString(">", 75, 550);
			}

			bgm2.start();
			hello.stop();
			bgmRiddler.stop();
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////
		//gameState 4 is the Controls page in the menu
		if(gameState == 4) {
			
			g2.drawImage(img53, 0, 0, screenWidth, screenHeight, null);
		}
		
		//Level 2 graphics
		if (level == 2) {

			g2.drawImage(img29, 0, 0, screenWidth, screenHeight, null);
			g2.drawImage(img26, 7, 60, 80, 80, null);
			g2.drawImage(img20, 406, 387, 40, 60, null);
			g2.setColor(Color.BLACK);
			g2.drawImage(img, player.x, player.y, 40, 40, null);
			
			//Player animations; draws gif depending on direction
			//This specifc block of code is repeated a total of 3 times
			//in the paint Component, one for each level
			if(up) {

				g2.drawImage(img2, player.x, player.y, 40, 40, null);

				img = img13;
			}

			else  {

				img = img14;
			}

			if(down) {

				g2.drawImage(img15, player.x, player.y, 40, 40, null);
				img = img15;
			}

			if(right) {

				g2.drawImage(img17, player.x, player.y, 40, 40, null);
				img = null;
			}

			if(left) {

				g2.drawImage(img18, player.x, player.y, 40, 40, null);
				img = null;
			}

			if(attack) {

				g2.drawImage(img31, player.x, player.y, 40, 40, null);
				img = null;
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//Draw the NPC in level 2
			g2.drawImage(img33, npc.x, npc.y, 40, 40, null);
			
			//Draw the player projectiles, and make it go towards aimed position
			if(numProjectile > 0) {

				for(int c = 0; c < 6; c++) {

					if(batarang[c] != null) {

						g2.drawImage(img30, batarang[c].x, batarang[c].y, 40, 40, null);
						batarangSFX.start();
					}
				}
			}


			if(batproj) {

				g2.drawImage(img31, player.x, player.y, 40, 40, null);
			}
			
			//If the player touches the NPC, draw a speech bubble
			if(touch) {

				g2.drawImage(img35, 100, 500, 600, 400, null);

			}

				g2.drawImage(img40, 0, 0, screenWidth, screenHeight, null);

		}

		//Level 3
		else if (level == 3) {

			g2.drawImage(img37, 0, 0, screenWidth, screenHeight, null);
			g2.drawImage(img, player.x, player.y, 40, 40, null);
			
			//Draw the bullets that shoot out of the turrets
			if(shoot == true) {

				g2.drawImage(img42, bullet.x+=bulletSpeed, turret.y-3, 16, 16, null);

			}

			if(shoot2 == true) {

				g2.drawImage(img42, bullet2.x+=bulletSpeed, turret2.y-3, 16, 16, null);
			}

			if(shoot3 == true) {
				g2.drawImage(img42, bullet3.x+=bulletSpeed, turret3.y-3, 16, 16, null);
			}

			if(shoot4 == true) {

				g2.drawImage(img42, bullet4.x+=bulletSpeed, turret4.y-3, 16, 16, null);
			}
			if(player.intersects(turret)) {

			}

			if(up) {

				g2.drawImage(img2, player.x, player.y, 40, 40, null);
				img = img13;
			}

			else  {

				img = img14;
			}

			if(down) {

				g2.drawImage(img15, player.x, player.y, 40, 40, null);
				img = img15;
			}

			if(right) {

				g2.drawImage(img17, player.x, player.y, 40, 40, null);
				img = null;
			}

			if(left) {

				g2.drawImage(img18, player.x, player.y, 40, 40, null);
				img = null;
			}

			if(attack) {

				g2.drawImage(img31, player.x, player.y, 40, 40, null);
				img = null;
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			g2.drawImage(img26, 695, 473, 80, 80, null);
			g2.drawImage(img41, 0, 0, screenWidth, screenHeight, null);
			g2.drawImage(img41, 0, 0, screenWidth, screenHeight, null);
			g2.drawImage(img41, 0, 0, screenWidth, screenHeight, null);

			if(player.intersects(keyPad)) {

				g2.drawImage(img48,  190, 130, 400, 600, null);

			}
			
			//If the player types in the correct code, display an indicator
			if(keycode.equalsIgnoreCase("1079") && player.intersects(keyPad)) {

				g2.drawImage(img49, 190, 130, 400, 600, null);
				
			}
			
			if(player.intersects(keyMark)) {

				g2.drawImage(img50 ,110, 250, 600, 400, null);
				
			}
			
			//End of game
			if(gameEnd) {
				g2.drawImage(img54, 0, 0, screenWidth, screenHeight, null);
			}
		} 
		
		//Level 1
		if(gameState == 2 && level == 1) {


			////////////////

			g2.drawImage(img3, 0, 0, screenWidth, screenHeight, null);
			g2.drawImage(img4, 590, 482, 80, 80, null);
			g2.drawImage(img22, 0, 0, screenWidth, screenHeight, null);


			if(player.intersects(hint2)) {		

				g2.drawImage(img27 ,110, 250, 600, 400, null);	

			}


			if(player.intersects(hint1)) {

				g2.drawImage(img25 ,110, 250, 600, 400, null);

			}





			//							g2.setColor(Color.GRAY);
			//							
			//								g2.fill(walls3[0]);
			//								g2.fill(walls3[1]);
			//								g2.fill(walls3[2]);
			//								g2.fill(walls3[3]);
			//								g2.fill(walls3[4]);
			//								g2.fill(walls3[5]);
			//								g2.fill(walls3[6]);
			//								g2.fill(walls3[7]);
			//								g2.fill(walls3[8]);
			//								g2.fill(walls3[9]);
			//								g2.fill(walls3[10]);

			if(player.intersects(door) && keyCount == 1) {

				g2.drawImage(img21, 0, 0, screenWidth, screenHeight, null);

				img22 = null;

			}

			else {

				img22 = img23;

			}
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			//Player Sprite Animation
			if(up) {

				g2.drawImage(img2, player.x, player.y, 40, 40, null);
				img = img13;
			}

			else  {

				img = img14;
			}

			if(down) {

				g2.drawImage(img15, player.x, player.y, 40, 40, null);
				img = img15;
			}

			if(right) {

				g2.drawImage(img17, player.x, player.y, 40, 40, null);
				img = null;
			}

			if(left) {

				g2.drawImage(img18, player.x, player.y, 40, 40, null);
				img = null;
			}

			if(attack) {

				g2.drawImage(img31, player.x, player.y, 40, 40, null);
				img = null;
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			g2.drawImage(img26, 7, 5, 80, 80, null);
			g2.drawImage(img20, 406, 387, 40, 60, null);

			g2.setColor(Color.BLACK);
			g2.drawImage(img, player.x, player.y, 40, 40, null);

			if(collision) {

				g2.drawImage(img19,  0, 0, screenWidth, screenHeight, null);
				g2.drawImage(img8, 100,300, 600, 400, null);
			}

			if(input.equalsIgnoreCase("time") && player.intersects(comp)) {

				g2.drawImage(img28, 0, 0, screenWidth, screenHeight, null);
				answer = true;
			}

		}
	}

	////////////////////Methods for keyboard input (what in here): keypresses such as the player's movement and 
	////////////////////When the player presses "V" which is for opening the JOptionPanes
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		code = e.getKeyCode();

		if(player.intersects(comp) && code == KeyEvent.VK_V) {		

			riddleAnswer();
			System.out.println(input);
		}

		if(player.intersects(keyPad) && code == KeyEvent.VK_V  && level == 3) {		

			codeAnswer();
			System.out.println(keycode);
		}

		if(input.equalsIgnoreCase("time")) {

			answer = true;
			bgmRiddler.start();
			hello.stop();
		}


		if(gameState == 1) {

			if(code == KeyEvent.VK_W) {
				menuNum--;
				if(menuNum < 0) {
					menuNum = 2;
				}
			}

			if(code == KeyEvent.VK_S) {
				menuNum++;
				if(menuNum > 2) {
					menuNum = 0;
				}

			}

			if(code == KeyEvent.VK_ENTER) {

				if(menuNum == 0) {
					gameState = 2;
					level = 1;
					hello.start();
					bgm2.stop();
				}

				if(menuNum == 1) {

					System.out.println("Options!");
					gameState = 4;
				}


				if(menuNum == 2) {
					System.exit(0);
				}
			}
		}

		if(code == KeyEvent.VK_W) {

			up = true;
			System.out.println(player.x);
			System.out.println(player.y);
		}

		if(code == KeyEvent.VK_S) {

			down = true;
		}

		if(code == KeyEvent.VK_A) {

			left = true;

		}

		if(code == KeyEvent.VK_D) {

			right = true;

		}

		if(code == KeyEvent.VK_ESCAPE ){

			gameState = 1;
			state = true;
		}

		if(code == KeyEvent.VK_O){

			gameState = 2;
			hello.start();
			bgm2.stop();

		}

		if(code == KeyEvent.VK_G) {

			System.out.println("punching!");
			punch.start();
			attack = true;
		}


		if(code == KeyEvent.VK_R) {

			batproj = true;

			if(numProjectile < 6) {

				for(int i = 0; i < 6; i++) {

					if(batarang[i] == null) {

						batarang[i] = new Rectangle(player.x, player.y, 40, 40);

						numProjectile++;

						break;
					}

				}
			}
		}
	}

	//Method to check when the player releases a key
	//All the booleans that were true when the player was 
	//pressing a key is then turned into false in this
	//Method
	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();


		if(code == KeyEvent.VK_W) {
			up = false;
		}

		if(code == KeyEvent.VK_S) {
			down = false;
		}

		if(code == KeyEvent.VK_A) {
			left = false;
		}

		if(code == KeyEvent.VK_D) {
			right = false;
		}

		if(code == KeyEvent.VK_G) {

			punch.setMicrosecondPosition(0);
			attack = false;
		}

		if(code == KeyEvent.VK_R) {

			batproj = false;
			batarangSFX.setMicrosecondPosition(0);

		}

	}

	//Method that handles the players movement speed
	void move() {

		if(left)
			player.x -= speed;

		else if(right)
			player.x += speed;

		if(up)
			player.y += -speed;

		else if(down)
			player.y += speed;

	}

	//Method that stops the player from exiting out of the 
	//frame (800 X 800)
	//Takes the players X and Y values and finds the difference between it and the 
	//frame's width and height
	void keepInBound() {

		if(player.x < 0)
			player.x = 0;

		else if(player.x > screenWidth - player.width)
			player.x = screenWidth - player.width;

		if(player.y < 0)
			player.y = 0;

		else if(player.y > screenHeight - player.height)
			player.y = screenHeight - player.height;
	}

	//Collision Methods

	//Collision method for the walls in level 1
	//When the player intersects with a wall at any side(top, bottom, right side, left side)
	//It will stop the player from moving that way

	void checkCollision(Rectangle wall) {
		//check if rect touches wall
		if(player.intersects(wall) && level == 1) {
			System.out.println("collision");
			//stop the rect from moving
			double left1 = player.getX();
			double right1 = player.getX() + player.getWidth();
			double top1 = player.getY();
			double bottom1 = player.getY() + player.getHeight();
			double left2 = wall.getX();
			double right2 = wall.getX() + wall.getWidth();
			double top2 = wall.getY();
			double bottom2 = wall.getY() + wall.getHeight();


			if(right1 > left2 && 
					left1 < left2 && 
					right1 - left2 < bottom1 - top2 && 
					right1 - left2 < bottom2 - top1)
			{
				//rect collides from left side of the wall
				player.x = wall.x - player.width;
			}
			else if(left1 < right2 &&
					right1 > right2 && 
					right2 - left1 < bottom1 - top2 && 
					right2 - left1 < bottom2 - top1)
			{
				//rect collides from right side of the wall
				player.x = wall.x + wall.width;
			}
			else if(bottom1 > top2 && top1 < top2)
			{
				//rect collides from top side of the wall
				player.y = wall.y - player.height;
			}
			else if(top1 < bottom2 && bottom1 > bottom2)
			{
				//rect collides from bottom side of the wall
				player.y = wall.y + wall.height;
			}


		}
	}

	//Collision checker for walls in level 2
	//Work the same as other collision methods
	void checkCollision2(Rectangle walls2) {
		//check if rect touches wall
		if(player.intersects(walls2) && level == 2) {
			System.out.println("collision!!!");
			//stop the rect from moving
			double left1 = player.getX();
			double right1 = player.getX() + player.getWidth();
			double top1 = player.getY();
			double bottom1 = player.getY() + player.getHeight();
			double left2 = walls2.getX();
			double right2 = walls2.getX() + walls2.getWidth();
			double top2 = walls2.getY();
			double bottom2 = walls2.getY() + walls2.getHeight();


			if(right1 > left2 && 
					left1 < left2 && 
					right1 - left2 < bottom1 - top2 && 
					right1 - left2 < bottom2 - top1)
			{
				//rect collides from left side of the wall
				player.x = walls2.x - player.width;
			}
			else if(left1 < right2 &&
					right1 > right2 && 
					right2 - left1 < bottom1 - top2 && 
					right2 - left1 < bottom2 - top1)
			{
				//rect collides from right side of the wall
				player.x = walls2.x + walls2.width;
			}
			else if(bottom1 > top2 && top1 < top2)
			{
				//rect collides from top side of the wall
				player.y = walls2.y - player.height;
			}
			else if(top1 < bottom2 && bottom1 > bottom2)
			{
				//rect collides from bottom side of the wall
				player.y = walls2.y + walls2.height;
			}


		}
	}

	//Collision chcker for walls in the third level, functionality works the same as other
	//Collision checkers for walls
	void checkCollision3(Rectangle walls3) {
		//check if rect touches wall
		if(player.intersects(walls3) && level == 3) {
			System.out.println("collision!!!");
			//stop the rect from moving
			double left1 = player.getX();
			double right1 = player.getX() + player.getWidth();
			double top1 = player.getY();
			double bottom1 = player.getY() + player.getHeight();
			double left2 = walls3.getX();
			double right2 = walls3.getX() + walls3.getWidth();
			double top2 = walls3.getY();
			double bottom2 = walls3.getY() + walls3.getHeight();


			if(right1 > left2 && 
					left1 < left2 && 
					right1 - left2 < bottom1 - top2 && 
					right1 - left2 < bottom2 - top1)
			{
				//rect collides from left side of the wall
				player.x = walls3.x - player.width;
			}
			else if(left1 < right2 &&
					right1 > right2 && 
					right2 - left1 < bottom1 - top2 && 
					right2 - left1 < bottom2 - top1)
			{
				//rect collides from right side of the wall
				player.x = walls3.x + walls3.width;
			}
			else if(bottom1 > top2 && top1 < top2)
			{
				//rect collides from top side of the wall
				player.y = walls3.y - player.height;
			}
			else if(top1 < bottom2 && bottom1 > bottom2)
			{
				//rect collides from bottom side of the wall
				player.y = walls3.y + walls3.height;
			}


		}
	}

	void boxCollision(Rectangle box) {
		//check if rect touches wall
		if(player.intersects(box) && level == 3) {
			System.out.println("collision!!!");
			//stop the rect from moving
			double left1 = player.getX();
			double right1 = player.getX() + player.getWidth();
			double top1 = player.getY();
			double bottom1 = player.getY() + player.getHeight();
			double left2 = box.getX();
			double right2 = box.getX() + box.getWidth();
			double top2 = box.getY();
			double bottom2 =box.getY() + box.getHeight();


			if(right1 > left2 && 
					left1 < left2 && 
					right1 - left2 < bottom1 - top2 && 
					right1 - left2 < bottom2 - top1)
			{
				//rect collides from left side of the wall
				player.x = box.x - player.width;
			}
			else if(left1 < right2 &&
					right1 > right2 && 
					right2 - left1 < bottom1 - top2 && 
					right2 - left1 < bottom2 - top1)
			{
				//rect collides from right side of the wall
				player.x = box.x + box.width;
			}
			else if(bottom1 > top2 && top1 < top2)
			{
				//rect collides from top side of the wall
				player.y = box.y - player.height;
			}
			else if(top1 < bottom2 && bottom1 > bottom2)
			{
				//rect collides from bottom side of the wall
				player.y = box.y + box.height;
			}


		}
	}

	//Collision method for many things just to avoid creating even more methods
	//What is in this method:
	//Collision checker for the computer seen in level 1,
	//Collision checker for other abilities/objects such as
	//the key, and detective vision
	//Collision checker on the door leading to the second level
	//Collision checker for the NPC in level 2 and the player

	public void objCollision(Rectangle comp) {

		//Computer collision
		if(player.intersects(comp) && level == 1) {						//When Player touches the computer
			collision = true;
			computer.start();

		}	
		else {

			collision = false;
		}				
							

		
		//Key collision
		if(player.intersects(key) && level == 1) {					
			keyCount = 1;								
			System.out.println(key);		
			img20 = null;								
			grab.start();								

		}

		//Hint (Question mark) collision
		if(player.intersects(hint1) && level == 1) {					

			img4 = null;								
			grab.start();								

		}

		//Hint (Question mark) collision
		if(player.intersects(hint2) && level == 1) {						

			img26 = null;									
			grab.start();								 

		}

		//Door leading to level 2 
		if(player.intersects(answercall) && answer == false && level == 1) {

			System.out.println("you havent completed the riddle yet!");
		}

		else if (player.intersects(answercall) && answer == true && level == 1) {
			bgmRiddler.stop();
			System.out.println("well done");
			level = 2;
			System.out.println(level);
		}

		//Npc and player collision checker
		if(level == 2 && player.intersects(npc)) {

			touch = true;
		}

		else {

			touch = false;
		}

	}

	//Collision method for the small room seen in level 1
	//It checks for collision on the top and bottom of the rectangle placed there
	//If the player attempts opening the door without getting the key, they will not be able to enter
	//otherwise, if they do have the key they can enter
	void doorCollision(Rectangle door) {
		//check if rect touches wall

		double top1 = player.getY();
		double bottom1 = player.getY() + player.getHeight();
		double top2 = door.getY();
		double bottom2 = door.getY() + door.getHeight();

		if(player.intersects(door) && keyCount == 0 && level == 1) {
			//stop the rect from moving
			if(top1 < bottom2 && bottom1 > bottom2)
			{
				//rect collides from bottom side of the wall
				player.y = door.y + door.height;
			}

			doorCollision = true;

		}


		if(player.intersects(door) && keyCount == 1 && level == 1) {
			unlock.start();

		}


	}

	//This method is for the electrified gate seen in level 2
	//The player is sent to level 1 upon collision
	void gateCollision() {



		if(player.intersects(gate) && hitCount == 0 && level == 2) {
			electricity.start();
			level = 1;
		}

		if(player.intersects(gate) && hitCount == 1 && level == 2) {
			unlock.start();

		}	
	}	

	//Method for the door that leads to level 3
	void finalDoor() {

		if(level == 2) {

			if(player.intersects(finalDoor)) {

				level = 3;

				}
			}
		}
	
	void level3door() {
		if(player.intersects(doorFinal) && keycode.equalsIgnoreCase("1079")) {
			gameEnd = true;
		}
	}

	//This method is for the danger areas in level 3, the places where 
	//if the player steps on them, the turrets will shoot bullets at the player
	//Just like many other methods this works through rectangle intersection
	void dangerArea() {

		if(player.intersects(dangerArea)&& level == 3) {

			shoot = true;
			System.out.println();
		}

		if(player.intersects(dangerArea2) && level == 3) {

			shoot2 = true;
			System.out.println();
		}

		if(player.intersects(dangerArea3) && level == 3) {

			shoot3 = true;
			System.out.println();
		}

		if(player.intersects(dangerArea4) && level == 3) {

			shoot4 = true;
			System.out.println();
		}

	}

	void keyCode() {

		if(player.intersects(keyPad) && level == 3) {					

		}	
	}

	//This method is for the traps that you see in level 3 (the saws) 
	//The player is given only 1 health to maximize difficulty and you will immediately be 
	//sent to the first level if the player were to step on one of these traps
	void trap() {

		if(player.intersects(trap) && level == 3) {
			level = 1;
		}

		if(player.intersects(trap2) && level == 3) {
			level = 1;
		}

		if(player.intersects(trap3) && level == 3) {
			level = 1;
		}

		if(player.intersects(trap5) && level == 3) {
			level = 1;
		}


	}

	//Method for the keycode 
	public void codeAnswer() {

		keycode = JOptionPane.showInputDialog("code: ");
	}

	//Method for the input of the very first riddle as well as the cheats 
	public void riddleAnswer() {

		input = JOptionPane.showInputDialog("Answer: ");

	}

	//This method is for the players projectiles (batarang)
	//
	public void moveProjectile() {

		//For loop that runs 6 times
		//Because the player is given 6 projectiles
		//If the projectile is not equal to null, 
		//then the player can continously throw projectiles until they reach 6
		for(j = 0; j < 6; j++) {
			if(batarang[j] != null) {

				batarang[j].x++;
				batarang[j].x += speed;


				//If the player projectile intersects the breakbox,
				//the electricity on the gate dissapears and allows
				//the player to pass through
				if(batarang[j].intersects(breakBox)) {
					electricity.start();
					img29 = img36;
					hitCount++;

				}
			}
		}

	}

	//Method for the question mark seen in level 3
	public void keyMark() {

		if(player.intersects(keyMark) && level == 3) {
			img26 = null;
		}
	}

	//Method for the cheats given to you Mrs.wong,
	//The cheats are all dependant on what you type into 
	//the JOPtionPane, the cheats allow you to transition between 
	//all the levels without having to solve any riddles 
	public void cheats() {

		if(input.equalsIgnoreCase("csrox")) {

			img8 = img51;
			answer = true;
			cheats = true;
		}

		if(cheats == true && input.equalsIgnoreCase("LEVEL 3")) {

			level = 3;
		}

		if(cheats == true && input.equalsIgnoreCase("LEVEL 2")) {

			level = 2;
		}

		if(cheats == true && input.equalsIgnoreCase("LEVEL 1")) {

			level = 1;
		}


	}

	//Method for the turrets in level 3, if the one of these bullets happen to hit the player
	//They will immediately be sent back to the first level
	void health() {

		if(player.intersects(bullet) && level == 3) {
			level = 1;
		}

		if(player.intersects(bullet2) && level == 3) {
			level = 1;
		}

		if(player.intersects(bullet3) && level == 3) {
			level = 1;
		}

		if(player.intersects(bullet4) && level == 3) {
			level = 1;
		}

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame ("The Batman");
		theBatman myPanel = new theBatman ();
		frame.add(myPanel);
		frame.addKeyListener(myPanel);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		//This is the main menu music, placed here just so that it plays as soon
		//as the JFrame is opened
		try {

			AudioInputStream sound = AudioSystem.getAudioInputStream(new File ("bmbgm.wav"));
			hello = AudioSystem.getClip();
			hello.open(sound);

		} 

		catch (Exception e) {

		}

		hello.start();
	}
}
