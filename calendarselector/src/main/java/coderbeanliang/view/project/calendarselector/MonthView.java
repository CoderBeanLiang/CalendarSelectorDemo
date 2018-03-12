package coderbeanliang.view.project.calendarselector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Liao on 2018/1/5 0005.
 *
 * 显示一个月的日历
 */

public class MonthView extends View {

    private int mWidth;
    private int mHeight;

    private int mDrawLeft;
    private int mCellWidth;
    private int mCellHeight;
    private int mCellRadius;
    private int mSolarTextSize;
    private int mLunarTextSize;
    private int mSolarTextOffsetY;
    private int mLunarTextOffsetY;
    private int mLineWidth;

    private Paint mPaintSolar;
    private Paint mPaintLunar;
    private Paint mPaintBgToday;
    private Paint mPaintBgSelected;
    private Paint mPaintLine;

    private int mColorDayEnable;
    private int mColorDayDisable;
    private int mColorLunarText;
    private int mColorDaySelected;
    private int mColorBgSelected;
    private int mColorLine;

    private MonthInfo mMonthInfo;
    private int mPrevMonthDayNum;
    private int mNextMonthDayNum;
    private int mCurrentMonthDayNum;
    private int mCurrentMonthDayIndex;
    private int mNextMonthDayIndex;

    private int mDayNum;
    private int mRowNum;

    private boolean mShowCurrentMonthOnly;

    private int mSelectedDay;

    private OnSelectListener mOnSelectListener;

    public interface OnSelectListener {
        void onDaySelected(CalendarDay calendarDay);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public void setSelectedDay(CalendarDay day) {
        if (day != null) {
            if (day.getYear() == mMonthInfo.getYear() && day.getMonth() == mMonthInfo.getMonth()) {
                mSelectedDay = day.getDay();
                invalidate();
            }
        }
    }

    public void clearSelectedDay() {
        mSelectedDay = 0;
        invalidate();
    }

    public void setShowCurrentMonthOnly(boolean show) {
        mShowCurrentMonthOnly = show;
    }

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMonthInfo = new MonthInfo();

        mColorDayEnable = 0xFF71ADFC;
        mColorDayDisable = 0xFFC0C8D3;
        mColorLunarText = 0xFF333333;
        mColorDaySelected = 0xFFFFFFFF;
        mColorBgSelected = 0xFFFFA947;
        mColorLine = 0xFFEBEBEB;

        mPaintSolar = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSolar.setTextAlign(Paint.Align.CENTER);
        mPaintLunar = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLunar.setTextAlign(Paint.Align.CENTER);
        mPaintBgToday = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBgSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBgSelected.setColor(mColorBgSelected);
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setColor(mColorLine);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);

        int widthMod = mWidth % 7;
        mDrawLeft = widthMod / 2;
        mCellWidth = (mWidth - widthMod) / 7;
        mCellHeight = mCellWidth;// 日期 Cell 设为正方形
        mCellRadius = mCellWidth / 2;
        mLineWidth = mCellHeight / 30;

        mSolarTextSize = mCellHeight / 3;
        mLunarTextSize = mCellHeight / 5;
        mSolarTextOffsetY = mCellRadius + mSolarTextSize / 3;// 阳历文本基线Y坐标偏移，依喜好设置
        mLunarTextOffsetY = mCellHeight * 17 / 20;// 阴历文本基线Y坐标偏移，依喜好设置
        mPaintSolar.setTextSize(mSolarTextSize);
        mPaintLunar.setTextSize(mLunarTextSize);

        mHeight = mRowNum * mCellHeight;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int curIndex;
        int curCellCenterX;
        int curCellTopY = 0;
        int curDay;
        String lunarText;

        mPaintLine.setStrokeWidth(mLineWidth);

