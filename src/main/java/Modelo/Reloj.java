package Modelo;

import java.util.ArrayList;
import java.util.List;

import Controlador.ClockObserver;

// Singleton clock 
public class Reloj {
	// false -> clock low; true -> clock high
	private boolean status;

	// If true, clock status cannot change
	private boolean isHalted;
	
	// Used for spacing in the event log
	private boolean firstToggleSpacing;
	
	// Since clock is observable, it needs to maintain a list of observers
	private List<ClockObserver> observers;
	
	// Singleton clock must maintain a static reference to itself
	private static Reloj reloj;

	public Reloj() {
		this.status = false;
		this.isHalted = false;
		this.firstToggleSpacing = false;
		observers = new ArrayList<ClockObserver>();
	}
	
	// Returns false if clock is low, true if clock is high
	public boolean getStatus() {
		// First call getClock to ensure that a clock has been created and stored in the static reference field
		getReloj();
		
		// Then, return singleton's status
		return this.status;
	}

	// Provides ability to halt or unhalt the clock
	public void setIsHalted(boolean newVal) {
		this.isHalted = newVal;
	}

	public void toggleClock() {
		// Validate that a clock exists
		getReloj();

		// If halted, do nothing
		if (this.isHalted) {
			return;
		}
		
		// Invert status
		this.status = !this.status;

		// Add spacing, except for the first clock toggle
		if (firstToggleSpacing) {
                    
		} else {
			firstToggleSpacing = true;
		}

		

		// Tell observers that the clock has changed
		notifyObservers();
		return;
	}

	// Getter method for singleton clock
	public static Reloj getReloj() {
		// If we don't have a clock, make a new one; else, return the current clock
		if (reloj == null) {
			reloj = new Reloj();
			return reloj;
		} else {
			return reloj;
		}
	}

	// Implement observable pattern
	public void addObserver(ClockObserver o) {
		if (o == null) {
			return;
		}
		this.observers.add(o);
	}

	public void removeObserver(ClockObserver o) {
		if (o == null) {
			return;
		}
		this.observers.remove(o);
	}

	public void notifyObservers() {
		for (ClockObserver o : observers) {
			o.clockChange();
		}
	}
}
