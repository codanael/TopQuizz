package com.example.anael.topquizz.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anael.topquizz.R;
import com.example.anael.topquizz.model.User;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private TextView mScoreText;

    private User mUser;

    private SharedPreferences mPreferences;

    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = new User();
        mGreetingText = (TextView) findViewById(R.id.activity_main_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mScoreText = findViewById(R.id.activity_main_score_txt);

        mPlayButton.setEnabled(false);
        mPreferences = getPreferences(MODE_PRIVATE);



        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mPlayButton.setEnabled(s.toString().length() != 0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
                mUser.setFirstName(mNameInput.getText().toString());
                mPreferences.edit().putString("firstname", mUser.getFirstName()).apply();

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            mPreferences.edit().putInt("score",score).apply();

            greetingUser();

        }

    }

    private void greetingUser() {
        String firstname = getPreferences(MODE_PRIVATE).getString("firstname",null);
        int previousScore = getPreferences(MODE_PRIVATE).getInt("score",0);

        mGreetingText.setText("Bonjour "+ firstname+ " ! Votre dernier score était "+previousScore);
        mNameInput.setText(firstname);
        mNameInput.setSelection(firstname.length());
        mPlayButton.setEnabled(true);

    }
}
