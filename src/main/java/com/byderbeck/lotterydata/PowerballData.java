package com.byderbeck.lotterydata;

import com.byderbeck.lotterydata.dao.DrawingDAOImpl;
import com.byderbeck.lotterydata.dao.TicketDAOImpl;
import com.byderbeck.lotterydata.drawing.PowerballDrawing;
import com.byderbeck.lotterydata.ticket.PowerballTicket;

import org.joda.time.LocalDate;

import java.util.Map;
import java.util.Set;

/**
 * Created by lyman on 12/24/2016.
 */

public class PowerballData {

	private DrawingDAOImpl drawingDAOImpl = new DrawingDAOImpl();
	private TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();
	private Map<LocalDate, PowerballDrawing> powerballDrawings;
	private Object[] powerballDrawingDates;
	private Set<LocalDate> powerballDrawingDatesSorted;
	private int powerballIndex;
	private String direction = "";
	private PowerballTicket powerballTicket;

	private PowerballData() {
		powerballDrawings = drawingDAOImpl.getPowerballDrawings();
		powerballDrawings = ticketDAOImpl.getPowerballTickets(powerballDrawings);
		if (powerballDrawings.isEmpty()) {
			powerballDrawingDatesSorted = null;
			powerballDrawingDates = null;
			powerballIndex = 0;
		} else {
			powerballDrawingDatesSorted = powerballDrawings.keySet();
			powerballDrawingDates = powerballDrawingDatesSorted.toArray();
			powerballIndex = powerballDrawingDates.length - 1;
		}
	}

	private static class SingletonHolder {
		private static final PowerballData INSTANCE = new PowerballData();
	}

	public static PowerballData getInstance() {
		return PowerballData.SingletonHolder.INSTANCE;
	}

	public PowerballTicket getPowerballTicket() {
		return powerballTicket;
	}

	public void setPowerballTicket(PowerballTicket powerballTicket) {
		this.powerballTicket = powerballTicket;
	}

	public boolean hasNext() {

		if (powerballIndex < powerballDrawings.size() - 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasPrevious() {
		if (powerballIndex > 0) {
			return true;
		} else {
			return false;
		}
	}

	public PowerballDrawing next() {

		PowerballDrawing powerballDrawing = null;

		if (powerballDrawingDates != null) {
			if (hasNext()) {
				powerballDrawing = powerballDrawings.get((LocalDate) powerballDrawingDates[++powerballIndex]);
				direction = "next";
			} else {
				powerballDrawing = powerballDrawings.get((LocalDate) powerballDrawingDates[powerballIndex]);
			}
		} else {
			powerballDrawing = new PowerballDrawing();
		}
		return powerballDrawing;
	}

	public PowerballDrawing previous() {

		PowerballDrawing powerballDrawing = null;

		if (powerballDrawingDates != null) {
			if (hasPrevious()) {
				powerballDrawing = powerballDrawings.get((LocalDate) powerballDrawingDates[--powerballIndex]);
				direction = "previous";
			} else {
				powerballDrawing = powerballDrawings.get((LocalDate) powerballDrawingDates[powerballIndex]);
			}
		} else {
			powerballDrawing = new PowerballDrawing();
		}
		return powerballDrawing;
	}

	public PowerballDrawing current() {

		PowerballDrawing powerballDrawing = null;

		if (powerballDrawingDates != null) {
			powerballDrawing = powerballDrawings.get((LocalDate) powerballDrawingDates[powerballIndex]);
		}
		return powerballDrawing;
	}

	public String getDirection() {
		return direction;
	}

	public int size() { return powerballDrawings.size(); }

	public Map<LocalDate, PowerballDrawing> getPowerballDrawings() { return powerballDrawings; }

	public PowerballDrawing getPowerballDrawing(LocalDate drawingDate) {

		PowerballDrawing powerballDrawing = powerballDrawings.get(drawingDate);
		if (powerballDrawing == null) {
			powerballDrawing = new PowerballDrawing();
			powerballDrawings.put(drawingDate, powerballDrawing);
		}
		return powerballDrawing;
	}

}
