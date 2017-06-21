package aceplus.survey2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by PhyoKyawSwar on 6/19/17.
 */

public class HomeActivity extends AppCompatActivity {

    Button btn_temp;
    Button btn_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        btn_report = (Button) findViewById(R.id.report_btn);
        btn_temp = (Button) findViewById(R.id.template_btn);

        btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Survey_List.class));
            }
        });


        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Report_List.class));
            }
        });
    }

}
