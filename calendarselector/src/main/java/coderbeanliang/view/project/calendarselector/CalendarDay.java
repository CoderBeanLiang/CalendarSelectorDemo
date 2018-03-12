package coderbeanliang.view.project.calendarselector;

/**
 * Created by Liao on 2018/1/5 0005.
 *
 * 日信息
 */

public class CalendarDay {

    private int year;
    private int month;
    private int day;

    private boolean enable;
    private int dateNumber;// 格式 20180108，用于日期比较
    private String lunarText;

    public CalendarDay() {
    }

    public CalendarDay(int year, int month, int day) {
        this(year, month, day, true);
    }

    public CalendarDay(int year, int month, int day, boolean enable) {
        this(year, month, day, enable, null);
    }

    public CalendarDay(int year, int month, int day, boolean enable, String lunarText) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.enable = enable;
        this.lunarText = lunarText;
        dateNumber = year * 10000 + month * 100 + day;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDateNumber() {
        return dateNumber;
    }

    public void setDateNumber(int dateNumber) {
        this.dateNumber = dateNumber;
    }

    public String getLunarText() {
        return lunarText;
    }

    public void setLunarText(String lunarText) {
        this.lunarText = lunarText;
    }
}
