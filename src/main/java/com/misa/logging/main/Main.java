package com.misa.logging.main;

import java.util.Timer;

public class Main {

	public static void main(String[] args){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Task(), 0, 86400000);
		System.out.println("Success!");
	}
}
