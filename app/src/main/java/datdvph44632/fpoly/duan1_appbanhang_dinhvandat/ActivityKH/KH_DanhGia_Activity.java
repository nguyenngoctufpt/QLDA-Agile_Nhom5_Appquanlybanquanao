package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityKH;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Activity.Info_QuanAo_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.DonHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoRateDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DonHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAoRate;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;

import java.util.ArrayList;

public class KH_DanhGia_Activity extends AppCompatActivity {

    Context context = this;
    DonHang donHang = null;
    String TAG = "KH_DanhGia_Activity_____";
    EditText reviewInput;
    QuanAo quanAo;
    QuanAoRateDAO quanAoRateDAO;
    DonHangDAO donHangDAO;
    QuanAoRate quanAoRate;
    ChangeType changeType = new ChangeType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kh_danh_gia);
        reviewInput = findViewById(R.id.editText_DanhGia);
        quanAoRateDAO = new QuanAoRateDAO(context);
        donHangDAO = new DonHangDAO(context);
        useToolbar();
        getInfoDonHang();
        setLaptopView();
        setLayout();

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

    private void setLayout() {
        RatingBar ratingBar = findViewById(R.id.ratingBar_DanhGia);
        EditText editTextDanhGia = findViewById(R.id.editText_DanhGia);
        Button buttonDanhGia = findViewById(R.id.button_DanhGia);
        quanAoRateDAO.selectQuanAoRate(null, null, null, null);
        if (donHang.getIsDanhGia().equals("false")) {
            setReviewText();
            ratingBar.setRating(0f);

            buttonDanhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!editTextDanhGia.getText().equals("")) {
                        quanAoRate = new QuanAoRate(donHang.getMaDH(), quanAo.getMaQuanAo(), editTextDanhGia.getText().toString(), ratingBar.getRating());
                        quanAoRateDAO.insertQuanAoRate(quanAoRate);
                        donHang.setIsDanhGia("true");
                        donHang.setMaRate(donHang.getMaDH());
                        donHangDAO.updateDonHang(donHang);
                        finish();
                    } else {
                        Toast.makeText(context, "Hãy nhập cảm nghĩ của bạn trước khi hoàn thành nhé!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            ArrayList<QuanAoRate> list = quanAoRateDAO.selectQuanAoRate(null, "maRate=?", new String[]{donHang.getMaRate()}, null);
            if (list.size() > 0) {
                Log.d(TAG, "setLayout: yo");
                quanAoRate = list.get(0);
                Log.d(TAG, "setLayout: Laptop rate: " + quanAoRate.toString());
            }
            if (quanAoRate != null) {
                ratingBar.setRating(changeType.getRatingFloat(quanAoRate.getRating()));
                ratingBar.setIsIndicator(true);
                editTextDanhGia.setText(quanAoRate.getDanhGia());
                editTextDanhGia.setEnabled(false);

                buttonDanhGia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
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
                Log.d(TAG, "getInfoLaptop: DonHang: " + donHang.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLaptopView() {
        ImageView imageLaptop = findViewById(R.id.imageView_QuanAo);
        TextView name = findViewById(R.id.textView_TenQuanAo);
        TextView soLuong = findViewById(R.id.textView_Soluong);
        TextView giaTien = findViewById(R.id.textView_GiaTien);
        LinearLayout onclickLaptop = findViewById(R.id.onclick_QuanAo);
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

        ChangeType changeType = new ChangeType();
        Bitmap anhLap = changeType.byteToBitmap(quanAo.getAnhquanAo());

        imageLaptop.setImageBitmap(anhLap);
        name.setText(quanAo.getTenQuanAo());
        giaTien.setText(donHang.getThanhTien());
        soLuong.setText(String.valueOf(donHang.getSoLuong()));

        onclickLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Info_QuanAo_Activity.class);
                if (quanAo != null) {
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("laptop", quanAo);
                    Log.d(TAG, "onBindViewHolder: Laptop: " + quanAo.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("openFrom", "other");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setReviewText() {
        AppCompatButton review1 = findViewById(R.id.button_Hint_DanhGia1);
        AppCompatButton review2 = findViewById(R.id.button_Hint_DanhGia2);
        AppCompatButton review3 = findViewById(R.id.button_Hint_DanhGia3);
        AppCompatButton review4 = findViewById(R.id.button_Hint_DanhGia4);

        review1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewInput.setText(R.string.ch_t_l_ng_s_n_ph_m_tuy_t_v_i);
            }
        });

        review2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewInput.setText(R.string.r_t_ng_ti_n);
            }
        });

        review3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewInput.setText(R.string.shop_ph_c_v_t_t);
            }
        });

        review4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewInput.setText(R.string.th_i_gian_giao_h_ng_r_t_nhanh);
            }
        });
    }
}