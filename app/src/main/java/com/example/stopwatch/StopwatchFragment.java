package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class StopwatchFragment extends Fragment {

    private TextView stopwatchTextView;
    private Button startButton, stopButton, resetButton, exitButton;
    private boolean isRunning = false;
    private long milliseconds = 0;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        stopwatchTextView = view.findViewById(R.id.stopwatchTextView);
        startButton = view.findViewById(R.id.startButton);
        stopButton = view.findViewById(R.id.stopButton);
        resetButton = view.findViewById(R.id.resetButton);
        exitButton = view.findViewById(R.id.exitButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopwatch();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStopwatch();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void startStopwatch() {
        if (!isRunning) {
            isRunning = true;
            runnable = new Runnable() {
                @Override
                public void run() {
                    milliseconds += 100;
                    int seconds = (int) (milliseconds / 1000);
                    int minutes = seconds / 60;
                    int hours = minutes / 60;

                    seconds %= 60;
                    minutes %= 60;

                    String time = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds % 1000);
                    stopwatchTextView.setText(time);

                    if (isRunning) {
                        handler.postDelayed(this, 100);
                    }
                }
            };
            handler.post(runnable);
        }
    }

    private void stopStopwatch() {
        isRunning = false;
        handler.removeCallbacks(runnable);
    }

    private void resetStopwatch() {
        isRunning = false;
        handler.removeCallbacks(runnable);
        milliseconds = 0;
        stopwatchTextView.setText("00:00:00.000");
    }
}
