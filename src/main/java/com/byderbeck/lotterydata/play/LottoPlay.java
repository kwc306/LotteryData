package com.byderbeck.lotterydata.play;

/**
 * Created by lyman on 10/12/2016.
 */

public class LottoPlay extends Play implements Comparable<LottoPlay> {

	private static final String TAG = "***" + LottoPlay.class.getSimpleName();

	public LottoPlay() {
		super();
	}

	public LottoPlay(char line, int one, int two, int three, int four, int five, int six) {
		super(line, one, two, three, four, five, six);
	}

	/**
	 * input shopuld be of the form A xx xx xx xx xx xx
	 */
	public static LottoPlay parse(String inputText) {

		char line = ' ';
		int[] numbers = {0, 0, 0, 0, 0, 0};
		int index = 0;
		String number = "";

//		Log.i(TAG, inputText);
		inputText.trim();
		char[] array = inputText.toCharArray();

		if (array[0] >= 'A' && array[0] <= 'J' && (index == 0)) {
			line = array[0];
//			Log.i(TAG, "line:" + Character.toString(array[0]));
		}
		for (int i = 1; i < array.length && (index <= 5); ++i) {
			if (Character.isWhitespace(array[i])) {
				if (number != "") {	//
					numbers[index++] = Integer.parseInt(number);
					number = "";
				}
			} else if (Character.isAlphabetic(array[i])) {
				// catch numbers, ignore all other alphas
				if (number != "") {	//
					numbers[index++] = Integer.parseInt(number);
					number = "";
				}
			} else if (Character.isDigit(array[i])) {
				if (number == "") {		// starting new number
					number = Character.toString(array[i]);
				} else {
					number = number + Character.toString(array[i]);
					numbers[index++] = Integer.parseInt(number);
					number = "";
				}
			}
		}
		//Log.i(TAG, line + ", " + numbers[0] + ", " + numbers[1] + ", " + numbers[2] + ", " +  numbers[3] + ", " +
		//		numbers[4] + ", " +  numbers[5]);
		return new LottoPlay(line, numbers[0], numbers[1], numbers[2], numbers[3], numbers[4], numbers[5]);
	}

	public String displayString() {
		return super.displayString() + String.format("%02d", super.six);
	}

	@Override
	public int hashCode() {
		return (super.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		return(super.equals(obj));
	}

	@Override
	public String toString() {
		return (super.toString() + String.format("%02d", super.six)) + "}";
	}

	@Override
	public int compareTo(LottoPlay o) {
		if (super.getLine() == o.getLine()) {
			return 0;
		} else if (super.getLine() < o.getLine()) {
			return -1;
		} else {
			return 1;
		}
	}
}
