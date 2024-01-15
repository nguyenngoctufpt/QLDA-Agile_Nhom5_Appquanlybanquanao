package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityKH;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter.KH_ViewDonHang_Adapter;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import com.google.android.material.tabs.TabLayout;

public class KH_DonHang_Activity extends AppCompatActivity {


    private KH_ViewDonHang_Adapter adapter;
    private TabLayout tablayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_don_hang);
        useToolbar();
        tablayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        adapter = new KH_ViewDonHang_Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);

    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Đơn Hàng đã mua");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}