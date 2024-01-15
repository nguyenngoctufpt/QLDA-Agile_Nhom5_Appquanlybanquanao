package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityKH.KH_ThanhToan_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.GioHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.KhachHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoRateDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.ThongBaoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.UseVoucherDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.GioHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.IdData;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.KhachHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAoRate;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.ThongBao;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.UseVoucher;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Info_QuanAo_Activity extends AppCompatActivity {

    Context context = this;
    QuanAo quanAo = null;
    String TAG = "Info_QuanAo_Activity_____";
    ImageView imagequanAo;
    TextView tenQuanAo, giaQuanAo, tsktQuanAo, soLuong, ratingTV, textView_CountRating;
    ChangeType changeType = new ChangeType();
    AppCompatButton buyNow, themVaoGio;
    GioHangDAO gioHangDAO;
    ArrayList<GioHang> listGio = new ArrayList<>();
    String openFrom;
    LinearLayout layout;
    KhachHang khachHang;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_quanao);
        imagequanAo = findViewById(R.id.imageView_QuanAo);
        tenQuanAo = findViewById(R.id.textView_TenQuanAo);
        giaQuanAo = findViewById(R.id.textView_GiaTien);
        tsktQuanAo = findViewById(R.id.textView_TSKT);
        soLuong = findViewById(R.id.textView_Soluong);
        buyNow = findViewById(R.id.button_Mua);
        ratingBar = findViewById(R.id.ratingBar_DanhGia);
        ratingTV = findViewById(R.id.textView_Rating);
        layout = findViewById(R.id.layoutViewer);
        textView_CountRating = findViewById(R.id.textView_CountRating);
        themVaoGio = findViewById(R.id.button_Add_To_GioHang);
        gioHangDAO = new GioHangDAO(context);

        getUser();
        getInfoQuanAo();
        setInfoQuanAo();
        addToCart();
        buyNowQuanAo();

        if (openFrom != null) {
            if (openFrom.equals("viewer")) {
                layout.setVisibility(View.VISIBLE);
                useToolbar(openFrom);
            } else {
                layout.setVisibility(View.GONE);
                useToolbar(openFrom);
            }
        }
    }

    private void buyNowQuanAo() {
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quanAo != null) {
                    if (quanAo.getSoLuong() != 0) {
                        Intent intent = new Intent(context, KH_ThanhToan_Activity.class);
                        final Bundle bundle = new Bundle();
                        bundle.putBinder("laptop", quanAo);
                        Log.d(TAG, "onBindViewHolder: Laptop: " + quanAo.toString());
                        intent.putExtras(bundle);
                        intent.putExtra("input", "MuaNgay");
                        IdData.getInstance().setIdDC("");
                        IdData.getInstance().setIdVou("");
                        context.startActivity(intent);
                        UseVoucherDAO useVoucherDAO = new UseVoucherDAO(context);
                        ArrayList<UseVoucher> listUS = useVoucherDAO.selectUseVoucher(null, "maKH=?", new String[]{khachHang.getMaKH()}, null);
                        if (listUS.size() > 0) {
                            for (UseVoucher use : listUS) {
                                if (use.getIsUsed().equals("truen't")) {
                                    use.setIsUsed("false");
                                    useVoucherDAO.updateUseVoucher(use);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Sản phẩm đang hết hàng!\nXin vui lòng đợi chúng tôi nhập sản phẩm!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUser() {
        SharedPreferences pref = getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            khachHang = null;
        } else {
            String user = pref.getString("who", "");
            KhachHangDAO khachHangDAO = new KhachHangDAO(context);
            ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{user}, null);
            if (list.size() > 0) {
                khachHang = list.get(0);
            }
        }
    }

    private void addToCart() {
        if (khachHang != null) {
            if (listGio != null) {
                themVaoGio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listGio = gioHangDAO.selectGioHang(null, "maKH=?", new String[]{khachHang.getMaKH()}, null);
                        if (quanAo.getSoLuong() != 0) {
                            GioHang getGio = null;
                            for (GioHang gio : listGio) {
                                if (gio.getMaQuanAo().equals(quanAo.getMaQuanAo())) {
                                    getGio = gio;
                                }
                            }
                            if (getGio == null) {
                                GioHang gioHang = new GioHang("GH" + listGio.size(), quanAo.getMaQuanAo(),
                                        khachHang.getMaKH(), "2022-11-17", "No Data", 1);
                                gioHangDAO.insertGioHang(gioHang);

                                Date currentTime = Calendar.getInstance().getTime();
                                String date = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                                ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
                                ThongBao thongBao = new ThongBao("TB", khachHang.getMaKH(), "Quản lý giỏ hàng",
                                        " Bạn đã thêm quần áo " + quanAo.getTenQuanAo() + " với giá " + quanAo.getGiaTien() + " vào giỏ hàng.", date);
                                thongBaoDAO.insertThongBao(thongBao, "kh");
                            } else {
                                if (getGio.getSoLuong() >= quanAo.getSoLuong()) {
                                    Toast.makeText(context, "Số lượng sản phẩm trong giỏ tối đa!", Toast.LENGTH_SHORT).show();
                                } else {
                                    getGio.setSoLuong(getGio.getSoLuong() + 1);
                                    gioHangDAO.updateGioHang(getGio);
                                    Toast.makeText(context, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Sản phẩm đang hết hàng!\n Xin vui lòng đợi chúng tôi nhập sản phẩm!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void setInfoQuanAo() {
        if (quanAo != null) {
            imagequanAo.setImageBitmap(changeType.byteToBitmap(quanAo.getAnhquanAo()));
            tenQuanAo.setText(quanAo.getTenQuanAo());
            giaQuanAo.setText("Giá tiền: " + quanAo.getGiaTien());
//            if (quanAo.getThongSoKT().equals("RAM 4GB")) {
//                tsktQuanAo.setText(R.string.tskt_ram_4g);
//            }
//            if (quanAo.getThongSoKT().equals("RAM 8GB")) {
//                tsktQuanAo.setText(R.string.tskt_ram_8g);
//            }
//            if (quanAo.getThongSoKT().equals("RAM 16GB")) {
//                tsktQuanAo.setText(R.string.tskt_ram_16g);
//            }
//            if (quanAo.getThongSoKT().equals("RAM 32GB")) {
//                tsktQuanAo.setText(R.string.tskt_ram_32g);
//            }
            soLuong.setText("Số lượng còn lại: " + quanAo.getSoLuong() + " sản phẩm");
            QuanAoRateDAO quanAoRateDAO = new QuanAoRateDAO(context);
            ArrayList<QuanAoRate> list = quanAoRateDAO.selectQuanAoRate(null, "maQuanAo=?", new String[]{quanAo.getMaQuanAo()}, null);
            if (list.size() > 0) {
                float rating = 0;
                for (QuanAoRate lapRate : list) {
                    rating += lapRate.getRating();
                }
                rating = rating / list.size();
                ratingBar.setRating(changeType.getRatingFloat(rating));
                ratingTV.setText(rating + "/5");
                textView_CountRating.setText(list.size() + " đánh giá:");
            } else {
                ratingBar.setRating(0f);
                ratingTV.setText("0/5");
                textView_CountRating.setText("0 đánh giá:");
            }
        }
    }

    private void getInfoQuanAo() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                quanAo = (QuanAo) intent.getExtras().getBinder("laptop");
                openFrom = intent.getStringExtra("openFrom");
                Log.d(TAG, "getInfoLaptop: laptop: " + quanAo.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void useToolbar(String openFrom) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Navi));
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
//        ImageButton home = findViewById(R.id.imageButton_Home);
        ImageButton noti = findViewById(R.id.imageButton_Notifi);
        ImageButton gio = findViewById(R.id.imageButton_GioHang);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                if (pref != null) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("what", "none");
                    editor.apply();
                }
                finish();
            }
        });

        if (openFrom != null) {
            if (openFrom.equals("other")) {
                gio.setVisibility(View.GONE);
//                home.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
//                        if (pref != null) {
//                            SharedPreferences.Editor editor = pref.edit();
//                            editor.putString("what", "home");
//                            editor.apply();
//                        }
//                        finish();
//                    }
//                });
                noti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                        if (pref != null) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("what", "noti");
                            editor.apply();
                        }
                        finish();
                    }
                });
            } else {
                gio.setVisibility(View.VISIBLE);
//                home.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
//                        if (pref != null) {
//                            SharedPreferences.Editor editor = pref.edit();
//                            editor.putString("what", "home");
//                            editor.apply();
//                        }
//                        finish();
//                    }
//                });
                noti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                        if (pref != null) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("what", "noti");
                            editor.apply();
                        }
                        finish();
                    }
                });
                gio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = getSharedPreferences("Info_Click", MODE_PRIVATE);
                        if (pref != null) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("what", "gio");
                            editor.apply();
                        }
                        finish();
                    }
                });
            }
        }
    }

}