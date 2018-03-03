package com.byderbeck.lotterydata.dao;

import com.byderbeck.lotterydata.Data;
import com.byderbeck.lotterydata.drawing.LottoDrawing;
import com.byderbeck.lotterydata.drawing.MegaMillionsDrawing;
import com.byderbeck.lotterydata.drawing.PowerballDrawing;
import com.byderbeck.lotterydata.play.MegaMillionsPlay;
import com.byderbeck.lotterydata.play.PowerballPlay;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.PatternSyntaxException;

public class DrawingDAOImpl implements DrawingDAO {

	private static final String TAG = "***" + DrawingDAOImpl.class.getSimpleName();

	public Map<LocalDate, LottoDrawing> getLottoDrawings() {

		Map<LocalDate, LottoDrawing> lottoDrawings = new TreeMap<LocalDate, LottoDrawing>();

//		Dropbox dropbox = new Dropbox();
		LottoDrawing lottoDrawing;
		String csvData;
//		BufferedReader csvData;
		CSVParser parser = null;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MMMM. dd, yyyy");
		LocalDate drawingDate;
		int[] numbers = new int[6];
		int jackpot = 0;
		int jackpotCashValue = 0;
		int match5Prize = 0;
		int match4Prize = 0;
		int match3Prize = 0;
		String inputString;

		try {
//			csvData = new BufferedReader(new FileReader(Constants.LOTTO_DRAWINGS));
//			parser = new CSVParser(csvData, CSVFormat.DEFAULT);
//			csvData.readLine();
//			csvData = dropbox.getLottoDrawings();
//			parser = CSVParser.parse(csvData, CSVFormat.DEFAULT);
			Data lottoData = new Data();
			csvData = lottoData.lottoDrawingData;
			parser = CSVParser.parse(csvData, CSVFormat.DEFAULT);
		} catch (IllegalArgumentException ia) {
			ia.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		for (CSVRecord csvRecord : parser) {
//			drawingDate = LocalDate.parse(csvRecord.get(0), formatter);
//			drawingDate = formatter.parseLocalDate(csvRecord.get(0));
			drawingDate = parseDate(csvRecord.get(0));
			inputString = csvRecord.get(1);
			numbers = convertDrawingNumbers(csvRecord.get(2));

			jackpot = convertCurrency(csvRecord.get(3));
			jackpotCashValue = convertCurrency(csvRecord.get(4));
			inputString = csvRecord.get(5);

			match5Prize = convertCurrency(csvRecord.get(6));
			inputString = csvRecord.get(7);

			match4Prize = convertCurrency(csvRecord.get(8));
			inputString = csvRecord.get(9);

			match3Prize = convertCurrency(csvRecord.get(10));
			inputString = csvRecord.get(11);
			inputString = inputString + "";

			lottoDrawing = new LottoDrawing(drawingDate, numbers[0], numbers[1], numbers[2], numbers[3], numbers[4],
					numbers[5], jackpot, jackpotCashValue, match5Prize, match4Prize, match3Prize);
			lottoDrawings.put(drawingDate, lottoDrawing);
//			Log.i(TAG, lottoDrawing.toString());
		}
		return lottoDrawings;
	}

	public Map<LocalDate, PowerballDrawing> getPowerballDrawings() {

		Map<LocalDate, PowerballDrawing> powerballDrawings = new TreeMap<LocalDate, PowerballDrawing>();
		PowerballDrawing powerballDrawing;
		String csvData;
		CSVParser parser = null;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM. dd, yyyy");
		LocalDate drawingDate;
		int[] numbers = new int[6];
		int powerball = 0;
		int powerPlay = 0;
		int jackpot = 0;
		int jackpotCashValue;
		int match5Prize = 0;
		int match4Prize = 0;
		int match3Prize = 0;
		int match4PbPrize = 0;
		int match3PbPrize = 0;
		int match2PbPrize = 0;
		int match1PbPrize = 0;
		int matchPbPrize = 0;
		String inputString;

		try {
			Data lottoData = new Data();
			csvData = lottoData.powerballDrawingData;
			parser = CSVParser.parse(csvData, CSVFormat.DEFAULT);
		} catch (IllegalArgumentException ia) {
			ia.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		for (CSVRecord csvRecord : parser) {
//			drawingDate = LocalDate.parse(csvRecord.get(0), formatter);
//			drawingDate = formatter.parseLocalDate(csvRecord.get(0));
			drawingDate = parseDate(csvRecord.get(0));
			inputString = csvRecord.get(1);
			numbers = convertDrawingNumbers(csvRecord.get(2));
			powerball = Integer.parseInt(csvRecord.get(3));
			powerPlay = Integer.parseInt(csvRecord.get(4));

			jackpot = convertCurrency(csvRecord.get(5));
			jackpotCashValue = convertCurrency(csvRecord.get(6));
			inputString = csvRecord.get(7);
			inputString = csvRecord.get(8);

			match5Prize = convertCurrency(csvRecord.get(9));
			inputString = csvRecord.get(10);
			inputString = csvRecord.get(11);
			inputString = csvRecord.get(12);

			match4PbPrize = convertCurrency(csvRecord.get(13));
			inputString = csvRecord.get(14);
			inputString = csvRecord.get(15);
			inputString = csvRecord.get(16);

			match4Prize = convertCurrency(csvRecord.get(17));
			inputString = csvRecord.get(18);
			inputString = csvRecord.get(19);
			inputString = csvRecord.get(20);

			match3PbPrize = convertCurrency(csvRecord.get(21));
			inputString = csvRecord.get(22);
			inputString = csvRecord.get(23);
			inputString = csvRecord.get(24);

			match3Prize = convertCurrency(csvRecord.get(25));
			inputString = csvRecord.get(26);
			inputString = csvRecord.get(27);
			inputString = csvRecord.get(28);

			match2PbPrize = convertCurrency(csvRecord.get(29));
			inputString = csvRecord.get(30);
			inputString = csvRecord.get(31);
			inputString = csvRecord.get(32);

			match1PbPrize = convertCurrency(csvRecord.get(33));
			inputString = csvRecord.get(34);
			inputString = csvRecord.get(35);
			inputString = csvRecord.get(36);

			matchPbPrize = convertCurrency(csvRecord.get(37));
			inputString = csvRecord.get(38);
			inputString = csvRecord.get(39);
			inputString = csvRecord.get(40);
			inputString = inputString + "";
			PowerballPlay powerballPlay = new PowerballPlay(' ', numbers[0], numbers[1], numbers[2], numbers[3],
					numbers[4], powerball);
			powerballDrawing = new PowerballDrawing(drawingDate, jackpot, jackpotCashValue, match5Prize, match4Prize,
					match3Prize, powerballPlay, match4PbPrize, match3PbPrize, match2PbPrize, match1PbPrize,
					matchPbPrize, powerPlay);
			powerballDrawing.setPowerballPlay(powerballPlay);
			powerballDrawings.put(drawingDate, powerballDrawing);
		}
		return powerballDrawings;
	}

	public Map<LocalDate, MegaMillionsDrawing> getMegaMillionsDrawings() {

		Map<LocalDate, MegaMillionsDrawing> megaMillionsDrawings = new TreeMap<LocalDate, MegaMillionsDrawing>();
		MegaMillionsDrawing megaMillionsDrawing;
//		Dropbox dropbox = new Dropbox();
		String csvData;
//		BufferedReader csvData;
		CSVParser parser = null;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM. dd, yyyy");
		LocalDate drawingDate;
		int[] numbers = new int[6];
		int megaBall = 0;
		int megaPlier = 0;
		int jackpot = 0;
		int jackpotCashValue;
		int match5Prize = 0;
		int match4Prize = 0;
		int match3Prize = 0;
		int match4MMPrize = 0;
		int match3MMPrize = 0;
		int match2MMPrize = 0;
		int match1MMPrize = 0;
		int matchMMPrize = 0;
		String inputString;

		try {
			Data lottoData = new Data();
			csvData = lottoData.megaMillionsDrawingData;
			parser = CSVParser.parse(csvData, CSVFormat.DEFAULT);
		} catch (IllegalArgumentException ia) {
			ia.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		for (CSVRecord csvRecord : parser) {
//			drawingDate = LocalDate.parse(csvRecord.get(0), formatter);
//			drawingDate = formatter.parseLocalDate(csvRecord.get(0));
			drawingDate = parseDate(csvRecord.get(0));
			inputString = csvRecord.get(1);
			numbers = convertDrawingNumbers(csvRecord.get(2));
			megaBall = Integer.parseInt(csvRecord.get(3));
			megaPlier = Integer.parseInt(csvRecord.get(4));

			jackpot = convertCurrency(csvRecord.get(5));
			jackpotCashValue = convertCurrency(csvRecord.get(6));
			inputString = csvRecord.get(7);
			inputString = csvRecord.get(8);

			match5Prize = convertCurrency(csvRecord.get(9));
			inputString = csvRecord.get(10);
			inputString = csvRecord.get(11);
			inputString = csvRecord.get(12);

			match4MMPrize = convertCurrency(csvRecord.get(13));
			inputString = csvRecord.get(14);
			inputString = csvRecord.get(15);
			inputString = csvRecord.get(16);

			match4Prize = convertCurrency(csvRecord.get(17));
			inputString = csvRecord.get(18);
			inputString = csvRecord.get(19);
			inputString = csvRecord.get(20);

			match3MMPrize = convertCurrency(csvRecord.get(21));
			inputString = csvRecord.get(22);
			inputString = csvRecord.get(23);
			inputString = csvRecord.get(24);

			match3Prize = convertCurrency(csvRecord.get(25));
			inputString = csvRecord.get(26);
			inputString = csvRecord.get(27);
			inputString = csvRecord.get(28);

			match2MMPrize = convertCurrency(csvRecord.get(29));
			inputString = csvRecord.get(30);
			inputString = csvRecord.get(31);
			inputString = csvRecord.get(32);

			match1MMPrize = convertCurrency(csvRecord.get(33));
			inputString = csvRecord.get(34);
			inputString = csvRecord.get(35);
			inputString = csvRecord.get(36);

			matchMMPrize = convertCurrency(csvRecord.get(37));
			inputString = csvRecord.get(38);
			inputString = csvRecord.get(39);
			inputString = csvRecord.get(40);
			inputString = inputString + "";
			MegaMillionsPlay megaMillionsPlay = new MegaMillionsPlay(' ', numbers[0], numbers[1], numbers[2], numbers[3],
					numbers[4], megaBall);
			megaMillionsDrawing = new MegaMillionsDrawing(drawingDate, jackpot, jackpotCashValue, match5Prize,
					match4Prize, match3Prize, megaMillionsPlay, match4MMPrize, match3MMPrize, match2MMPrize,
					match1MMPrize, matchMMPrize, megaPlier);
			megaMillionsDrawing.setMegaMillionsPlay(megaMillionsPlay);
			megaMillionsDrawings.put(drawingDate, megaMillionsDrawing);
		}
		return megaMillionsDrawings;
	}

	private int convertCurrency(String inputString) {

		NumberFormat numberFormat = NumberFormat.getInstance();
		Number number = null;
//		System.out.println("inputString:" + inputString);
		inputString = inputString.substring(1);
		try {
			number = numberFormat.parse(inputString);
		} catch (ParseException pe) {

		}
		return number.intValue();
	}

	private int[] convertDrawingNumbers(String inputString) {

		int[] numbers = new int[6];
		String[] output = null;

		try {
			output = inputString.split(" - ");
		} catch (PatternSyntaxException pse) {

		}
		for (int i = 0; i < output.length; ++i) {
			numbers[i] = Integer.parseInt(output[i]);
		}
		return numbers;
	}

	private LocalDate parseDrawingDate(String input) {
		LocalDate drawingDate;
		String delims = "[()]+";

//		System.out.println(input);
		String[] tokens = input.split(delims);
//		System.out.println(tokens[0]);
		drawingDate = LocalDate.parse(tokens[2], DateTimeFormat.forPattern("yyyy, MM, dd"));
		return drawingDate;
	}

	LocalDate parseDate(String dateString) {

		String month;
		String modifiedDate = "";
		LocalDate date;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM. dd, yyyy");

		int index = dateString.indexOf(" ");
//		System.out.println("index:" + index);
		if (index > 0) {
			month = dateString.substring(0, index);
			switch (month.toLowerCase()) {
				case "march":
					dateString = "Mar. " + dateString.substring(index + 1);
					break;
				case "april":
					dateString = "Apr. " + dateString.substring(index + 1);
					break;
				case "may":
					dateString = "May. " + dateString.substring(index + 1);
					break;
				case "june":
					dateString = "Jun. " + dateString.substring(index + 1);
					break;
				case "july":
					dateString = "Jul. " + dateString.substring(index + 1);
					break;
				case "sept.":
					dateString = "Sep. " + dateString.substring(index + 1);
					break;
			}
//			System.out.println("dateString:" + modifiedDate);
		} else {
			System.out.println("!!!!! index <= 0");
		}
		return LocalDate.parse(dateString, formatter);
	}

}
