package ProcrastinationTimer;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.Duration;
import java.awt.event.MouseListener;

public class ProcrastinationTimer extends Applet implements Runnable, KeyListener, MouseListener{
	final int WIDTH = 480, HEIGHT = 400;
	Thread thread;
	Graphics gfx;
	Image img;
	private boolean timerStarted, w, cmnd;
	LocalTime start, now;

	/**
	* Initialises applet and associated variables.
	**/
	public void init(){
		this.resize(WIDTH, HEIGHT);
		this.addKeyListener(this);
		this.addMouseListener(this);
		img = createImage(WIDTH, HEIGHT);
		gfx = img.getGraphics();
		thread = new Thread(this);
		thread.start();
		timerStarted = false;

	}

	public void paint(Graphics g){
		gfx.setColor(Color.WHITE);
		gfx.fillRect(0, 0, WIDTH, HEIGHT);

		gfx.setColor(Color.BLACK);
		gfx.fillRect(120, 100, 240, 100);
		gfx.setColor(new Color(178, 187, 194));
		gfx.fillRect(122, 102, 236, 96);

		gfx.setFont(new Font("Tahoma", Font.PLAIN, 30));
		gfx.setColor(Color.BLACK);
		gfx.drawString("Timer", 200, 35);

		gfx.setFont(new Font("Tahoma", Font.PLAIN, 28));
		if(!timerStarted){
			//draw button
			gfx.drawString("Start Timer", 175, 155);
		}
		else{
			gfx.drawString("Stop Timer", 175, 155);
			now = LocalTime.now();
			long duration = Duration.between(start, now).getSeconds();
			long hours = duration / 3600;
			long minutes = (duration % 3600) / 60;
			long seconds = duration % 60;
			gfx.drawString(String.format("%02d:%02d:%02d", hours, minutes, seconds), 200, 250);
		}

		// gfx.drawString("10:00:00", 200, 250);

		g.drawImage(img, 0, 0, this);
	}

	/**
	* Updates the paint method to redraw the graphics display.
	**/
	public void update(Graphics g){
		paint(g);
	}

	public void run() {
		while(true){


			repaint();

			try{
				Thread.sleep(10);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// if button clicked, toggle start timer
		if(e.getX() >= 122 && e.getX() <= 358 && e.getY() >= 102 && e.getY() <= 198){
			timerStarted = !timerStarted;
		}
		if(timerStarted){
			start = LocalTime.now();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			System.exit(0);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

}
