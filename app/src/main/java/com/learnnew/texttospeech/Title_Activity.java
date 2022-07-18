package com.learnnew.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Title_Activity extends AppCompatActivity {

    Button btnAdd,btnSub,btnMul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAdd=findViewById(R.id.add_btn);
        btnSub=findViewById(R.id.sub_btn);
        btnMul=findViewById(R.id.mul_btn);
        taptoAnimate(btnAdd);
        taptoAnimate(btnSub);
        taptoAnimate(btnMul);

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_Add.class);
                startActivity(intent);

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_Sub.class);
                startActivity(intent);

            }
        });


    }
    public void taptoAnimate(Button btn){

        //Button button=(Button)findViewById(R.id.listen);
        final Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        MyBounceInterpolator interpolator=new MyBounceInterpolator(0.2,20);
        animation.setInterpolator(interpolator);
        btn.startAnimation(animation);

    }
}