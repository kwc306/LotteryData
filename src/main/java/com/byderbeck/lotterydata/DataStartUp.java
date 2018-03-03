package com.byderbeck.lotterydata;

import android.content.Context;

/**
 * Created by lyman on 2/9/2018.
 */

public class DataStartUp {

    private static final String TAG = "***" + DataStartUp.class.getSimpleName();

    LottoData lottoData = null;
    MegaMillionsData megaMillionsData = null;
    PowerballData powerballData = null;
    Context appContext;
    private int coloradoLottoDrawingsCounter;
    private int megaMillionsDrawingsCounter;
    private int powerballDrawingsCounter;
    private int coloradoLottoTicketsCounter;
    private int megaMillionsTicketsCounter;
    private int powerballTicketsCounter;
    private int megaMillionsPlaysCounter;
    private int powerballPlaysCounter;
    private int coloradoLottoPlaysCounter;

    public DataStartUp() {
    }

    public void getCSVData() {

//        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this.appContext);

        lottoData = LottoData.getInstance();
        megaMillionsData = MegaMillionsData.getInstance();
        powerballData = PowerballData.getInstance();
    }
}
