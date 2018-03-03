package com.byderbeck.lotterydata.dao;

import android.content.Context;

import com.byderbeck.lotterydata.Constants;
import com.byderbeck.lotterydata.Data;
import com.byderbeck.lotterydata.database.DatabaseHelper;
import com.byderbeck.lotterydata.drawing.LottoDrawing;
import com.byderbeck.lotterydata.drawing.MegaMillionsDrawing;
import com.byderbeck.lotterydata.drawing.PowerballDrawing;
import com.byderbeck.lotterydata.play.LottoPlay;
import com.byderbeck.lotterydata.play.MegaMillionsPlay;
import com.byderbeck.lotterydata.play.PowerballPlay;
import com.byderbeck.lotterydata.ticket.LottoTicket;
import com.byderbeck.lotterydata.ticket.MegaMillionsTicket;
import com.byderbeck.lotterydata.ticket.PowerballTicket;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Map;
import java.util.TreeSet;

public class TicketDAOImpl implements TicketDAO {

	private static final String TAG = "***" + TicketDAOImpl.class.getSimpleName();
	private Context appContext;

	@Override
	public Map<LocalDate, LottoDrawing> getLottoTickets(Map<LocalDate, LottoDrawing> lottoDrawings) {

		String csvData;
		TreeSet<LottoTicket> lottoTickets = null;
		TreeSet<LottoPlay> lottoPlays = null;
		LottoTicket lottoTicket = null;
		LottoPlay lottoPlay;
		LottoDrawing lottoDrawing = null;
		CSVParser parser = null;
		int i = 0;
		char line;
		int one;
		int two;
		int three;
		int four;
		int five;
		int six;
		LocalDate drawingDate;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
		LocalDate oldDrawingDate = null;
		char oldLine = 'z';
		boolean isFirstTime = true;
		int ticketCounter = 0;

		try {
			Data lottoData = new Data();
			csvData = lottoData.lottoTicketData;
			parser = CSVParser.parse(csvData, CSVFormat.DEFAULT);
		} catch (IllegalArgumentException ia) {

		} catch (IOException io) {

		}
		for (CSVRecord csvRecord : parser) {
			drawingDate = formatter.parseLocalDate(csvRecord.get(i = 0));
			line = csvRecord.get(++i).charAt(0);
			one = Integer.parseInt(csvRecord.get(++i));
			two = Integer.parseInt(csvRecord.get(++i));
			three = Integer.parseInt(csvRecord.get(++i));
			four = Integer.parseInt(csvRecord.get(++i));
			five = Integer.parseInt(csvRecord.get(++i));
			six = Integer.parseInt(csvRecord.get(++i));
			lottoPlay = new LottoPlay(line, one, two, three, four, five, six);
			if (isFirstTime) {
				lottoPlays = new TreeSet<LottoPlay>();
				lottoTicket = new LottoTicket(drawingDate, "0");
				lottoTickets = new TreeSet<LottoTicket>();
				if (lottoDrawings.containsKey(drawingDate)) {    // find the drawing
					lottoDrawing = lottoDrawings.get(drawingDate);
				} else {    // drawing doesn't exist, so create it
					lottoDrawing = new LottoDrawing(drawingDate);
					lottoDrawings.put(drawingDate, lottoDrawing);
				}
				lottoPlay.compare(lottoDrawing.getLottoPlay());
				lottoPlay.setPrize(lottoDrawing.computePrize(lottoPlay));
				lottoPlays.add(lottoPlay);
				oldDrawingDate = drawingDate;
				oldLine = line;
				isFirstTime = false;
			} else {    // This is not the first play
				if (drawingDate.equals(oldDrawingDate)) {    // this is not a new drawing
					if (oldLine < line) {  // using same ticket
						lottoPlay.compare(lottoDrawing.getLottoPlay());
						lottoPlay.setPrize(lottoDrawing.computePrize(lottoPlay));
						lottoPlays.add(lottoPlay);
						oldLine = line;
					} else {  // same drawing date but different ticket
						lottoTicket.setLottoPlays(lottoPlays);
						lottoTicket.setId(Integer.toString(ticketCounter));
						lottoTickets.add(lottoTicket);
						++ticketCounter;
//						Log.i(TAG, lottoTicket.toString());  // ************************
						lottoTicket = new LottoTicket(drawingDate, Integer.toString(ticketCounter), lottoPlays);
						lottoPlays = new TreeSet<LottoPlay>();
						lottoPlay.compare(lottoDrawing.getLottoPlay());
						lottoPlay.setPrize(lottoDrawing.computePrize(lottoPlay));
						lottoPlays.add(lottoPlay);
						oldLine = line;
					}
				} else {    // new drawing date, save old drawing and get new drawing data
					lottoTicket.setLottoPlays(lottoPlays);
					lottoTicket.setId(Integer.toString(ticketCounter));
					lottoTickets.add(lottoTicket);
					lottoDrawing.setLottoTickets(lottoTickets);
//					Log.i(TAG, lottoTicket.toString());  // ************************
					lottoDrawings.put(oldDrawingDate, lottoDrawing);  // Save drawing
					if (lottoDrawings.containsKey(drawingDate)) {    // find the next drawing
						lottoDrawing = lottoDrawings.get(drawingDate);
					} else {    // drawing doesn't exist, so create it
						lottoDrawing = new LottoDrawing(drawingDate);
						lottoDrawings.put(drawingDate, lottoDrawing);
					}
					lottoPlays = new TreeSet<LottoPlay>();
					lottoTicket = new LottoTicket(drawingDate, "0");
					lottoTickets = new TreeSet<LottoTicket>();
					lottoPlay.compare(lottoDrawing.getLottoPlay());
					lottoPlay.setPrize(lottoDrawing.computePrize(lottoPlay));
					lottoPlays.add(lottoPlay);
					ticketCounter = 0;
					oldDrawingDate = drawingDate;
					oldLine = line;
				}
			}
		}
		lottoTicket.setLottoPlays(lottoPlays);
		lottoTickets.add(lottoTicket);
		lottoDrawing.setLottoTickets(lottoTickets);
		lottoDrawings.put(oldDrawingDate, lottoDrawing);
		return CoalesceLottoData(lottoDrawings);
	}

