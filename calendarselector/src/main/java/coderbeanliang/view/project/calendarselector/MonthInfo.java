package coderbeanliang.view.project.calendarselector;

import java.util.List;

/**
 * Created by Liao on 2018/1/5 0005.
 *
 * 保存月份信息
 */

public class MonthInfo {

    private int year;
    private int month;

    private List<CalendarDay> prevMonthDayList;
    private List<CalendarDay> nextMonthDayList;
    private List<CalendarDay> currentMonthDayList;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<CalendarDay> getPrevMonthDayList() {
        return prevMonthDayList;
    }

    public void setPrevMonthDayList(List<CalendarDay> prevMonthDayList) {
        this.prevMonthDayList = prevMonthDayList;
    }

    public List<CalendarDay> getNextMonthDayList() {
        return nextMonthDayList;
    }

    public void setNextMonthDayList(List<CalendarDay> nextMonthDayList) {
        this.nextMonthDayList = nextMonthDayList;
    }

    public List<CalendarDay> getCurrentMonthDayList() {
        return currentMonthDayList;
    }

    public void setCurrentMonthDayList(List<CalendarDay> currentMonthDayList) {
        this.currentMonthDayList = currentMonthDayList;
    }
}
