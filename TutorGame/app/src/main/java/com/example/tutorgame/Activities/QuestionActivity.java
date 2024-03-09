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
import com.example.tutorgame.databinding.ActivityQuestionBinding;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;
    ActivityQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resetTimer();
        timer.start();

        String setName = getIntent().getStringExtra("set");

        if (setName != null && setName.equals("SET-1")) {
            setOne();
        } else if (setName != null && setName.equals("SET-2")) {
            setTwo();
        } else if (setName != null && setName.equals("SET-3")) {
            setThree();
        }

        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((Button) view);
                }
            });
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
                    Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("total", list.size());
                    startActivity(intent);
                    finish();
                    return;
                }

                count = 0;
                playAnimation(binding.question, 0, list.get(position).getQuestion());
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
                Dialog dialog = new Dialog(QuestionActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(QuestionActivity.this, SetsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
        }.start();
    }

    private void playAnimation(View view, int value, String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0) {
                    try {
                        ((TextView) view).setText(data);
                        binding.totalQuestion.setText((position + 1) + "/" + list.size());
                    } catch (Exception e) {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAnimation(view, 1, data);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4) {
                    String option = "";
                    switch (count) {
                        case 0:
                            option = list.get(position).getOptionA();
                            break;
                        case 1:
                            option = list.get(position).getOptionB();
                            break;
                        case 2:
                            option = list.get(position).getOptionC();
                            break;
                        case 3:
                            option = list.get(position).getOptionD();
                            break;
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

        list.add(new QuestionModel("Which event marked the official beginning of World War II in Europe?",
                "The invasion of Poland by Germany","The bombing of Pearl Harbor by Japan","The Battle of Stalingrad","The signing of the Treaty of Versailles","The invasion of Poland by Germany"));
        list.add(new QuestionModel("Which country was invaded by Nazi Germany in June 1941, leading to a two-front war for Germany?",
                "France","Soviet Union","United Kingdom","Italy","Soviet Union"));
        list.add(new QuestionModel("What was the code name for the Allied invasion of Normandy, France on June 6, 1944?",
                "Operation Desert Storm","Operation Overlord","Operation Barbarossa","Operation Market Garden","Operation Overlord"));
        list.add(new QuestionModel("The dropping of atomic bombs on which two Japanese cities led to Japan's surrender and the end of World War II?",
                "Hiroshima and Nagasaki","Tokyo and Kyoto","Osaka and Yokohama","Okinawa and Iwo Jima","Hiroshima and Nagasaki"));
        list.add(new QuestionModel("Which Allied conference held during World War II featured leaders Winston Churchill, Franklin D. Roosevelt, and Joseph Stalin, and led to discussions on the post-war reorganization of Europe?",
                "Tehran Conference","Potsdam Conference","Yalta Conference","Casablanca Conference","Yalta Conference"));

    }

    private void setTwo() {

        list.add(new QuestionModel("What event is widely considered the immediate cause of World War I?",
                "The assassination of Archduke Franz Ferdinand","The sinking of the Lusitania","The signing of the Treaty of Versailles","The Russian Revolution","The assassination of Archduke Franz Ferdinand"));
        list.add(new QuestionModel("Which two major alliances were the primary belligerents in World War I?",
                "Allies and Axis","Central Powers and Allies","Entente Powers and Central Forces","Triple Alliance and Triple Entente","Central Powers and Allies"));
        list.add(new QuestionModel("Which country was the first to declare war in World War I, starting a chain reaction of declarations across Europe?",
                "Germany","Austria-Hungary","Russia","Serbia","Austria-Hungary"));
        list.add(new QuestionModel("Which battle on the Western Front is often associated with extensive trench warfare and high casualties?",
                "Battle of Gallipoli","Battle of Jutland","Battle of the Somme","attle of Tannenberg","Battle of the Somme"));
        list.add(new QuestionModel("What was the name of the famous agreement that ended World War I and imposed harsh penalties on Germany?",
                "Treaty of Brest-Litovsk","Treaty of Tordesillas","Treaty of Versailles","Treaty of Vienna","Treaty of Versailles"));
    }

    private void setOne() {

        list.add(new QuestionModel("Who was the first Prime Minister of Malaysia following its independence from British colonial rule?",
                "Tunku Abdul Rahman","Mahathir Mohamad","Sultan Abdul Samad","Ismail Abdul Rahman","Tunku Abdul Rahman"));
        list.add(new QuestionModel("Malaysia was formed in 1963 through the merger of several states. Which of the following was NOT one of the original founding states of Malaysia?",
                "Malaya","Sarawak","Singapore","Sabah","Singapore"));
        list.add(new QuestionModel("What was the name of the communist insurgency that took place in Malaysia from 1948 to 1989?",
                "Malayan Emergency","Indonesian Confrontation","May 13 Incident","Operation Coldstore","Malayan Emergency"));
        list.add(new QuestionModel("Which European colonial power controlled the Malay Peninsula for much of its colonial history before the Japanese occupation during World War II?",
                "Portugal","Spain","Britain","Netherlands","Britain"));
        list.add(new QuestionModel("The historic city of Malacca, a UNESCO World Heritage Site, was a significant trading port in Southeast Asia and was once ruled by a famous sultan. What was his name?",
                "Sultan Salahuddin","Sultan Iskandar","Sultan Mansur Shah","Sultan Abdullah","Sultan Mansur Shah"));

    }

}