package com.byderbeck.lotterydata.drawing;

/**
 * Created by lyman on 10/12/2016.
 */

import android.util.Log;

import com.byderbeck.lotterydata.play.MegaMillionsPlay;
import com.byderbeck.lotterydata.ticket.MegaMillionsTicket;

import org.joda.time.LocalDate;

import java.util.TreeSet;

public class MegaMillionsDrawing extends Drawing {

	private static final String TAG = "***" + MegaMillionsDrawing.class.getSimpleName();
	MegaMillionsPlay megaMillionsPlay = null;
	int megaPlier;
	TreeSet<MegaMillionsTicket> megaMillionsTickets = null;
	int match4MMPrize;
	int match3MMPrize;
	int match2MMPrize;
	int match1MMPrize;
	int matchMMPrize;

	public MegaMillionsDrawing() {
		super();

	}

	public MegaMillionsDrawing(LocalDate drawingDate) {
		super(drawingDate);
		this.megaMillionsPlay = new MegaMillionsPlay(' ', 0, 0, 0, 0, 0, 0);
		this.megaPlier = 1;
		this.megaMillionsTickets = null;
		this.match4MMPrize = 0;
		this.match3MMPrize = 0;
		this.match2MMPrize = 0;
		this.match1MMPrize = 0;
		this.matchMMPrize = 0;
	}

	public MegaMillionsDrawing(LocalDate drawingDate, int jackpot, int jackpotCashValue, int match5Prize,
							   int match4Prize, int match3Prize, MegaMillionsPlay megaMillionsPlay, int match4MMPrize,
							   int match3MMPrize, int match2MMPrize, int match1MMPrize, int matchMMPrize,
							   int megaPlier) {
		super(drawingDate, jackpot, jackpotCashValue, match5Prize, match4Prize, match3Prize);
		this.megaMillionsPlay = megaMillionsPlay;
		this.megaMillionsTickets = null;
		this.match4MMPrize = match4MMPrize;
		this.match3MMPrize = match3MMPrize;
		this.match2MMPrize = match2MMPrize;
		this.match1MMPrize = match1MMPrize;
		this.matchMMPrize = matchMMPrize;
		this.megaPlier = megaPlier;
	}

	public MegaMillionsPlay getMegaMillionsPlay() {
		return megaMillionsPlay;
	}

	public void setMegaMillionsPlay(MegaMillionsPlay megaMillionsPlay) {
		this.megaMillionsPlay = megaMillionsPlay;
	}

	public int getMegaPlier() {
		return megaPlier;
	}

	public void setMegaPlier(int megaPlier) {
		this.megaPlier = megaPlier;
	}

	public TreeSet<MegaMillionsTicket> getMegaMillionsTickets() {
		return megaMillionsTickets;
	}

	public void setMegaMillionsTickets(TreeSet<MegaMillionsTicket> megaMillionsTickets) {
		this.megaMillionsTickets = megaMillionsTickets;
	}

	public int getMatch4MMPrize() {
		return match4MMPrize;
	}

	public void setMatch4MMPrize(int match4MMPrize) {
		this.match4MMPrize = match4MMPrize;
	}

	public int getMatch3MMPrize() {
		return match3MMPrize;
	}

	public void setMatch3MMPrize(int match3MMPrize) {
		this.match3MMPrize = match3MMPrize;
	}

	public int getMatch2MMPrize() {
		return match2MMPrize;
	}

	public void setMatch2MMPrize(int match2MMPrize) {
		this.match2MMPrize = match2MMPrize;
	}

	public int getMatch1MMPrize() {
		return match1MMPrize;
	}

	public void setMatch1MMPrize(int match1MMPrize) {
		this.match1MMPrize = match1MMPrize;
	}

	public int getMatchMMPrize() {
		return matchMMPrize;
	}

	public void setMatchMMPrize(int matchMMPrize) {
		this.matchMMPrize = matchMMPrize;
	}

	public String displayPlay() {

		String play = "";

		if (megaMillionsPlay != null) {
			play = megaMillionsPlay.displayString();
			play = play + String.format(" MP %02d", megaPlier);
		} else {
			Log.d(TAG, "megaMillionsPlay is null");
		}
		return play;
	}

	public void addTicket(MegaMillionsTicket megaMillionsTicket) {

		if (megaMillionsTickets == null) {	// no tickets
			megaMillionsTickets = new TreeSet<MegaMillionsTicket>();
		}
		megaMillionsTickets.add(megaMillionsTicket);
	}

	public int computePrize(MegaMillionsPlay megaMillionsPlay, boolean hasMegaplier) {

		int prize = 0;
		int megaplier = 0;

		if (hasMegaplier) {
			megaplier = getMegaPlier();
		} else {
			megaplier = 1;
		}
		switch (megaMillionsPlay.getQuanityMatched()) {
			case 6:
				prize = super.getJackpot();
				break;
			case 5:
				prize = this.getMatch5Prize() * megaplier;
				break;
			case 4:
				if (this.megaMillionsPlay.getMegaBall() == megaMillionsPlay.getMegaBall()) {
					prize = this.getMatch3MMPrize() * megaplier;
				} else {
					prize = this.getMatch4Prize() * megaplier;
				}
				break;
			case 3:
				if (this.megaMillionsPlay.getMegaBall() == megaMillionsPlay.getMegaBall()) {
					prize = this.getMatch2MMPrize() * megaplier;
				} else {
					prize = this.getMatch3Prize() * megaplier;
				}
				break;
			case 2:
				if (this.megaMillionsPlay.getMegaBall() == megaMillionsPlay.getMegaBall()) {
					prize = this.getMatch1MMPrize() * megaplier;
				} else {
					prize = 0;
				}
				break;
			case 1:
				if (this.megaMillionsPlay.getMegaBall() == megaMillionsPlay.getMegaBall()) {
					prize = this.getMatchMMPrize() * megaplier;
				} else {
					prize = 0;
				}
				break;
			case 0:
				prize = 0;
		}
		return prize;
	}

	@Override
	public String toString() {
		return "MegaMillionsDrawing{" +
				"megaMillionsPlay=" + megaMillionsPlay +
				", megaPlier=" + megaPlier +
				", megaMillionsTickets=" + megaMillionsTickets +
				", match4MMPrize=" + match4MMPrize +
				", match3MMPrize=" + match3MMPrize +
				", match2MMPrize=" + match2MMPrize +
				", match1MMPrize=" + match1MMPrize +
				", matchMMPrize=" + matchMMPrize +
				'}';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + match1MMPrize;
		result = prime * result + match2MMPrize;
		result = prime * result + match3MMPrize;
		result = prime * result + match4MMPrize;
		result = prime * result + matchMMPrize;
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
		MegaMillionsDrawing other = (MegaMillionsDrawing) obj;
		if (match1MMPrize != other.match1MMPrize)
			return false;
		if (match2MMPrize != other.match2MMPrize)
			return false;
		if (match3MMPrize != other.match3MMPrize)
			return false;
		if (match4MMPrize != other.match4MMPrize)
			return false;
		if (matchMMPrize != other.matchMMPrize)
			return false;
		return true;
	}
}