	public Map<LocalDate, MegaMillionsDrawing> getMegaMillionsTickets(Map<LocalDate, MegaMillionsDrawing> megaMillionsDrawings) {

		String csvData;
		TreeSet<MegaMillionsTicket> megaMillionsTickets = null;
		TreeSet<MegaMillionsPlay> megaMillionsPlays = null;
		MegaMillionsTicket megaMillionsTicket = null;
		MegaMillionsPlay megaMillionsPlay;
		MegaMillionsDrawing megaMillionsDrawing = null;
		CSVParser parser = null;
		char line = ' ';
		int one = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		int five = 0;
		int megaBall = 0;
		int megaPlay = 0;
		boolean isMegaPlier = false;
		boolean isMegaPlierTicket = false;
		LocalDate drawingDate = null;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
		LocalDate oldDrawingDate = null;
		char oldLine = 'z';
		boolean isFirstTime = true;
		int ticketCounter = 0;

		try {
			Data lottoData = new Data();
			csvData = lottoData.megaMillionsTicketData;
			parser = CSVParser.parse(csvData, CSVFormat.DEFAULT);
		} catch (IllegalArgumentException ia) {

		} catch (IOException io) {

		}

		for (CSVRecord csvRecord : parser) {
			if (csvRecord.size() < 8) {

			} else {
				try {
					drawingDate = formatter.parseLocalDate(csvRecord.get(0));
					line = csvRecord.get(1).charAt(0);
					one = Integer.parseInt(csvRecord.get(2));
					two = Integer.parseInt(csvRecord.get(3));
					three = Integer.parseInt(csvRecord.get(4));
					four = Integer.parseInt(csvRecord.get(5));
					five = Integer.parseInt(csvRecord.get(6));
					megaBall = Integer.parseInt(csvRecord.get(7));
					if (csvRecord.size() == 9) {
						megaPlay = Integer.parseInt(csvRecord.get(8));
						isMegaPlier = (megaPlay == 1) ? true : false;
						//Log.i(TAG, "drawingDate:" + drawingDate.toString() + "|isMegaPlay: " + isMegaPlier);
					} else {
						isMegaPlier = false;
					}
				} catch (NumberFormatException nfe) {

				} catch (IllegalArgumentException iae) {

				}
/*				try {
					megaPlay = Integer.parseInt(csvRecord.get(8));
					isMegaPlier = (megaPlay == 1) ? true : false;
				} catch (ArrayIndexOutOfBoundsException ae) {
					isMegaPlier = false;
				}*/
				megaMillionsPlay = new MegaMillionsPlay(line, one, two, three, four, five, megaBall);
				if (isFirstTime) {
					megaMillionsPlays = new TreeSet<MegaMillionsPlay>();
					megaMillionsTicket = new MegaMillionsTicket(drawingDate, null);
					megaMillionsTickets = new TreeSet<MegaMillionsTicket>();
					if (megaMillionsDrawings.containsKey(drawingDate)) {
						megaMillionsDrawing = megaMillionsDrawings.get(drawingDate);
					} else {
						megaMillionsDrawing = new MegaMillionsDrawing(drawingDate);
						megaMillionsDrawings.put(drawingDate, megaMillionsDrawing);
					}
					megaMillionsPlay.compare(megaMillionsDrawing.getMegaMillionsPlay());
					megaMillionsPlay.setPrize(megaMillionsDrawing.computePrize(megaMillionsPlay, isMegaPlier));
					megaMillionsPlays.add(megaMillionsPlay);
					oldDrawingDate = drawingDate;
					oldLine = line;
					isMegaPlierTicket = isMegaPlier;
					isFirstTime = false;
				} else {    // This is not the first play
					if (drawingDate.equals(oldDrawingDate)) {    // is this not a new drawing
						if (oldLine < line) {  // using same ticket
							megaMillionsPlay.compare(megaMillionsDrawing.getMegaMillionsPlay());
							megaMillionsPlay.setPrize(megaMillionsDrawing.computePrize(megaMillionsPlay, isMegaPlier));
							megaMillionsPlays.add(megaMillionsPlay);
							isMegaPlierTicket = isMegaPlier;
							oldLine = line;
						} else {  // same drawing date but different ticket
							megaMillionsTicket.setMegaMillionsPlays(megaMillionsPlays);
							megaMillionsTicket.setId(Integer.toString(ticketCounter));
							++ticketCounter;
							megaMillionsTicket.setMegaPlierTicket(isMegaPlierTicket);
							megaMillionsTickets.add(megaMillionsTicket);
							isMegaPlierTicket = isMegaPlier;
							megaMillionsTicket = new MegaMillionsTicket(drawingDate, megaMillionsPlays);
							megaMillionsPlays = new TreeSet<MegaMillionsPlay>();
							megaMillionsPlay.compare(megaMillionsDrawing.getMegaMillionsPlay());
							megaMillionsPlay.setPrize(megaMillionsDrawing.computePrize(megaMillionsPlay, isMegaPlier));
							megaMillionsPlays.add(megaMillionsPlay);
							oldLine = line;
						}
					} else {    // new drawing, save old drawing and get new drawing data
						megaMillionsTicket.setMegaMillionsPlays(megaMillionsPlays);
						megaMillionsTicket.setId(Integer.toString(ticketCounter));
						megaMillionsTicket.setMegaPlierTicket(isMegaPlierTicket);
						megaMillionsTickets.add(megaMillionsTicket);
						isMegaPlierTicket = isMegaPlier;
						megaMillionsDrawing.setMegaMillionsTickets(megaMillionsTickets);
						megaMillionsDrawings.put(oldDrawingDate, megaMillionsDrawing);
						megaMillionsPlays = new TreeSet<MegaMillionsPlay>();
						megaMillionsTicket = new MegaMillionsTicket(drawingDate, null);
						megaMillionsTickets = new TreeSet<MegaMillionsTicket>();
						if (megaMillionsDrawings.containsKey(drawingDate)) {
							megaMillionsDrawing = megaMillionsDrawings.get(drawingDate);
						} else {
							megaMillionsDrawing = new MegaMillionsDrawing(drawingDate);
							megaMillionsDrawings.put(drawingDate, megaMillionsDrawing);
						}
						megaMillionsPlay.compare(megaMillionsDrawing.getMegaMillionsPlay());
						megaMillionsPlay.setPrize(megaMillionsDrawing.computePrize(megaMillionsPlay, isMegaPlier));
						megaMillionsPlays.add(megaMillionsPlay);
						ticketCounter = 0;
						oldDrawingDate = drawingDate;
						oldLine = line;
					}
				}
			}
		}    // Done inputting data, save last read play
		megaMillionsTicket.setMegaMillionsPlays(megaMillionsPlays);
		megaMillionsTicket.setId(Integer.toString(ticketCounter));
		megaMillionsTicket.setMegaPlierTicket(isMegaPlierTicket);
		megaMillionsTickets.add(megaMillionsTicket);
		megaMillionsDrawing.setMegaMillionsTickets(megaMillionsTickets);
		megaMillionsDrawings.put(oldDrawingDate, megaMillionsDrawing);
		return CoalesceMegaMillionsData(megaMillionsDrawings);
	}

