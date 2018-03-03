package com.byderbeck.lotterydata;

import com.byderbeck.lotterydata.dao.DrawingDAOImpl;
import com.byderbeck.lotterydata.dao.TicketDAOImpl;
import com.byderbeck.lotterydata.drawing.LottoDrawing;
import com.byderbeck.lotterydata.ticket.LottoTicket;

import org.joda.time.LocalDate;

import java.util.Map;
import java.util.Set;

/**
 * Created by lyman on 12/5/2016.
 */

public class LottoData {

	private static final String TAG = "***" + LottoData.class.getSimpleName();
	private DrawingDAOImpl drawingDAOImpl = new DrawingDAOImpl();
	private TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();
	private Map<LocalDate, LottoDrawing> lottoDrawings;
	private Object[] lottoDrawingDates;
	private Set<LocalDate> lottoDrawingDatesSorted;
	private int lottoIndex;
	private String direction = "";
	private LottoTicket lottoTicket;

	private LottoData() {
		lottoDrawings = drawingDAOImpl.getLottoDrawings();
		lottoDrawings = ticketDAOImpl.getLottoTickets(lottoDrawings);
		if (lottoDrawings.isEmpty()) {
			lottoDrawingDatesSorted = null;
			lottoDrawingDates = null;
			lottoIndex = 0;
//			Log.i(TAG, "lottoDrawings is empty");  // ********************
		} else {
			lottoDrawingDatesSorted = lottoDrawings.keySet();
			lottoDrawingDates = lottoDrawingDatesSorted.toArray();
			lottoIndex = lottoDrawingDates.length - 1;
//			Log.i(TAG, "lottoDrawings.size():" + lottoDrawings.size());  // ********************
		}
	}

	private static class SingletonHolder {
		private static final LottoData INSTANCE = new LottoData();
	}

	public LottoTicket getLottoTicket() {
		return lottoTicket;
	}

	public void setLottoTicket(LottoTicket lottoTicket) {
		this.lottoTicket = lottoTicket;
	}

	public static LottoData getInstance() {
		return LottoData.SingletonHolder.INSTANCE;
	}

	public boolean hasNext() {

		if (lottoIndex < lottoDrawings.size() - 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasPrevious() {
		if (lottoIndex > 0) {
			return true;
		} else {
			return false;
		}
	}

	public LottoDrawing next() {

		LottoDrawing lottoDrawing = null;

		if (lottoDrawingDates != null) {
			if (hasNext()) {
				lottoDrawing = lottoDrawings.get((LocalDate) lottoDrawingDates[++lottoIndex]);
				direction = "next";
			} else {
				lottoDrawing = lottoDrawings.get((LocalDate) lottoDrawingDates[lottoIndex]);
			}
		} else {
			lottoDrawing = new LottoDrawing();
		}
		return lottoDrawing;
	}

	public LottoDrawing previous() {

		LottoDrawing lottoDrawing = null;

		if (lottoDrawingDates != null) {
			if (hasPrevious()) {
				lottoDrawing = lottoDrawings.get((LocalDate) lottoDrawingDates[--lottoIndex]);
				direction = "previous";
			} else {
				lottoDrawing = lottoDrawings.get((LocalDate) lottoDrawingDates[lottoIndex]);
			}
		} else {
			lottoDrawing = new LottoDrawing();
		}
		return lottoDrawing;
	}

	public LottoDrawing current() {

		LottoDrawing lottoDrawing = null;

		if (lottoDrawingDates != null) {
			lottoDrawing = lottoDrawings.get((LocalDate) lottoDrawingDates[lottoIndex]);
		}
//		Log.i(TAG, "lottoIndex:" + lottoIndex);  // ***********************
		return lottoDrawing;
	}

	public String getDirection() {
		return direction;
	}

	public int size() { return lottoDrawings.size(); }

	public Map<LocalDate, LottoDrawing> getLottoDrawings() {

		return lottoDrawings;
	}

	public LottoDrawing getLottoDrawing(LocalDate drawingDate) {

		LottoDrawing lottoDrawing = lottoDrawings.get(drawingDate);
		if (lottoDrawing == null) {
			lottoDrawing = new LottoDrawing();
			lottoDrawings.put(drawingDate, lottoDrawing);
		}
		return lottoDrawing;
	}

}
