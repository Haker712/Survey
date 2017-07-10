package aceplus.survey2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

import aceplus.survey2.objects.Template;
import aceplus.survey2.utils.DataBase;

/**
 * Created by PhyoKyawSwar on 6/2/17.
 */

public class Survey_List extends AppCompatActivity {


    SQLiteDatabase database;
    private ArrayList<Template> gettemplate;
    private templateAdapter adapter;
    RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<Template> dictionaryWords;
    private ArrayList<Template> filterList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_list);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        database = new DataBase(this).getDataBase();
        gettemplate = gettemplateFromDataBase();

        EditText searchEdit = (EditText) findViewById(R.id.searchBox);
        dictionaryWords = gettemplateFromDataBase();
        filterList = new ArrayList<Template>();
        filterList.addAll(dictionaryWords);

        recyclerView = (RecyclerView) findViewById(R.id.recyclersurvey_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        assert recyclerView != null;
        adapter = new templateAdapter(filterList);
        recyclerView.setAdapter(adapter);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapter.getTemFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private ArrayList<Template>  gettemplateFromDataBase (){
        ArrayList<Template> templateArrayList = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM survey_template", null);
        int i = 1;
        while (c.moveToNext()){
            Template template = new Template();
            template.setId(c.getString(c.getColumnIndex("id")));
            template.setTitle(/*i + ". " +*/c.getString(c.getColumnIndex("Name")));
            template.setDescription(c.getString(c.getColumnIndex("Description")));
            template.setQoQ(c.getString(c.getColumnIndex("QuantityOfquestions")));
            templateArrayList.add(template);
            i++;
        }
        c.close();
        return templateArrayList;
    }

    public class templateAdapter extends RecyclerView.Adapter<templateAdapter.ViewHolder>{

        private ArrayList<Template> gettemplate;
        private CustomFilter temFilter;
        public templateAdapter(ArrayList<Template> gettemplate) {

            temFilter = new CustomFilter(templateAdapter.this);

            this.gettemplate = gettemplate;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_single_list, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerView.getChildAdapterPosition(view);
                    Template template = gettemplate.get(position);
                    Toast.makeText(Survey_List.this, template.getId() , Toast.LENGTH_SHORT).show();
                    Survey_Detail.TempID = template.getId();
                    Log.i("List_from_Id.........",Survey_Detail.TempID+"");
                    startActivity(new Intent(Survey_List.this, Survey_Detail.class));
                }
            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Template template = gettemplate.get(position);
            holder.title.setText(template.getTitle());
            holder.description.setText(template.getDescription());
        }

        @Override
        public int getItemCount() {
            return gettemplate.size();
        }

        public CustomFilter getTemFilter() {
            return temFilter;
        }

        public void setTemFilter(CustomFilter temFilter) {
            this.temFilter = temFilter;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView title;
            private TextView description;
            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.listTiltle);
                description = (TextView) itemView.findViewById(R.id.listDescription);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                backupDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("SdCardPath")
    private void backupDB() {
        /*Calendar now = Calendar.getInstance();
        String today = now.get(Calendar.DATE) + "." + (now.get(Calendar.MONTH) + 1)
                + "." + now.get(Calendar.YEAR);*/

        Calendar c = Calendar.getInstance();
        /*int seconds = c.get(Calendar.SECOND);
        String today = Utility.getCurrentDate(true);*/
        String today = String.valueOf(c.get(Calendar.SECOND));

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                Toast.makeText(getApplicationContext(), "Backup database is starting...",
                        Toast.LENGTH_SHORT).show();
                String currentDBPath = "/data/aceplus.survey2/databases/Survey.sqlite";
                //String currentDBPath = "/data/com.aceplus.myanmar_padauk/databases/myanmar-padauk.db";

                String backupDBPath = "Survey_DB_Backup_" + today + ".db";
                File currentDB = new File(data, currentDBPath);

                String folderPath = "mnt/sdcard/Survey_DB_Backup";
                File f = new File(folderPath);
                f.mkdir();
                File backupDB = new File(f, backupDBPath);
                FileChannel source = new FileInputStream(currentDB).getChannel();
                FileChannel destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
                Toast.makeText(getApplicationContext(), "Backup database Successful!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please set Permission for Storage in Setting!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cannot Backup!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public class CustomFilter extends Filter {

        private templateAdapter templateAdapter;

        public CustomFilter(templateAdapter templateAdapter) {
            super();
            this.templateAdapter = templateAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filterList.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0){
                filterList.addAll(dictionaryWords);
            }
            else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Template title : dictionaryWords){
                    if (title.getTitle().toLowerCase().startsWith(filterPattern)){
                        filterList.add(title);
                    }
                }
            }
            results.values = filterList;
            results.count = filterList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            this.templateAdapter.notifyDataSetChanged();
        }
    }

}
