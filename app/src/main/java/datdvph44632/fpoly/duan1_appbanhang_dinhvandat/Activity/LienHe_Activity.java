package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.KhachHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter.LienHe_Adapter;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;

public class LienHe_Activity extends AppCompatActivity {

    ArrayList<KhachHang> listAdmin = new ArrayList<>();
    RecyclerView recyclerView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        recyclerView = findViewById(R.id.recyclerView_LienHe);
        listAdmin.add(new KhachHang("", "codedoan.com", "Quyết", "", "codedoan.com@gmail.com", "", "", "0337574502", null));
        listAdmin.add(new KhachHang("", "codedoan.com", "Quyết", "", "codedoan.com@gmail.com", "", "", "033343223", null));
        listAdmin.add(new KhachHang("", "codedoan.com", "Quyết", "", "codedoan.com@gmail.com", "", "", "06767685", null));
        listAdmin.add(new KhachHang("", "codedoan.com", "Quyết", "", "codedoan.com@gmail.com", "", "", "03456578", null));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        LienHe_Adapter lienHe_adapter = new LienHe_Adapter(context, listAdmin);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(lienHe_adapter);
        useToolbar();
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Danh sách Admin");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}