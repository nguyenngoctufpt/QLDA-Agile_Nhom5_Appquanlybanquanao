package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityKH.KH_DanhGia_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.DonHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.GiaoDichDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoRateDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.ThongBaoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.ViTienDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DonHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.GiaoDich;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAoRate;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.ThongBao;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.ViTien;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.GetData;

public class KH_DonHang_Adapter extends RecyclerView.Adapter<KH_DonHang_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<QuanAo> listQuanAo;
    ArrayList<DonHang> listDon;
    QuanAoRateDAO quanAoRateDAO;
    QuanAo getQuanAo;
    ViTien getVi;
    DonHangDAO donHangDAO;
    ChangeType changeType = new ChangeType();
    String TAG = "KH_DonHang_Adapter_____";

    public KH_DonHang_Adapter(ArrayList<QuanAo> listQuanAo, ArrayList<DonHang> listDon, Context context) {
        this.listQuanAo = listQuanAo;
        this.listDon = listDon;
        this.context = context;
        quanAoRateDAO = new QuanAoRateDAO(context);
        donHangDAO = new DonHangDAO(context);
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_kh_donhang_quanao, vGroup, false);
        return new AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        DonHang donHang = setRow(pos, author);

        if (donHang != null) {
            onclickDanhGia(author, donHang);
        } else {
            Toast.makeText(context, "Load thông tin sản phẩm lỗi!\nXin vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
        }

        if(donHang.getTrangThai().equals("Chờ xác nhận")) {
            author.huy.setVisibility(View.VISIBLE);
            author.huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                    builder.setTitle("Hủy đơn hàng");
                    builder.setMessage("Bạn có chắc chắn muốn hủy đơn hàng này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            donHangDAO.deleteDonHang(donHang);
                            listDon.remove(pos);
                            notifyItemRemoved(pos);
                            notifyItemRangeChanged(pos, listDon.size());
                            Toast.makeText(context, "Hủy đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Không", null);
                    builder.show();
                }
            });
        }else{
            author.huy.setVisibility(View.GONE);
        }

        author.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (donHang != null) {
                    Intent intent = new Intent(context, ChiTiet_DonHang_Activity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("donhang", donHang);
                    Log.d(TAG, "onBindViewHolder: DonHang: " + donHang.toString());
                    intent.putExtras(bundle);
                    intent.putExtra("typeUser", "KH");
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
        ImageView imgquanao, imgTrangThai;
        TextView name, gia, soLuong, trangThai, tienDo, hint;
        RatingBar ratingBar;
        Button danhGia,huy;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgquanao = itemView.findViewById(R.id.imageView_QuanAo);
            imgTrangThai = itemView.findViewById(R.id.imageView_TrangThai);
            name = itemView.findViewById(R.id.textView_TenQuanAo);
            gia = itemView.findViewById(R.id.textView_GiaTien);
            soLuong = itemView.findViewById(R.id.textView_Soluong);
            danhGia = itemView.findViewById(R.id.button_Open_DanhGia);
            trangThai = itemView.findViewById(R.id.textView_TrangThai);
            tienDo = itemView.findViewById(R.id.textView_TienDo);
            hint = itemView.findViewById(R.id.textView_Hint);
            ratingBar = itemView.findViewById(R.id.ratingBar_DanhGia);
            huy = itemView.findViewById(R.id.button_Huy);
        }
    }

    public DonHang setRow(int pos, @NonNull AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        DonHang donHang = listDon.get(pos);
        QuanAo quanAo = new QuanAo("No Data", "No Data", "No Data", "0", 0, 0, new byte[]{});
        Log.d(TAG, "setRow: DonHang: " + donHang.toString());

        for (int i = 0; i < listQuanAo.size(); i++) {
            QuanAo getLap = listQuanAo.get(i);
            if (donHang.getMaQuanAo().equals(getLap.getMaQuanAo())) {
                quanAo = getLap;
            }
        }

        ChangeType changeType = new ChangeType();
        Bitmap anhLap = changeType.byteToBitmap(quanAo.getAnhquanAo());

        setDonHangDanhGia(author, donHang);
        author.imgquanao.setImageBitmap(anhLap);
        author.name.setText(quanAo.getTenQuanAo());
        author.gia.setText(donHang.getThanhTien());
        author.soLuong.setText(String.valueOf(donHang.getSoLuong()));
        return donHang;
    }

    private void setDonHangDanhGia(@NonNull AuthorViewHolder author, DonHang donHang) {
        if (donHang.getTrangThai().equals("Hoàn thành")) {
            author.tienDo.setText("Hoàn thành");
            author.hint.setVisibility(View.VISIBLE);
            author.imgTrangThai.setImageResource(R.drawable.check_icon);
            author.imgTrangThai.setColorFilter(Color.parseColor("#4CAF50"));
            author.trangThai.setText("Đơn hàng giao thành công");
            author.trangThai.setTextColor(Color.parseColor("#C93852B0"));
        } else if (donHang.getTrangThai().equals("Chưa thanh toán")) {
            author.tienDo.setText("Đang chờ");
            author.hint.setVisibility(View.GONE);
            author.imgTrangThai.setImageResource(R.drawable.waiting_confirm_icon);
            author.imgTrangThai.setColorFilter(Color.parseColor("#FF9800"));
            author.trangThai.setText("Đơn hàng đang chờ thanh toán");
            author.trangThai.setTextColor(Color.parseColor("#FF9800"));
        } else if (donHang.getTrangThai().equals("Chờ xác nhận")) {
            author.tienDo.setText("Đang chờ");
            author.hint.setVisibility(View.GONE);
            author.imgTrangThai.setImageResource(R.drawable.send_icon);
            author.imgTrangThai.setColorFilter(Color.parseColor("#FF9800"));
            author.trangThai.setText("Đơn hàng đang chờ xác nhận");
            author.trangThai.setTextColor(Color.parseColor("#FF9800"));
        } else if (donHang.getTrangThai().equals("Đang giao hàng")) {
            author.tienDo.setText("Đang giao");
            author.hint.setVisibility(View.GONE);
            author.imgTrangThai.setImageResource(R.drawable.icon_delivery_dining);
            author.imgTrangThai.setColorFilter(Color.parseColor("#FF9800"));
            author.trangThai.setText("Đơn hàng đang được giao");
            author.trangThai.setTextColor(Color.parseColor("#FF9800"));
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
                Log.d(TAG, "setLayout: QuanAo rate: " + quanAoRate.toString());
            }
            if (quanAoRate != null) {
                author.hint.setVisibility(View.GONE);
                author.ratingBar.setRating(changeType.getRatingFloat(quanAoRate.getRating()));
                author.ratingBar.setIsIndicator(true);
            }
        }
    }

    private void onclickDanhGia(@NonNull AuthorViewHolder author, DonHang donHang) {
        QuanAoDAO quanAoDAO = new QuanAoDAO(context);
        ViTienDAO viTienDAO = new ViTienDAO(context);
        ThongBaoDAO thongBaoDAO = new ThongBaoDAO(context);
        GetData getData = new GetData(context);
        ArrayList<QuanAo> listL = quanAoDAO.selectQuanAo(null, "maQuanAo=?", new String[]{donHang.getMaQuanAo()}, null);
        ArrayList<ViTien> listV = viTienDAO.selectViTien(null, "maKH=?", new String[]{donHang.getMaKH()}, null);
        if (listL.size() > 0) {
            getQuanAo = listL.get(0);
        }
        if (listV.size() > 0) {
            getVi = listV.get(0);
        }

        if (donHang.getTrangThai().equals("Hoàn thành")) {
            if (donHang.getIsDanhGia().equals("true")) {
                author.danhGia.setText("Xem đánh giá");
                author.danhGia.setEnabled(true);
            } else {
                author.danhGia.setText("Đánh giá ngay");
                author.danhGia.setEnabled(true);
            }
            author.danhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, KH_DanhGia_Activity.class);
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("donhang", donHang);
                    Log.d(TAG, "onBindViewHolder: DonHang: " + donHang.toString());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        // thanh toasn qua vi
        if (donHang.getTrangThai().equals("Chưa thanh toán")) {
            author.danhGia.setText("Thanh toán ngay");
            author.danhGia.setEnabled(true);
            author.danhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder aBuild = new android.app.AlertDialog.Builder(context);
                    aBuild.setTitle("Thanh toán qua ví FPT Pay");
                    aBuild.setMessage("Xác nhận thanh toán " + donHang.getThanhTien() + " cho đơn hàng " + getQuanAo.getTenQuanAo());
                    aBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onClick(DialogInterface dialog, int stt) {
                            if (getVi != null) {
                                int thanhTien;
                                int soTien;
                                if (donHang.getThanhTien().length() > 12) {
                                    thanhTien = changeType.stringMoneyToInt(donHang.getThanhTien());
                                } else {
                                    thanhTien = changeType.stringMoneyToInt(donHang.getThanhTien()) / 1000;
                                }
                                if (getVi.getSoTien().length() > 12) {
                                    soTien = changeType.stringMoneyToInt(getVi.getSoTien());
                                } else {
                                    soTien = changeType.stringMoneyToInt(getVi.getSoTien()) / 1000;
                                }
                                if (soTien >= thanhTien) {
                                    int soDu = soTien - thanhTien;
                                    getVi.setSoTien(changeType.stringToStringMoney(soDu + "000"));
                                    int check = viTienDAO.updateViTien(getVi);
                                    if (check == 1) {
                                        donHang.setTrangThai("Chờ xác nhận");
                                        donHangDAO.updateDonHang(donHang);
                                        GiaoDichDAO giaoDichDAO = new GiaoDichDAO(context);
                                        giaoDichDAO.insertGiaoDich(new GiaoDich("", getVi.getMaVi(), "Thanh toán đơn hàng",
                                                "Thanh toán đơn hàng " + getQuanAo.getTenQuanAo() + " bằng Ví điện tử FPT Pay",
                                                donHang.getThanhTien(), getData.getNowDateSQL()));
                                    }
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "Số dư trong ví của bạn không đủ\n để thực hiên giao dịch! ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    aBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int stt) {

                        }
                    });
                    android.app.AlertDialog alertDialog = aBuild.create();
                    alertDialog.show();
                }
            });
        }
        if (donHang.getTrangThai().equals("Chờ xác nhận")) {
            author.danhGia.setText("Đánh giá ngay");
            author.danhGia.setEnabled(true);
        }
        if (donHang.getTrangThai().equals("Đang giao hàng")) {
            author.danhGia.setText("Đánh giá ngay");
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
    }
}