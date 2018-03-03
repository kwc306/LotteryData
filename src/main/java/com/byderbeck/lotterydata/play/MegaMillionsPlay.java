package com.byderbeck.lotterydata.play;

/**
 * Created by lyman on 10/12/2016.
 */

public class MegaMillionsPlay extends Play implements Comparable<MegaMillionsPlay> {

	int megaBall;
	boolean isMatched;
	int quanityMatched;

	public MegaMillionsPlay() {
		super();
	}

	public MegaMillionsPlay(int megaBall) {
		super();
		this.megaBall = megaBall;
		numbers[5] = megaBall;
	}

	public MegaMillionsPlay(char line, int one, int two, int three, int four, int five, int megaBall) {
		super(line, one, two, three, four, five, megaBall);
		this.setMegaBall(megaBall);
	}

	public int getMegaBall() {
		return this.megaBall;
	}

	public void setMegaBall(int megaBall) {
		this.megaBall = megaBall;
		super.numbers[5] = megaBall;
	}

	public boolean isMatched() {
		return isMatched;
	}

	public void clearIsMatched() {
		this.isMatched = false;
		super.clearMatched();
	}

	public void setMatched(boolean value) {
		this.isMatched = value;
		super.isMatched[5] = value;
	}

	public int getQuanityMatched() {
		return quanityMatched;
	}


	public int compare(MegaMillionsPlay megaMillionsPlay) {

		int count = 0;

		clearIsMatched();
		count = super.compare(megaMillionsPlay);
		if (this.megaBall == megaMillionsPlay.getMegaBall()) {
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
		display = display + String.format("%02d ", this.megaBall);
		return display;
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
							setMegaBall(Integer.parseInt(number));
						}
						++index;
						number = "";
					} else {	// put a zero in front
						number = "0" + number;
						setNumber(index, Integer.parseInt(number));
						if (index == 5) {
							setMegaBall(Integer.parseInt(number));
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
						setMegaBall(Integer.parseInt(number));
					}
					++index;
					number = "";
				}
			} else if (Character.isWhitespace(array[i])) {
				if (number != "") {	//
					if (number.length() == 2) {
						setNumber(index, Integer.parseInt(number));
						if (index == 5) {
							setMegaBall(Integer.parseInt(number));
						}
						++index;
						number = "";
					} else {	// put a zero in front
						number = "0" + number;
						setNumber(index, Integer.parseInt(number));
						if (index == 5) {
							setMegaBall(Integer.parseInt(number));
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
				setMegaBall(0);
			}
			++index;
		}
//		Log.i(TAG, this.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + megaBall;
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
		MegaMillionsPlay other = (MegaMillionsPlay) obj;
		if (megaBall != other.megaBall)
			return false;
		return true;
	}

	@Override
	public int compareTo(MegaMillionsPlay o) {

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
		return super.toString() + String.format("%02d", this.megaBall) + "}";
	}

}
