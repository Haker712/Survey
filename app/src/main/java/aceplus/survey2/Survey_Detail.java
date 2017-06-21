package aceplus.survey2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aceplus.survey2.objects.Answer;
import aceplus.survey2.objects.QuestionAndAnswerId;
import aceplus.survey2.objects.Questions;
import aceplus.survey2.objects.SelectedAnswer;
import aceplus.survey2.objects.Template_Answer;
import aceplus.survey2.utils.DataBase;

/**
 * Created by PhyoKyawSwar on 6/2/17.
 */

public class Survey_Detail extends AppCompatActivity {

    public static String TempID = null;
    SQLiteDatabase database;
    ArrayList<Questions> questionsArrayList;
    ArrayList<Template_Answer> templateAnswerArrayList;
    LinearLayout linearLayout;
    RadioGroup radioGroup;
    ArrayList<RadioButton> radioButtonArrayList = new ArrayList<>();
    ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
    ArrayList<EditText> editTextArrayList = new ArrayList<>();
    ArrayList<Spinner> spinnerArrayList = new ArrayList<>();
    ArrayList<QuestionAndAnswerId> QusAndAnsArraylilst = new ArrayList<>();
    int i = 0;
    int editTextRowCount = 0;
    Random rand;
    int randomColor;
    EditText edtName, edtPhone, edtEmail, edtAddress;
    String regEx ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ArrayList<SelectedAnswer> selectedAnswerForRadioButtonArrayList = new ArrayList<>();
    ArrayList<SelectedAnswer> selectedAnswerForCheckBoxArrayList = new ArrayList<>();
    ArrayList<SelectedAnswer> selectedAnswerForEditTextArrayList = new ArrayList<>();
    ArrayList<SelectedAnswer> selectedAnswerForSpinnersArrayList = new ArrayList<>();
    private Toolbar toolbar;
    private String spinnerValue1 = "";


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_detail);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        database = new DataBase(this).getDataBase();

        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtAddress = (EditText) findViewById(R.id.edtPhone);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        JSONArray ques = new JSONArray();

        questionsArrayList = getquestionsfromdatabase(Survey_Detail.TempID);
        Log.i("quesSize......", questionsArrayList.size() + "");


        for (int q = 0; q < questionsArrayList.size(); q++) {
            ques.put(questionsArrayList.get(q));
        }
        Log.i("qus_json", ques.toString());

        int iforR = 0;
        int iforC = 0;
        int iforS = 0;
        int iforE = 0;

        for (i = 0; i < questionsArrayList.size(); i++) {

            String question = questionsArrayList.get(i).getText();
            String QuestionID = questionsArrayList.get(i).getId();
            TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setTypeface(null, Typeface.BOLD);

            randomColorMethod();

            QuestionAndAnswerId qus1 = new QuestionAndAnswerId();
            qus1.setQuestionsID(QuestionID);
            Log.i("QuestionAndAnswerId......", qus1.getQuestionsID());

            linearLayout.addView(textView);
            textView.setText((i + 1) + ". " + question);
            Log.i("questionssssssss......", question);


            templateAnswerArrayList = getAnswerfromdatabase(QuestionID);


            LinearLayout linearLayout1 = new LinearLayout(this);
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

            if (questionsArrayList.get(i).getQoT().equals("2") ) {
                iforR += Integer.parseInt(String.valueOf(templateAnswerArrayList.size()));
                int answerCounter = 0;
                radioGroup = new RadioGroup(this);
                ArrayList<Answer> AnswerIdList = new ArrayList<>();
                for (int j = iforR; j < iforR + templateAnswerArrayList.size(); j++) {
                    RadioButton radioButton = new RadioButton(this);
                    Answer ans1 = new Answer();
                    radioButton.setId(R.id.radio_button + j);
                    ans1.setID(radioButton.getId());
                    radioGroup.addView(radioButton);
                    radioButton.setText(templateAnswerArrayList.get(answerCounter).getText());
                    ans1.setText(String.valueOf(radioButton.getText()));
                    radioButtonArrayList.add(radioButton);
                    AnswerIdList.add(ans1);
                    Log.i("ans1IDDD......", ans1.getID()+ "");
                    Log.i("ans1tetetet......", ans1.getText() + "");
                    Log.i("question......", radioButtonArrayList.get(radioButtonArrayList.indexOf(radioButton)).getId() + "");
                    Log.i("ansanasananaasss......", radioButton.getText() + "");
                    answerCounter++;
                }
                qus1.setAnswer(AnswerIdList);
                Log.i("qus1.getAnswer()qus1.getAnswer()",qus1.getAnswer()+"");
                Log.i("answerlistsizeeeeeeee",AnswerIdList.size()+"");
                linearLayout1.addView(radioGroup);
                linearLayout.addView(linearLayout1);
                for(final RadioButton radioButton : radioButtonArrayList) {
                    radioButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }

            }

            else if(questionsArrayList.get(i).getQoT().equals("3")){
                iforC += Integer.parseInt(String.valueOf(templateAnswerArrayList.size()));
                int answerCount = 0;
                ArrayList<Answer> AnswerIdList = new ArrayList<>();
                for(int j = iforC; j < iforC + templateAnswerArrayList.size(); j++){
                    CheckBox checkBox = new CheckBox(this);
                    Answer ans2 = new Answer();
                    checkBox.setId(R.id.checkbox_button +j);
                    ans2.setID(checkBox.getId());
                    linearLayout.addView(checkBox);
                    checkBox.setText(templateAnswerArrayList.get(answerCount).getText());
                    ans2.setText(String.valueOf(checkBox.getText()));
                    checkBoxArrayList.add(checkBox);
                    AnswerIdList.add(ans2);
                    Log.i("question......", checkBoxArrayList.get(checkBoxArrayList.indexOf(checkBox)).getId() + "");
                    Log.i("ansanasananaasss......", checkBox.getText() + "");
                    answerCount++;
                }
                qus1.setAnswer(AnswerIdList);
                for (CheckBox checkBox : checkBoxArrayList){
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            }

            else if(questionsArrayList.get(i).getQoT().equals("4")){
                iforS += Integer.parseInt(String.valueOf(templateAnswerArrayList.size()));
                int answerCount = 0;
                List<String> forSpinnerText = new ArrayList<>();
                for (int is = 0; is < templateAnswerArrayList.size(); is++){
                    forSpinnerText.add(templateAnswerArrayList.get(is).getText());
                }
                ArrayList<Answer> AnswerIdList = new ArrayList<>();
                final Spinner spinner = new Spinner(this);
                for (int j = iforS; j < iforS + templateAnswerArrayList.size(); j++){
                    Answer answers = new Answer();
                    spinner.setId(R.id.spinner + j);
                    Log.i("spinnerValue1", spinnerValue1 + "whyyyy");
                    answers.setID(spinner.getId());
                    answers.setText(forSpinnerText.get(answerCount));
                    AnswerIdList.add(answers);
                    answerCount++;
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, forSpinnerText);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
                Log.i("spinnerValue1", spinnerValue1 + "whyyyy");
                spinnerArrayList.add(spinner);
                linearLayout.addView(spinner);

                qus1.setAnswer(AnswerIdList);

            }

            else {
                iforE += Integer.parseInt(String.valueOf(templateAnswerArrayList.size()));
                for (int j = iforE; j < iforE + templateAnswerArrayList.size(); j++){
                    ArrayList<Answer> AnswerIdList = new ArrayList<>();
                EditText editText = new EditText(this);
                Answer ans2 = new Answer();
                editText.setId(R.id.edittext_box + j);
                ans2.setID(editText.getId());
                editText.setLines(1);
                editTextArrayList.add(editText);
                linearLayout.addView(editText);
                AnswerIdList.add(ans2);
                qus1.setAnswer(AnswerIdList);
                Log.i("question......", editTextArrayList.get(editTextArrayList.indexOf(editText)).getId() + "");
                Log.i("ansanasananaasss......", editText.getText() + "");

                editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Survey_Detail.this, view.getId() + "", Toast.LENGTH_SHORT).show();
                    }
                });
                editText.setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        // if enter is pressed start calculating
                        if (keyCode == KeyEvent.KEYCODE_ENTER
                                && event.getAction() == KeyEvent.ACTION_UP) {

                            // get EditText text
                            String text = ((EditText) v).getText().toString();

                            // find how many rows it cointains
                            editTextRowCount = text.split("\\n").length;

                            // user has input more than limited - lets do something
                            // about that
                            if (editTextRowCount >= 3) {

                                // find the last break
                                int lastBreakIndex = text.lastIndexOf("\n");

                                // compose new text
                                String newText = text.substring(0, lastBreakIndex);

                                // add new text - delete old one and append new one
                                // (append because I want the cursor to be at the end)
                                ((EditText) v).setText("");
                                ((EditText) v).append(newText);

                            }
                        }

                        return false;
                    }
                });
            }
            }
            QusAndAnsArraylilst.add(qus1);
            LinearLayout.LayoutParams layoutParams_for_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
            layoutParams_for_line.setMargins(0, 20, 0, 20);
            View line = new View(this);
            line.setLayoutParams(layoutParams_for_line);
            line.setBackgroundColor(Color.parseColor("#B3B3B3"));
            linearLayout.addView(line);

        }

            LinearLayout.LayoutParams layoutParams_for_btn = new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams_for_btn.setMargins(0, 20, 0, 0);
            Button button = new Button(this);
            button.setLayoutParams(layoutParams_for_btn);
            button.setWidth(10);
            linearLayout.addView(button);
            button.setText("Save");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            for (RadioButton radioButton : radioButtonArrayList) {
                                if (radioButton.isChecked() == true) {
                                    SelectedAnswer seleAns1 = new SelectedAnswer();
                                    seleAns1.setAnswerID(radioButton.getId());
                                    seleAns1.setAnswerText(String.valueOf(radioButton.getText()));
                                    selectedAnswerForRadioButtonArrayList.add(seleAns1);
                                }
                            }
                            for (CheckBox checkBox : checkBoxArrayList) {
                                if (checkBox.isChecked() == true) {
                                    SelectedAnswer seleAns2 = new SelectedAnswer();
                                    seleAns2.setAnswerID(checkBox.getId());
                                    seleAns2.setAnswerText(String.valueOf(checkBox.getText()));
                                    selectedAnswerForCheckBoxArrayList.add(seleAns2);

                                }
                            }
                            for (Spinner spinner : spinnerArrayList){
                                if (spinnerArrayList.size() != 0){
                                    SelectedAnswer seleAns4 = new SelectedAnswer();
                                    seleAns4.setAnswerID((int) spinner.getId());
                                    seleAns4.setAnswerText(spinner.getSelectedItem().toString());
                                    selectedAnswerForSpinnersArrayList.add(seleAns4);
                                }
                            }
                                for (EditText editText : editTextArrayList) {
                                    SelectedAnswer seleAns3 = new SelectedAnswer();
                                    seleAns3.setAnswerID(editText.getId());
                                    seleAns3.setAnswerText(String.valueOf(editText.getText()));
                                    selectedAnswerForEditTextArrayList.add(seleAns3);
                                }

                                int validate = selectedAnswerForRadioButtonArrayList.size()+selectedAnswerForCheckBoxArrayList.size();
                                String customerName =  String.valueOf(edtName.getText());
                                String customerPhone = String.valueOf(edtPhone.getText());
                                String customerEmail = String.valueOf(edtEmail.getText());
                                String customerAddress = String.valueOf(edtAddress.getText());
                                String customerID = makeCustomerId();
                    if(customerName == "" || customerPhone == "" || customerEmail == "" || customerAddress == ""){
                        Toast.makeText(Survey_Detail.this, "Please fill the all of customer information!", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Matcher matcher = Pattern.compile(regEx).matcher(customerEmail);
                        if (matcher.matches()){
                            if(validate == 0){
                                showAlert("questions");
                            }
                            else {

                                database.beginTransaction();
                                ContentValues cv5 = new ContentValues();
                                cv5.put("id",customerID);
                                cv5.put("name",customerName);
                                cv5.put("phoneNo",customerPhone);
                                cv5.put("Email",customerEmail);
                                cv5.put("Address",customerAddress);
                                database.insert("customer", null, cv5);
                                database.setTransactionSuccessful();
                                database.endTransaction();

                                String AnsId;
                                AnsId = makeAnsId();
                                database.beginTransaction();
                                ContentValues cv1 = new ContentValues();
                                cv1.put("id",AnsId);
                                cv1.put("survey_template_id",Survey_Detail.TempID);
                                cv1.put("customer_id", customerID);
                                cv1.put("user_id","U0001");
                                cv1.put("Date",makeDateTime());
                                database.insert("survey_answer", null, cv1);
                                database.setTransactionSuccessful();
                                database.endTransaction();

                                if( selectedAnswerForRadioButtonArrayList != null){
                                    for (SelectedAnswer  s :   selectedAnswerForRadioButtonArrayList){
                                        String AnsDetailId;
                                        AnsDetailId = makeAnsDetailId();
                                        database.beginTransaction();
                                        ContentValues cv2 = new ContentValues();
                                        cv2.put("id",AnsDetailId);
                                        cv2.put("Answer",s.getAnswerText());
                                        cv2.put("question_id",getQuestionsID(s.getAnswerID()));
                                        cv2.put("survey_answer_id",AnsId);
                                        database.insert("survey_answer_detail", null, cv2);
                                        database.setTransactionSuccessful();
                                        database.endTransaction();
                                    }
                                }

                                if( selectedAnswerForCheckBoxArrayList != null){
                                    for (SelectedAnswer s :   selectedAnswerForCheckBoxArrayList){
                                        String AnsDetailId;
                                        AnsDetailId = makeAnsDetailId();
                                        database.beginTransaction();
                                        ContentValues cv3 = new ContentValues();
                                        cv3.put("id",AnsDetailId);
                                        cv3.put("Answer",s.getAnswerText());
                                        cv3.put("question_id",getQuestionsID(s.getAnswerID()));
                                        cv3.put("survey_answer_id",AnsId);
                                        database.insert("survey_answer_detail", null, cv3);
                                        database.setTransactionSuccessful();
                                        database.endTransaction();
                                    }
                                }

                                if ( selectedAnswerForSpinnersArrayList != null){
                                    for (SelectedAnswer s : selectedAnswerForSpinnersArrayList){
                                        String AnsDetailId;
                                        AnsDetailId = makeAnsDetailId();
                                        database.beginTransaction();
                                        ContentValues cv4 = new ContentValues();
                                        cv4.put("id",AnsDetailId);
                                        cv4.put("Answer",s.getAnswerText());
                                        cv4.put("question_id",getQuestionsID(s.getAnswerID()));
                                        cv4.put("survey_answer_id",AnsId);
                                        database.insert("survey_answer_detail", null, cv4);
                                        database.setTransactionSuccessful();
                                        database.endTransaction();
                                    }
                                }

                                if( selectedAnswerForEditTextArrayList != null){
                                    for (SelectedAnswer s :   selectedAnswerForEditTextArrayList){
                                        String AnsDetailId;
                                        AnsDetailId = makeAnsDetailId();
                                        database.beginTransaction();
                                        ContentValues cv4 = new ContentValues();
                                        cv4.put("id",AnsDetailId);
                                        cv4.put("Answer",s.getAnswerText());
                                        cv4.put("question_id",getQuestionsID(s.getAnswerID()));
                                        cv4.put("survey_answer_id",AnsId);
                                        database.insert("survey_answer_detail", null, cv4);
                                        database.setTransactionSuccessful();
                                        database.endTransaction();
                                    }
                                }
                                startActivity(new Intent(Survey_Detail.this, Survey_List.class));
                            }

                        }
                        else {
                            Toast.makeText(Survey_Detail.this, "Please fill the Email Address correctly !", Toast.LENGTH_SHORT).show();
                        }
                    }
                            }
            });

        }

    private void showAlert(String s) {
        new AlertDialog.Builder(Survey_Detail.this).setTitle("Alert").setMessage("Please answer the  " + s + ". Or you need to fill your informations").setPositiveButton("Ok", null).show();
    }

    private void randomColorMethod() {
        rand = new Random();

        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        randomColor = Color.rgb(r, g, b);
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

    private ArrayList<Template_Answer> getAnswerfromdatabase (String qusID){
        ArrayList<Template_Answer> template_answerArrayList = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM survey_question_detail WHERE survey_question_id = '"+qusID+"' ", null);
        while (c.moveToNext()){
            Template_Answer answer = new Template_Answer();
            answer.setText(c.getString(c.getColumnIndex("Text")));
            template_answerArrayList.add(answer);
        }
        return template_answerArrayList;
    }

    private String getQuestionsID (int answerID){
        String questionID = null;
        for (QuestionAndAnswerId QNA :QusAndAnsArraylilst){
            for (Answer ans : QNA.getAnswer()){
                if (ans.getID() == answerID){
                    questionID =  QNA.getQuestionsID();
                }
            }
        }
        return questionID;
    }

    private  String makeAnsId() {
        int Iddd = 0;
        Cursor c = database.rawQuery("SELECT COUNT(*) FROM survey_answer", null);

        while (c.moveToNext()){
             Iddd = c.getInt(c.getColumnIndex("COUNT(*)"));
        }
        String AnsId = "SA" + (Iddd +1);
        return AnsId;
    }

    private  String makeAnsDetailId () {
        int Iddd = 0;
        Cursor c = database.rawQuery("SELECT COUNT(*) FROM survey_answer_detail", null);
        while (c.moveToNext()){
            Iddd = c.getInt(c.getColumnIndex("COUNT(*)"));
        }
        String AnsDetailId = "SAD" + (Iddd +1);
        return AnsDetailId;
    }

    private String makeCustomerId() {
        int Iddd = 0;
        Cursor c = database.rawQuery("SELECT COUNT(*) FROM customer", null);
        while (c.moveToNext()){
            Iddd = c.getInt(c.getColumnIndex("COUNT(*)"));
        }
        String userId = "U" + (Iddd +1);
        return userId;
    }

    private String makeDateTime() {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        String myDate = format.format(new Date());

        return myDate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
