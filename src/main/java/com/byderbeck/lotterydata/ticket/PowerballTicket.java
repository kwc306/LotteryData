package com.byderbeck.lotterydata.ticket;

import com.byderbeck.lotterydata.drawing.PowerballDrawing;
import com.byderbeck.lotterydata.play.PowerballPlay;

import org.joda.time.LocalDate;

import java.util.TreeSet;

/**
 * Created by lyman on 5/11/2017.
 */

public class PowerballTicket extends Ticket implements Comparable<PowerballTicket> {

	TreeSet<PowerballPlay> powerballPlays;
	boolean isPowerPlayTicket = false;

	public PowerballTicket() {
		super();
	}

	public PowerballTicket(LocalDate drawingDate, boolean isPowerPlayTicket, TreeSet<PowerballPlay> powerballPlays) {

		super(drawingDate, "0");
		this.powerballPlays = powerballPlays;
		this.isPowerPlayTicket = isPowerPlayTicket;
	}

	public PowerballTicket(LocalDate drawingDate, String id, boolean isPowerPlayTicket,
						   TreeSet<PowerballPlay> powerballPlays) {

		super(drawingDate, id);
		this.powerballPlays = powerballPlays;
		this.isPowerPlayTicket = isPowerPlayTicket;
	}

	public TreeSet<PowerballPlay> getPowerballPlays() {
		return powerballPlays;
	}

	public void setPowerballPlays(TreeSet<PowerballPlay> powerballPlays) {
		this.powerballPlays = powerballPlays;
	}

	public boolean isPowerPlayTicket() {
		return isPowerPlayTicket;
	}

	public void setPowerPlayTicket(boolean isPowerPlayTicket) {
		this.isPowerPlayTicket = isPowerPlayTicket;
	}

	public void computePrize(PowerballDrawing powerballDrawing) {

		for (PowerballPlay powerballPlay: powerballPlays) {
			powerballPlay.compare(powerballDrawing.getPowerballPlay());
			powerballPlay.setPrize(powerballDrawing.computePrize(powerballPlay, this.isPowerPlayTicket));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((drawingDate == null) ? 0 : drawingDate.hashCode());
		result = prime * result + ((powerballPlays == null) ? 0 : powerballPlays.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerballTicket other = (PowerballTicket) obj;
		if (drawingDate == null) {
			if (other.drawingDate != null)
				return false;
		} else if (!drawingDate.equals(other.drawingDate))
			return false;
		if (powerballPlays == null) {
			if (other.powerballPlays != null)
				return false;
		} else if (!powerballPlays.equals(other.powerballPlays))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PowerballTicket [drawingDate=" + drawingDate + ", id=" + id + ", PowerPlay=" + isPowerPlayTicket +
				", powerballPlays=" + powerballPlays + "]";
	}

	@Override
	public int compareTo(PowerballTicket o) {
		return this.id.compareTo(o.id);
	}

}
