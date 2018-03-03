package com.byderbeck.lotterydata.dao;

/**
 * Created by lyman on 10/12/2016.
 */

import com.byderbeck.lotterydata.drawing.LottoDrawing;
import com.byderbeck.lotterydata.drawing.MegaMillionsDrawing;
import com.byderbeck.lotterydata.drawing.PowerballDrawing;

import org.joda.time.LocalDate;

import java.util.Map;

public interface DrawingDAO {

	public Map<LocalDate, LottoDrawing> getLottoDrawings();

	public Map<LocalDate, MegaMillionsDrawing> getMegaMillionsDrawings();

	public Map<LocalDate, PowerballDrawing> getPowerballDrawings();

}