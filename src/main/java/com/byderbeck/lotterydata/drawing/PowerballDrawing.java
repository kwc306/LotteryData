package com.byderbeck.lotterydata.drawing;

/**
 * Created by lyman on 10/12/2016.
 */

import android.util.Log;

import com.byderbeck.lotterydata.play.PowerballPlay;
import com.byderbeck.lotterydata.ticket.PowerballTicket;

import org.joda.time.LocalDate;

import java.util.TreeSet;

public class PowerballDrawing extends Drawing {

	private static final String TAG = "***" + PowerballDrawing.class.getSimpleName();
	PowerballPlay powerballPlay = null;
	int powerPlay;
	TreeSet<PowerballTicket> powerballTickets = null;
	int match4PbPrize;
	int match3PbPrize;
	int match2PbPrize;
	int match1PbPrize;
	int matchPbPrize;

	public PowerballDrawing() {
		super();
	}

	public PowerballDrawing(LocalDate drawingDate) {
		super(drawingDate);
		this.powerballPlay = new PowerballPlay(' ', 0, 0, 0, 0, 0, 0);
		this.powerPlay = 1;
		this.powerballTickets = null;
		this.match4PbPrize = 0;
		this.match3PbPrize = 0;
		this.match2PbPrize = 0;
		this.match1PbPrize = 0;
		this.matchPbPrize = 0;
	}

	public PowerballDrawing(LocalDate drawingDate, int jackpot, int jackpotCashValue, int match5Prize, int match4Prize,
							int match3Prize, PowerballPlay powerballPlay, int match4PbPrize, int match3PbPrize,
							int match2PbPrize, int match1PbPrize, int matchPbPrize, int powerPlay) {
		super(drawingDate, jackpot, jackpotCashValue, match5Prize, match4Prize, match3Prize);
		this.powerballPlay = powerballPlay;
		this.powerballTickets = null;
		this.match4PbPrize = match4PbPrize;
		this.match3PbPrize = match3PbPrize;
		this.match2PbPrize = match2PbPrize;
		this.match1PbPrize = match1PbPrize;
		this.matchPbPrize = matchPbPrize;
		this.powerPlay = powerPlay;
	}

	public PowerballPlay getPowerballPlay() {
		return powerballPlay;
	}

	public void setPowerballPlay(PowerballPlay powerballPlay) {
		this.powerballPlay = powerballPlay;
	}

	public int getPowerPlay() {
		return powerPlay;
	}

	public void setPowerPlay(int powerPlay) {
		this.powerPlay = powerPlay;
	}

	public TreeSet<PowerballTicket> getPowerballTickets() {
		return powerballTickets;
	}

	public void setPowerballTickets(TreeSet<PowerballTicket> powerballTickets) {
		this.powerballTickets = powerballTickets;
	}

	public int getMatch4PbPrize() {
		return match4PbPrize;
	}

	public void setMatch4PbPrize(int match4PbPrize) {
		this.match4PbPrize = match4PbPrize;
	}

	public int getMatch3PbPrize() {
		return match3PbPrize;
	}

	public void setMatch3PbPrize(int match3PbPrize) {
		this.match3PbPrize = match3PbPrize;
	}

	public int getMatch2PbPrize() {
		return match2PbPrize;
	}

	public void setMatch2PbPrize(int match2PbPrize) {
		this.match2PbPrize = match2PbPrize;
	}

	public int getMatch1PbPrize() {
		return match1PbPrize;
	}

	public void setMatch1PbPrize(int match1PbPrize) {
		this.match1PbPrize = match1PbPrize;
	}

	public int getMatchPbPrize() {
		return matchPbPrize;
	}

	public void setMatchPbPrize(int matchPbPrize) {
		this.matchPbPrize = matchPbPrize;
	}

	public String displayPlay() {

		String play = "";

		if (powerballPlay != null) {
			play = powerballPlay.displayString();
			play = play + String.format("PP %02d", powerPlay);
		} else {
			Log.d(TAG, "powerballPlay is null");
		}
		return play;
	}

	public void addTicket(PowerballTicket powerballTicket) {

		if (powerballTickets == null) {	// no tickets
			powerballTickets = new TreeSet<PowerballTicket>();
		}
		powerballTickets.add(powerballTicket);
	}

	@Override
	public String toString() {
		PowerballPlay powerballPlay = this.powerballPlay;
		int[] numbers = powerballPlay.getNumbers();
		String out = String.format("%02d %02d %02d %02d %02d %02d  %02d", numbers[0], numbers[1], numbers[2],
				numbers[3], numbers[4], powerballPlay.getPowerBall());
		return drawingDate.toString() + "  " + out;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + match1PbPrize;
		result = prime * result + match2PbPrize;
		result = prime * result + match3PbPrize;
		result = prime * result + match4PbPrize;
		result = prime * result + matchPbPrize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerballDrawing other = (PowerballDrawing) obj;
		if (match1PbPrize != other.match1PbPrize)
			return false;
		if (match2PbPrize != other.match2PbPrize)
			return false;
		if (match3PbPrize != other.match3PbPrize)
			return false;
		if (match4PbPrize != other.match4PbPrize)
			return false;
		if (matchPbPrize != other.matchPbPrize)
			return false;
		return true;
	}

	public int computePrize(PowerballPlay powerballPlay, boolean hasPowerPlay) {

		int prize = 0;
		int powerPlay = 0;

		if (hasPowerPlay) {
			powerPlay = getPowerPlay();
		} else {
			powerPlay = 1;
		}
		switch (powerballPlay.getQuanityMatched()) {
			case 6:
				prize = super.getJackpot();
				break;
			case 5:
				prize = this.getMatch5Prize() * powerPlay;
				break;
			case 4:
				if (this.powerballPlay.getPowerBall() == powerballPlay.getPowerBall()) {
					prize = this.getMatch3PbPrize() * powerPlay;
				} else {
					prize = this.getMatch4Prize() * powerPlay;
				}
				break;
			case 3:
				if (this.powerballPlay.getPowerBall() == powerballPlay.getPowerBall()) {
					prize = this.getMatch2PbPrize() * powerPlay;
				} else {
					prize = this.getMatch3Prize() * powerPlay;
				}
				break;
			case 2:
				if (this.powerballPlay.getPowerBall() == powerballPlay.getPowerBall()) {
					prize = this.getMatch1PbPrize() * powerPlay;
				} else {
					prize = 0;
				}
				break;
			case 1:
				if (this.powerballPlay.getPowerBall() == powerballPlay.getPowerBall()) {
					prize = this.getMatchPbPrize() * powerPlay;
				} else {
					prize = 0;
				}
				break;
			case 0:
				prize = 0;
		}
		return prize;
	}

}
