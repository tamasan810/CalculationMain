package com.example.admin.calculationmain;

import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private TextView textView1, textView2;
    private ImageView imageView1, imageView2;
    private SoundPool soundPool;
    private int[] sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ImageViewは箱を用意する必要がある？
        imageView1 =(ImageView)findViewById(R.id.imageview1);
        imageView1.setImageResource(R.drawable.beasteye);

        imageView2 =(ImageView)findViewById(R.id.imageview2);
        imageView2.setImageResource(R.drawable.ton);

        textView1 = (TextView) findViewById(R.id.textview1);
        textView2 = (TextView) findViewById(R.id.textview2);

        // SoundPoolのインスタンス作成
        SoundPool.Builder builder = new SoundPool.Builder();
        soundPool = builder.build();

        //loadYJSNPISound();
        loadTONSound();

        // ACボタンとbuttonListenerを結びつける
        // ボタンを生成する必要は無い
        findViewById(R.id.button).setOnClickListener(buttonListener);

        setNumber();
    }

    // ACボタンが押されると呼ばれる
    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            recentOperator = R.id.button_equal;
            result = 0;
            isOperatorKeyPushed = false;
            textView1.setText("");
            textView2.setText("0");

            soundPool.play(sounds[16], 0.5f, 0.5f, 0, 0, 1);
        }
    };

    // 数字キーが押されると呼ばれる
    View.OnClickListener buttonNumberListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;

            if (isOperatorKeyPushed == true) {
                // 演算子キーを押した直後に押された数字キーを表示
                textView2.setText(button.getText());
            } else{
                //後ろに文字を追加
                textView2.append(button.getText());
            }

            // 数字キーが押された時点でfalseにする
            isOperatorKeyPushed = false;

            playNumberSound(button);
        }
    };

    int recentOperator = R.id.button_equal; // 最近に押された演算子キー
    double result; // 計算結果
    boolean isOperatorKeyPushed; // 演算子キーが押されたかどうかを記憶

    // 演算子キーが押されると呼ばれる
    View.OnClickListener buttonOperatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 押されたボタンを取得
            Button operatorButton = (Button) v;
            // 表示されている値を取得する
            // 値が表示されていない状態で取得しようとすると落ちる
            // 起動すると既に0が入っているように改善
            double value = Double.parseDouble(textView2.getText().toString());
            if(recentOperator == R.id.button_equal){
                result = value;
            } else{
                // "="以外であれば計算してresultに格納
                result = calc(recentOperator, result, value);
                // 計算結果を表示
                textView2.setText(String.valueOf(result));
            }

            // 押されたボタンのIDを取得
            recentOperator = operatorButton.getId();
            //押された演算子キーを表示
            textView1.setText(operatorButton.getText());
            textView2.setText(String.valueOf(result));
            // 演算子キーを押すとtrueになる
            isOperatorKeyPushed = true;

            playOperatorSound(operatorButton);
        }
    };

    void setNumber(){
        // 数字キーとbuttonNumberListenerを結びつける
        findViewById(R.id.button_1).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_2).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_3).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_4).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_5).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_6).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_7).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_8).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_9).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_0).setOnClickListener(buttonNumberListener);
        findViewById(R.id.button_dot).setOnClickListener(buttonNumberListener);

        // 演算子キーとbuttonOperatorListenerを結びつける
        findViewById(R.id.button_add).setOnClickListener(buttonOperatorListener);
        findViewById(R.id.button_subtract).setOnClickListener(buttonOperatorListener);
        findViewById(R.id.button_multiply).setOnClickListener(buttonOperatorListener);
        findViewById(R.id.button_divide).setOnClickListener(buttonOperatorListener);
        findViewById(R.id.button_equal).setOnClickListener(buttonOperatorListener);
    }

    double calc(int operator, double value1, double value2){
        switch (operator){
            case R.id.button_add:
                return value1 + value2;
            case R.id.button_subtract:
                return value1 - value2;
            case R.id.button_multiply:
                return value1 * value2;
            case R.id.button_divide:
                return value1 / value2;
            default:
                return value1;
        }
    }

    // 効果音のロード
    void loadYJSNPISound(){
        sounds = new int[17];

        sounds[0] = soundPool.load(this, R.raw.yjsnpi_0, 1);
        sounds[1] = soundPool.load(this, R.raw.yjsnpi_1, 1);
        sounds[2] = soundPool.load(this, R.raw.yjsnpi_2, 1);
        sounds[3] = soundPool.load(this, R.raw.yjsnpi_3, 1);
        sounds[4] = soundPool.load(this, R.raw.yjsnpi_4, 1);
        sounds[5] = soundPool.load(this, R.raw.yjsnpi_5, 1);
        sounds[6] = soundPool.load(this, R.raw.yjsnpi_6, 1);
        sounds[7] = soundPool.load(this, R.raw.yjsnpi_7, 1);
        sounds[8] = soundPool.load(this, R.raw.yjsnpi_8, 1);
        sounds[9] = soundPool.load(this, R.raw.yjsnpi_9, 1);
        sounds[10] = soundPool.load(this, R.raw.yjsnpi_10, 1);
        sounds[11] = soundPool.load(this, R.raw.yjsnpi_11, 1);
        sounds[12] = soundPool.load(this, R.raw.yjsnpi_12, 1);
        sounds[13] = soundPool.load(this, R.raw.yjsnpi_13, 1);
        sounds[14] = soundPool.load(this, R.raw.yjsnpi_14, 1);
        sounds[15] = soundPool.load(this, R.raw.yjsnpi_15, 1);
        sounds[16] = soundPool.load(this, R.raw.yjsnpi_16, 1);
    }

    void loadTONSound(){
        sounds = new int[17];

        for(int i = 0; i < sounds.length; i++){
            sounds[i] = soundPool.load(this, R.raw.ton, 1);
        }
    }

    void playNumberSound(Button button){
        int id = button.getId();
        int sound = 0;
        switch (id){
            case R.id.button_0:
                sound = sounds[0];
                break;
            case R.id.button_1:
                sound = sounds[1];
                break;
            case R.id.button_2:
                sound = sounds[2];
                break;
            case R.id.button_3:
                sound = sounds[3];
                break;
            case R.id.button_4:
                sound = sounds[4];
                break;
            case R.id.button_5:
                sound = sounds[5];
                break;
            case R.id.button_6:
                sound = sounds[6];
                break;
            case R.id.button_7:
                sound = sounds[7];
                break;
            case R.id.button_8:
                sound = sounds[8];
                break;
            case R.id.button_9:
                sound = sounds[9];
                break;
            case R.id.button_dot:
                sound = sounds[10];
                break;
        }

        soundPool.play(sound, 0.5f, 0.5f, 0, 0, 1);
    }

    void playOperatorSound(Button button){
        int id = button.getId();
        int sound = 0;
        switch (id){
            case R.id.button_add:
                sound = sounds[11];
                break;
            case R.id.button_subtract:
                sound = sounds[12];
                break;
            case R.id.button_multiply:
                sound = sounds[13];
                break;
            case R.id.button_divide:
                sound = sounds[14];
                break;
            case R.id.button_equal:
                sound = sounds[15];
                break;
        }

        soundPool.play(sound, 0.5f, 0.5f, 0, 0, 1);
    }

}
