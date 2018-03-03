package com.byderbeck.lotterydata.drawing;

/**
 * Created by lyman on 10/12/2016.
 */

import com.byderbeck.lotterydata.play.LottoPlay;
import com.byderbeck.lotterydata.ticket.LottoTicket;

import org.joda.time.LocalDate;

import java.util.TreeSet;

public class LottoDrawing extends Drawing {

	private static final String TAG = "***" + LottoDrawing.class.getSimpleName();
	private TreeSet<LottoTicket> lottoTickets = null;
	private LottoPlay lottoPlay = null;

	public LottoDrawing() {
		super();
	}

	public LottoDrawing(LocalDate drawingDate) {
		super(drawingDate);
		lottoPlay = new LottoPlay(' ', 0, 0, 0, 0, 0, 0);
	}

	public LottoDrawing(LocalDate drawingDate, int jackpot, int jackpotCashValue, int fiveOfSixPrize,
						int fourOfSixPrize, int threeOfSixPrize) {
		super(drawingDate, jackpot, jackpotCashValue, fiveOfSixPrize, fourOfSixPrize, threeOfSixPrize);
	}

	public LottoDrawing(TreeSet<LottoTicket> lottoTickets, LottoPlay lottoPlay) {
		super();
		this.lottoTickets = lottoTickets;
		this.lottoPlay = lottoPlay;
	}

	public LottoDrawing(LocalDate drawingDate, int one, int two, int three, int four, int five, int six, int jackpot,
						int jackpotCashValue, int fiveOfSixPrize, int fourOfSixPrize, int threeOfSixPrize) {
		super(drawingDate, jackpot, jackpotCashValue, fiveOfSixPrize, fourOfSixPrize, threeOfSixPrize);
		this.lottoPlay = new LottoPlay(' ', one, two, three, four, five, six);
		this.lottoTickets = null;
	}

	public LottoDrawing(LocalDate drawingDate, int jackpot, int jackpotCashValue, int fiveOfSixPrize,
						int fourOfSixPrize, int threeOfSixPrize, LottoPlay lottoPlay) {
		super(drawingDate, jackpot, jackpotCashValue, fiveOfSixPrize, fourOfSixPrize, threeOfSixPrize);
		this.lottoPlay = lottoPlay;
		this.lottoTickets = null;
	}

	public TreeSet<LottoTicket> getLottoTickets() {
		return lottoTickets;
	}

	public void setLottoTickets(TreeSet<LottoTicket> lottoTickets) {
		this.lottoTickets = lottoTickets;
	}

	public LottoPlay getLottoPlay() {
		return lottoPlay;
	}

	public void setLottoPlay(LottoPlay lottoPlay) {
		this.lottoPlay = lottoPlay;
	}

	public int computePrize(LottoPlay lottoPlay) {

		int prize = 0;

		switch (lottoPlay.getQuanityMatched()) {
			case 6:
				prize = super.getJackpot();
				break;
			case 5:
				prize = super.getMatch5Prize();
				break;
			case 4:
				prize = super.getMatch4Prize();
				break;
			case 3:
				prize = super.getMatch3Prize();
				break;
			default:
				prize = 0;
		}
		return prize;
	}

	public void addTicket(LottoTicket lottoTicket) {

		if (lottoTickets == null) {	// no tickets
			lottoTickets = new TreeSet<LottoTicket>();
		}
		lottoTickets.add(lottoTicket);
	}

	@Override
	public String toString() {
		if (lottoTickets == null) {
			return drawingDate.toString() + "  " + lottoPlay.toString() + "  No Tickets";
		} else {
			return drawingDate.toString() + "  " + lottoPlay.toString() + "  " + lottoTickets.toString();
		}
	}

}
