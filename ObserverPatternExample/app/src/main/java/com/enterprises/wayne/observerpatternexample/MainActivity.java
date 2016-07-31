package com.enterprises.wayne.observerpatternexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements StopWatch.Listener
{

    /* UI */
    TextView mTextViewTicks;

    /* fields */
    private StopWatch mStopWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference views
        mTextViewTicks = (TextView) findViewById(R.id.text_view_ticks);

        // setup the stopwatch
        mStopWatch = new StopWatch();
        mStopWatch.setListener(MainActivity.this);
        mStopWatch.start();
    }


    @Override
    public void tick(int count)
    {
        // update the text view
        mTextViewTicks.setText(count + "");
    }
}
