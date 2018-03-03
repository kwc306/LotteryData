package com.byderbeck.lotterydata.play;

import android.util.Log;

/**
 * Created by lyman on 10/12/2016.
 */

public class PowerballPlay extends Play implements Comparable<PowerballPlay> {

	private static final String TAG = "***" + PowerballPlay.class.getSimpleName();
	int powerBall;
	boolean isMatched;
	int quanityMatched;

	public PowerballPlay() {
		super();
	}

	public PowerballPlay(int powerBall) {
		super();
		this.powerBall = powerBall;
		numbers[5] = powerBall;
	}

	public PowerballPlay(char line, int one, int two, int three, int four, int five, int powerBall) {
		super(line, one, two, three, four, five, powerBall);
		this.setPowerBall(powerBall);
	}

	public int getPowerBall() {
		return powerBall;
	}

	public void setPowerBall(int powerBall) {
		this.powerBall = powerBall;
		numbers[5] = powerBall;
	}

	public void clearIsMatched() {
		this.isMatched = false;
		super.clearMatched();
	}

	public void setMatched(boolean value) {
		this.isMatched = value;
		super.isMatched[5] = value;
	}

	public boolean isMatched() {
		return this.isMatched;
	}

	public void setIsMatched(int index, boolean value) {
		super.setMatched(index, value);
	}

	public int getQuanityMatched() {
		return quanityMatched;
	}

	/**
	 * input shopuld be of the form A xx xx xx xx xx xx
	 */
	public void parse(String inputText) {

//		Log.i(TAG, inputText);
		inputText.trim();
		char[] array = inputText.toCharArray();
		int index = 0;
		String number = "";

		if (array[0] >= 'A' && array[0] <= 'J' && (index == 0)) {
			setLine(array[0]);
//			Log.i(TAG, "line:" + Character.toString(array[0]));
		}
		for (int i = 1; i < array.length && (index <= 5); ++i) {
			if (Character.isAlphabetic(array[i])) {
				// catch line numbers, ignore all other alphas
				if (number != "") {	//
					if (number.length() == 2) {
						setNumber(index, Integer.parseInt(number));
						if (index == 5) {
							setPowerBall(Integer.parseInt(number));
						}
						++index;
						number = "";
					} else {	// put a zero in front
						number = "0" + number;
						setNumber(index, Integer.parseInt(number));
						if (index == 5) {
							setPowerBall(Integer.parseInt(number));
						}
						++index;
						number = "";
					}
				}
			} else if (Character.isDigit(array[i])) {
				if (number == "") {
					number = Character.toString(array[i]);
				} else {
					number = number + Character.toString(array[i]);
					setNumber(index, Integer.parseInt(number));
					if (index == 5) {
						setPowerBall(Integer.parseInt(number));
					}
					++index;
					number = "";
				}
			} else if (Character.isWhitespace(array[i])) {
				if (number != "") {	//
					if (number.length() == 2) {
						setNumber(index, Integer.parseInt(number));
						if (index == 5) {
							setPowerBall(Integer.parseInt(number));
						}
						++index;
						number = "";
					} else {	// put a zero in front
						number = "0" + number;
						setNumber(index, Integer.parseInt(number));
						if (index == 5) {
							setPowerBall(Integer.parseInt(number));
						}
						++index;
						number = "";
					}
				}
			}
		}
		while (index <= 5) {	// complete playString if input is truncated
			setNumber(index, 0);
			if (index == 5) {
				setPowerBall(0);
			}
			++index;
		}
		Log.i(TAG, this.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + powerBall;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerballPlay other = (PowerballPlay) obj;
		if (powerBall != other.powerBall)
			return false;
		return true;
	}

	@Override
	public int compareTo(PowerballPlay o) {

		if (super.getLine() == o.getLine()) {
			return 0;
		} else if (super.getLine() < o.getLine()) {
			return -1;
		} else {
			return 1;
		}
	}

	@Override
	public String toString() {
		return super.toString() + String.format("%02d", powerBall) + "}";
	}

	public int compare(PowerballPlay powerballPlay) {

		int count;

		clearMatched();
		count = super.compare(powerballPlay);
		if (this.powerBall == powerballPlay.getPowerBall()) {
			this.setMatched(Boolean.TRUE);
			++count;
		}
		this.quanityMatched = count;
		return count;
	}

	public String displayString() {
		String display = "";

		for (int i = 0; i < numbers.length - 1; ++i) {
			display = display + String.format("%02d ", numbers[i]);
		}
		display = display + String.format("%02d ", this.powerBall);
		return display;
	}
}
