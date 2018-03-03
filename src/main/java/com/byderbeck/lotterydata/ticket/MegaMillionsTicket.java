package com.byderbeck.lotterydata.ticket;

import com.byderbeck.lotterydata.drawing.MegaMillionsDrawing;
import com.byderbeck.lotterydata.play.MegaMillionsPlay;

import org.joda.time.LocalDate;

import java.util.TreeSet;

/**
 * Created by lyman on 5/11/2017.
 */

public class MegaMillionsTicket extends Ticket implements Comparable<MegaMillionsTicket> {

	TreeSet<MegaMillionsPlay> megaMillionsPlays;
	boolean isMegaPlierTicket = false;

	public MegaMillionsTicket() {
		super();
	}

	public MegaMillionsTicket(LocalDate drawingDate, TreeSet<MegaMillionsPlay> megaMillionsPlays) {
		super(drawingDate, "0");
		this.megaMillionsPlays = megaMillionsPlays;
	}

	public MegaMillionsTicket(LocalDate drawingDate, String ticketId, TreeSet<MegaMillionsPlay> megaMillionsPlays) {
		super(drawingDate, ticketId);
		this.megaMillionsPlays = megaMillionsPlays;
	}

	public MegaMillionsTicket(LocalDate drawingDate, String ticketId, boolean isMegaPlierTicket,
							  TreeSet<MegaMillionsPlay> megaMillionsPlays) {
		super(drawingDate, ticketId);
		this.isMegaPlierTicket = isMegaPlierTicket;
		this.megaMillionsPlays = megaMillionsPlays;
	}

	public TreeSet<MegaMillionsPlay> getMegaMillionsPlays() {
		return megaMillionsPlays;
	}

	public void setMegaMillionsPlays(TreeSet<MegaMillionsPlay> megaMillionsPlays) {
		this.megaMillionsPlays = megaMillionsPlays;
	}

	public boolean isMegaPlierTicket() {
		return isMegaPlierTicket;
	}

	public void setMegaPlierTicket(boolean isMegaPlierTicket) {
		this.isMegaPlierTicket = isMegaPlierTicket;
	}

	public void computePrize(MegaMillionsDrawing megaMillionsDrawing) {

		for (MegaMillionsPlay megaMillionsPlay: megaMillionsPlays) {
			megaMillionsPlay.compare(megaMillionsDrawing.getMegaMillionsPlay());
			megaMillionsPlay.setPrize(megaMillionsDrawing.computePrize(megaMillionsPlay, isMegaPlierTicket));
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((drawingDate == null) ? 0 : drawingDate.hashCode());
		result = prime * result + ((megaMillionsPlays == null) ? 0 : megaMillionsPlays.hashCode());
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
		MegaMillionsTicket other = (MegaMillionsTicket) obj;
		if (drawingDate == null) {
			if (other.drawingDate != null)
				return false;
		} else if (!drawingDate.equals(other.drawingDate))
			return false;
		if (megaMillionsPlays == null) {
			if (other.megaMillionsPlays != null)
				return false;
		} else if (!megaMillionsPlays.equals(other.megaMillionsPlays))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MegaMillionsTicket [drawingDate=" + drawingDate + ", id=" + id + ", MegaPlier=" + isMegaPlierTicket +
				", megaMillionsPlays=" + megaMillionsPlays + "]";
	}

	@Override
	public int compareTo(MegaMillionsTicket o) {
		return this.id.compareTo(o.id);
	}
}
