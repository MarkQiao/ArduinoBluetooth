package com.mark.arduinobluetooth.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mark.arduinobluetooth.R;

/**
 * @author wangqiao
 */
public class GameSettingActivity extends AppCompatActivity {
    ImageView config_leftArrow, config_upArrow, config_rightArrow, config_downArrow;
    ImageView config_leftButton, config_upButton, config_rightButton, config_downButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);
        initView();
    }

    private void initView() {
        config_leftArrow = findViewById(R.id.config_leftArrow);
        config_upArrow = findViewById(R.id.config_upArrow);
        config_rightArrow = findViewById(R.id.config_rightArrow);
        config_downArrow = findViewById(R.id.config_downArrow);

        config_leftArrow.setColorFilter(getResources().getColor(R.color.black));
        config_upArrow.setColorFilter(getResources().getColor(R.color.black));
        config_rightArrow.setColorFilter(getResources().getColor(R.color.black));
        config_downArrow.setColorFilter(getResources().getColor(R.color.black));

        config_leftButton = findViewById(R.id.config_leftButton);
        config_upButton = findViewById(R.id.config_upButton);
        config_rightButton = findViewById(R.id.config_rightButton);
        config_downButton = findViewById(R.id.config_downButton);

        config_leftButton.setColorFilter(getResources().getColor(R.color.arduinoOrange2));
        config_upButton.setColorFilter(getResources().getColor(R.color.arduinoBrown1));
        config_rightButton.setColorFilter(getResources().getColor(R.color.arduinoYellow2));
        config_downButton.setColorFilter(getResources().getColor(R.color.arduinoGray1));

    }
}
