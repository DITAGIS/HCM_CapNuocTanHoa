package com.ditagis.hcm.docsotanhoa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ditagis.hcm.docsotanhoa.adapter.GridViewXemLoTrinhDaTaiAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XemLoTrinhDaTai extends AppCompatActivity {
    TextView m_txtTongMLT;
    TextView m_txtTongDB;
    EditText editTextSearch;
    GridView gridView;
    //Dùng mảng 1 chiều hoặc ArrayList để lưu một số dữ liệu
    private ArrayList<String> m_mlt;
    private HashMap<String, Integer> m_MLT_TongDanhBo;
    private GridViewXemLoTrinhDaTaiAdapter da;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_xem_lo_trinh_da_tai);
        m_mlt = new ArrayList<String>();
        m_txtTongMLT = (TextView) findViewById(R.id.txt_llt_mlt);
        m_txtTongDB = (TextView) findViewById(R.id.txt_llt_db);

        editTextSearch = (EditText) findViewById(R.id.etxt_llt_search);
        gridView = (GridView) findViewById(R.id.grid_llt_danhSachLoTrinh);
        this.m_MLT_TongDanhBo = (HashMap<String, Integer>) getIntent().getSerializableExtra("data");

        m_txtTongMLT.setText("Mã lộ trình: " + this.m_MLT_TongDanhBo.size());
        int sum_db = 0;

        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
//                    System.out.println(gridView.getAdapter().getItem(0).toString());
                    List<String> result = new ArrayList<String>();
                    //Lấy dữ liệu bắt đầu với text search
                    for (String mlt : m_mlt) {
                        if (mlt.contains(s.toString()))
                            result.add(mlt);
                    }
                    //Gán dữ liệu vào data source
                    if (da != null && result.size() > 0) {
                        da.clear();
                        for (String mlt : result)
                            da.add(new GridViewXemLoTrinhDaTaiAdapter.Item(mlt, 0, false));
                    }

                } else {
                    //TODO
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Gán DataSource vào ArrayAdapter

        da = new GridViewXemLoTrinhDaTaiAdapter(XemLoTrinhDaTai.this, new ArrayList<GridViewXemLoTrinhDaTaiAdapter.Item>());

        for (HashMap.Entry<String, Integer> entry : this.m_MLT_TongDanhBo.entrySet()) {
            da.add(new GridViewXemLoTrinhDaTaiAdapter.Item(entry.getKey(), entry.getValue(), true));
            sum_db += entry.getValue();
        }

        m_txtTongDB.setText("Danh bộ: " + sum_db);
        //gán Datasource vào GridView

        gridView.setAdapter(da);
        registerForContextMenu(XemLoTrinhDaTai.this.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt_row_MLT = (TextView) view.findViewById(R.id.row_xltdt_txt_malotrinh);
                TextView txt_row_danhbo = (TextView) view.findViewById(R.id.row_xltdt_txt_tongDanhBo);
                ImageView imgCheck = (ImageView) findViewById(R.id.row_xltdt_img_Check);
                LinearLayout row_layout = (LinearLayout) findViewById(R.id.row_xltdt_layout);
                String mlt = txt_row_MLT.getText().toString();
                String danhbo = txt_row_danhbo.getText().toString();
                if (da.getItem(mlt).getCheckpos()) {
                    da.getItem(mlt).setCheckpos(false);
                    XemLoTrinhDaTai.this.m_MLT_TongDanhBo.remove(mlt);
                    imgCheck.setImageResource(0);
                    row_layout.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.color_row_uncheck));
                } else {
                    da.getItem(mlt).setCheckpos(true);
                    XemLoTrinhDaTai.this.m_MLT_TongDanhBo.put(mlt, Integer.parseInt(danhbo));
                    imgCheck.setImageResource(R.drawable.checked);
                    row_layout.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.color_row_check));
                }

            }

        });


    }

    public void doDownloadMLT(View v) {
        finish();
    }

    public void doDocSo(View v) {
        if (this.m_MLT_TongDanhBo.size() == 0)
            Toast.makeText(this, "Chưa có lộ trình!!!", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(XemLoTrinhDaTai.this, DocSoActivity.class);
            Bundle extras = new Bundle();

            String[] mltArr = new String[this.m_MLT_TongDanhBo.size()];
            int j = 0;
            for (String mlt : this.m_MLT_TongDanhBo.keySet())
                mltArr[j++] = mlt;
            extras.putStringArray("mMltArr", mltArr);
//            extras.putStringArrayList("mMlt", LayLoTrinhActivity.this.m_mlt);
//            extras.putBooleanArray("chkPosition", LayLoTrinhActivity.this.m_MLT_TongDanhBo);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    public void doQuanLyDocSo(View v) {
        Intent intent = new Intent(XemLoTrinhDaTai.this, QuanLyDocSoActivity.class);

        startActivity(intent);
    }
}