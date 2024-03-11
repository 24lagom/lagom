package com.example.logindemo;

import static android.content.Context.MODE_PRIVATE;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryFragment extends Fragment {

    //定义spinner中的数据
    private String[] date_data = {"", "202301", "202302", "202303", "202304", "202305", "202306", "202307", "202308", "202309", "202310", "202311", "202312"};
    private String[] type_data = {"", "收入", "支出"};
    Spinner spin_date, spin_type;
    ListView listView;
    TextView tv_show;
    Button btnSearch;
    float sum = 0;
    //数据库
    private String selectDate, selectType;
    private static final String DATABASE_NAME = "Test.db";
    private static final String TABLE_NAME = "record";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_STATE = "state";
    private SQLiteDatabase sqLiteDatabase = null;

    private void selectSumMoney() {
        //自定义查询的sql语句
        String sql;
        //如果查询时间和查询类型都为空，则查询整个表
        if (TextUtils.isEmpty(selectDate) && TextUtils.isEmpty(selectType)) {
            sql = "select * from " + TABLE_NAME;
            //如果有查询时间，没有查询类型，查询指定内容
        } else if (!TextUtils.isEmpty(selectDate) && TextUtils.isEmpty(selectType)) {
            sql = "select * from " + TABLE_NAME + " where date='" + selectDate + "'";
            //如果没有查询时间，有查询类型，查询指定内容
        } else if (TextUtils.isEmpty(selectDate) && !TextUtils.isEmpty(selectType)) {//如果没有查询时间，有查询类型
            sql = "select * from " + TABLE_NAME + " where type='" + selectType + "'";
        } else {//否则，查询条件都不为空，查询指定内容
            sql = "select * from " + TABLE_NAME + " where date='" + selectDate + "' and type='" + selectType + "'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        while (cursor.moveToNext()) {

            float money = cursor.getFloat(cursor.getColumnIndex(COLUMN_MONEY));
            sum = sum + money;

            //list.add(map);
        }
        String money2 = String.valueOf(sum);
        tv_show.setText(money2);
        sum = 0;
    }

    //选择数据
    private void selectData() {
        //自定义查询的sql语句
        String sql;
        //如果查询时间和查询类型都为空，则查询整个表
        if (TextUtils.isEmpty(selectDate) && TextUtils.isEmpty(selectType)) {
            sql = "select * from " + TABLE_NAME;
            //如果有查询时间，没有查询类型，查询指定内容
        } else if (!TextUtils.isEmpty(selectDate) && TextUtils.isEmpty(selectType)) {
            sql = "select * from " + TABLE_NAME + " where date='" + selectDate + "'";
            //如果没有查询时间，有查询类型，查询指定内容
        } else if (TextUtils.isEmpty(selectDate) && !TextUtils.isEmpty(selectType)) {//如果没有查询时间，有查询类型
            sql = "select * from " + TABLE_NAME + " where type='" + selectType + "'";
        } else {//否则，查询条件都不为空，查询指定内容
            sql = "select * from " + TABLE_NAME + " where date='" + selectDate + "' and type='" + selectType + "'";
        }
        //将查询到的数据封装到Cursor
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (cursor.getCount() == 0) {
            //查无数据则怒不显示列表
            listView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "无数据", Toast.LENGTH_SHORT).show();
        } else {
            //查有数据则显示列表
            listView.setVisibility(View.VISIBLE);
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                float money = cursor.getFloat(cursor.getColumnIndex(COLUMN_MONEY));
                String state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE));
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(id));
                map.put("date", date);
                map.put("type", type);
                map.put("money", String.valueOf(money));
                map.put("state", state);
                list.add(map);
            }
            //创建SimpleAdapter
            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                    list,
                    R.layout.record_item_layout,
                    new String[]{"id", "date", "type", "money", "state"},
                    new int[]{R.id.list_id, R.id.list_date, R.id.list_type, R.id.list_money, R.id.list_state});
            listView.setAdapter(simpleAdapter);

        }
    }

    //时间和类别spinner点击事件
    private void initClick() {
        //时间事件
        spin_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectDate = date_data[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //类别事件
        spin_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType = type_data[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectData();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        tv_show = view.findViewById(R.id.tv_show);
        btnSearch = view.findViewById(R.id.btn_search);
        try {
            //打开数据库，如果是第一次会创建该数据库，模式为MODE_PRIVATE
            sqLiteDatabase = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            //执行创建表的sql语句，虽然每次都调用，但只有首次才创建表

            //执行查询
            listView = view.findViewById(R.id.searchlistview);//绑定列表
            selectData();
        } catch (SQLException e) {
            Toast.makeText(getActivity(), "数据库异常!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, date_data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_date = view.findViewById(R.id.spin_date);
        spin_date.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new
                ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, type_data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_type = view.findViewById(R.id.spin_type);
        spin_type.setAdapter(adapter1);
        initClick();
        Button btn_calc = view.findViewById(R.id.btn_calc);
        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSumMoney();

            }
        });
        return view;
    }
}
