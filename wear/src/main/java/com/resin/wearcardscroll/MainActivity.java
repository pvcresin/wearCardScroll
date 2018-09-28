package com.resin.wearcardscroll;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends WearableActivity
        implements MessageApi.MessageListener {
    String Test = "test";
    long start, end;
    double time;
    int num = 5;
    WearableListView listView;

    String TAG = "Watch";

    GoogleApiClient mGoogleApiClient;
    String MESSAGE = "/message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Log.d(TAG, "Google Api Client connected");

                        Wearable.MessageApi.addListener(mGoogleApiClient, MainActivity.this);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                }).build();

        listView = (WearableListView) this.findViewById(R.id.list_view);

        listView.setAdapter(new Adapter(this));

        listView.setEnableGestureNavigation(true);

        listView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();

                TextView v = (TextView) viewHolder.itemView.findViewById(R.id.text_title);

                Log.d(TAG, "onClick " + position);
            }

            @Override
            public void onTopEmptyRegionClick() {
                start = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "start !!", Toast.LENGTH_LONG).show();
                Log.d(Test, "start: " + start);
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num += 5;
                if (num == 20) num = 5;

                listView.setAdapter(new Adapter(MainActivity.this));

                Toast.makeText(MainActivity.this, "num: " + num, Toast.LENGTH_SHORT).show();
            }
        });

    }

    class Adapter extends WearableListView.Adapter {
        private Context context;
        private LayoutInflater inflater;

        Adapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(this.context);
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new WearableListView.ViewHolder(
                    this.inflater.inflate(R.layout.list_view_item, viewGroup, false)
            );
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
            Log.d("Adapter", "index: " + i);

            if (i == num + 1) {
                end = System.currentTimeMillis();
                Log.d(Test, "end: " + end);

                time = (end - start)/(1000.0);
                Toast.makeText(MainActivity.this, "" + time, Toast.LENGTH_LONG).show();
                Log.d(Test, "end - start: " + time + "\n");
            }

            TextView tv = (TextView) viewHolder.itemView.findViewById(R.id.text_title);
            tv.setText("index: " + i);
        }

        @Override
        public int getItemCount() {
            return 20 + 2;
        }

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {  // move,210.0,220.0
        if (MESSAGE.equals(messageEvent.getPath())) {
            String msg = new String(messageEvent.getData());

            String[] data = msg.split(",");

            Log.d(TAG, data[0] + " " + data[1] + " " + data[2]);

            float x = Float.parseFloat(data[1]), y = Float.parseFloat(data[2]);
            long time = SystemClock.uptimeMillis();
            int dur = 1; // duration

            switch (data[0]) {
                case "down":
//                    Log.d(TAG, "down");
//                    dispatchTouchEvent(
//                            MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_DOWN, x, y, 0));
                    break;

                case  "move":
//                    Log.d(TAG, "move");
//                    dispatchTouchEvent(
//                            MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_MOVE, x, y, 0));
                    break;

                case  "up":
//                    Log.d(TAG, "up");
                    if (y > 0) {
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_DOWN, 200, 311, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_MOVE, 200, 309, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_MOVE, 200, 304, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_MOVE, 200, 287, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_MOVE, 200, 274, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_UP, 200, 274, 0));

                    } else {
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_DOWN, 200, 374, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_MOVE, 200, 287, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_MOVE, 200, 304, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_MOVE, 200, 309, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_MOVE, 200, 311, 0));

                        time += 10;
                        dispatchTouchEvent(
                                MotionEvent.obtain(time, time + dur, MotionEvent.ACTION_UP, 200, 311, 0));
                    }

                    break;
            }
        }
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        int id = event.getAction();
//
//        if (id == MotionEvent.ACTION_DOWN) {
//            Log.d(TAG, "down: " + event.getX() + ", " + event.getY() + ", time: " + event.getEventTime());
//        } else if (id == MotionEvent.ACTION_MOVE) {
//            Log.d(TAG, "move: " + event.getX() + ", " + event.getY() + ", time: " + event.getEventTime());
//        } else if (id == MotionEvent.ACTION_UP) {
//            Log.d(TAG, "up: " + event.getX() + ", " + event.getY() + ", time: " + event.getEventTime());
//        }
//
//        return super.dispatchTouchEvent(event);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
    }

}
