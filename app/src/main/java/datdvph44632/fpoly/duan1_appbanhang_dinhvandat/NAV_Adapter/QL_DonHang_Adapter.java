package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Activity.ChiTiet_DonHang_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.DonHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.KhachHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.NhanVienDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.ThongBaoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.VoucherDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DonHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.KhachHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.NhanVien;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.ThongBao;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.Voucher;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.ChangeType;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.GetData;

public class QL_DonHang_Adapter extends RecyclerView.Adapter<QL_DonHang_Adapter.AuthorViewHolder> {

    Context context;
    TextInputLayout tilDate, tilNV, tilKH, tilDC, tilLap, tilSL, tilVou, tilTT;

    Spinner hangLapSpinner;
    AppCompatButton addDHButton;
    int posDH;
    NhanVien nhanVien, getNV;
    KhachHang khachHang;
    QuanAo quanAo;
    Voucher voucher;
    ArrayList<QuanAo> listquanAo;
    ArrayList<KhachHang> listKH;
    ArrayList<DonHang> listDon;
    DonHangDAO donHangDAO;
    NhanVienDAO nhanVienDAO;
    KhachHangDAO khachHangDAO;
    VoucherDAO voucherDAO;
    QuanAoDAO quanAoDAO;
    ThongBaoDAO thongBaoDAO;
    GetData getData;
    TextView countDH;
    ChangeType changeType = new ChangeType();
    String TAG = "QL_DonHang_Adapter_____", LoaiQuanAo;

    public QL_DonHang_Adapter(ArrayList<QuanAo> listquanAo, ArrayList<DonHang> listDon, ArrayList<KhachHang> listKH, Context context, TextView countDH) {
        this.listquanAo = listquanAo;
        this.listDon = listDon;
        this.listKH = listKH;
        this.context = context;
        this.countDH = countDH;
        donHangDAO = new DonHangDAO(context);
        nhanVienDAO = new NhanVienDAO(context);
        quanAoDAO = new QuanAoDAO(context);
        voucherDAO = new VoucherDAO(context);
        khachHangDAO = new KhachHangDAO(context);
        thongBaoDAO = new ThongBaoDAO(context);
        getData = new GetData(context);
        getUserNV();
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_nva_don_hang, vGroup, false);
        return new AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        DonHang donHang = setRow(pos, author);

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

        author.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (donHang.getTrangThai().equals("Chờ xác nhận")) {
                        android.app.AlertDialog.Builder aBuild = new android.app.AlertDialog.Builder(context);
                        aBuild.setTitle("Xác nhận đơn hàng " + donHang.getMaDH());
                        aBuild.setMessage("Sau khi thay đổi sẽ không thể hoàn tác!");
                        aBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int stt) {
                                xacNhanDonHang(donHang);
                            }
                        });
                        aBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int stt) {
                            }
                        });
                        android.app.AlertDialog alertDialog = aBuild.create();
                        alertDialog.show();
                } else {
                    setPosDH(pos);
                    author.itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) author);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listDon.size() == 0) {
            countDH.setText(String.valueOf(0));
        }
        return listDon.size();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView tenKH, tenquanao, phone, money, date;
//        ImageView imgquanao, imgTrangThai;
//        TextView name, gia, soLuong, trangThai, tienDo, hint;



        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            tenKH = itemView.findViewById(R.id.textView_TenKH);
            tenquanao = itemView.findViewById(R.id.textView_TenQuanAo);
            phone = itemView.findViewById(R.id.textView_SDT);
            money = itemView.findViewById(R.id.textView_Soluong);
            date = itemView.findViewById(R.id.textView_Date);
