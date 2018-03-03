package com.byderbeck.lotterydata;

/**
 * Created by lyman on 2/17/2018.
 */

public class Constants {

    public Constants() {
    }

    public static final String LOTTO_DRAWINGS = "D:/Downloads\\Lotto\\Data\\lottoDrawings.csv";
    public static final String MEGA_MILLIONS_DRAWINGS = "D:\\Downloads\\Lotto\\Data\\megaMillionsDrawings.csv";
    public static final String POWERBALL_DRAWINGS = "D:\\Downloads\\Lotto\\Data\\powerballDrawings.csv";

    public static final String LOTTO_TICKETS = "D:\\Downloads\\Lotto\\Data\\lottoTickets.csv";
    public static final String MEGA_MILLIONS_TICKETS = "D:\\Downloads\\Lotto\\Data\\megaMillionsTickets.csv";
    public static final String POWERBALL_TICKETS = "D:\\Downloads\\Lotto\\Data\\powerballTickets.csv";
//	public static final String LOTTO_TICKETS= "D:\\Downloads\\Lotto\\Data\\lottoTicketsTest.csv";
//	public static final String MEGA_MILLIONS_TICKETS= "D:\\Downloads\\Lotto\\Data\\megaMillionsTicketsTest.csv";
//	public static final String POWERBALL_TICKETS= "D:\\Downloads\\Lotto\\Data\\powerballTicketsTest.csv";

    public static final int LOTTO_TICKET_PRICE = 1;
    public static final int POWERBALL_TICKET_PRICE = 2;
    public static final int POWERBALL_TICKET_POWERPLAY_PRICE = 3;
    public static final int MEGA_MILLIONS_TICKET_PRICE = 1;
    public static final int MEGA_MILLIONS_TICKET_MEGAPLAY_PRICE = 2;
    public static final String SETTINGS = "com.byderbeck.lotteryverify.SettingsFile";

    public static final String DATE_MEGA_MILLIONS_WENT_TO_TWO_DOLLARS = "20171031";
    public static final String DATE_POWREBALL_WENT_TO_TWO_DOLLARS = "20120115";

    public static final int COLORADO_LOTTO_TYPE = 30;
    public static final int MEGAMILLIONS_TYPE = 10;
    public static final int POWERBALL_TYPE = 20;

    public enum LotteryTypes {COLORADOLOTTO, MEGAMILLIONS, POWERBALL}
}
