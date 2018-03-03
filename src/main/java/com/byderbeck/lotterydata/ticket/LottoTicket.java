package com.byderbeck.lotterydata.ticket;

import com.byderbeck.lotterydata.drawing.LottoDrawing;
import com.byderbeck.lotterydata.play.LottoPlay;

import org.joda.time.LocalDate;

import java.util.TreeSet;

/**
 * Created by lyman on 5/11/2017.
 */

public class LottoTicket extends Ticket implements Comparable<LottoTicket> {

	TreeSet<LottoPlay> lottoPlays;

	public LottoTicket() {
	}

	public LottoTicket(LocalDate drawingDate, String ticketId) {
		super(drawingDate, ticketId);
	}

	public LottoTicket(LocalDate drawingDate, String ticketId, TreeSet<LottoPlay> lottoPlays) {
		super(drawingDate, ticketId);
		this.lottoPlays = lottoPlays;
	}

	public TreeSet<LottoPlay> getLottoPlays() {
		return lottoPlays;
	}

	public void setLottoPlays(TreeSet<LottoPlay> lottoPlays) {
		this.lottoPlays = lottoPlays;
	}

	public void computePrize(LottoDrawing lottoDrawing) {

		for (LottoPlay lottoPlay : lottoPlays) {
			lottoPlay.compare(lottoDrawing.getLottoPlay());
			lottoPlay.setPrize(lottoDrawing.computePrize(lottoPlay));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((drawingDate == null) ? 0 : drawingDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());

		return result;
	}

	@Override
	public int compareTo(LottoTicket o) {
		return this.id.compareTo(o.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LottoTicket other = (LottoTicket) obj;
		if (drawingDate == null) {
			if (other.drawingDate != null)
				return false;
		} else if (!drawingDate.equals(other.drawingDate))
			return false;
		if (lottoPlays == null) {
			if (other.lottoPlays != null)
				return false;
		} else if (!lottoPlays.equals(other.lottoPlays))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "LottoTicket [drawingDate=" + drawingDate + ", id=" + id + " , lottoPlays=" + lottoPlays.toString() + "]";
	}
}