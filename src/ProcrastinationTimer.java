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
import java.util.Date;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.Duration;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ProcrastinationTimer extends Applet implements Runnable, KeyListener, MouseListener{
	final int WIDTH = 480, HEIGHT = 400;
	Thread thread;
	Graphics gfx;
	Image img;
	private boolean timerStarted, fileExists, w, cmnd;
	LocalTime start, now;
	private long duration, hours, minutes, seconds;
	private long hoursToday, minutesToday, secondsToday;

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
		//read data file: check if anything is recorded for Today
		//if so set hoursToday .. accordingly, otherwise 0
		checkData();
		// hoursToday = 0;
		// minutesToday = 0;
		// secondsToday = 0;

	}

	/**
	* Draw graphics on screen.
	**/
	public void paint(Graphics g){
		gfx.setColor(Color.WHITE);
		gfx.fillRect(0, 0, WIDTH, HEIGHT);

		gfx.setColor(Color.BLACK);
		gfx.fillRect(120, 100, 240, 100);
		gfx.setColor(new Color(178, 187, 194));
		gfx.fillRect(122, 102, 236, 96);

		gfx.setFont(new Font("Tahoma", Font.PLAIN, 30));
		gfx.setColor(Color.BLACK);
		gfx.drawString("Procrastination Timer", 100, 35);

		gfx.setFont(new Font("Tahoma", Font.PLAIN, 28));
		gfx.drawString("Time Wasted: ", 105, 250);
		gfx.drawString("Time Wasted Today: ", 60, 295);

		if(!timerStarted){
			gfx.drawString("Start Timer", 175, 155);
			gfx.drawString(String.format("%d:%02d:%02d", 0, 0, 0), 280, 250);
			gfx.drawString(String.format("%d:%02d:%02d", hoursToday, minutesToday, secondsToday), 320, 295);
		}
		else{
			gfx.drawString("Stop Timer", 175, 155);
			now = LocalTime.now();
			duration = Duration.between(start, now).getSeconds();
			hours = duration / 3600;
			minutes = (duration % 3600) / 60;
			seconds = duration % 60;
			gfx.drawString(String.format("%d:%02d:%02d", hours, minutes, seconds), 280, 250);
			gfx.drawString(String.format("%d:%02d:%02d", hoursToday, minutesToday, secondsToday), 320, 295);
		}

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

	private void checkData(){
		try{
			FileReader fr = new FileReader("data.txt");
			BufferedReader br = new BufferedReader(fr);
			fileExists = true;
			//need to check if last line is today, rewrite, otherwise append
			System.out.println(br.readLine());
			hoursToday = 0;
			minutesToday = 0;
			secondsToday = 0;
		}
		catch(IOException e){
			fileExists = false;
			hoursToday = 0;
			minutesToday = 0;
			secondsToday = 0;
		}
	}

	private void writeToFile(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		try{
			FileWriter write = new FileWriter("data.txt", false);
			PrintWriter printLine = new PrintWriter(write);
			printLine.printf("%s " + "%d:%02d:%02d", dateFormat.format(date), hoursToday, minutesToday, secondsToday);
			printLine.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// if button clicked, toggle start timer
		if(e.getX() >= 122 && e.getX() <= 358 && e.getY() >= 102 && e.getY() <= 198){
			timerStarted = !timerStarted;
			if(timerStarted){
				start = LocalTime.now();
			}
			else{
				secondsToday += seconds;
				minutesToday += minutes + secondsToday/60;
				hoursToday += hours + minutesToday/60;

				secondsToday = secondsToday % 60;
				minutesToday = minutesToday % 60;

				//save time to file
			}
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
			writeToFile();
			System.exit(0);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

}