//            imgquanao = itemView.findViewById(R.id.imageView_QuanAo);
//            imgTrangThai = itemView.findViewById(R.id.imageView_TrangThai);
//            name = itemView.findViewById(R.id.textView_TenQuanAo);
//            gia = itemView.findViewById(R.id.textView_GiaTien);
//            soLuong = itemView.findViewById(R.id.textView_Soluong);
//            trangThai = itemView.findViewById(R.id.textView_TrangThai);
//            tienDo = itemView.findViewById(R.id.textView_TienDo);
//            hint = itemView.findViewById(R.id.textView_Hint);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem edit = menu.add(Menu.NONE, R.id.item_CapNhat, Menu.NONE, "Cập nhật");
            MenuItem delete = menu.add(Menu.NONE, R.id.item_Xoa, Menu.NONE, "Xóa");
            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

            @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DonHang donHang = listDon.get(getPosDH());

                if (item.getItemId() == R.id.item_Xoa){
                    donHangDAO.deleteDonHang(donHang);
                    listDon.remove(getPosDH());
                    notifyDataSetChanged();
                } else if (item.getItemId() == R.id.item_CapNhat) {
                    donHangDAO = new DonHangDAO(context);
                    nhanVienDAO = new NhanVienDAO(context);
                    quanAoDAO = new QuanAoDAO(context);
                    voucherDAO = new VoucherDAO(context);
                    khachHangDAO = new KhachHangDAO(context);
                    openDialogUpdate(donHang);
                }