	/**
	 * Returns the Map object passed to it with the PowerballTicket objects populated into each PowerballDrawing object.
	 *
	 * @param powerballDrawings	Map of all Powerball drawings organized by date of drawing.
	 * @return	Map updated with powerball tickets found in Data.
	 */
	public Map<LocalDate, PowerballDrawing> getPowerballTickets(Map<LocalDate, PowerballDrawing> powerballDrawings) {

		String csvData;
		TreeSet<PowerballTicket> powerballTickets = null;
		TreeSet<PowerballPlay> powerballPlays = null;
		PowerballTicket powerballTicket = null;
		PowerballPlay powerballPlay;
		PowerballDrawing powerballDrawing = null;
		CSVParser parser = null;
		char line;
		int one;
		int two;
		int three;
		int four;
		int five;
		int powerBall;
		int powerPlay;
		boolean isPowerPlay = false;
		boolean isPowerTicket = false;
		LocalDate drawingDate = null;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
		LocalDate predrawingDate = new LocalDate(1900, 01, 01);
		LocalDate oldDrawingDate = new LocalDate(1900, 01, 01);
		char oldLine = 'z';
		boolean isFirstTime = true;
		int ticketCounter = 0;

		try {
			Data powerballData = new Data();
			csvData = powerballData.powerballTicketData;
			parser = CSVParser.parse(csvData, CSVFormat.DEFAULT);
		} catch (IllegalArgumentException ia) {
			ia.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		for (CSVRecord csvRecord : parser) {
			if (csvRecord.size() < 8) {

			} else {
				drawingDate = formatter.parseLocalDate(csvRecord.get(0));
				line = csvRecord.get(1).charAt(0);
				one = Integer.parseInt(csvRecord.get(2));
				two = Integer.parseInt(csvRecord.get(3));
				three = Integer.parseInt(csvRecord.get(4));
				four = Integer.parseInt(csvRecord.get(5));
				five = Integer.parseInt(csvRecord.get(6));
				powerBall = Integer.parseInt(csvRecord.get(7));
				if (csvRecord.size() == 9) {
					powerPlay = Integer.parseInt(csvRecord.get(8));
					isPowerPlay = (powerPlay == 1) ? true : false;
					//Log.i(TAG, "drawingDate:" + drawingDate.toString() + "|isPowerPlay: " + isPowerPlay);
				} else {
					isPowerPlay = false;
				}
/*				try {
					powerPlay = Integer.parseInt(csvRecord.get(8));
					isPowerPlay = (powerPlay == 1) ? true : false;
				} catch (ArrayIndexOutOfBoundsException ae) {
					isPowerPlay = false;
				}*/
				powerballPlay = new PowerballPlay(line, one, two, three, four, five, powerBall);

				if (predrawingDate.equals(oldDrawingDate)) { // first time through, create data structure
					powerballPlays = new TreeSet<PowerballPlay>();
					powerballTicket = new PowerballTicket(drawingDate, isPowerPlay, null);
					powerballTickets = new TreeSet<PowerballTicket>();
					if (powerballDrawings.containsKey(drawingDate)) {  // if drawing exists use it.
						powerballDrawing = powerballDrawings.get(drawingDate);
					} else {	// otherwise create a new drawing and insert it into powerballDrawings
						powerballDrawing = new PowerballDrawing(drawingDate);
						powerballDrawings.put(drawingDate, powerballDrawing);
					}
					// compare this play with drawing play to set isMatched[]
					powerballPlay.compare(powerballDrawing.getPowerballPlay());
					// set prize won for this play
					powerballPlay.setPrize(powerballDrawing.computePrize(powerballPlay, isPowerPlay));
					powerballPlays.add(powerballPlay);
					oldDrawingDate = drawingDate;
					oldLine = line;
					isPowerTicket = isPowerPlay;
				} else {    // This is not the first play
					if (drawingDate.equals(oldDrawingDate)) {    // this not a new drawing
						if (oldLine < line) {  // using same ticket
							powerballPlay.compare(powerballDrawing.getPowerballPlay());
							powerballPlay.setPrize(powerballDrawing.computePrize(powerballPlay, isPowerTicket));
							powerballPlays.add(powerballPlay);
							isPowerTicket = isPowerPlay;
							oldLine = line;
						} else {  // same drawing date but different ticket
							powerballTicket.setPowerballPlays(powerballPlays);
							powerballTicket.setId(Integer.toString(ticketCounter));
							++ticketCounter;
							//powerballTicket.setPowerPlayTicket(isPowerTicket);
							powerballTickets.add(powerballTicket);
							isPowerTicket = isPowerPlay;
							powerballTicket = new PowerballTicket(drawingDate, isPowerTicket, powerballPlays);
							powerballPlays = new TreeSet<PowerballPlay>();
							powerballPlay.compare(powerballDrawing.getPowerballPlay());
							powerballPlay.setPrize(powerballDrawing.computePrize(powerballPlay, isPowerPlay));
							powerballPlays.add(powerballPlay);
							oldLine = line;
						}
					} else {    // new drawing, save old drawing and get new drawing data
						powerballTicket.setPowerballPlays(powerballPlays);
						powerballTicket.setId(Integer.toString(ticketCounter));
						powerballTicket.setPowerPlayTicket(isPowerTicket);
						powerballTickets.add(powerballTicket);
						isPowerTicket = isPowerPlay;
						powerballDrawing.setPowerballTickets(powerballTickets);
						powerballDrawings.put(oldDrawingDate, powerballDrawing);
						powerballPlays = new TreeSet<PowerballPlay>();
						powerballTicket = new PowerballTicket(drawingDate, isPowerTicket, null);
						powerballTickets = new TreeSet<PowerballTicket>();
						isPowerTicket = false;
						if (powerballDrawings.containsKey(drawingDate)) {
							powerballDrawing = powerballDrawings.get(drawingDate);
						} else {
							powerballDrawing = new PowerballDrawing(drawingDate);
							powerballDrawings.put(drawingDate, powerballDrawing);
						}
						powerballPlay.compare(powerballDrawing.getPowerballPlay());
						powerballPlay.setPrize(powerballDrawing.computePrize(powerballPlay, isPowerPlay));
						powerballPlays.add(powerballPlay);
						ticketCounter = 0;
						oldDrawingDate = drawingDate;
						oldLine = line;
					}
				}
			}
		}
		powerballTicket.setPowerballPlays(powerballPlays);
		powerballTicket.setId(Integer.toString(ticketCounter));
		powerballTicket.setPowerPlayTicket(isPowerTicket);
		powerballTickets.add(powerballTicket);
		powerballDrawing.setPowerballTickets(powerballTickets);
		powerballDrawings.put(oldDrawingDate, powerballDrawing);
		return CoalescePowerballData(powerballDrawings);
	}

	private Map<LocalDate, LottoDrawing> CoalesceLottoData(Map<LocalDate, LottoDrawing> inputDrawings) {

		DatabaseHelper dbHelper = DatabaseHelper.getInstance(this.appContext);
		LottoPlay blankPlay = new LottoPlay(' ',0,0,0,0,0,0);	// blank play
		Map<LocalDate, LottoDrawing> dbDrawings = dbHelper.getAllColoradoLottoDrawings();
		int drawingType = Constants.COLORADO_LOTTO_TYPE;

		for (LottoDrawing inputDrawing : inputDrawings.values()) {
			LocalDate drawingDate = inputDrawing.getDrawingDate();
			LottoPlay inputPlay = inputDrawing.getLottoPlay();
			LottoDrawing dbDrawing = dbDrawings.get(drawingDate);
			if (dbDrawing == null) {  // Drawing is not in database might need to add it
				long drawingId = dbHelper.insertDrawing(inputDrawing);
				dbDrawings.put(drawingDate, inputDrawing);
			} else if (!dbDrawing.equals(inputDrawing)) { // Drawing is in database but different
				dbHelper.updatePlay(drawingDate.toDate().getTime(), drawingType, inputPlay);
				dbHelper.updateDrawing(inputDrawing);
				dbDrawings.put(drawingDate, inputDrawing); // Replace drawing with inputDrawing
			}
		}
		dbHelper.close();
		return dbDrawings;
	}

	private Map<LocalDate, MegaMillionsDrawing> CoalesceMegaMillionsData(
			Map<LocalDate, MegaMillionsDrawing> inputDrawings) {

		DatabaseHelper dbHelper = DatabaseHelper.getInstance(this.appContext);
		MegaMillionsPlay blankPlay = new MegaMillionsPlay(' ',0,0,0,0,0,0);
		Map<LocalDate, MegaMillionsDrawing> dbDrawings = dbHelper.getAllMegaMillionsDrawings();
		int drawingType = Constants.MEGAMILLIONS_TYPE;

		for (MegaMillionsDrawing inputDrawing : inputDrawings.values()) {
			LocalDate drawingDate = inputDrawing.getDrawingDate();
			MegaMillionsPlay inputPlay = inputDrawing.getMegaMillionsPlay();
			MegaMillionsDrawing dbDrawing = dbDrawings.get(drawingDate);
			if (dbDrawing == null) {  // Drawing is not in database might need to add it
				long drawingId = dbHelper.insertDrawing(inputDrawing);
				dbDrawings.put(drawingDate, inputDrawing);
			} else if (!dbDrawing.equals(inputDrawing)) { // Drawing is in database but different
				dbHelper.updatePlay(drawingDate.toDate().getTime(), drawingType, inputPlay);
				dbHelper.updateDrawing(inputDrawing);
				dbDrawings.put(drawingDate, inputDrawing); // Replace drawing with inputDrawing
			}
		}
		dbHelper.close();
		return dbDrawings;
	}

	private Map<LocalDate, PowerballDrawing> CoalescePowerballData(Map<LocalDate, PowerballDrawing> inputDrawings) {

		DatabaseHelper dbHelper = DatabaseHelper.getInstance(this.appContext);
		PowerballPlay blankPlay = new PowerballPlay(' ',0,0,0,0,0,0);	// blank play
		Map<LocalDate, PowerballDrawing> dbDrawings = dbHelper.getAllPowerballDrawings();
		int drawingType = Constants.POWERBALL_TYPE;

		for (PowerballDrawing inputDrawing : inputDrawings.values()) {
			LocalDate drawingDate = inputDrawing.getDrawingDate();
			PowerballPlay inputPlay = inputDrawing.getPowerballPlay();
			PowerballDrawing dbDrawing = dbDrawings.get(drawingDate);
			if (dbDrawing == null) {  // Drawing is not in database might need to add it
				long drawingId = dbHelper.insertDrawing(inputDrawing);
				dbDrawings.put(drawingDate, inputDrawing);
			} else if (!dbDrawing.equals(inputDrawing)) { // Drawing is in database but different
				dbHelper.updatePlay(drawingDate.toDate().getTime(), drawingType, inputPlay);
				dbHelper.updateDrawing(inputDrawing);
				dbDrawings.put(drawingDate, inputDrawing); // Replace drawing with inputDrawing
			}
		}
		dbHelper.close();
		return dbDrawings;
	}
}
