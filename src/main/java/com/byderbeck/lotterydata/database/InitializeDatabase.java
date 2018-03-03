package com.byderbeck.lotterydata.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.byderbeck.lotterydata.Constants;

/**
 * Created by lyman on 5/8/2017.
 */

public class InitializeDatabase {

	private static final String TAG = "***" + InitializeDatabase.class.getSimpleName();
	DatabaseHelper dbHelper;
	SQLiteDatabase lotteryDB;
	Context appContext;

	private static final String TABLE_LOTTERY_TYPES = "lottery_types";

	public InitializeDatabase() {

	}

	public InitializeDatabase(Context appContext) {
		this.appContext = appContext;
	}

	public void initialize(boolean isReset) {

		dbHelper = DatabaseHelper.getInstance(this.appContext);
		this.lotteryDB = dbHelper.getWritableDatabase();
		int init = 0;
		Log.i(TAG, "Initializing database");
		if (isReset) {
			Log.i(TAG, "Resetting database");
			dbHelper.onUpgrade(lotteryDB, init, init + 1);
		}
	}

	public long setupLotteryTypes() {

		long lotteryId = 0l;

		lotteryId = dbHelper.insertLotteryType(Constants.MEGAMILLIONS_TYPE, "Mega Millions");
		lotteryId = dbHelper.insertLotteryType(Constants.POWERBALL_TYPE, "Powerball");
		lotteryId = dbHelper.insertLotteryType(Constants.COLORADO_LOTTO_TYPE, "Colorado Lotto");

		return lotteryId;
	}
}
