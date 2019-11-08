package com.kitesoft.adapterviewmemberex;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Member> datas= new ArrayList<Member>();

    ListView listview;
    MemberAdapter memberAdapter;

    TextView tvEmpty;

    SearchView searchView;


    View layout_add;
    EditText edit_name;
    Spinner spinner_nation;
    RadioGroup rg_gender;

    InputMethodManager imm;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("MemberList");

        listview= (ListView)findViewById(R.id.listview);
        memberAdapter= new MemberAdapter(getLayoutInflater(), datas);
        listview.setAdapter(memberAdapter);
        listview.setOnItemLongClickListener(longListener);

        tvEmpty= (TextView)findViewById(R.id.text_empty);
        listview.setEmptyView(tvEmpty);

        layout_add= findViewById(R.id.layout_add);

        edit_name= (EditText)findViewById(R.id.edit_name);
        rg_gender= (RadioGroup)findViewById(R.id.rg_gender);
        spinner_nation= (Spinner)findViewById(R.id.spinner_nation);

        ArrayAdapter adapter= ArrayAdapter.createFromResource(this, R.array.nations, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_nation.setAdapter(adapter);

        imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);

        MenuItem item= menu.findItem(R.id.option_search);
        searchView= (SearchView)item.getActionView();

        searchView.setQueryHint("enter name");
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(query_listener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ){
            case R.id.option_add:
                layout_add.setVisibility(View.VISIBLE);
                edit_name.requestFocus();
                imm.showSoftInput(edit_name, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void mOnClick(View v){
        switch( v.getId() ){
            case R.id.btn_add:

                String name= edit_name.getText().toString();
                int gender= ( rg_gender.getCheckedRadioButtonId()==R.id.rb_male )? 0:1;
                int flagId= spinner_nation.getSelectedItemPosition();
                String nation= getResources().getStringArray(R.array.nations)[flagId];

                //작성일
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
                String date= sdf.format(new Date());

                datas.add(0, new Member(name, nation, gender, flagId, date));
                memberAdapter.notifyDataSetChanged();

                edit_name.setText("");
                rg_gender.check(R.id.rb_male);
                spinner_nation.setSelection(0);

                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                layout_add.setVisibility(View.GONE);

                break;
            case R.id.btn_cancel:

                edit_name.setText("");
                rg_gender.check(R.id.rb_male);
                spinner_nation.setSelection(0);

                if(getCurrentFocus()!=null) imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                layout_add.setVisibility(View.GONE);

                break;
        }
    }


    SearchView.OnQueryTextListener query_listener= new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {

            for(int i=0; i<datas.size(); i++){
                if( datas.get(i).getName().equals(query)){


                    searchView.setQuery("", false);
                    searchView.setIconified(true);

                    Toast.makeText(MainActivity.this, i+" 번째에 데이터가 존재합니다.", Toast.LENGTH_SHORT).show();

                    listview.setSelection(i);
                    //imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return false;
                }
            }

            Toast.makeText(getApplicationContext(), query+" 검색이 완료되었습니다.\n해당하는 데이터를 찾지 못했습니다.", Toast.LENGTH_SHORT).show();
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            return false;
        }
    };

    AdapterView.OnItemLongClickListener longListener= new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            index= position;

            PopupMenu pop= new PopupMenu(getApplicationContext(), view);
            getMenuInflater().inflate(R.menu.popup, pop.getMenu());

            pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {


                    switch( item.getItemId() ){
                        case R.id.delete:
                            datas.remove(index);
                            memberAdapter.notifyDataSetChanged();
                            break;
                        case R.id.modify:
                            layout_add.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), datas.get(index).getName()+" 수정합니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.info:
                            Toast.makeText(getApplicationContext(), datas.get(index).getName()+" 정보를 봅니다.", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    return false;
                }
            });
            pop.show();



            return true;
        }
    };

}