        for (int i = 0; i < mRowNum; i++) {
            for (int j = 0; j < 7; j++) {

                curIndex = i * 7 + j;
                if (curIndex >= mDayNum) {
                    continue;
                }

                curCellCenterX = mCellRadius + mDrawLeft + j * mCellWidth;
                curCellTopY = i * mCellHeight;

                if (curIndex < mCurrentMonthDayIndex) {
                    // 上个月
                    if (mShowCurrentMonthOnly) {
                        continue;
                    }
                    mPaintSolar.setColor(mColorDayDisable);
                    mPaintLunar.setColor(mColorDayDisable);
                    curDay = mMonthInfo.getPrevMonthDayList().get(curIndex).getDay();
                    lunarText = mMonthInfo.getPrevMonthDayList().get(curIndex).getLunarText();
                } else if (curIndex > mNextMonthDayIndex - 1) {
                    // 下个月
                    if (mShowCurrentMonthOnly) {
                        continue;
                    }
                    mPaintSolar.setColor(mColorDayDisable);
                    mPaintLunar.setColor(mColorDayDisable);
                    curDay = mMonthInfo.getNextMonthDayList().get(curIndex - mNextMonthDayIndex).getDay();
                    lunarText = mMonthInfo.getNextMonthDayList().get(curIndex - mNextMonthDayIndex).getLunarText();
                } else {
                    // 当月
                    boolean enable = mMonthInfo.getCurrentMonthDayList().get(curIndex - mCurrentMonthDayIndex).isEnable();
                    mPaintSolar.setColor(enable ? mColorDayEnable : mColorDayDisable);
                    mPaintLunar.setColor(enable ? mColorDayEnable : mColorDayDisable);
                    curDay = mMonthInfo.getCurrentMonthDayList().get(curIndex - mCurrentMonthDayIndex).getDay();
                    lunarText = mMonthInfo.getCurrentMonthDayList().get(curIndex - mCurrentMonthDayIndex).getLunarText();
                    if (curDay == mSelectedDay) {
                        // 选中的日期
                        mPaintSolar.setColor(mColorDaySelected);
                        mPaintLunar.setColor(mColorDaySelected);
                        int rectTop = i * mCellHeight + mLineWidth;
                        int rectBottom = (i + 1) * mCellHeight - mLineWidth;
                        canvas.drawRect(curCellCenterX - mCellRadius, rectTop, curCellCenterX + mCellRadius, rectBottom, mPaintBgSelected);
                    }
                }

                // 画日期
                canvas.drawText(String.valueOf(curDay), curCellCenterX, curCellTopY + mSolarTextOffsetY, mPaintSolar);
                // 画农历（农历文本来源于 lunarText 可能是农历日期也可能是节假日，取决于个人设置）
                if (lunarText != null && !lunarText.isEmpty()) {
                    canvas.drawText(lunarText, curCellCenterX, curCellTopY + mLunarTextOffsetY , mPaintLunar);
                }
            }

            // 画分割线
            if (i < mRowNum - 1) {
                canvas.drawLine(0, curCellTopY + mCellHeight, mWidth, curCellTopY + mCellHeight, mPaintLine);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                click(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    private void click(int x, int y) {
        int position = checkClickedDay(x, y);
        if (position != -1) {
            CalendarDay day = mMonthInfo.getCurrentMonthDayList().get(position);
            if (day.isEnable()) {
                setSelectedDay(day);
                if (mOnSelectListener != null) {
                    mOnSelectListener.onDaySelected(day);
                }
            }
        }
    }

    private int checkClickedDay(int x, int y) {
        int rowIndex = y / mCellHeight;
        int columnIndex = (x - mDrawLeft) / mCellWidth;
        int index = rowIndex * 7 + columnIndex;
        if (index >= mCurrentMonthDayIndex && index < mNextMonthDayIndex) {
            return index - mCurrentMonthDayIndex;
        }
        return -1;
    }

    public void setMonthInfo(MonthInfo monthInfo) {
        mMonthInfo = monthInfo;
        mPrevMonthDayNum = monthInfo.getPrevMonthDayList() == null ? 0 : monthInfo.getPrevMonthDayList().size();
        mNextMonthDayNum = monthInfo.getNextMonthDayList() == null ? 0 : monthInfo.getNextMonthDayList().size();
        mCurrentMonthDayNum = monthInfo.getCurrentMonthDayList() == null ? 0 : monthInfo.getCurrentMonthDayList().size();

        mCurrentMonthDayIndex = mPrevMonthDayNum;
        mNextMonthDayIndex = mPrevMonthDayNum + mCurrentMonthDayNum;

        mDayNum = mPrevMonthDayNum + mCurrentMonthDayNum + mNextMonthDayNum;
        mRowNum = mDayNum / 7 + (mDayNum % 7 > 0 ? 1 : 0);
        requestLayout();
        invalidate();
    }
}
