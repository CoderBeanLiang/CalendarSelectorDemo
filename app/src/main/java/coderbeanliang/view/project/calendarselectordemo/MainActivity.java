package coderbeanliang.view.project.calendarselectordemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import coderbeanliang.view.project.calendarselector.CalendarDay;
import coderbeanliang.view.project.calendarselector.CalendarUtils;
import coderbeanliang.view.project.calendarselector.MonthInfo;
import coderbeanliang.view.project.calendarselector.MonthView;

public class MainActivity extends AppCompatActivity implements MonthView.OnSelectListener {

    private MonthView mMonthView;
    private MonthInfo mMonthInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMonthView = (MonthView) findViewById(R.id.month_view);
        mMonthView.setShowCurrentMonthOnly(true);
        mMonthView.setOnSelectListener(this);

        List<MonthInfo> monthInfoList = CalendarUtils.getMonthList(2018, 1, 9, 2020, 3, 32);

        mMonthInfo = CalendarUtils.getMonthInfo(2018, 1, 6, 22);
        mMonthView.setMonthInfo(mMonthInfo);
    }

    @Override
    public void onDaySelected(CalendarDay calendarDay) {
        //
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
