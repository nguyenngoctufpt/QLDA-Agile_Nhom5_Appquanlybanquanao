package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Activity.ChiTiet_DonHang_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityNV_Admin.NV_DanhGia_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.DonHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoRateDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.ThongBaoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DonHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAoRate;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.ThongBao;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.GetData;

public class NV_DonHang_Adapter extends RecyclerView.Adapter<NV_DonHang_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<QuanAo> listquanAo;
    ArrayList<DonHang> listDon;
    QuanAoRateDAO quanAoRateDAO;
    ChangeType changeType = new ChangeType();
    String TAG = "NV_DonHang_Adapter_____";
    QuanAo getQuanAo;
    DonHangDAO donHangDAO;

    public NV_DonHang_Adapter(ArrayList<QuanAo> listquanAo, ArrayList<DonHang> listDon, Context context) {
        this.listquanAo = listquanAo;
        this.listDon = listDon;
        this.context = context;
        quanAoRateDAO = new QuanAoRateDAO(context);
        donHangDAO = new DonHangDAO(context);
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_nv_donhang_quanao, vGroup, false);
        return new AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        DonHang donHang = setRow(pos, author);

//

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (donHang != null) {
                    Intent intent = new Intent(context, ChiTiet_DonHang_Activity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("donhang", donHang);
                    Log.d(TAG, "onBindViewHolder: DonHang: " + donHang.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("typeUser", "NV");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDon.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLaptop, imgTrangThai;
        TextView name, gia, soLuong, trangThai, tienDo;
        RatingBar ratingBar;
        Button danhGia;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLaptop = itemView.findViewById(R.id.imageView_QuanAo);
            imgTrangThai = itemView.findViewById(R.id.imageView_TrangThai);
            name = itemView.findViewById(R.id.textView_TenQuanAo);
            gia = itemView.findViewById(R.id.textView_GiaTien);
            soLuong = itemView.findViewById(R.id.textView_Soluong);
            danhGia = itemView.findViewById(R.id.button_Open_DanhGia);
            trangThai = itemView.findViewById(R.id.textView_TrangThai);
            tienDo = itemView.findViewById(R.id.textView_TienDo);
            ratingBar = itemView.findViewById(R.id.ratingBar_DanhGia);
        }
    }

    public DonHang setRow(int pos, @NonNull AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        DonHang donHang = listDon.get(pos);
        QuanAo quanAo = new QuanAo("No Data", "No Data", "No Data", "0", 0, 0, new byte[]{});
        Log.d(TAG, "setRow: DonHang: " + donHang.toString());

        for (int i = 0; i < listquanAo.size(); i++) {
            QuanAo getQuanAo = listquanAo.get(i);
            if (donHang.getMaQuanAo().equals(getQuanAo.getMaQuanAo())) {
                quanAo = getQuanAo;
            }
        }

        ChangeType changeType = new ChangeType();
        Bitmap anhLap = changeType.byteToBitmap(quanAo.getAnhquanAo());

        author.imgLaptop.setImageBitmap(anhLap);
        author.name.setText(quanAo.getTenQuanAo());
        author.gia.setText(donHang.getThanhTien());
        author.soLuong.setText(String.valueOf(donHang.getSoLuong()));

        setDonHangDanhGia(author, donHang);
        return donHang;
    }

    private void setDonHangDanhGia(@NonNull AuthorViewHolder author, DonHang donHang) {
        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
        GetData getData = new GetData(context);
        if (donHang.getTrangThai().equals("Hoàn thành")) {
            author.tienDo.setText("Hoàn thành");
            author.imgTrangThai.setImageResource(R.drawable.check_icon);
            author.imgTrangThai.setColorFilter(Color.parseColor("#4CAF50"));
            author.trangThai.setText("Đơn hàng giao thành công");
            author.trangThai.setTextColor(Color.parseColor("#C93852B0"));
            author.danhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (donHang != null) {
                        Intent intent = new Intent(context, NV_DanhGia_Activity.class);
                        final Bundle bundle = new Bundle();
                        bundle.putBinder("donhang", donHang);
                        Log.d(TAG, "onBindViewHolder: DonHang: " + donHang.toString());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            author.tienDo.setText("Đang giao hàng");
            author.imgTrangThai.setImageResource(R.drawable.icon_delivery_dining);
            author.imgTrangThai.setColorFilter(Color.parseColor("#FF9800"));
            author.trangThai.setText("Đơn hàng đang được giao");
            author.trangThai.setTextColor(Color.parseColor("#FF9800"));
            author.danhGia.setText("Xác nhận đơn hàng");
            author.danhGia.setEnabled(true);
            author.danhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    donHang.setTrangThai("Hoàn thành");
                    int check = donHangDAO.updateDonHang(donHang);
                    if (check == 1) {
                        Toast.makeText(context, "Xác nhận nhận hàng thành công", Toast.LENGTH_SHORT).show();
                        if (getQuanAo != null) {
                            ThongBao thongBaoKH = new ThongBao("TB", donHang.getMaKH(), "Quản lý đơn hàng",
                                    " Đơn hàng " + getQuanAo.getTenQuanAo() + "đã được bạn xác nhận nhận hàng\n " +
                                            "Hãy đánh giá sớm để chúng tôi biết suy nghĩ của bạn vể sản phẩm của chúng tôi nhé", getData.getNowDateSQL());
                            thongBaoDAO.insertThongBao(thongBaoKH, "kh");
                        } else {
                            ThongBao thongBaoKH = new ThongBao("TB", donHang.getMaKH(), "Quản lý đơn hàng",
                                    " Đơn hàng Nota" + "đã được bạn xác nhận nhận hàng\n " +
                                            "Hãy đánh giá sớm để chúng tôi biết suy nghĩ của bạn vể sản phẩm của chúng tôi nhé", getData.getNowDateSQL());
                            thongBaoDAO.insertThongBao(thongBaoKH, "kh");
                        }
                    } else {
                        Toast.makeText(context, "Xác nhận nhận hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
                }
            });
        }

        if (donHang.getIsDanhGia().equals("false")) {
            author.ratingBar.setRating(0f);
            author.ratingBar.setIsIndicator(true);
        } else {
            ArrayList<QuanAoRate> list = quanAoRateDAO.selectQuanAoRate(null, "maRate=?", new String[]{donHang.getMaRate()}, null);
            QuanAoRate quanAoRate = null;
            if (list.size() > 0) {
                Log.d(TAG, "setLayout: yo");
                quanAoRate = list.get(0);
                Log.d(TAG, "setLayout: quan ao rate: " + quanAoRate.toString());
            }
            if (quanAoRate != null) {
                author.ratingBar.setRating(changeType.getRatingFloat(quanAoRate.getRating()));
                author.ratingBar.setIsIndicator(true);
            }
        }
    }
}