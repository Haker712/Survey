package aceplus.survey2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import aceplus.survey2.objects.AnswerDetail;
import aceplus.survey2.objects.CustomerDetail;
import aceplus.survey2.objects.Questions;
import aceplus.survey2.utils.DataBase;

/**
 * Created by PhyoKyawSwar on 6/20/17.
 */

public class Report_Detail extends AppCompatActivity{

    SQLiteDatabase database;
    public static String AnsID = null;
    public static String ReportTempID = null;
    public static String CusID = null;
    LinearLayout linearLayout;
    private Toolbar toolbar;

    ArrayList<Questions> questionsArrayList;
    ArrayList<AnswerDetail> answerDetailArrayList;
    ArrayList<CustomerDetail> customerDetailArrayList;

    TextView txtViewName,txtViewPhone,txtViewEmail,txtViewAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detail);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = new DataBase(this).getDataBase();

        txtViewName = (TextView) findViewById(R.id.txtViewName);
        txtViewPhone = (TextView) findViewById(R.id.txtViewPhone);
        txtViewEmail = (TextView) findViewById(R.id.txtViewEmail);
        txtViewAddress = (TextView) findViewById(R.id.txtViewAddress);

        customerDetailArrayList = getCustomerDetailfromdatabase(CusID);

        txtViewName.setText(customerDetailArrayList.get(0).getName());
        txtViewPhone.setText(customerDetailArrayList.get(0).getPhone());
        txtViewEmail.setText(customerDetailArrayList.get(0).getEmail());
        txtViewAddress.setText(customerDetailArrayList.get(0).getAddress());

        linearLayout = (LinearLayout) findViewById(R.id.AnswerlinearLayout);

        questionsArrayList = getquestionsfromdatabase(ReportTempID);

        for (int q = 0; q < questionsArrayList.size(); q++){
            String question = questionsArrayList.get(q).getText();
            String QuestionID = questionsArrayList.get(q).getId();
            TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setTypeface(null, Typeface.BOLD);

            linearLayout.addView(textView);
            textView.setText((q + 1) + ". " + question);
            Log.i("questionssssssss......", question);

            LinearLayout linearLayout1 = new LinearLayout(this);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);

            answerDetailArrayList = getAnswerDetailfromdatabase(QuestionID);

            for (int i = 0; i < answerDetailArrayList.size(); i++){
                TextView textView1 = new TextView(this);
                textView1.setTextSize(20);

                linearLayout1.addView(textView1);
                textView1.setText("-" +answerDetailArrayList.get(i).getAnswer());


            }
            linearLayout.addView(linearLayout1);
            LinearLayout.LayoutParams layoutParams_for_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            layoutParams_for_line.setMargins(0, 20, 0, 20);
            View line = new View(this);
            line.setLayoutParams(layoutParams_for_line);
            line.setBackgroundColor(Color.parseColor("#B3B3B3"));
            linearLayout.addView(line);
        }

    }
    private ArrayList<Questions> getquestionsfromdatabase(String tempID){
        ArrayList<Questions> questionsArrayList = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM survey_question WHERE survey_template_id ='"+ tempID+"' ", null);
        while (c.moveToNext()){
            Questions question = new Questions();
            question.setId(c.getString(c.getColumnIndex("id")));
            question.setText(c.getString(c.getColumnIndex("Text")));
            question.setQoT(c.getString(c.getColumnIndex("Question_type")));
            question.setQoC(c.getInt(c.getColumnIndex("Question_count")));
            questionsArrayList.add(question);
        }
        c.close();
        return questionsArrayList;
    }

    private ArrayList<AnswerDetail> getAnswerDetailfromdatabase(String questionID){
        ArrayList<AnswerDetail> answerDetailArrayList = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM survey_answer_detail WHERE question_id ='"+ questionID+"' ", null);
        while (c.moveToNext()){
            AnswerDetail answerDetail = new AnswerDetail();
            answerDetail.setId(c.getString(c.getColumnIndex("id")));
            answerDetail.setAnswer(c.getString(c.getColumnIndex("Answer")));
            answerDetail.setAnswerID(c.getString(c.getColumnIndex("survey_answer_id")));
            answerDetailArrayList.add(answerDetail);
        }
        c.close();
        return answerDetailArrayList;
    }

    private ArrayList<CustomerDetail> getCustomerDetailfromdatabase(String customerID){
        ArrayList<CustomerDetail> customerDetailArrayList = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM customer WHERE id ='"+ customerID+"' ", null);
        while (c.moveToNext()){
            CustomerDetail customerDetail = new CustomerDetail();
            customerDetail.setName(c.getString(c.getColumnIndex("name")));
            customerDetail.setPhone(c.getString(c.getColumnIndex("phoneNo")));
            customerDetail.setEmail(c.getString(c.getColumnIndex("Email")));
            customerDetail.setAddress(c.getString(c.getColumnIndex("Address")));
            customerDetailArrayList.add(customerDetail);
        }
        c.close();
        return customerDetailArrayList;
    }
}
