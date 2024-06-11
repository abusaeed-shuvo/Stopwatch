package com.example.stopwatch;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class TimerFragment extends Fragment {

    private EditText hoursInput, minutesInput, secondsInput;
    private TextView timerTextView;
    private Button startTimerButton, stopTimerButton, resetTimerButton;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeInMilliseconds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        hoursInput = view.findViewById(R.id.hoursInput);
        minutesInput = view.findViewById(R.id.minutesInput);
        secondsInput = view.findViewById(R.id.secondsInput);
        timerTextView = view.findViewById(R.id.timerTextView);
        startTimerButton = view.findViewById(R.id.startTimerButton);
        stopTimerButton = view.findViewById(R.id.stopTimerButton);
        resetTimerButton = view.findViewById(R.id.resetTimerButton);

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        stopTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        resetTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        return view;
    }

    private void startTimer() {
        if (!isRunning) {
            int hours = Integer.parseInt(hoursInput.getText().toString());
            int minutes = Integer.parseInt(minutesInput.getText().toString());
            int seconds = Integer.parseInt(secondsInput.getText().toString());

            timeInMilliseconds = (hours * 3600 + minutes * 60 + seconds) * 1000;

            countDownTimer = new CountDownTimer(timeInMilliseconds, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int hours = (int) (millisUntilFinished / 3600000);
                    int minutes = (int) (millisUntilFinished % 3600000) / 60000;
                    int seconds = (int) ((millisUntilFinished % 3600000) % 60000) / 1000;

                    String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                    timerTextView.setText(time);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("00:00:00");
                }
            }.start();

            isRunning = true;
        }
    }

    private void stopTimer() {
        if (isRunning) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    private void resetTimer() {
        if (isRunning) {
            countDownTimer.cancel();
            isRunning = false;
        }
        timerTextView.setText("00:00:00");
        hoursInput.setText("");
        minutesInput.setText("");
        secondsInput.setText("");
    }
}
