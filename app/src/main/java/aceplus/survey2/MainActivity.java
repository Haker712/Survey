package aceplus.survey2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edtxtUserName;
    EditText edtxtPassword;
    Button btnLogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtxtUserName = (EditText) findViewById(R.id.username_txt);
        edtxtPassword = (EditText) findViewById(R.id.password_txt);
        btnLogIn = (Button) findViewById(R.id.log_in_btn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });

    }


}
