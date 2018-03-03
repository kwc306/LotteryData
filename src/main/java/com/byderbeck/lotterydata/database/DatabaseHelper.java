package com.byderbeck.lotterydata.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.byderbeck.lotterydata.Constants;
import com.byderbeck.lotterydata.drawing.Drawing;
import com.byderbeck.lotterydata.drawing.LottoDrawing;
import com.byderbeck.lotterydata.drawing.MegaMillionsDrawing;
import com.byderbeck.lotterydata.drawing.PowerballDrawing;
import com.byderbeck.lotterydata.play.LottoPlay;
import com.byderbeck.lotterydata.play.MegaMillionsPlay;
import com.byderbeck.lotterydata.play.Play;
import com.byderbeck.lotterydata.play.PowerballPlay;
import com.byderbeck.lotterydata.ticket.LottoTicket;
import com.byderbeck.lotterydata.ticket.MegaMillionsTicket;
import com.byderbeck.lotterydata.ticket.PowerballTicket;
import com.byderbeck.lotterydata.ticket.Ticket;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by lyman on 5/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "***" + DatabaseHelper.class.getSimpleName();
	private static DatabaseHelper mInstance = null;

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "lottery.db";

	// Table Names
	private static final String TABLE_DRAWINGS = "drawings";
	private static final String TABLE_TICKETS = "tickets";
	private static final String TABLE_PLAYS = "plays";
	private static final String TABLE_LOTTERY_TYPES = "lottery_types";

	// Common column names
	private static final String KEY_ID = "id";

	// Table Create Statements
	// Lottery_types table create statement
	private static final String CREATE_TABLE_LOTTERY_TYPES = "CREATE TABLE " + TABLE_LOTTERY_TYPES + "(" +
			KEY_ID + " INTEGER PRIMARY KEY," +
			"type      INTEGER NOT NULL UNIQUE," +
			"name      TEXT NOT NULL" + ")";

	// Drawings table create statement
	private static final String CREATE_TABLE_DRAWINGS = "CREATE TABLE " + TABLE_DRAWINGS + "(" +
			KEY_ID + " INTEGER PRIMARY KEY," +
			"lottery_type       INTEGER," +
			"drawing_date       INTEGER," +
			"play_id            INTEGER," +
			"multiplier			INTEGER," +
			"jackpot            INTEGER," +
			"jackpot_cash_value INTEGER," +
			"match5prize        INTEGER," +
			"match4prize        INTEGER," +
			"match3prize        INTEGER," +
			"match4prize_plus   INTEGER," +
			"match3prize_plus   INTEGER," +
			"match2prize_plus   INTEGER," +
			"match1prize_plus   INTEGER," +
			"match_prize_plus   INTEGER," +
			"FOREIGN KEY (play_id) REFERENCES plays(id)," +
			"CONSTRAINT drawings_unique UNIQUE (lottery_type, drawing_date)" + ")";

	// Tickets table create statement
	private static final String CREATE_TABLE_TICKETS = "CREATE TABLE " + TABLE_TICKETS + "(" +
			KEY_ID + " INTEGER PRIMARY KEY," +
			"ticket_type  INTEGER," +
			"drawing_id   INTEGER," +
			"ticket_id    TEXT," +
			"multiplier   INTEGER," +
			"FOREIGN KEY(drawing_id) REFERENCES drawings(id)," +
			"CONSTRAINT tickets_unique UNIQUE (ticket_type, drawing_id, ticket_id)" + ")";

	// Plays table create statement
	private static final String CREATE_TABLE_PLAYS = "CREATE TABLE " + TABLE_PLAYS + "(" +
			KEY_ID + " INTEGER PRIMARY KEY," +
			"drawing_date INTEGER," +
			"drawing_type INTEGER," +
			"line         TEXT," +
			"one          INTEGER," +
			"two          INTEGER," +
			"three        INTEGER," +
			"four         INTEGER," +
			"five         INTEGER," +
			"six          INTEGER," +
			"ticket_id    INTEGER," +
			"FOREIGN KEY(ticket_id) REFERENCES tickets(id)," +
			"CONSTRAINT plays_unique UNIQUE (drawing_date, drawing_type, line, ticket_id)" + ")";

	private static final int LOTTERY_TYPE_MEGA_MILLIONS = 10;
	private static final int LOTTERY_TYPE_POWERBALL = 20;
	private static final int LOTTERY_TYPE_COLORADO_LOTTO = 30;

	private DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

	public static DatabaseHelper getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new DatabaseHelper(ctx.getApplicationContext());
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_LOTTERY_TYPES);
		db.execSQL(CREATE_TABLE_DRAWINGS);
		db.execSQL(CREATE_TABLE_TICKETS);
		db.execSQL(CREATE_TABLE_PLAYS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOTTERY_TYPES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRAWINGS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYS);

		// create new tables
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	public long setupLotteryTypes() {

		long lotteryId = 0l;

		lotteryId = insertLotteryType(10, "Mega Millions");
		lotteryId = insertLotteryType(20, "Powerball");
		lotteryId = insertLotteryType(30, "Colorado Lotto");

		return lotteryId;
	}

	public void initializeDB() {

		SQLiteDatabase db = getWritableDatabase();
		onUpgrade(db, 0, 1);
//		onCreate(db);
	}

	// insert lottery types into db
	public long insertLotteryType(int type, String name) {

		SQLiteDatabase db = this.getWritableDatabase();
		long lotteryTypeId;
		ContentValues values = new ContentValues();

		values.put("type", type);
		values.put("name", name);
		// insert row
		return lotteryTypeId = db.insert(TABLE_LOTTERY_TYPES, null, values);
	}

	// insert drawing into drawings table
	public long insertDrawing(Drawing drawing) {

		LottoDrawing lottoDrawing = null;
		MegaMillionsDrawing megaMillionsDrawing = null;
		PowerballDrawing powerballDrawing = null;
		String playInsertSQL = "insert into plays (drawing_date, drawing_type, line, one, two ,three, four, " +
				"five, six, ticket_id) values (?,?,?,?,?,?,?,?,?,?)";
		String ticketInsertSQL = "insert into tickets (ticket_type, drawing_id, ticket_id, multiplier)" +
				" values (?,?,?,?)";
		String drawingInsertSQL = "insert into drawings (lottery_type, drawing_date, play_id, multiplier, jackpot," +
				"jackpot_cash_value, match5prize, match4prize, match3prize, match4prize_plus,match3prize_plus," +
				"match2prize_plus, match1prize_plus, match_prize_plus) " +
				"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		long drawingId = 0l;
		long playId = 0l;
		long ticketId = 0l;
		int multiplier = 1;
		int match4PrizePlus = 0;
		int match3PrizePlus = 0;
		int match2PrizePlus = 0;
		int match1PrizePlus = 0;
		int matchPrizePlus = 0;
		TreeSet<Ticket> tickets = null;
		TreeSet<LottoTicket> lottoTickets = null;
		TreeSet<MegaMillionsTicket> megaMillionsTickets = null;
		TreeSet<PowerballTicket> powerballTickets = null;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		SQLiteStatement playInsertStmt = db.compileStatement(playInsertSQL);
		SQLiteStatement ticketInsertStmt = db.compileStatement(ticketInsertSQL);
		SQLiteStatement drawingInsertStmt = db.compileStatement(drawingInsertSQL);

		Play drawingPlay = null;
		ContentValues values = new ContentValues();
		int lotteryType = 0;

		if (drawing instanceof LottoDrawing) {
			lotteryType = Constants.COLORADO_LOTTO_TYPE;
			lottoDrawing = (LottoDrawing) drawing;
			drawingPlay = lottoDrawing.getLottoPlay();
			match4PrizePlus = 0;
			match3PrizePlus = 0;
			match2PrizePlus = 0;
			match1PrizePlus = 0;
			matchPrizePlus = 0;
			lottoTickets = lottoDrawing.getLottoTickets();
		} else if (drawing instanceof MegaMillionsDrawing) {
			lotteryType = Constants.MEGAMILLIONS_TYPE;
			megaMillionsDrawing = (MegaMillionsDrawing) drawing;
			drawingPlay = megaMillionsDrawing.getMegaMillionsPlay();
			multiplier = megaMillionsDrawing.getMegaPlier();
			match4PrizePlus = megaMillionsDrawing.getMatch4MMPrize();
			match3PrizePlus = megaMillionsDrawing.getMatch3MMPrize();
			match2PrizePlus = megaMillionsDrawing.getMatch2MMPrize();
			match1PrizePlus = megaMillionsDrawing.getMatch1MMPrize();
			matchPrizePlus = megaMillionsDrawing.getMatchMMPrize();
			megaMillionsTickets = megaMillionsDrawing.getMegaMillionsTickets();
		} else if (drawing instanceof PowerballDrawing) {
			lotteryType = Constants.POWERBALL_TYPE;
			powerballDrawing = (PowerballDrawing) drawing;
			drawingPlay = powerballDrawing.getPowerballPlay();
			multiplier = powerballDrawing.getPowerPlay();
			match4PrizePlus = powerballDrawing.getMatch4PbPrize();
			match3PrizePlus = powerballDrawing.getMatch3PbPrize();
			match2PrizePlus = powerballDrawing.getMatch2PbPrize();
			match1PrizePlus = powerballDrawing.getMatch1PbPrize();
			matchPrizePlus = powerballDrawing.getMatchPbPrize();
			powerballTickets = powerballDrawing.getPowerballTickets();
		}
		Date date = drawing.getDrawingDate().toDate();
		playInsertStmt.bindLong(1, date.getTime());
		playInsertStmt.bindLong(2, lotteryType);
		playInsertStmt.bindString(3, Character.toString(drawingPlay.getLine()));
		playInsertStmt.bindLong(4, drawingPlay.getOne());
		playInsertStmt.bindLong(5, drawingPlay.getTwo());
		playInsertStmt.bindLong(6, drawingPlay.getThree());
		playInsertStmt.bindLong(7, drawingPlay.getFour());
		playInsertStmt.bindLong(8, drawingPlay.getFive());
		playInsertStmt.bindLong(9, drawingPlay.getSix());
		playInsertStmt.bindLong(10, 0l);
		playId = playInsertStmt.executeInsert();
		playInsertStmt.clearBindings();

		drawingInsertStmt.bindLong(1, lotteryType);
		drawingInsertStmt.bindLong(2, date.getTime());
		drawingInsertStmt.bindLong(3, playId);
		drawingInsertStmt.bindLong(4, multiplier);
		drawingInsertStmt.bindLong(5, drawing.getJackpot());
		drawingInsertStmt.bindLong(6, drawing.getJackpotCashValue());
		drawingInsertStmt.bindLong(7, drawing.getMatch5Prize());
		drawingInsertStmt.bindLong(8, drawing.getMatch4Prize());
		drawingInsertStmt.bindLong(9, drawing.getMatch3Prize());
		drawingInsertStmt.bindLong(10, match4PrizePlus);
		drawingInsertStmt.bindLong(11, match3PrizePlus);
		drawingInsertStmt.bindLong(12, match2PrizePlus);
		drawingInsertStmt.bindLong(13, match1PrizePlus);
		drawingInsertStmt.bindLong(14, matchPrizePlus);
		drawingId = drawingInsertStmt.executeInsert();
		drawingInsertStmt.clearBindings();

		if (drawing instanceof LottoDrawing) {
			if (lottoTickets != null && !lottoTickets.isEmpty()) {
				for (LottoTicket ticket : lottoTickets) {
//					Log.i(TAG, "lottoTicket:" + ticket.toString());  // *****************************
					ticketInsertStmt.bindLong(1, lotteryType);
					ticketInsertStmt.bindLong(2, drawingId);
					ticketInsertStmt.bindString(3, ticket.getId());
					ticketInsertStmt.bindLong(4, 1);
					ticketId = ticketInsertStmt.executeInsert();
					for (LottoPlay play : ticket.getLottoPlays()) {
						playInsertStmt.bindLong(1, date.getTime());
						playInsertStmt.bindLong(2, lotteryType);
						playInsertStmt.bindString(3, Character.toString(play.getLine()));
						playInsertStmt.bindLong(4, play.getOne());
						playInsertStmt.bindLong(5, play.getTwo());
						playInsertStmt.bindLong(6, play.getThree());
						playInsertStmt.bindLong(7, play.getFour());
						playInsertStmt.bindLong(8, play.getFive());
						playInsertStmt.bindLong(9, play.getSix());
						playInsertStmt.bindLong(10, ticketId);
						playId = playInsertStmt.executeInsert();
						playInsertStmt.clearBindings();
					}
				}
			}
		} else if (drawing instanceof MegaMillionsDrawing) {
			if (megaMillionsTickets != null && !megaMillionsTickets.isEmpty()) {
				for (MegaMillionsTicket ticket : megaMillionsTickets) {
					ticketInsertStmt.bindLong(1, lotteryType);
					ticketInsertStmt.bindLong(2, drawingId);
					ticketInsertStmt.bindString(3, ticket.getId());
					ticketInsertStmt.bindLong(4, (ticket.isMegaPlierTicket() ? 1 : 0));
					ticketId = ticketInsertStmt.executeInsert();
					for (MegaMillionsPlay play : ticket.getMegaMillionsPlays()) {
						playInsertStmt.bindLong(1, date.getTime());
						playInsertStmt.bindLong(2, lotteryType);
						playInsertStmt.bindString(3, Character.toString(play.getLine()));
						playInsertStmt.bindLong(4, play.getOne());
						playInsertStmt.bindLong(5, play.getTwo());
						playInsertStmt.bindLong(6, play.getThree());
						playInsertStmt.bindLong(7, play.getFour());
						playInsertStmt.bindLong(8, play.getFive());
						playInsertStmt.bindLong(9, play.getSix());
						playInsertStmt.bindLong(10, ticketId);
						playId = playInsertStmt.executeInsert();
						playInsertStmt.clearBindings();
					}
				}
			}
		} else if (drawing instanceof PowerballDrawing) {
			if (powerballTickets != null && !powerballTickets.isEmpty()) {

				for (PowerballTicket ticket: powerballTickets) {
					ticketInsertStmt.bindLong(1, lotteryType);
					ticketInsertStmt.bindLong(2, drawingId);
					ticketInsertStmt.bindString(3, ticket.getId());
					ticketInsertStmt.bindLong(4, (ticket.isPowerPlayTicket() ? 1 : 0));
					ticketId = ticketInsertStmt.executeInsert();
					for (PowerballPlay play : ticket.getPowerballPlays()) {
						playInsertStmt.bindLong(1, date.getTime());
						playInsertStmt.bindString(2, Character.toString(play.getLine()));
						playInsertStmt.bindLong(3, play.getOne());
						playInsertStmt.bindLong(4, play.getTwo());
						playInsertStmt.bindLong(5, play.getThree());
						playInsertStmt.bindLong(6, play.getFour());
						playInsertStmt.bindLong(7, play.getFive());
						playInsertStmt.bindLong(8, play.getSix());
						playInsertStmt.bindLong(9, ticketId);
						playId = playInsertStmt.executeInsert();
						playInsertStmt.clearBindings();
					}
				}
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		return drawingId;
	}

	// insert play into plays table
	public long insertPlay(long drawingDate, Play play, long ticketId) {

		long playId = 0l;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		int numbers[] = play.getNumbers();

		values.put("drawing_date", drawingDate);
		values.put("line", Character.toString(play.getLine()));
		values.put("one", numbers[0]);
		values.put("two", numbers[1]);
		values.put("three", numbers[2]);
		values.put("four", numbers[3]);
		values.put("five", numbers[4]);
		values.put("six", numbers[5]);
		values.put("ticket_id", ticketId);
		return playId = db.insert(TABLE_PLAYS, null, values);
	}

	// insert ticket into tickets table
	public long insertTicket(int ticketType, long drawingId, String ticketIdString,  int multiplier) {

		long ticketId = 0l;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("ticket_type", ticketType);
		values.put("drawing_id", drawingId);
		values.put("ticket_id", ticketIdString);
		values.put("multiplier", multiplier);

		return ticketId = db.insert(TABLE_TICKETS, null, values);
	}

	// get all Colorado Lotto drawings
	public Map<LocalDate, LottoDrawing> getAllColoradoLottoDrawings() {

		TreeSet<LottoPlay> plays = new TreeSet<LottoPlay>();
		TreeSet<LottoTicket> tickets = new TreeSet<LottoTicket>();
		Map<LocalDate, LottoDrawing> drawings = new TreeMap<LocalDate, LottoDrawing>();
		LottoDrawing drawing = null;
		LottoPlay play = null;
		long oldTicketId = -1;
		LocalDate oldDrawingDate = new LocalDate(1900,1,1);
		int drawingType = Constants.COLORADO_LOTTO_TYPE;
		// Select Colorado lotto drawings Query
		String drawingsQuery = "select d.drawing_date,d.jackpot,d.jackpot_cash_value,d.match5prize,d.match4prize," +
				"d.match3prize,p.one,p.two,p.three,p.four,p.five,p.six " +
				"from drawings as d " +
				"inner join plays as p on d.drawing_date = p.drawing_date " +
				"where d.lottery_type=" + drawingType + " and d.play_id=p.id";
		// Select tickets and plays query
		String playsQuery = "select d.drawing_date,t.id,t.ticket_id, t.multiplier,p.line,p.one,p.two,p.three," +
				"p.four,p.five,p.six " +
				"from drawings as d " +
				"inner join tickets as t on t.drawing_id=d.id " +
				"inner join plays as p on p.ticket_id=t.id " +
				"where d.lottery_type=" + drawingType;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor drawingCursor = db.rawQuery(drawingsQuery, null);

		if (drawingCursor.moveToFirst()) {
			do {
				LocalDate drawingDate = new LocalDate(drawingCursor.getLong(1));
				int jackpot = drawingCursor.getInt(2);
				int jackpotCashValue = drawingCursor.getInt(3);
				int match5Prize = drawingCursor.getInt(4);
				int match4Prize = drawingCursor.getInt(5);
				int match3Prize = drawingCursor.getInt(6);
				int one = drawingCursor.getInt(7);
				int two = drawingCursor.getInt(8);
				int three = drawingCursor.getInt(9);
				int four = drawingCursor.getInt(10);
				int five = drawingCursor.getInt(11);
				int six = drawingCursor.getInt(12);

				play = new LottoPlay(' ', one, two, three, four, five, six);
				drawing = new LottoDrawing(drawingDate, jackpot, jackpotCashValue, match5Prize,
						match4Prize, match3Prize, play);
				drawings.put(drawingDate, drawing);
//				Log.i(TAG, "lottoDrawing:" + lottoDrawing.toString());
			} while (drawingCursor.moveToNext());
		}
		drawingCursor.close();

		Cursor playsCursor = db.rawQuery(playsQuery, null);
		if (playsCursor.moveToFirst()) {
			do {
				LocalDate drawingDate = new LocalDate(playsCursor.getLong(1));
				long ticketId = playsCursor.getLong(2);
				String designation = playsCursor.getString(3);
				int multiplier = playsCursor.getInt(4);
				char line = playsCursor.getString(5).charAt(0);
				int one = playsCursor.getInt(6);
				int two = playsCursor.getInt(7);
				int three = playsCursor.getInt(8);
				int four = playsCursor.getInt(9);
				int five = playsCursor.getInt(10);
				int six = playsCursor.getInt(11);

				play = new LottoPlay(' ', one, two, three, four, five, six);
				if (ticketId == oldTicketId) {
					plays.add(play);
				} else if (drawingDate.equals(oldDrawingDate)) {  // new ticket or new drawing?
					LottoTicket ticket = new LottoTicket(drawingDate, designation, plays);
					tickets.add(ticket);
					oldTicketId = ticketId;
					plays = new TreeSet<LottoPlay>();
				} else {  // new drawing
					drawing = drawings.get(oldDrawingDate);
					drawing.setLottoTickets(tickets);
					drawings.put(oldDrawingDate, drawing);
					tickets = new TreeSet<LottoTicket>();
					plays = new TreeSet<LottoPlay>();
					plays.add(play);
					oldTicketId = ticketId;
					oldDrawingDate = drawingDate;
				}
//				Log.i(TAG, "lottoDrawing:" + lottoDrawing.toString());
			} while (playsCursor.moveToNext());
		}
		playsCursor.close();
		db.close();
		return drawings;
	}

	// get all Mega Millions drawings
	public Map<LocalDate, MegaMillionsDrawing> getAllMegaMillionsDrawings() {

		Map<LocalDate, MegaMillionsDrawing> megaMillionsDrawings = new TreeMap<LocalDate, MegaMillionsDrawing>();
		// Select All Query
		String drawingSelectQuery = "select d.drawing_date,p.one,p.two,p.three,p.four,p.five,p.six,d.multiplier," +
				"d.jackpot,d.jackpot_cash_value,d.match5prize,d.match4prize,d.match3prize," +
				"d.match4prize_plus,d.match3prize_plus,d.match2prize_plus,d.match1prize_plus,d.match_prize_plus," +
				"d.multiplier " +
				"from drawings as d,plays as p " +
				"where d.lottery_type=10 and p.id=d.play_id";
		String ticketSelectQuery = "";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor drawingCursor = db.rawQuery(drawingSelectQuery, null);
		long drawingId = 0l;

		if (drawingCursor.moveToFirst()) {
			do {
				drawingId = drawingCursor.getLong(1);
				LocalDate drawingDate = new LocalDate(drawingCursor.getLong(2));
				int one = drawingCursor.getInt(3);
				int two = drawingCursor.getInt(4);
				int three = drawingCursor.getInt(6);
				int four = drawingCursor.getInt(7);
				int five = drawingCursor.getInt(8);
				int six = drawingCursor.getInt(8);
				int jackpot = drawingCursor.getInt(9);
				int jackpotCashValue = drawingCursor.getInt(10);
				int match5Prize = drawingCursor.getInt(11);
				int match4Prize = drawingCursor.getInt(12);
				int match3Prize = drawingCursor.getInt(13);
				int match4PrizePlus = drawingCursor.getInt(14);
				int match3PrizePlus = drawingCursor.getInt(15);
				int match2PrizePlus = drawingCursor.getInt(16);
				int match1PrizePlus = drawingCursor.getInt(17);
				int matchPrizePlus = drawingCursor.getInt(18);
				int megaPlier = drawingCursor.getInt(19);
				MegaMillionsPlay megaMillionsPlay = new MegaMillionsPlay(' ', one, two, three, four, five, six);
				MegaMillionsDrawing megaMillionsDrawing =
						new MegaMillionsDrawing(drawingDate, jackpot, jackpotCashValue, match5Prize, match4Prize,
								match3Prize, megaMillionsPlay, match4PrizePlus, match3PrizePlus, match2PrizePlus,
								match1PrizePlus, matchPrizePlus, megaPlier);
				megaMillionsDrawings.put(drawingDate, megaMillionsDrawing);
			} while (drawingCursor.moveToNext());
		}
		drawingCursor.close();
		db.close();
		return megaMillionsDrawings;
	}

	// get all Powerball drawings
	public Map<LocalDate, PowerballDrawing> getAllPowerballDrawings() {

		Map<LocalDate, PowerballDrawing> powerballDrawings = new TreeMap<LocalDate, PowerballDrawing>();

		// Select All Query
		String drawingSelectQuery = "select d.drawing_date,p.one,p.two,p.three,p.four,p.five,p.six,d.multiplier," +
				"d.jackpot,d.jackpot_cash_value,d.match5prize,d.match4prize,d.match3prize," +
				"d.match4prize_plus,d.match3prize_plus,d.match2prize_plus,d.match1prize_plus,d.match_prize_plus " +
				"from drawings as d,plays as p " +
				"where d.lottery_type=20 and p.id=d.play_id";
		String ticketSelectQuery = "";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor drawingCursor = db.rawQuery(drawingSelectQuery, null);
		long drawingId = 0l;

		if (drawingCursor.moveToFirst()) {
			do {
				drawingId = drawingCursor.getLong(1);
				LocalDate drawingDate = new LocalDate(drawingCursor.getLong(2));
				int one = drawingCursor.getInt(3);
				int two = drawingCursor.getInt(4);
				int three = drawingCursor.getInt(6);
				int four = drawingCursor.getInt(7);
				int five = drawingCursor.getInt(8);
				int six = drawingCursor.getInt(8);
				int jackpot = drawingCursor.getInt(9);
				int jackpotCashValue = drawingCursor.getInt(10);
				int match5Prize = drawingCursor.getInt(11);
				int match4Prize = drawingCursor.getInt(12);
				int match3Prize = drawingCursor.getInt(13);
				int match4PrizePlus = drawingCursor.getInt(14);
				int match3PrizePlus = drawingCursor.getInt(15);
				int match2PrizePlus = drawingCursor.getInt(16);
				int match1PrizePlus = drawingCursor.getInt(17);
				int matchPrizePlus = drawingCursor.getInt(18);
				int powerPlay = drawingCursor.getInt(19);
				PowerballPlay powerballPlay = new PowerballPlay(' ', one, two, three, four, five, six);
				PowerballDrawing powerballDrawing =
						new PowerballDrawing(drawingDate, jackpot, jackpotCashValue, match5Prize, match4Prize,
								match3Prize, powerballPlay, match4PrizePlus, match3PrizePlus, match2PrizePlus,
								match1PrizePlus, matchPrizePlus, powerPlay);
				powerballDrawings.put(drawingDate, powerballDrawing);
			} while (drawingCursor.moveToNext());
		}
		drawingCursor.close();
		db.close();
		return powerballDrawings;
	}

	public Set<LottoTicket> getColoradoLottoTickets(LocalDate drawingDate) {

		Set<LottoTicket> tickets = new TreeSet<LottoTicket>();
		LottoTicket ticket = null;
		TreeSet<LottoPlay> plays = null;
		String ticketSelectQuery = "select t.ticket_id, p.line, p.one, p.two, p.three, p.four, p.five, p.six " +
				"from tickets as t, plays as p " +
				"where t.drawing_id=" + drawingDate.toDate().getTime() + " and p.ticket_id=t.id and t.ticket_type=30 " +
				"order by t.ticket_id, p.line";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor ticketsCursor = db.rawQuery(ticketSelectQuery, null);

		if (ticketsCursor.moveToFirst()) {
			do {
				String ticketId = ticketsCursor.getString(1);
				String line = ticketsCursor.getString(2);
				int one = ticketsCursor.getInt(3);
				int two = ticketsCursor.getInt(4);
				int three = ticketsCursor.getInt(5);
				int four = ticketsCursor.getInt(6);
				int five = ticketsCursor.getInt(7);
				int six = ticketsCursor.getInt(8);
				LottoPlay play = new LottoPlay(line.charAt(0), one, two, three, four, five, six);
				if (ticket != null ) {
					if (play.getLine() == 'A') {
						tickets.add(ticket);
						ticket.setLottoPlays(plays);
						ticket = new LottoTicket(drawingDate, ticketId);
						plays = new TreeSet<LottoPlay>();
						plays.add(play);
					} else {
						plays.add(play);
					}
				} else {
					ticket = new LottoTicket(drawingDate, ticketId);
					plays = new TreeSet<LottoPlay>();
					plays.add(play);
				}
			} while (ticketsCursor.moveToNext());
		}
		ticketsCursor.close();
		db.close();
		return tickets;
	}

	public Set<MegaMillionsTicket> getMegaMillionsTickets(LocalDate drawingDate) {

		Set<MegaMillionsTicket> tickets = new TreeSet<MegaMillionsTicket>();
		MegaMillionsTicket ticket = null;
		TreeSet<MegaMillionsPlay> plays = null;
		String ticketSelectQuery = "select t.ticket_id, t.multiplier, p.line, p.one, p.two, p.three, p.four, " +
				"p.five, p.six " +
				"from tickets as t, plays as p " +
				"where t.drawing_id=" + drawingDate.toDate().getTime() + " and p.ticket_id=t.id and t.ticket_type = 10" +
				"order by t.ticket_id, p.line";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor ticketsCursor = db.rawQuery(ticketSelectQuery, null);

		if (ticketsCursor.moveToFirst()) {
			do {
				String ticketId = ticketsCursor.getString(1);
				boolean isMegaplierTicket = (ticketsCursor.getInt(2) == 0) ? false : true;
				String line = ticketsCursor.getString(3);
				int one = ticketsCursor.getInt(4);
				int two = ticketsCursor.getInt(5);
				int three = ticketsCursor.getInt(6);
				int four = ticketsCursor.getInt(7);
				int five = ticketsCursor.getInt(8);
				int six = ticketsCursor.getInt(9);
				MegaMillionsPlay play = new MegaMillionsPlay(line.charAt(0), one, two, three, four, five, six);
				if (ticket != null ) {
					if (play.getLine() == 'A') {
						tickets.add(ticket);
						ticket.setMegaMillionsPlays(plays);
						ticket = new MegaMillionsTicket(drawingDate, ticketId, isMegaplierTicket, plays);
						plays = new TreeSet<MegaMillionsPlay>();
						plays.add(play);
					} else {
						plays.add(play);
					}
				} else {
					ticket = new MegaMillionsTicket(drawingDate, ticketId, isMegaplierTicket, plays);
					plays = new TreeSet<MegaMillionsPlay>();
					plays.add(play);
				}
			} while (ticketsCursor.moveToNext());
		}
		ticketsCursor.close();
		db.close();
		return tickets;
	}

	public long getDrawingId(int lotteryType, LocalDate drawingDate) {

		long drawingId = 0l;
		String selectQuery = "select id from drawings where lottery_type =" + lotteryType +
				" and drawing_date=" + drawingDate.toDate().getTime();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			drawingId = cursor.getLong(1);
		} else {
			drawingId = 0l;
		}
		cursor.close();
		db.close();
		return drawingId;
	}

	public long updatePlay(long drawingDate, int drawingType,  Play play) {

		String whereClause = "drawing_date=? and drawing_type=? and line=?";
		String[] whereArgs = {String.valueOf(drawingDate), String.valueOf(drawingType), " "};
		int insertCount = 0;

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("one", play.getOne());
		values.put("two", play.getTwo());
		values.put("three", play.getThree());
		values.put("four", play.getFour());
		values.put("five", play.getFive());
		values.put("six", play.getSix());
		insertCount = db.update(TABLE_PLAYS, values, whereClause, whereArgs);
		db.close();
		Log.i(TAG,"insertCount:" + insertCount);  // **************************
		return insertCount;
	}

	public long updateDrawing(Drawing drawing) {

		long drawingDate = drawing.getDrawingDate().toDate().getTime();
		int drawingType = 0;
		int insertCount = 0;

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("jackpot", drawing.getJackpot());
		values.put("jackpot_cash_value", drawing.getJackpotCashValue());
		values.put("match5prize", drawing.getMatch5Prize());
		values.put("match4prize", drawing.getMatch4Prize());
		values.put("match3prize", drawing.getMatch3Prize());
		if (drawing instanceof LottoDrawing) {
			values.put("multiplier", 1);
			drawingType = Constants.COLORADO_LOTTO_TYPE;
		} else if (drawing instanceof MegaMillionsDrawing) {
			MegaMillionsDrawing MMDrawing = (MegaMillionsDrawing) drawing;
			drawingType = Constants.MEGAMILLIONS_TYPE;
			values.put("multiplier", MMDrawing.getMegaPlier());
			values.put("match4prize_plus", MMDrawing.getMatch4MMPrize());
			values.put("match3prize_plus", MMDrawing.getMatch3MMPrize());
			values.put("match2prize_plus", MMDrawing.getMatch2MMPrize());
			values.put("match1prize_plus", MMDrawing.getMatch1MMPrize());
			values.put("match_prize_plus", MMDrawing.getMatchMMPrize());
		} else if (drawing instanceof PowerballDrawing) {
			PowerballDrawing PbDrawing = (PowerballDrawing) drawing;
			drawingType = Constants.POWERBALL_TYPE;
			values.put("multiplier", PbDrawing.getPowerPlay());
			values.put("match4prize_plus", PbDrawing.getMatch4PbPrize());
			values.put("match3prize_plus", PbDrawing.getMatch3PbPrize());
			values.put("match2prize_plus", PbDrawing.getMatch2PbPrize());
			values.put("match1prize_plus", PbDrawing.getMatch1PbPrize());
			values.put("match_prize_plus", PbDrawing.getMatchPbPrize());
		}
		String whereClause = "drawing_date=? and drawing_type=?";
		String[] whereArgs = {String.valueOf(drawingDate), String.valueOf(drawingType)};
		insertCount = db.update(TABLE_DRAWINGS, values, whereClause, whereArgs);
		db.close();
		Log.i(TAG,"insertCount:" + insertCount);  // **************************
		return insertCount;
	}
}
