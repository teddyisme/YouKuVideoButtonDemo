package lixs.com.youkuvideobuttondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import lixs.com.youkuvideobutton.YoukuPlayButtonView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final YoukuPlayButtonView youkuBorderView = findViewById(R.id.borderView);
        findViewById(R.id.borderView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youkuBorderView.startAnimation();
            }
        });

    }
}
