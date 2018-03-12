package coderbeanliang.view.project.calendarselector;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Liao on 2018/1/9 0009.
 *
 * 用于获取月份数据
 */

public class CalendarUtils {
    private static final String TAG = CalendarUtils.class.getSimpleName();

    /**
     * 传入起止日期的年月日，获取月份信息列表
     * 该方法在4.4旧手机上获取两年月份耗时30ms，获取大量月份时建议用子线程，或逐月的方式
     *
     * @param startYear 起始日期年
     * @param startMonth 起始日期月
     * @param startDay 起始日期日
     * @param endYear 结束日期年
     * @param endMonth 结束日期月
     * @param endDay 结束日期日
     * @return 月份信息列表
     */
    public static List<MonthInfo> getMonthList(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {

        // 判断参数异常
        if (startYear > endYear) {
            Log.e(TAG, "Wrong startYear or endYear in Method getMonthList(...)");
            return null;
        } else if (startYear == endYear){
            if (startMonth > endMonth) {
                Log.e(TAG, "Wrong startMonth or endMonth in Method getMonthList(...)");
                return null;
            } else if (startMonth == endMonth){
                if (startDay > endDay) {
                    Log.e(TAG, "Wrong startDay or endDay in Method getMonthList(...)");
                    return null;
                }
            }
        }

        // 参数正常
        List<MonthInfo> monthList = new ArrayList<>();

        boolean isEnd = false;
        int sDay;
        int eDay;
        int year = startYear;
        int month = startMonth;
        // 月份太多应该会卡顿，这里限制了100年
        for (int i = 0; i < 1200; i++) {
            sDay = 0;
            eDay = 32;
            if (year == startYear && month == startMonth) {
                sDay = startDay;
            }
            if (year == endYear && month == endMonth) {
                eDay = endDay;
                isEnd = true;
            }
            MonthInfo info = getMonthInfo(year, month, sDay, eDay);
            monthList.add(info);
            if (isEnd) {
                break;
            }
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
        return monthList;
    }

    public static MonthInfo getMonthInfo(int year, int month) {
        return getMonthInfo(year, month, 0, 32);
    }

    public static MonthInfo getMonthInfo(int year, int month, int enableDateStart, int enableDateEnd) {

        List<CalendarDay> prevMonthDayList = new ArrayList<>();
        List<CalendarDay> nextMonthDayList = new ArrayList<>();
        List<CalendarDay> curMonthDayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(year, month - 1, 1);
        int preMonthDayNum = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int curMonthDayNum = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, - preMonthDayNum - 1);

        for (int i = 0; i < preMonthDayNum; i++) {
            calendar.add(Calendar.DATE, 1);
            int calYear = calendar.get(Calendar.YEAR);
            int calMonth = calendar.get(Calendar.MONTH) + 1;
            int calDay = calendar.get(Calendar.DATE);
            String lunarText = getLunarString(calYear, calMonth, calDay);
            CalendarDay prevMonthDay = new CalendarDay(calYear, calMonth, calDay, false, lunarText);
            prevMonthDayList.add(prevMonthDay);
        }

        for (int i = 0; i < curMonthDayNum; i++) {
            calendar.add(Calendar.DATE, 1);
            int calDay = calendar.get(Calendar.DATE);
            boolean enable = (i >= enableDateStart - 1) && (i < enableDateEnd);
            String lunarText = getLunarString(year, month, calDay);
            CalendarDay curMonthDay = new CalendarDay(year, month, calDay, enable, lunarText);
            curMonthDayList.add(curMonthDay);
        }

        int nextMonthDayNum = 7 - calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < nextMonthDayNum; i++) {
            calendar.add(Calendar.DATE, 1);
            int calYear = calendar.get(Calendar.YEAR);
            int calMonth = calendar.get(Calendar.MONTH) + 1;
            int calDay = calendar.get(Calendar.DATE);
            String lunarText = getLunarString(calYear, calMonth, calDay);
            CalendarDay nextMonthDay = new CalendarDay(calYear, calMonth, calDay, false, lunarText);
            nextMonthDayList.add(nextMonthDay);
        }

        MonthInfo info = new MonthInfo();
        info.setYear(year);
        info.setMonth(month);
        info.setPrevMonthDayList(prevMonthDayList);
        info.setCurrentMonthDayList(curMonthDayList);
        info.setNextMonthDayList(nextMonthDayList);
        return info;
    }

    public static String getLunarString(int solarYear, int solarMonth, int solarDay) {
        LunarCalendarUtils.Solar solar = new LunarCalendarUtils.Solar(solarYear, solarMonth, solarDay);
        LunarCalendarUtils.Lunar lunar = LunarCalendarUtils.solarToLunar(solar);

        String lunarHoliday = LunarCalendarUtils.getLunarHoliday(lunar.lunarYear, lunar.lunarMonth, lunar.lunarDay);
        if (lunarHoliday != null && !lunarHoliday.isEmpty()) {
            return lunarHoliday;
        }

        String solarHoliday = LunarCalendarUtils.getHolidayFromSolar(solarYear, solarMonth - 1, solarDay);
        if (solarHoliday != null && !solarHoliday.isEmpty()) {
            return solarHoliday;
        }
        return null;
    }

}
