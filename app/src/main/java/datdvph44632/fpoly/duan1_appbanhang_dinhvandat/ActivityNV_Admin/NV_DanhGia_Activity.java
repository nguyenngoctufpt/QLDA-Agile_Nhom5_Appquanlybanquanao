package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityNV_Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Activity.Info_QuanAo_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.KhachHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoRateDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DonHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.KhachHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAoRate;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;

public class NV_DanhGia_Activity extends AppCompatActivity {

    Context context = this;
    DonHang donHang = null;
    QuanAoRateDAO quanAoRateDAO;
    QuanAoRate quanAoRate;
    ChangeType changeType = new ChangeType();
    QuanAo quanAo;
    String TAG = "NV_DanhGia_Activity_____";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nv_danh_gia);
        quanAoRateDAO = new QuanAoRateDAO(context);
        useToolbar();
        getInfoDonHang();
        setQuanAoView();

        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("what", "none");
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
        if (pref != null) {
            String infoWhat = pref.getString("what", "null");
            if (!infoWhat.equals("none")) {
                finish();
            }
        }
    }

    private void useToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText("Đánh giá Sản phẩm");
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getInfoDonHang() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                donHang = (DonHang) intent.getExtras().getBinder("donhang");
                Log.d(TAG, "getInfoQuanAo: DonHang: " + donHang.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setReview(String isRate){
        LinearLayout layoutReview = findViewById(R.id.layout_Review);
        TextView detailReview = findViewById(R.id.textView_DetailReview);
        ImageView imageReview = findViewById(R.id.imageView_Review);
        EditText reviewKH = findViewById(R.id.editText_DanhGia);
        RatingBar ratingBar = findViewById(R.id.ratingBar_DanhGia);
        Button buttonDanhGia = findViewById(R.id.button_DanhGia);

        if (isRate.equals("true")){
            ratingBar.setRating(0f);
            detailReview.setText("Khách hàng đã đánh giá đơn hàng này!");
            layoutReview.setBackgroundColor(Color.parseColor("#26AB9A"));
            imageReview.setImageResource(R.drawable.check_icon);
            ArrayList<QuanAoRate> list = quanAoRateDAO.selectQuanAoRate(null, "maRate=?", new String[]{donHang.getMaRate()}, null);
            if (list.size() > 0) {
                Log.d(TAG, "setLayout: yo");
                quanAoRate = list.get(0);
                Log.d(TAG, "setLayout: quanao rate: " + quanAoRate.toString());
            }
            if (quanAoRate != null) {
                ratingBar.setRating(changeType.getRatingFloat(quanAoRate.getRating()));
                ratingBar.setIsIndicator(true);
                reviewKH.setText(quanAoRate.getDanhGia());
                reviewKH.setEnabled(false);
            }
        }

        if (isRate.equals("false")){
            ratingBar.setRating(0f);
            reviewKH.setText("Đánh giá của khách hàng.");
            detailReview.setText("Khách hàng chưa đánh giá đơn hàng này!");
            layoutReview.setBackgroundColor(Color.parseColor("#F44336"));
            imageReview.setImageResource(R.drawable.crossed_icon);
        }

        buttonDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setQuanAoView() {
        ImageView imageQuanAo = findViewById(R.id.imageView_QuanAo);
        ImageView imageUser = findViewById(R.id.imageView_Avatar);
        TextView nameLap = findViewById(R.id.textView_TenQuanAo);
        TextView soLuong = findViewById(R.id.textView_Soluong);
        TextView giaTien = findViewById(R.id.textView_GiaTien);
        TextView nameKH = findViewById(R.id.textView_TenUser);
        TextView email = findViewById(R.id.textView_Email);
        LinearLayout onclickQuanAo = findViewById(R.id.onclick_QuanAo);

        quanAo = new QuanAo("No Data", "No Data", "No Data", "0", 0, 0, new byte[]{});
        Log.d(TAG, "setRow: DonHang: " + donHang.toString());
        QuanAoDAO quanAoDAO = new QuanAoDAO(context);
        ArrayList<QuanAo> listLap = quanAoDAO.selectQuanAo(null, null, null, null);

        for (int i = 0; i < listLap.size(); i++) {
            QuanAo getLap = listLap.get(i);
            if (donHang.getMaQuanAo().equals(getLap.getMaQuanAo())) {
                quanAo = getLap;
            }
        }

        KhachHang khachHang = new KhachHang("No Data", "No Data", "No Data", "No Data",
                "0", "No Data", "No Data", "No Data", new byte[]{});
        Log.d(TAG, "setRow: KhachHang: " + khachHang.toString());
        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
        ArrayList<KhachHang> listKH = khachHangDAO.selectKhachHang(null, null, null, null);

        for (int i = 0; i < listKH.size(); i++) {
            KhachHang getKH = listKH.get(i);
            if (donHang.getMaKH().equals(getKH.getMaKH())) {
                khachHang = getKH;
            }
        }

        ChangeType changeType = new ChangeType();
        Bitmap anhLap = changeType.byteToBitmap(quanAo.getAnhquanAo());
        Bitmap anhKH = changeType.byteToBitmap(khachHang.getAvatar());

        imageQuanAo.setImageBitmap(anhLap);
        imageUser.setImageBitmap(anhKH);
        nameLap.setText(quanAo.getTenQuanAo());
        giaTien.setText(donHang.getThanhTien());
        soLuong.setText(String.valueOf(donHang.getSoLuong()));
        nameKH.setText(changeType.fullNameKhachHang(khachHang));
        email.setText(khachHang.getEmail());

        setReview(donHang.getIsDanhGia());

        onclickQuanAo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Info_QuanAo_Activity.class);
                if (quanAo != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("laptop", quanAo);
                    Log.d(TAG, "onBindViewHolder: quanao: " + quanAo.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("openFrom", "other");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}