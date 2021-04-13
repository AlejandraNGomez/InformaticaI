/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Anderson
 */
public class CiclosDeReloj extends Thread {

	// Instance Variables 
	private boolean isDone;
	private double pauseDuration;

	// Constructor 
	public CiclosDeReloj(double pauseDuration) {
		// Set Default Values 
		this.isDone = false;

		// Validate Input 
		if (pauseDuration < 10 || pauseDuration > 1000) {
			pauseDuration = 500;
		} else {
			this.pauseDuration = pauseDuration;
		}
	}

	// Ends termination of the thread 
	public void terminate() {
		isDone = true;
	}

	public double getPauseDuration() {
		return this.pauseDuration;
	}

	public void run() {
		// Loop indefinitely, while possible 
		while (!isDone) {
			// Sleep the thread for the correct duration 
			try {
				Thread.sleep((long) pauseDuration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Make a move, then repeat the loop 
			Reloj.getReloj().toggleClock();
		}
	}

}