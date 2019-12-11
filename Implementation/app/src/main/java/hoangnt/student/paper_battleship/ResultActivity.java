package hoangnt.student.paper_battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewResult = findViewById(R.id.textViewResult);
        if(getIntent().getStringExtra("context").equals("1")){
            textViewResult.setText(R.string.you_win_text);
            Log.d("Result", "" + getIntent().getStringExtra("exp"));
        }else {
            textViewResult.setText(R.string.you_loose_text);
            Log.d("Result", "" + getIntent().getStringExtra("exp"));
        }
    }
}