//                switch (item.getItemId()) {
//                    case R.id.item_Xoa:
//
//                        break;
//                    case R.id.item_CapNhat:
//
//                        break;
//                }
                return true;
            }
        };

    }

    public DonHang setRow(int pos, @NonNull AuthorViewHolder author) {
        ChangeType changeType = new ChangeType();
        Log.d(TAG, "setRow: " + pos);
        DonHang donHang = listDon.get(pos);
        QuanAo quanAo = new QuanAo("No Data", "No Data", "No Data", "0", 0, 0, new byte[]{});
        KhachHang khachHang = new KhachHang("No Data", "No Data", "No Data", "No Data", "No Data", "No Data", "No Data",
                "No Data", new byte[]{});
        Log.d(TAG, "setRow: DonHang: " + donHang.toString());

        for (int i = 0; i < listquanAo.size(); i++) {
            QuanAo getLap = listquanAo.get(i);
            if (donHang.getMaQuanAo().equals(getLap.getMaQuanAo())) {
                quanAo = getLap;
            }
        }

        for (int i = 0; i < listKH.size(); i++) {
            KhachHang getKH = listKH.get(i);
            if (donHang.getMaKH().equals(getKH.getMaKH())) {
                khachHang = getKH;
            }
        }
//        setDonHangDanhGia(author, donHang);
        countDH.setText(String.valueOf(listDon.size()));
        author.tenKH.setText(changeType.fullNameKhachHang(khachHang));
        author.tenquanao.setText(quanAo.getTenQuanAo());
        author.phone.setText(khachHang.getPhone());
        author.money.setText(donHang.getThanhTien());
        author.date.setText(donHang.getNgayMua());
        return donHang;
    }


    private void openDialogUpdate(DonHang donHang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inft = ((Activity) context).getLayoutInflater();
        View view = inft.inflate(R.layout.dialog_donhang_manager, null);

        tilDate = view.findViewById(R.id.textInput_Date);
        tilNV = view.findViewById(R.id.textInput_MaNV);
        tilKH = view.findViewById(R.id.textInput_MaKH);
        tilDC = view.findViewById(R.id.textInput_DiaChi);
        tilLap = view.findViewById(R.id.textInput_MaQuanAo);
        tilSL = view.findViewById(R.id.textInput_SoLuong);
        tilVou = view.findViewById(R.id.textInput_MaVoucher);
        tilTT = view.findViewById(R.id.textInput_Total);
        hangLapSpinner = view.findViewById(R.id.spinner_HangQuanao);
        addDHButton = view.findViewById(R.id.button_AddDH);
        ChangeType changeType = new ChangeType();


        Date currentTime = Calendar.getInstance().getTime();

        setDialogData(currentTime, donHang);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        setUpListDialog();
        addDHButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                checkInputDonHang();

                String dateSQL = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
                String maNV = donHang.getMaNV();
                if (nhanVien != null) {
                    maNV = nhanVien.getMaNV();
                }
                String maKH = donHang.getMaKH();
                if (khachHang != null) {
                    maKH = khachHang.getMaKH();
                }
                String maLaptop = donHang.getMaQuanAo();
                if (quanAo != null) {
                    maLaptop = quanAo.getMaQuanAo();
                }
                String maVou = donHang.getMaVoucher();
                if (voucher != null) {
                    maVou = voucher.getMaVoucher();
                }
                String diachi = changeType.deleteSpaceText(tilDC.getEditText().getText().toString());
                String soluong = changeType.deleteSpaceText(tilSL.getEditText().getText().toString());
                String thanhtien = changeType.deleteSpaceText(tilTT.getEditText().getText().toString());
                donHang.setNgayMua(dateSQL);
                donHang.setMaNV(maNV);
                donHang.setMaKH(maKH);
                donHang.setDiaChi(diachi);
                donHang.setMaQuanAo(maLaptop);
                donHang.setSoLuong(Integer.parseInt(soluong));
                donHang.setMaVoucher(maVou);
                donHang.setThanhTien(changeType.stringToStringMoney(changeType.stringMoneyToInt(thanhtien) + ""));

                donHangDAO.updateDonHang(donHang);
                listDon.clear();
                listDon = donHangDAO.selectDonHang(null, "trangThai=?", new String[]{"Hoàn thành"}, "ngayMua");
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });
    }

    private void setDialogData(Date currentTime, DonHang donHang) {
        String dateForm = new SimpleDateFormat("dd/MM/yyyy").format(currentTime);
        tilDate.getEditText().setText(dateForm);
        tilDC.getEditText().setText(donHang.getDiaChi());
        tilSL.getEditText().setText(String.valueOf(donHang.getSoLuong()));
        tilTT.getEditText().setText(donHang.getThanhTien());
        addDHButton.setText("Cập nhật");

        ArrayList<NhanVien> listNV = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{donHang.getMaNV()}, null);
        if (listNV.size() > 0) {
            NhanVien nv = listNV.get(0);
            tilNV.getEditText().setText(changeType.fullNameNhanVien(nv));
        }

        ArrayList<KhachHang> listKH = khachHangDAO.selectKhachHang(null, "maKH=?", new String[]{donHang.getMaKH()}, null);
        if (listKH.size() > 0) {
            KhachHang kh = listKH.get(0);
            tilKH.getEditText().setText(changeType.fullNameKhachHang(kh));
        }

        ArrayList<QuanAo> listLap = quanAoDAO.selectQuanAo(null, "maQuanAo=?", new String[]{donHang.getMaQuanAo()}, null);
        if (listLap.size() > 0) {
            QuanAo lap = listLap.get(0);
            tilLap.getEditText().setText(lap.getTenQuanAo());
            if (lap.getMaHangQuanAo().equals("LAoKaki")) {
                hangLapSpinner.setSelection(0);
                LoaiQuanAo = "AoKaki";
            }
            if (lap.getMaHangQuanAo().equals("LAoThun")) {
                hangLapSpinner.setSelection(1);
                LoaiQuanAo = "AoThun";
            }
            if (lap.getMaHangQuanAo().equals("LAoHoodie")) {
                hangLapSpinner.setSelection(2);
                LoaiQuanAo = "AoHoodie";
            }
            if (lap.getMaHangQuanAo().equals("LAoGio")) {
                hangLapSpinner.setSelection(3);
                LoaiQuanAo = "AoGio";
            }
            if (lap.getMaHangQuanAo().equals("LQuanBo")) {
                hangLapSpinner.setSelection(4);
                LoaiQuanAo = "QuanBo";
            }
            if (lap.getMaHangQuanAo().equals("LQuanVai")) {
                hangLapSpinner.setSelection(5);
                LoaiQuanAo = "QuanVai";
            }
        }

        ArrayList<Voucher> listVou = voucherDAO.selectVoucher(null, "maVoucher=?", new String[]{donHang.getMaVoucher()}, null);
        if (listVou.size() > 0) {
            Voucher vou = listVou.get(0);
            tilVou.getEditText().setText(vou.getTenVoucher() + " : Sale: " + vou.getGiamGia());
        }

    }

    private void setUpListDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
        TextView title = view.findViewById(R.id.textView_Title_Dialog);
        ListView listView = view.findViewById(R.id.listView_Item_DH);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(view);

        tilNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Nhân viên");
                dialog.show();
                ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, list, null, null);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        nhanVien = list.get(position);
                        tilNV.getEditText().setText(changeType.fullNameNhanVien(nhanVien));
                        dialog.dismiss();
                    }
                });
            }
        });

        tilNV.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Nhân viên");
                dialog.show();
                ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, list, null, null);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        nhanVien = list.get(position);
                        tilNV.getEditText().setText(changeType.fullNameNhanVien(nhanVien));
                        dialog.dismiss();
                    }
                });
            }
        });

        tilKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Khách hàng");
                dialog.show();
                ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, null, list, null);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        khachHang = list.get(position);
                        tilKH.getEditText().setText(changeType.fullNameKhachHang(khachHang));
                        dialog.dismiss();
                    }
                });
            }
        });

        tilKH.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Khách hàng");
                dialog.show();
                ArrayList<KhachHang> list = khachHangDAO.selectKhachHang(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, null, list, null);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        khachHang = list.get(position);
                        tilKH.getEditText().setText(changeType.fullNameKhachHang(khachHang));
                        dialog.dismiss();
                    }
                });
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.loai_quan_ao_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hangLapSpinner.setAdapter(adapter);
        hangLapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoaiQuanAo = hangLapSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tilLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoaiQuanAo != null) {
                    title.setText("Danh sách quan ao " + LoaiQuanAo);
                    dialog.show();
                    ArrayList<QuanAo> list = quanAoDAO.selectQuanAo(null, "maHangLap=?", new String[]{"L" + LoaiQuanAo}, null);
                    DH_Manager_Adapter adapter2 = new DH_Manager_Adapter(list, null, null, null);
                    listView.setAdapter(adapter2);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            quanAo = list.get(position);
                            tilLap.getEditText().setText(quanAo.getTenQuanAo());
                            hangLapSpinner.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        tilLap.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoaiQuanAo != null) {
                    title.setText("Danh sách quan ao " + LoaiQuanAo);
                    dialog.show();
                    ArrayList<QuanAo> list = quanAoDAO.selectQuanAo(null, "maHangLap=?", new String[]{"L" + LoaiQuanAo}, null);
                    DH_Manager_Adapter adapter2 = new DH_Manager_Adapter(list, null, null, null);
                    listView.setAdapter(adapter2);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            quanAo = list.get(position);
                            tilLap.getEditText().setText(quanAo.getTenQuanAo());
                            hangLapSpinner.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        tilVou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Voucher");
                dialog.show();
                ArrayList<Voucher> list = voucherDAO.selectVoucher(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, null, null, list);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Voucher voucher;
                        voucher = list.get(position);
                        tilVou.getEditText().setText(voucher.getTenVoucher() + " : Sale " + voucher.getGiamGia());
                        dialog.dismiss();
                    }
                });
            }
        });

        tilVou.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Danh sách Voucher");
                dialog.show();
                ArrayList<Voucher> list = voucherDAO.selectVoucher(null, null, null, null);
                DH_Manager_Adapter adapter = new DH_Manager_Adapter(null, null, null, list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        voucher = list.get(position);
                        tilVou.getEditText().setText(voucher.getTenVoucher() + " : Sale " + voucher.getGiamGia());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private DonHang checkInputDonHang() {
        Date currentTime = Calendar.getInstance().getTime();
        String dateSQL = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
        String dateForm = new SimpleDateFormat("dd/MM/yyyy").format(currentTime);

        tilDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilDate.getEditText().setText(dateForm);
            }
        });

        tilDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilDate.getEditText().setText(dateForm);
            }
        });

        String dc = tilDC.getEditText().getText().toString();
        String tt = tilTT.getEditText().getText().toString();
        String quaao = "", kh = "", nv = "", vou = "";
        int sl;
        if (quanAo != null) {
            quaao = quanAo.getTenQuanAo();
        }
        if (khachHang != null) {
            kh = changeType.fullNameKhachHang(khachHang);
        }
        if (nhanVien != null) {
            nv = nhanVien.getHoNV() + " " + nhanVien.getTenNV();
        }
        if (voucher != null) {
            vou = voucher.getTenVoucher();
        }
        if (nv.equals("")) {
            tilNV.setErrorEnabled(true);
            tilNV.setError("Nhân viên không được bỏ trống!");
            return null;
        } else {
            tilNV.setErrorEnabled(false);
            tilNV.setError("");
        }
        if (kh.equals("")) {
            tilKH.setErrorEnabled(true);
            tilKH.setError("Khách hàng không được bỏ trống!");
            return null;
        } else {
            tilKH.setErrorEnabled(false);
            tilKH.setError("");
        }

        if (dc.equals("")) {
            tilDC.setErrorEnabled(true);
            tilDC.setError("Địa chỉ không được bỏ trống!");
            return null;
        } else {
            tilDC.setErrorEnabled(false);
            tilDC.setError("");
        }

        if (quaao.equals("")) {
            tilLap.setErrorEnabled(true);
            tilLap.setError("quan ao không được bỏ trống!");
            return null;
        } else {
            tilLap.setErrorEnabled(false);
            tilLap.setError("");
        }

        try {
            sl = Integer.parseInt(tilSL.getEditText().getText().toString());
            tilSL.setErrorEnabled(false);
            tilSL.setError("");
        } catch (Exception e) {
            e.printStackTrace();
            tilSL.setErrorEnabled(true);
            tilSL.setError("Số lượng phải là số!");
            return null;
        }

        if (sl == 0) {
            tilSL.setErrorEnabled(true);
            tilSL.setError("Số lượng quan ao phải lớn hơn 0");
            return null;
        } else {
            tilSL.setErrorEnabled(false);
            tilSL.setError("");
        }

        if (quanAo != null) {
            if (sl > quanAo.getSoLuong()) {
                tilSL.setErrorEnabled(true);
                tilSL.setError("Số lượng quan ao còn lại: " + quanAo.getSoLuong());
                return null;
            } else {
                tilSL.setErrorEnabled(false);
                tilSL.setError("");
            }
        }

        if (vou.equals("")) {
            tilVou.setErrorEnabled(true);
            tilVou.setError("Voucher không được bỏ trống!");
            return null;
        } else {
            tilVou.setErrorEnabled(false);
            tilVou.setError("");
        }

        if (tt.equals("")) {
            tilTT.setErrorEnabled(true);
            tilTT.setError("Thành tiền không được bỏ trống!");
            return null;
        } else {
            tilTT.setErrorEnabled(false);
            tilTT.setError("");
        }

        return new DonHang("", nhanVien.getMaNV(), khachHang.getMaKH(), quanAo.getMaQuanAo(),
                voucher.getMaVoucher(), "No Data", dc, dateSQL, "Thanh toán tại cửa hàng",
                "Hoàn thành", "false", changeType.stringToStringMoney(tt), sl);
    }

    private void setThongBaoXoaDH(DonHang donHang) {
        ArrayList<QuanAo> listLap = quanAoDAO.selectQuanAo(null, "maQuanAo=?", new String[]{donHang.getMaQuanAo()}, null);
        if (listLap.size() > 0) {
            quanAo = listLap.get(0);
        }

        Toast.makeText(context, "Xóa đơn hàng thành công!", Toast.LENGTH_SHORT).show();
        ThongBao thongBaoAll = new ThongBao("", "Online", "Xác nhận đơn hàng " + donHang.getMaDH(),
                " Admin đã xóa đơn hàng " + donHang.getMaDH() + ". Đơn hàng sẽ không thể xác nhận được nữa", getData.getNowDateSQL());
        thongBaoDAO.insertThongBao(thongBaoAll, "nv");

        ThongBao thongBaoAd = new ThongBao("", "No Data", "Xác nhận đơn hàng " + donHang.getMaDH(),
                " Bạn đã xóa đơn hàng " + donHang.getMaDH() + ". Thông báo sẽ được gửi đến cho khách hàng", getData.getNowDateSQL());
        thongBaoDAO.insertThongBao(thongBaoAd, "ad");

        if (quanAo != null) {
            ThongBao thongBaoKH = new ThongBao("TB", donHang.getMaKH(), "Đơn hàng đã được xác nhận",
                    " Đơn hàng " + quanAo.getTenQuanAo() + " đã bị Admin xóa\n Chúng tôi rất tiếc về trường hợp này." +
                            " Bạn hãy đặt lại Laptop hoặc đổi sang quần áo khác nhé", getData.getNowDateSQL());
            thongBaoDAO.insertThongBao(thongBaoKH, "kh");
        } else {
            ThongBao thongBaoKH = new ThongBao("TB", donHang.getMaKH(), "Đơn hàng đã được xác nhận",
                    " Đơn hàng No data" + " đã bị Admin xóa\n Chúng tôi rất tiếc về trường hợp này." +
                            " Bạn hãy đặt lại quần áo hoặc đổi sang quần áo khác nhé", getData.getNowDateSQL());
            thongBaoDAO.insertThongBao(thongBaoKH, "kh");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void xacNhanDonHang(DonHang donHang) {
        if (donHang.getMaNV().equals("No Data")) {
                ArrayList<QuanAo> listLap = quanAoDAO.selectQuanAo(null, "maQuanAo=?", new String[]{donHang.getMaQuanAo()}, null);
                if (listLap.size() > 0) {
                    quanAo = listLap.get(0);
                }
                if(getNV == null){
                    donHang.setMaNV("No Data");
                }else{
                    donHang.setMaNV(getNV.getMaNV());
                }
                donHang.setTrangThai("Đang giao hàng");

                int check = donHangDAO.updateDonHang(donHang);
                if (check == 1) {
                    quanAo.setSoLuong(quanAo.getSoLuong() - donHang.getSoLuong());
                    quanAo.setDaBan(quanAo.getDaBan() + donHang.getSoLuong());
                    quanAoDAO.updateQuanAo(quanAo);

                    Toast.makeText(context, "Xác nhận đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                    ThongBao thongBaoAll = new ThongBao("", "Online", "Xác nhận đơn hàng " + donHang.getMaDH(),
                            " Nhân viên " + changeType.fullNameNhanVien(getNV) + " đã xác nhận đơn hàng " + donHang.getMaDH() + "\n Đơn hàng sẽ không thể xác nhận được nữa", getData.getNowDateSQL());
                    thongBaoDAO.insertThongBao(thongBaoAll, "nv");

                    ThongBao thongBaoAd = new ThongBao("", "No Data", "Xác nhận đơn hàng " + donHang.getMaDH(),
                            " Nhân viên " + changeType.fullNameNhanVien(getNV) + " đã xác nhận đơn hàng " + donHang.getMaDH() + "\n Thông tin chi tiết đơn hàng ở trong Quản lý Đơn hàng", getData.getNowDateSQL());
                    thongBaoDAO.insertThongBao(thongBaoAd, "ad");
                    if(getNV != null){
                        ThongBao thongBaoNV = new ThongBao("", getNV.getMaNV(), "Xác nhận đơn hàng " + donHang.getMaDH(),
                                " Bạn đã xác nhận đơn hàng " + donHang.getMaDH() + "\n Đơn hàng đã được lưu trong Đơn hàng đã bán", getData.getNowDateSQL());
                        thongBaoDAO.insertThongBao(thongBaoNV, "nv");
                    }else{
                        ThongBao thongBaoNV = new ThongBao("", "Admin", "Xác nhận đơn hàng " + donHang.getMaDH(),
                                " Bạn đã xác nhận đơn hàng " + donHang.getMaDH() + "\n Đơn hàng đã được lưu trong Đơn hàng đã bán", getData.getNowDateSQL());
                        thongBaoDAO.insertThongBao(thongBaoNV, "nv");
                    }
                    listDon.remove(donHang);
                    notifyDataSetChanged();

                    if (quanAo != null) {
                        ThongBao thongBaoKH = new ThongBao("TB", donHang.getMaKH(), "Đơn hàng đã được xác nhận", " Đơn hàng "
                                + quanAo.getTenQuanAo() + " đã được xác nhận\n Hãy vào Đơn hàng đã mua để xem đơn hàng nhé", getData.getNowDateSQL());
                        thongBaoDAO.insertThongBao(thongBaoKH, "kh");
                    } else {
                        ThongBao thongBaoKH = new ThongBao("TB", donHang.getMaKH(), "Đơn hàng đã được xác nhận",
                                " Đơn hàng No data" + " đã được xác nhận\n Hãy vào Đơn hàng đã mua để xem đơn hàng nhé", getData.getNowDateSQL());
                        thongBaoDAO.insertThongBao(thongBaoKH, "kh");
                    }
                } else {
                    Toast.makeText(context, "Xác nhận đơn hàng thất bại!", Toast.LENGTH_SHORT).show();
                }

        } else {
            Toast.makeText(context, "Đơn hàng đã được xác nhận!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserNV() {
        SharedPreferences pref = context.getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pref == null) {
            getNV = null;
        } else {
            String user = pref.getString("who", "");
            ArrayList<NhanVien> list = nhanVienDAO.selectNhanVien(null, "maNV=?", new String[]{user}, null);
            if (list.size() > 0) {
                getNV = list.get(0);
            }
        }
    }

    public int getPosDH() {
        return posDH;
    }

    public void setPosDH(int posDH) {
        this.posDH = posDH;
    }
}