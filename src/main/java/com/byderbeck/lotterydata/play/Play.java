package com.byderbeck.lotterydata.play;

/**
 * Created by lyman on 10/12/2016.
 */

import java.util.Arrays;

public class Play {

	char line;
	int one;
	int two;
	int three;
	int four;
	int five;
	int six;
	int[] numbers = new int[6];
	boolean[] isMatched = new boolean[6];
	int quanityMatched;
	int prize;

	public Play() {
		super();
	}

	public Play(char line, int[] numbers) {
		super();
		this.line = line;
		this.numbers = numbers;
		this.prize = 0;
	}

	public Play(char line, int one, int two, int three, int four, int five, int six) {
		super();
		this.line = line;
		this.numbers[0] = one;
		this.numbers[1] = two;
		this.numbers[2] = three;
		this.numbers[3] = four;
		this.numbers[4] = five;
		this.numbers[5] = six;
		this.one = one;
		this.two = two;
		this.three = three;
		this.four = four;
		this.five = five;
		this.six = six;
		this.quanityMatched = 0;
		this.prize = 0;
	}

	public char getLine() {
		return line;
	}

	public void setLine(char line) {
		this.line = line;
	}

	public int getOne() {
		return one;
	}

	public void setOne(int one) {
		this.one = one;
		this.numbers[0] = one;
	}

	public int getTwo() {
		return two;
	}

	public void setTwo(int two) {
		this.two = two;
		this.numbers[1] = two;
	}

	public int getThree() {
		return three;
	}

	public void setThree(int three) {
		this.three = three;
		this.numbers[2] = three;
	}

	public int getFour() {
		return four;
	}

	public void setFour(int four) {
		this.four = four;
		this.numbers[3] = four;
	}

	public int getFive() {
		return five;
	}

	public void setFive(int five) {
		this.five = five;
		this.numbers[4] = five;
	}

	public int getSix() {
		return six;
	}

	public void setSix(int six) {
		this.six = six;
		this.numbers[5] = six;
	}

	public int getNumber(int index) { return numbers[index]; }

	public void setNumber (int index, int number) {

		numbers[index] = number;
		switch (index) {
			case 0:
				this.one = number;
				break;
			case 1:
				this.two = number;
				break;
			case 2:
				this.three = number;
				break;
			case 3:
				this.four = number;
				break;
			case 4:
				this.five = number;
				break;
			case 5:
				this.six = number;
				break;
			default:

		}
	}

	public int[] getNumbers() {
		return numbers;
	}

	public void setNumbers(int[] numbers) {

		this.numbers = numbers;
		this.one = numbers[0];
		this.two = numbers[1];
		this.three = numbers[2];
		this.four = numbers[3];
		this.five = numbers[4];
		this.six = numbers[5];
	}

	public boolean[] getMatched() {
		return isMatched;
	}

	public boolean getMatched(int index) {
		return isMatched[index];
	}

	public void setMatched(int index, boolean value) {
		isMatched[index] = value;
	}

	public void clearMatched() {
		Arrays.fill(isMatched, Boolean.FALSE);
	}

	public int getQuanityMatched() {
		return quanityMatched;
	}

	public void setQuanityMatched(int quanityMatched) {
		this.quanityMatched = quanityMatched;
	}

	public int getPrize() {
		return prize;
	}

	public void setPrize(int prize) {
		this.prize = prize;
	}

	public String displayString() {
		return 	String.format("%1c %02d %02d %02d %02d %02d ", line, one, two, three, four, five);
	}

	@Override
	public String toString() {
		return "{" + String.format("%1c %02d %02d %02d %02d %02d ", line, one, two, three, four, five);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + line;
		result = prime * result + Arrays.hashCode(numbers);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Play other = (Play) obj;
		if (line != other.line)
			return false;
		if (!Arrays.equals(numbers, other.numbers))
			return false;
		return true;
	}

	public int compare(Play play) {

		int count = 0;
		int[] playNumbers = play.getNumbers();

		clearMatched();
		for (int i = 0; i < this.numbers.length - 1; ++i) {
			for (int j = 0; j < playNumbers.length - 1; ++j) {
				if (this.numbers[i] == playNumbers[j]) {
					setMatched(i, Boolean.TRUE);
					++count;
				}
			}
		}
		this.quanityMatched = count;
		return count;
	}
}
