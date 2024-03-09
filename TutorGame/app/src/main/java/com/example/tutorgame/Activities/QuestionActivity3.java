package com.example.tutorgame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.tutorgame.Models.QuestionModel;
import com.example.tutorgame.R;
import com.example.tutorgame.databinding.ActivityQuestion3Binding;

import java.util.ArrayList;

public class QuestionActivity3 extends AppCompatActivity {

    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;
    ActivityQuestion3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resetTimer();
        timer.start();

        String setName = getIntent().getStringExtra("set");

        if (setName == null) {
            // Handle the case where "setName" is not set
            return;
        }

        switch (setName) {
            case "SET-1":
                setOne();
                break;
            case "SET-2":
                setTwo();
                break;
            case "SET-3":
                setThree();
                break;
        }

        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((Button) view);
                }
            });
        }

        if (list.isEmpty()) {
            // Handle the case where "list" is empty
            return;
        }

        playAnimation(binding.question, 0, list.get(position).getQuestion());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                }

                assert timer != null;
                timer.start();
                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha(0.3f);
                enableOption(true);
                position++;

                if (position == list.size()) {
                    Intent intent = new Intent(QuestionActivity3.this, ScoreActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("total", list.size());
                    startActivity(intent);
                    finish();
                } else {
                    count = 0;
                    playAnimation(binding.question, 0, list.get(position).getQuestion());
                }
            }
        });
    }

    private void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(QuestionActivity3.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(QuestionActivity3.this, SetsActivity3.class);
                        startActivity(intent);
                        finish();
                    }
                });

                dialog.show();
            }
        };
    }

    private void playAnimation(View view, int value, String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0) {
                    try {
                        if (view instanceof TextView) {
                            ((TextView) view).setText(data);
                        } else if (view instanceof Button) {
                            ((Button) view).setText(data);
                        }
                        binding.totalQuestion.setText((position + 1) + "/" + list.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    view.setTag(data);
                    playAnimation(view, 1, data);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4) {
                    String option = "";

                    if (count == 0) {
                        option = list.get(position).getOptionA();
                    } else if (count == 1) {
                        option = list.get(position).getOptionB();
                    } else if (count == 2) {
                        option = list.get(position).getOptionC();
                    } else if (count == 3) {
                        option = list.get(position).getOptionD();
                    }

                    playAnimation(binding.optionContainer.getChildAt(count), 0, option);
                    count++;
                }
            }
        });
    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }
    }

    private void checkAnswer(Button selectedOption) {
        if (timer != null) {
            timer.cancel();
        }
        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);

        if (selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())) {
            score++;
            selectedOption.setBackgroundResource(R.drawable.right_answ);
        } else {
            selectedOption.setBackgroundResource(R.drawable.wrong_answ);
            Button correctOption = (Button) binding.optionContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            if (correctOption != null) {
                correctOption.setBackgroundResource(R.drawable.right_answ);
            }
        }
    }

    private void setThree() {

        list.add(new QuestionModel("If 4 + 3x = 19, what is the value of x?",
                "3","5","6","7","5"));
        list.add(new QuestionModel("What is 5 squared?",
                "10","15","20","25","25"));
        list.add(new QuestionModel("If a triangle has a base of 8 units and a height of 6 units, what is its area?",
                "24 square units","30 square units","36 square units","48 square units","48 square units"));
        list.add(new QuestionModel("What is the largest prime number less than 50?",
                "47","49","53","59","47"));
        list.add(new QuestionModel("If 2/5 of a number is 12, what is the number?",
                "10","15","20","30","20"));

    }

    private void setTwo() {

        list.add(new QuestionModel("If 3x - 5 = 16, what is the value of x?",
                "3","5","7","9","7"));
        list.add(new QuestionModel("What is the result of 6 ÷ 2 + 1?",
                "4","5","6","7","5"));
        list.add(new QuestionModel("If a circle has a radius of 5 units, what is its circumference (approximate, π ≈ 3.14)?",
                "10 units","15 units","20 units","31.4 units","31.4 units"));
        list.add(new QuestionModel("If 2/3 of a number is 18, what is the number?",
                "12","24","27","36","24"));
        list.add(new QuestionModel("What is the product of 8 and 7?",
                "15","42","56","64","56"));
    }

    private void setOne() {

        list.add(new QuestionModel("What is the result of 7 * 4?",
                "21","28","35","42","28"));
        list.add(new QuestionModel("If x = 3 and y = 5, what is the value of 2x + 3y?",
                "13","19","23","27","19"));
        list.add(new QuestionModel("What is the square root of 144?",
                "8","10","12","14","12"));
        list.add(new QuestionModel("If a rectangle has a length of 10 units and a width of 5 units, what is its area?",
                "20 square units","30 square units","40 square units","50 square units","40 square units"));
        list.add(new QuestionModel("What is the next prime number after 17?",
                "19","21","23","27","19"));

    }
}