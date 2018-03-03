package com.byderbeck.lotterydata.dao;

import com.byderbeck.lotterydata.drawing.LottoDrawing;
import com.byderbeck.lotterydata.drawing.MegaMillionsDrawing;
import com.byderbeck.lotterydata.drawing.PowerballDrawing;

import org.joda.time.LocalDate;

import java.util.Map;

public interface TicketDAO {

	public Map<LocalDate, LottoDrawing> getLottoTickets(Map<LocalDate, LottoDrawing> lottoDrawings);

	public Map<LocalDate, PowerballDrawing> getPowerballTickets(Map<LocalDate, PowerballDrawing> powerballDrawings);

	public Map<LocalDate, MegaMillionsDrawing> getMegaMillionsTickets(Map<LocalDate, MegaMillionsDrawing> megaMillionsDrawings);
}
