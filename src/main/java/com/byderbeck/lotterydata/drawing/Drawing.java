package com.byderbeck.lotterydata.drawing;

/**
 * Created by lyman on 10/12/2016.
 */

import org.joda.time.LocalDate;

public class Drawing implements Comparable<Drawing> {

	LocalDate drawingDate = new LocalDate();
	int jackpot = 0;
	int jackpotCashValue = 0;
	int match5Prize = 0;
	int match4Prize = 0;
	int match3Prize = 0;

	public Drawing() {
		super();
	}

	public Drawing(LocalDate drawingDate) {
		super();
		this.drawingDate = drawingDate;
		this.jackpot = 0;
		this.match5Prize = 0;
		this.match4Prize = 0;
		this.match3Prize = 0;
	}

	public Drawing(LocalDate drawingDate, int jackpot, int jackpotCashValue, int match5Prize, int match4Prize,
				   int match3Prize) {
		super();
		this.drawingDate = drawingDate;
		this.jackpot = jackpot;
		this.jackpotCashValue = jackpotCashValue;
		this.match5Prize = match5Prize;
		this.match4Prize = match4Prize;
		this.match3Prize = match3Prize;
	}

	public LocalDate getDrawingDate() {
		return drawingDate;
	}

	public void setDrawingDate(LocalDate drawingDate) {
		this.drawingDate = drawingDate;
	}

	public int getJackpot() {
		return jackpot;
	}

	public void setJackpot(int jackpot) {
		this.jackpot = jackpot;
	}

	public int getJackpotCashValue() {
		return jackpotCashValue;
	}

	public void setJackpotCashValue(int jackpotCashValue) {
		this.jackpotCashValue = jackpotCashValue;
	}

	public int getMatch5Prize() {
		return match5Prize;
	}

	public void setMatch5Prize(int match5Prize) {
		this.match5Prize = match5Prize;
	}

	public int getMatch4Prize() {
		return match4Prize;
	}

	public void setMatch4Prize(int match4Prize) {
		this.match4Prize = match4Prize;
	}

	public int getMatch3Prize() {
		return match3Prize;
	}

	public void setMatch3Prize(int match3Prize) {
		this.match3Prize = match3Prize;
	}

	@Override
	public String toString() {
		return "Drawing [drawingDate=" + drawingDate + ", jackpot=" + jackpot + ", match5Prize=" + match5Prize
				+ ", match4Prize=" + match4Prize + ", match3Prize=" + match3Prize + "]";
	}

	@Override
	public int hashCode() {
		return drawingDate.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drawing other = (Drawing) obj;
		if (drawingDate == null) {
			if (other.drawingDate != null)
				return false;
		} else if (!drawingDate.equals(other.drawingDate))
			return false;
		if (match5Prize != other.match5Prize)
			return false;
		if (match4Prize != other.match4Prize)
			return false;
		if (jackpot != other.jackpot)
			return false;
		if (match3Prize != other.match3Prize)
			return false;
		return true;
	}

	public int compareTo(Drawing o) {
		return this.drawingDate.compareTo(o.drawingDate);
	}
}