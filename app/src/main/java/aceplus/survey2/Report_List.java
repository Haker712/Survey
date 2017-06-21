package aceplus.survey2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aceplus.survey2.objects.AnswerReport;
import aceplus.survey2.utils.DataBase;

/**
 * Created by PhyoKyawSwar on 6/19/17.
 */

public class Report_List extends AppCompatActivity {

    private Toolbar toolbar;
    SQLiteDatabase database;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<AnswerReport> getAnswerList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_list);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        database = new DataBase(this).getDataBase();
        getAnswerList = getAnswerFromDataBase();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerUser_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new answerAdapater(getAnswerList);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<AnswerReport> getAnswerFromDataBase() {
        ArrayList<AnswerReport> answerReportArrayList = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM survey_answer", null);
        int i = 1;
        while (c.moveToNext()){
            AnswerReport answerReport = new AnswerReport();
            answerReport.setId(c.getString(c.getColumnIndex("id")));
            answerReport.setTemplate_Id(c.getString(c.getColumnIndex("survey_template_id")));
            answerReport.setCustomer_Id(c.getString(c.getColumnIndex("customer_id")));
            answerReport.setUser_Id(c.getString(c.getColumnIndex("user_id")));
            answerReport.setDate(c.getString(c.getColumnIndex("Date")));
            answerReportArrayList.add(answerReport);
        }
        c.close();
        return  answerReportArrayList;
    }

    private String getTempName(String tempId){
        String tempName = null;
        Cursor c = database.rawQuery("SELECT * FROM survey_template WHERE id = '"+tempId+"' ", null);
        while (c.moveToNext()){
            tempName = c.getString(c.getColumnIndex("Name"));
        }

        return tempName;
    }

    private String getCustomName(String custoId){
        String customerName = null;
        Cursor c = database.rawQuery("SELECT * FROM customer WHERE id = '"+custoId+"' ", null);
        while (c.moveToNext()){
            customerName = c.getString(c.getColumnIndex("name"));
        }

        return customerName;
    }

    public class answerAdapater extends RecyclerView.Adapter<answerAdapater.ViewHolder>{

        private ArrayList<AnswerReport> getAnswerList;

        public answerAdapater(ArrayList<AnswerReport> getAnswerList) {
            this.getAnswerList = getAnswerList;
        }

        @Override
        public answerAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_single_list, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    AnswerReport answerReport = getAnswerList.get(position);
                    Report_Detail.AnsID = answerReport.getId();
                    Report_Detail.ReportTempID = answerReport.getTemplate_Id();
                    Report_Detail.CusID = answerReport.getCustomer_Id();
                    Toast.makeText(Report_List.this, answerReport.getId() , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Report_List.this, Report_Detail.class));
                }
            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(answerAdapater.ViewHolder holder, int position) {
            AnswerReport answerReport = getAnswerList.get(position);
            holder.tempName.setText(String.valueOf(getTempName(answerReport.getTemplate_Id())) );
            holder.custoName.setText(String.valueOf(getCustomName(answerReport.getCustomer_Id())));
            holder.dateTime.setText(answerReport.getDate());
        }

        @Override
        public int getItemCount() {
            return getAnswerList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tempName;
            private TextView custoName;
            private TextView dateTime;

            public ViewHolder(View itemView) {
                super(itemView);
                tempName = (TextView) itemView.findViewById(R.id.listTempName);
                custoName = (TextView) itemView.findViewById(R.id.listUserName);
                dateTime = (TextView) itemView.findViewById(R.id.listDate);

            }
        }

    }
}
