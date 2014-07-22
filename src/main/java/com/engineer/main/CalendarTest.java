package com.engineer.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTest {

	private static boolean isBatchSubmitTime(Calendar nowCalendar, Calendar startCalendar, Calendar endCalendar) {
		int nowHourOfDay = nowCalendar.get(Calendar.HOUR_OF_DAY);
		int nowMinuteOfDay = nowCalendar.get(Calendar.MINUTE);
		int nowTime = nowHourOfDay * 60 + nowMinuteOfDay;

		int startHourOfDay = startCalendar.get(Calendar.HOUR_OF_DAY);
		int startMinuteOfDay = startCalendar.get(Calendar.MINUTE);
		int startTime = startHourOfDay * 60 + startMinuteOfDay;

		int endHourOfDay = endCalendar.get(Calendar.HOUR_OF_DAY);
		int endMinuteOfDay = endCalendar.get(Calendar.MINUTE);
		int endTime = endHourOfDay * 60 + endMinuteOfDay;
		if (endHourOfDay < startHourOfDay) {
			if (nowTime > endTime) {
				endTime += 24 * 60;
			} else {
				startTime -= 24 * 60;
			}
		}

		boolean flag = ((nowTime >= startTime) && (nowTime < endTime));
		return flag;
	}

	public static void main(String[] args) {
		String timePattern = "hh:mm";
		String startSubmitBatchTime = "17:00";
		String endSubmitBatchTime = "10:00";
		String nowBatchTime = "00:01";
		
		Calendar startCalendar = null;
		Calendar endCalendar = null;
		try {
			Date startSubmitBatchTimeDate = new SimpleDateFormat(timePattern).parse(startSubmitBatchTime);
			startCalendar = Calendar.getInstance();
			startCalendar.setTime(startSubmitBatchTimeDate);
			
			Date endSubmitBatchTimeDate = new SimpleDateFormat(timePattern).parse(endSubmitBatchTime);
			endCalendar = Calendar.getInstance();
			endCalendar.setTime(endSubmitBatchTimeDate);
			
			Date nowTimeDate = new SimpleDateFormat(timePattern).parse(nowBatchTime);
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.setTime(nowTimeDate);
			System.out.println(isBatchSubmitTime(nowCalendar, startCalendar, endCalendar));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
