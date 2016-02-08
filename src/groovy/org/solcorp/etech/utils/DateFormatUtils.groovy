package org.solcorp.etech.utils

import java.text.DateFormat;
import java.text.SimpleDateFormat

import org.solcorp.etech.Constants;

class DateFormatUtils {

	def static Date getDateFromString(String dateString, String format = Constants.DATE_FORMAT) {
		if(!dateString)
			return null
			
		SimpleDateFormat sdf = new SimpleDateFormat(format)
		sdf.setLenient(false)
		try{
			return sdf.parse(dateString)
		}catch(Exception e){
			println "Error while parsing date: ${dateString}"
			return null
		}
	}

	def static String getStringFromDate(Date date, String format = Constants.DATE_FORMAT) {
		if(!date)
			return ""
		SimpleDateFormat sdf = new SimpleDateFormat(format)
		sdf.setLenient(false)
		sdf.format(date)
	} 
	
	def static String dateConvertFromString(String dateString, String format = Constants.DATE_FORMAT, String formatFrom = Constants.DATETIME_FORMAT_YYYY_MM_DD) {
		
		if(!dateString) {
			return null
		}
		
		Date formatDate = new SimpleDateFormat(formatFrom).parse(dateString);	
	
		SimpleDateFormat dt1 = new SimpleDateFormat(format);
	
		return dt1.format(formatDate)
	
	}
	
	def static getPredefinedDates(){
		
		GregorianCalendar gc = new GregorianCalendar()
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT)
		
		def today1 = ""
		def today2 = ""
		def yesterday1 = ""
		def yesterday2 = ""
		def currentweek1 = ""
		def currentweek2 = ""
		def currentmonth1 = ""
		def currentmonth2 = ""
		def currentqtr1 = ""
		def currentqtr2 = ""
		def currentyear1 = ""
		def currentyear2 = ""
		def lastweek1 = ""
		def lastweek2 = ""
		def lastmonth1 = ""
		def lastmonth2 = ""
		def lastqtr1 = ""
		def lastqtr2 = ""
		def lastyear1 = ""
		def lastyear2 = ""
		
		//Today
		java.util.Date today = gc.getTime();
		today1 = sdf.format(today);
		today2 = sdf.format(today);
		
		//Yesterday
		gc = new GregorianCalendar();
		gc.add(gc.DAY_OF_MONTH, -1);
		java.util.Date yesterday = gc.getTime();
		yesterday1 = sdf.format(yesterday);
		yesterday2 = sdf.format(yesterday);
		
		//current week
		gc = new GregorianCalendar();
		int cw;
		cw = gc.get(gc.DAY_OF_WEEK);
		gc.add(gc.DAY_OF_MONTH, -cw );
		java.util.Date currentweek = gc.getTime();
		currentweek1 = sdf.format(currentweek);
		currentweek2 = sdf.format(today);
		
		//current month
		gc = new GregorianCalendar();
		int cm = ((gc.get(gc.DAY_OF_MONTH)) - 1);
		gc.add(gc.DATE, -cm);
		java.util.Date currentmonth = gc.getTime();
		currentmonth1 = sdf.format(currentmonth);
		currentmonth2 = sdf.format(today);
		
		//current quarter
		gc = new GregorianCalendar();
		int cy = gc.get(gc.YEAR);
		int cq = gc.get(gc.MONTH) + 1;
		int tmp = cq % 3;
		if(tmp == 0 ) tmp = 3;
		gc.set(cy, cq-tmp ,1);
		gc.setTime(gc.getTime());
		java.util.Date currentqtr = gc.getTime();
		currentqtr1 = sdf.format(currentqtr);
		currentqtr2 = sdf.format(today);
		
		//current year
		gc.set(cy, Calendar.JANUARY, 1);
		gc.setTime(gc.getTime()); // to avoid a bug</b>
		java.util.Date currentyear = gc.getTime();
		currentyear1 = sdf.format(currentyear);
		currentyear2 = sdf.format(today);
		
		//last week
		gc = new GregorianCalendar();
		int lw;
	    lw = gc.get(gc.DAY_OF_WEEK) +7;
		gc.add(gc.DAY_OF_MONTH, -lw );
		java.util.Date lastweek = gc.getTime();
		lastweek1 = sdf.format(lastweek);		
		gc.add(gc.DAY_OF_MONTH, +6);
		
		java.util.Date lastweekend = gc.getTime();
		lastweek2 = sdf.format(lastweekend);
		
		//last month
		gc = new GregorianCalendar();
		int lmonth = gc.get(gc.MONTH) - 1;
		int lyear = gc.get(gc.YEAR);
		gc.set(lyear, lmonth, 1);
		gc.setTime(gc.getTime()); // to avoid a bug
		java.util.Date lastmonth = gc.getTime();
		lastmonth1 = sdf.format(lastmonth);
		
		//lastmonth2
		int lastm = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
		gc.set(Calendar.DAY_OF_MONTH, lastm);
		gc.setTime(gc.getTime()); // to avoid a bug
		java.util.Date lastmonthend = gc.getTime();
		lastmonth2 = sdf.format(lastmonthend);
		
		//last qtr
		gc = new GregorianCalendar();
		int lasttmp = tmp+ 3; //adjust for first day of next month
		gc.set(cy, cq-lasttmp ,1);
		gc.setTime(gc.getTime());
		java.util.Date lastqtr = gc.getTime();
		lastqtr1 = sdf.format(lastqtr);
		
		int lastq = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
		gc.set(Calendar.DAY_OF_MONTH, lastq);
		gc.add(Calendar.MONTH, 2);
		gc.setTime(gc.getTime());
		java.util.Date lastqtrend = gc.getTime();
		lastqtr2 = sdf.format(lastqtrend);
		
		//last year
		gc.set(cy - 1, Calendar.JANUARY, 1);
		gc.setTime(gc.getTime()); // to avoid a bug</b>
		java.util.Date lastyearstart = gc.getTime();
		lastyear1 = sdf.format(lastyearstart);
		
		gc.set(cy - 1, Calendar.DECEMBER, 31);
		gc.setTime(gc.getTime()); // to avoid a bug</b>
		java.util.Date lastyearend = gc.getTime();
		lastyear2 = sdf.format(lastyearend);
		
		return [today1: today1, today2: today2, yesterday1: yesterday1, yesterday2: yesterday2, currentweek1: currentweek1, currentweek2: currentweek2, currentmonth1:currentmonth1, currentmonth2: currentmonth2, currentqtr1: currentqtr1, currentqtr2: currentqtr2, currentyear1:currentyear1, currentyear2: currentyear2, lastweek1: lastweek1, lastweek2: lastweek2, lastmonth1: lastmonth1, lastmonth2: lastmonth2, lastqtr1: lastqtr1, lastqtr2: lastqtr2, lastyear1: lastyear1, lastyear2: lastyear2]
		
	}
}
