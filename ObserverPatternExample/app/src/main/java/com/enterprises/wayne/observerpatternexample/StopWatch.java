package com.enterprises.wayne.observerpatternexample;

import android.os.Handler;

/**
 * Created by ahmed on 7/31/2016.
 * ticks every second and increments the mCount
 */
public class StopWatch
{
    private int mCount;
    private Listener mListener;

    public StopWatch()
    {
        mCount = 0;
    }

    /**
     * registers to be invoked on tick event
     */
    public void setListener(Listener listener)
    {
        mListener = listener;
    }

    /**
     * schedules a tick every second
     */
    public void start()
    {
        tick();
    }

    private void tick()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                // increment the count
                mCount++;

                // notify the listener
                if (mListener != null)
                    mListener.tick(mCount);

                // schedule another tick
                tick();
            }
        }, 1000);
    }


    /**
     * To register to be invoked at every tick, implement this interface and call setmListener()
     */
    public interface Listener
    {
        void tick(int count);
    }
}
