package com.byderbeck.lotterydata;

import android.util.Log;

import com.byderbeck.lotterydata.dao.DrawingDAOImpl;
import com.byderbeck.lotterydata.dao.TicketDAOImpl;
import com.byderbeck.lotterydata.drawing.MegaMillionsDrawing;
import com.byderbeck.lotterydata.ticket.MegaMillionsTicket;

import org.joda.time.LocalDate;

import java.util.Map;
import java.util.Set;

/**
 * Created by lyman on 12/24/2016.
 */

public class MegaMillionsData {

	private static final String TAG = "***" + MegaMillionsData.class.getSimpleName();
	private DrawingDAOImpl drawingDAOImpl = new DrawingDAOImpl();
	private TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();
	private Map<LocalDate, MegaMillionsDrawing> megaMillionsDrawings;
	private Object[] megaMillionsDrawingDates;
	private Set<LocalDate> megaMillionsDrawingDatesSorted;
	private int megaMillionsIndex;
	private String direction = "";
	private MegaMillionsTicket megaMillionsTicket;

	private MegaMillionsData() {
		megaMillionsDrawings = drawingDAOImpl.getMegaMillionsDrawings();
		megaMillionsDrawings = ticketDAOImpl.getMegaMillionsTickets(megaMillionsDrawings);
		if (megaMillionsDrawings.isEmpty()) {
			megaMillionsDrawingDatesSorted = null;
			megaMillionsDrawingDates = null;
			megaMillionsIndex = 0;
		} else {
			megaMillionsDrawingDatesSorted = megaMillionsDrawings.keySet();
			megaMillionsDrawingDates = megaMillionsDrawingDatesSorted.toArray();
			megaMillionsIndex = megaMillionsDrawingDates.length - 1;
		}
	}

	private static class SingletonHolder {
		private static final MegaMillionsData INSTANCE = new MegaMillionsData();
	}

	public static MegaMillionsData getInstance() {
		return MegaMillionsData.SingletonHolder.INSTANCE;
	}

	public MegaMillionsTicket getMegaMillionsTicket() {
		return megaMillionsTicket;
	}

	public void setMegaMillionsTicket(MegaMillionsTicket megaMillionsTicket) {
		this.megaMillionsTicket = megaMillionsTicket;
	}

	public boolean hasNext() {

		if (megaMillionsIndex < megaMillionsDrawings.size() - 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasPrevious() {
		if (megaMillionsIndex > 0) {
			return true;
		} else {
			return false;
		}
	}

	public MegaMillionsDrawing next() {

		MegaMillionsDrawing megaMillionsDrawing = null;

		if (megaMillionsDrawingDates != null) {
			if (hasNext()) {
				megaMillionsDrawing = megaMillionsDrawings.get((LocalDate) megaMillionsDrawingDates[++megaMillionsIndex]);
				direction = "next";
			} else {
				megaMillionsDrawing = megaMillionsDrawings.get((LocalDate) megaMillionsDrawingDates[megaMillionsIndex]);
			}
		} else {
			megaMillionsDrawing = new MegaMillionsDrawing();
		}
		return megaMillionsDrawing;
	}

	public MegaMillionsDrawing previous() {

		MegaMillionsDrawing megaMillionsDrawing = null;

		if (megaMillionsDrawingDates != null) {
			if (hasPrevious()) {
				megaMillionsDrawing = megaMillionsDrawings.get((LocalDate) megaMillionsDrawingDates[--megaMillionsIndex]);
				direction = "previous";
			} else {
				megaMillionsDrawing = megaMillionsDrawings.get((LocalDate) megaMillionsDrawingDates[megaMillionsIndex]);
			}
		} else {
			megaMillionsDrawing = new MegaMillionsDrawing();
		}
		return megaMillionsDrawing;
	}

	public MegaMillionsDrawing current() {

		MegaMillionsDrawing megaMillionsDrawing = null;

		if (megaMillionsDrawingDates != null) {
			megaMillionsDrawing = megaMillionsDrawings.get((LocalDate) megaMillionsDrawingDates[megaMillionsIndex]);
		}
		return megaMillionsDrawing;
	}

	public String getDirection() {
		return direction;
	}

	public int size() { return megaMillionsDrawings.size(); }

	public Map<LocalDate, MegaMillionsDrawing> getMegaMillionsDrawings() {

		return megaMillionsDrawings;
	}

	public MegaMillionsDrawing getMegaMillionsDrawing(LocalDate drawingDate) {

		MegaMillionsDrawing megaMillionsDrawing = megaMillionsDrawings.get(drawingDate);
		if (megaMillionsDrawing == null) {
			Log.i(TAG, "megaMillionsDrawing=null");
			megaMillionsDrawing = new MegaMillionsDrawing(drawingDate);
			Log.i(TAG, megaMillionsDrawing.toString());
			megaMillionsDrawings.put(drawingDate, megaMillionsDrawing);
		}
		Log.i(TAG, megaMillionsDrawing.toString());
		return megaMillionsDrawing;
	}
}
