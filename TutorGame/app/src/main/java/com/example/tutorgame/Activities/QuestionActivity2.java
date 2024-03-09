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
import com.example.tutorgame.databinding.ActivityQuestion2Binding;

import java.util.ArrayList;

public class QuestionActivity2 extends AppCompatActivity {

    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;
    ActivityQuestion2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion2Binding.inflate(getLayoutInflater());
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
                    Intent intent = new Intent(QuestionActivity2.this, ScoreActivity.class);
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
                Dialog dialog = new Dialog(QuestionActivity2.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(QuestionActivity2.this, SetsActivity2.class);
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
        list.add(new QuestionModel("What is the powerhouse of the cell responsible for producing energy?",
                "Nucleus", "Mitochondrion", "Chloroplast", "Endoplasmic reticulum", "Mitochondrion"));
        list.add(new QuestionModel("Which gas do plants release during photosynthesis?",
                "Oxygen", "Carbon dioxide", "Nitrogen", "Hydrogen", "Oxygen"));
        list.add(new QuestionModel("What is the body's largest organ?",
                "Heart", "Liver", "Skin", "Brain", "Skin"));
        list.add(new QuestionModel("Which of the following is a function of red blood cells?",
                "Fighting infections", "Carrying oxygen", "Digesting food", "Filtering toxins", "Carrying oxygen"));
        list.add(new QuestionModel("What is the process by which living organisms produce offspring of the same kind?",
                "Metabolism", "Respiration", "Reproduction", "Digestion", "Reproduction"));
    }

    private void setTwo() {
        list.add(new QuestionModel("What is the chemical symbol for oxygen?",
                "Ox", "O2", "Oc", "O", "O"));
        list.add(new QuestionModel("Which element is the most abundant in the Earth's crust?",
                "Iron", "Silicon", "Oxygen", "Carbon", "Oxygen"));
        list.add(new QuestionModel("What is the chemical formula for water?",
                "CO2", "H2O", "O3", "CH4", "H2O"));
        list.add(new QuestionModel("Which gas is known as the \"silent killer\" because it is odorless and colorless?",
                "Oxygen", "Carbon dioxide", "Hydrogen", "Nitrogen", "Carbon dioxide"));
        list.add(new QuestionModel("What is the process of a substance changing directly from a solid to a gas without passing through the liquid state?",
                "Condensation", "Melting", "Sublimation", "Evaporation", "Sublimation"));
    }

    private void setOne() {
        list.add(new QuestionModel("What is the chemical symbol for gold?",
                "Go", "Ag", "Ge", "Au", "Au"));
        list.add(new QuestionModel("Which gas do plants absorb from the atmosphere during photosynthesis?",
                "Oxygen", "Carbon dioxide", "Nitrogen", "Hydrogen", "Carbon dioxide"));
        list.add(new QuestionModel("What is the largest planet in our solar system?",
                "Earth", "Mars", "Jupiter", "Saturn", "Jupiter"));
        list.add(new QuestionModel("What is the process by which plants make their own food using sunlight?",
                "Respiration", "Germination", "Photosynthesis", "Transpiration", "Photosynthesis"));
        list.add(new QuestionModel("Which of the following is a type of renewable energy source?",
                "Coal", "Natural Gas", "Solar", "Oil", "Solar"));
    }
}
