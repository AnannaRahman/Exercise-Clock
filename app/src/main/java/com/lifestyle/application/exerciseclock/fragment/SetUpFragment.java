package com.lifestyle.application.exerciseclock.fragment;


import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.hmspicker.HmsPickerBuilder;
import com.codetroopers.betterpickers.hmspicker.HmsPickerDialogFragment;
import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.facebook.stetho.Stetho;
import com.github.clans.fab.FloatingActionButton;
import com.lifestyle.application.exerciseclock.R;
import com.lifestyle.application.exerciseclock.database.DBHelper;
import com.lifestyle.application.exerciseclock.model.WorkOutPlan;
import com.lifestyle.application.exerciseclock.utill.Common;
import com.google.gson.Gson;
import com.mapzen.speakerbox.Speakerbox;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ananna on 10/28/2017.
 */
//fragment is a library
public class SetUpFragment extends Fragment implements
        NumberPickerDialogFragment.NumberPickerDialogHandlerV2,
        HmsPickerDialogFragment.HmsPickerDialogHandlerV2 {
    private static final int WO_PICKER = 100;
    private static final int RI_PICKER = 110;
    private static final int SETS_PICKER = 120;
    private static final int HANDLER_WAIT_TIME = 1500;
    private TextView tvSets, tvWOTime, tvRestInterval;
    private WorkOutPlan oSetup = null;
    private FloatingActionButton btnPlay;
    private FloatingActionButton btnFabStop;
    private CountDownTimer countDownTimer;
    private long timeleft;
    private boolean isPaused = false;
    private long wTimer;
    private int setsNumber;
    private boolean isFromworkout;
    Speakerbox speakerbox;
    private TextView txtRestInterval;
    private TextView txtWOTime;
    private TextView txtSets;
    private FloatingActionButton btnFabReset;
    private FloatingActionButton btnPause;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.setup_fragment_layout, null);
        tvSets = (TextView) view.findViewById(R.id.tvSets);
        tvWOTime = (TextView) view.findViewById(R.id.tvWOTime);
        tvRestInterval = (TextView) view.findViewById(R.id.tvRestInterval);
        oSetup = new WorkOutPlan();
        txtRestInterval = (TextView) view.findViewById(R.id.txtRestInterval);
        txtSets = (TextView) view.findViewById(R.id.txtSets);
        txtWOTime = (TextView) view.findViewById(R.id.txtWOTime);
        speakerbox = new Speakerbox(getActivity().getApplication());
        db = new DBHelper(getContext());
        tvSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPickerBuilder npb = new NumberPickerBuilder()
                        .setFragmentManager(getFragmentManager())
                        .setTargetFragment(SetUpFragment.this)
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                        .setPlusMinusVisibility(View.INVISIBLE).setDecimalVisibility(View.INVISIBLE).setReference(SETS_PICKER);
                npb.show();
            }
        });
        tvWOTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HmsPickerBuilder hpb = new HmsPickerBuilder()
                        .setFragmentManager(getFragmentManager())
                        .setTargetFragment(SetUpFragment.this)
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light).setReference(WO_PICKER);
                hpb.show();
            }
        });

        tvRestInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HmsPickerBuilder hpb = new HmsPickerBuilder()
                        .setFragmentManager(getFragmentManager())
                        .setTargetFragment(SetUpFragment.this)
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light).setReference(RI_PICKER);
                hpb.show();
            }
        });


        btnFabStop = (FloatingActionButton) view.findViewById(R.id.btnStop);
        btnFabStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWindowValues();
                tvSets.setText("0");
                tvWOTime.setText("00:00:00");
                tvRestInterval.setText("00:00:00");
                oSetup = new WorkOutPlan();
                isPaused = false;
                timeleft = 0;
                btnPlay.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.GONE);
            }
        });

        btnPlay = (FloatingActionButton) view.findViewById(R.id.btnFab);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doEventStart();
            }
        });

        btnPause = (FloatingActionButton) view.findViewById(R.id.btnPause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerPause();
                btnPlay.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.GONE);

            }
        });


        return view;
    }


    private void clearWindowValues() {
        btnFabStop.setVisibility(View.GONE);
        txtSets.setVisibility(View.VISIBLE);
        txtWOTime.setVisibility(View.VISIBLE);
        txtRestInterval.setVisibility(View.VISIBLE);
        tvSets.setVisibility(View.VISIBLE);
        tvWOTime.setVisibility(View.VISIBLE);
        tvRestInterval.setVisibility(View.VISIBLE);
        if (countDownTimer != null)
            countDownTimer.cancel();

    }

    private void restIntervalVisibility() {

        txtRestInterval.setVisibility(View.GONE);
        tvRestInterval.setVisibility(View.GONE);

    }

    private void doEventStart() {
        //countDownTimer = null;
        if (!isPaused) {

            if (oSetup != null) {

                if (oSetup.getSets() > 0 && oSetup.getWorkOutDuration() > 0 && oSetup.getRestInterval() > 0) {
                    boolean result = db.addExerciseLog(tvSets.getText().toString(), tvWOTime.getText().toString(),tvRestInterval.getText().toString());

                    restIntervalVisibility();
                    btnFabStop.setVisibility(View.VISIBLE);
                    btnPlay.setVisibility(View.GONE);
                    btnPause.setVisibility(View.VISIBLE);
                    speakerbox.play("Ready.");
                    tvSets.setText("GET READY!!");
                    txtSets.setVisibility(View.GONE);
                    txtWOTime.setVisibility(View.GONE);
                    startReadyCountDown(TimeUnit.SECONDS.toMillis(3) + 100); //FIX FOR 00:00:00


                } else {
                    Toast.makeText(getContext(), "Please correct your input.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            btnPlay.setVisibility(View.GONE);
            btnPause.setVisibility(View.VISIBLE);
            timerResume();
        }

    }

    private void timerResume() {
        timeCountDown(timeleft - 1000);
    }

    @Override
    public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {
        if (reference == SETS_PICKER) {
            tvSets.setText(number.toString());
            oSetup.sets = number.intValue();


            //Common.SaveOnSharedPreference(getActivity(), SystemConstants.WorkOut_Information, SystemConstants.WorkOut_Plan_KEY, gson.toJson(oSetup));
        }

    }

    @Override
    public void onDialogHmsSet(int reference, boolean isNegative, int hours, int minutes, int seconds) {
        long milihours = TimeUnit.HOURS.toMillis(hours);
        long miliminuits = TimeUnit.MINUTES.toMillis(minutes);
        long miliseconds = TimeUnit.SECONDS.toMillis(seconds);
        long millis = milihours + miliminuits + miliseconds;
        String formatedTime = String.format("%02d : %02d : %02d ",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        if (reference == WO_PICKER) {
            tvWOTime.setText(formatedTime);

            oSetup.workOutDuration = millis + 100;
        }

        if (reference == RI_PICKER) {
            tvRestInterval.setText(formatedTime);

            oSetup.restInterval = millis + 100;
        }

        //Common.SaveOnSharedPreference(getActivity(), SystemConstants.WorkOut_Information, SystemConstants.WorkOut_Plan_KEY, gson.toJson(oSetup));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer = null;

        }
    }

    private void startReadyCountDown(long futureDuration) {

        countDownTimer = new CountDownTimer(futureDuration, 1000) {

            public void onTick(long millisUntilFinished) {
                timeleft = millisUntilFinished;
                tvWOTime.setText(Common.fotmattedTime(millisUntilFinished));
            }

            public void onFinish() {

                speakerbox.play("Start.");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        txtSets.setVisibility(View.VISIBLE);
                        txtWOTime.setVisibility(View.VISIBLE);
                        startCountDown();
                    }
                }, HANDLER_WAIT_TIME);

            }

        }.start();
    }

    public void timerPause() {
        isPaused = true;
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    private void startCountDown() {

        setsNumber = oSetup.sets;
        wTimer = oSetup.workOutDuration;

        if (oSetup.sets > 1)
            isFromworkout = true;

        timeCountDown(wTimer);
        tvSets.setText(String.valueOf(oSetup.sets));
    }

    private synchronized final void timeCountDown(long futureDuration) {

        countDownTimer = new CountDownTimer(futureDuration + 100, 1000) {

            public void onTick(long millisUntilFinished) {
                timeleft = millisUntilFinished;
                tvWOTime.setText(Common.fotmattedTime(millisUntilFinished));
            }

            public void onFinish() {
                if (!isFromworkout) {
                    workOutExecution();
                } else if (oSetup.sets > 1 && isFromworkout) {
                    restIntervalExecution();
                } else {
                    workOutExecution();
                }

            }

        }.start();
    }

    private void restIntervalExecution() {
        isFromworkout = false;
        tvWOTime.setText("00:00:00");
        speakerbox.play("Take rest.");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvSets.setText("Rest Interval");
                txtSets.setVisibility(View.GONE);
                txtWOTime.setVisibility(View.GONE);
                timeCountDown(oSetup.restInterval);
            }
        }, HANDLER_WAIT_TIME);

    }

    private void workOutExecution() {
        isFromworkout = true;
        //speakerbox = new //speakerbox(getActivity().getApplication());
        oSetup.sets--;


        if (oSetup.sets == 0) {
            tvSets.setText(String.valueOf(oSetup.sets));
            tvRestInterval.setVisibility(View.VISIBLE);
            tvWOTime.setText("00:00:00");
            tvRestInterval.setText("Completed!");
            speakerbox.play("Completed.");
            btnPause.setVisibility(View.GONE);
            oSetup = new WorkOutPlan();
            isPaused = false;
            timeleft = 0;
        }
        if (oSetup.sets > 0) {
            //speakerbox.play("Set " + oSetup.getSets() + " start.");
            speakerbox.play(" Start.");
            tvWOTime.setText("00:00:00");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // do something...
                    txtSets.setVisibility(View.VISIBLE);
                    txtWOTime.setVisibility(View.VISIBLE);
                    tvSets.setText(String.valueOf(oSetup.sets));
                    timeCountDown(oSetup.workOutDuration);
                }
            }, HANDLER_WAIT_TIME);

        }
    }
}
