package com.byderbeck.lotterydata.ticket;

import android.os.Parcel;
import android.os.Parcelable;

import com.byderbeck.lotterydata.play.Play;

import org.joda.time.LocalDate;

import java.util.TreeSet;

/**
 * Created by lyman on 5/11/2017.
 */

public class Ticket implements Parcelable {

	public LocalDate drawingDate;
	public String id;
	public TreeSet<Play> plays;

	public Ticket() {

	}

	public Ticket(LocalDate drawingDate, String ticketId) {

		this.drawingDate = drawingDate;
		this.id = ticketId;
	}

	public Ticket(LocalDate drawingDate, String ticketId, TreeSet<Play> plays) {

		this.drawingDate = drawingDate;
		this.id = ticketId;
		this.plays = plays;
	}

	public LocalDate getDrawingDate() {
		return drawingDate;
	}

	public void setDrawingDate(LocalDate drawingDate) {
		this.drawingDate = drawingDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TreeSet<Play> getPlays() {
		return plays;
	}

	public void setPlays(TreeSet<Play> plays) {
		this.plays = plays;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.drawingDate);
		dest.writeString(this.id);
		dest.writeSerializable(this.plays);
	}

	protected Ticket(Parcel in) {
		this.drawingDate = (LocalDate) in.readSerializable();
		this.id = in.readString();
		this.plays = (TreeSet) in.readSerializable();
	}

	public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
		@Override
		public Ticket createFromParcel(Parcel source) {
			return new Ticket(source);
		}

		@Override
		public Ticket[] newArray(int size) {
			return new Ticket[size];
		}
	};
}
