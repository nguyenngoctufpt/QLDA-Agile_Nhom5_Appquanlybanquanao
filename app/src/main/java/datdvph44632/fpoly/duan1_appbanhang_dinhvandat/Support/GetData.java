package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.DonHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.KhachHangDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.NhanVienDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.UseVoucherDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.VoucherDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.DonHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.KhachHang;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.NhanVien;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.UseVoucher;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.Voucher;

public class GetData {
    Context context;
    NhanVienDAO nhanVienDAO;
    DonHangDAO donHangDAO;
    QuanAoDAO quanAoDAO;
    UseVoucherDAO useVoucherDAO;
    VoucherDAO voucherDAO;
    KhachHangDAO khachHangDAO;
    ChangeType changeType = new ChangeType();
    String TAG = "AddData_____";

    public GetData(Context context) {
        this.context = context;
        nhanVienDAO = new NhanVienDAO(context);
        donHangDAO = new DonHangDAO(context);
        quanAoDAO = new QuanAoDAO(context);
        useVoucherDAO = new UseVoucherDAO(context);
        khachHangDAO = new KhachHangDAO(context);
        voucherDAO = new VoucherDAO(context);
    }

    public void addDataUseVoucher() {
        ArrayList<KhachHang> listK = khachHangDAO.selectKhachHang(null, null, null, null);
        ArrayList<Voucher> listV = voucherDAO.selectVoucher(null, null, null, null);
        Voucher vou = listV.get(listV.size() - 1);
        if (listK.size() > 0) {
            for (KhachHang kh : listK) {
                useVoucherDAO.insertUseVoucher(new UseVoucher("", vou.getMaVoucher(), kh.getMaKH(), "false"));
            }
        }
    }

    public void addDataDoanhSo(ArrayList<NhanVien> listNV, String[] getDate) {
        if (listNV != null) {
            if (listNV.size() > 0) {
                for (NhanVien nhanVien : listNV) {
                    String[] full = {nhanVien.getMaNV(), getDate[0], getDate[1]};
                    ArrayList<DonHang> listDon = donHangDAO.selectDonHang(null, "maNV=? and ngayMua>=? and ngayMua<?", full, null);

                    int doanhSo = 0;
                    int cout = 0;
                    if (listDon != null) {
                        if (listDon.size() > 0) {
                            for (DonHang donHang : listDon) {
                                int giaTien;
                                if (donHang.getThanhTien().length() < 12) {
                                    giaTien = changeType.stringMoneyToInt(donHang.getThanhTien()) / 1000;
                                } else {
                                    giaTien = changeType.stringMoneyToInt(donHang.getThanhTien());
                                }
                                doanhSo += giaTien;
                            }
                            cout = listDon.size();
                        }
                    }

                    nhanVien.setDoanhSo(doanhSo);
                    nhanVien.setSoSP(cout);
                    nhanVienDAO.updateNhanVien(nhanVien);
                }
            }
        }
    }

    public int tinhTongKhoanThu(ArrayList<DonHang> listDon) {
        int khoanThu = 0;
        if (listDon != null) {
            if (listDon.size() > 0) {
                for (DonHang donHang : listDon) {
                    int giaTien = changeType.stringMoneyToInt(donHang.getThanhTien()) / 1000;
                    khoanThu += giaTien;
                }
            }
        }
        return khoanThu;
    }

    public int tinhTongKhoanChi(ArrayList<QuanAo> listLap) {
        int khoanChi = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (QuanAo quanAo : listLap) {
                    int giaTien = ((changeType.stringMoneyToInt(quanAo.getGiaTien()) / 1000) * quanAo.getSoLuong());
                    Log.d(TAG, "tinhTongKhoanChi: giaTien: " + giaTien);
                    khoanChi += giaTien;
                }
            }
        }
        Log.d(TAG, "tinhTongKhoanChi: khoanChi: " + khoanChi);
        return khoanChi;
    }

    public ArrayList getQuanAo(ArrayList<QuanAo> listLap, int pos) {
        ArrayList<QuanAo> list8 = new ArrayList<>();
        int size = listLap.size();
        if (size > 0) {
            if (size >= 8) {
                if ((pos + 1) * 8 <= size) {
                    for (int i = (pos * 8); i < ((pos + 1) * 8); i++) {
                        list8.add(listLap.get(i));
                    }
                } else {
                    for (int i = (pos * 8); i < size; i++) {
                        list8.add(listLap.get(i));
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    list8.add(listLap.get(i));
                }
            }
        }
        return list8;
    }

    public QuanAo getTop1DoanhThu(ArrayList<QuanAo> listLap) {
        QuanAo top1 = null;
        int topDoanhThu = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (QuanAo quanAo : listLap) {
                    int doanhThu = 0;
                    ArrayList<DonHang> listDon = donHangDAO.selectDonHang(null, "maQuanAo=?",
                            new String[]{quanAo.getMaQuanAo()}, null);
                    if (listDon != null) {
                        if (listDon.size() > 0) {
                            for (DonHang donHang : listDon) {
                                doanhThu += (changeType.stringMoneyToInt(donHang.getThanhTien()) / 1000);
                            }
                        }
                    }
                    if (doanhThu > topDoanhThu) {
                        topDoanhThu = doanhThu;
                        top1 = quanAo;
                    } else {
                        top1 = quanAo;
                        topDoanhThu = doanhThu;
                    }
                }
            }
        }
        return top1;
    }

    public QuanAo getTop1SoLuong(ArrayList<QuanAo> listLap, String sort) {
        QuanAo top1 = null;
        int topSoLuong = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (QuanAo quanAo : listLap) {
                    if (quanAo.getDaBan() > topSoLuong) {
                        top1 = quanAo;
                        topSoLuong = quanAo.getDaBan();
                    } else if (quanAo.getDaBan() == topSoLuong) {
                        topSoLuong = quanAo.getDaBan();
                        if (top1 != null) {
                            if (sort.equals("desc")) {
                                //Giảm dần
                                if (changeType.stringMoneyToInt(quanAo.getGiaTien()) > changeType.stringMoneyToInt(top1.getGiaTien())) {
                                    top1 = quanAo;
                                }
                            }
                            if (sort.equals("asc")) {
                                //Tăng dần
                                if (changeType.stringMoneyToInt(quanAo.getGiaTien()) < changeType.stringMoneyToInt(top1.getGiaTien())) {
                                    top1 = quanAo;
                                }
                            }
                        } else {
                            top1 = quanAo;
                        }
                    }
                }
            }
        }
        return top1;
    }

    public int tinhSoLuongNhapVe(ArrayList<QuanAo> listLap) {
        int soLuong = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (QuanAo quanAo : listLap) {
                    int nhap = quanAo.getSoLuong();
                    soLuong += nhap;
                }
            }
        }
        return soLuong;
    }

    public int tinhSoLuongDaBan(ArrayList<QuanAo> listLap) {
        int soLuong = 0;
        if (listLap != null) {
            if (listLap.size() > 0) {
                for (QuanAo quanAo : listLap) {
                    int ban = quanAo.getDaBan();
                    soLuong += ban;
                }
            }
        }
        return soLuong;
    }

    public String getNowDateSQL() {
        Date currentTime = Calendar.getInstance().getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
    }

    public void addDemoAoKaki() {
        Bitmap bm0 = changeType.urlToBitmap(context, "https://cdn.tgdd.vn/Products/Images/44/260171/dell-gaming-g15-5515-r5-p105f004dgr-291121-114930-600x600.jpg");

        Bitmap bm2 = changeType.urlToBitmap(context, "https://www.akmen.vn/images/2021/05/ao-khoac-kaki-basic-ak018-11032-p.jpg");
        if (bm2 != null) {
            QuanAo lp2 = new QuanAo("22", "LAoKaki", "ÁO KHOÁC KAKI BASIC AK018"
                    , "239.000₫", 15, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            quanAoDAO.insertQuanAo(lp2);
        }

        Bitmap bm3 = changeType.urlToBitmap(context, "https://salt.tikicdn.com/cache/750x750/ts/product/36/77/2d/b894cae68802d9a77c909e0733aaeaf0.jpg.webp");
        if (bm3 != null) {
            QuanAo lp3 = new QuanAo("23", "LAoKaki", "Áo khoác kaki jean Nam UNISEX"
                    , "145.000₫", 16, 0, changeType.checkByteInput(changeType.bitmapToByte(bm3)));
            quanAoDAO.insertQuanAo(lp3);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://product.hstatic.net/1000026602/product/dsc06328_6f17dc0f050a45ab8e27fa35164c6ee8_master.jpg");
        if (bm4 != null) {
            QuanAo lp4 = new QuanAo("4", "LAoKaki", "Áo Khoác Kaki Waves Pattern"
                    , "299.000₫", 19, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            quanAoDAO.insertQuanAo(lp4);
        }
        Bitmap bm5 = changeType.urlToBitmap(context, "https://cdn0199.cdn4s.com/media/13e72450b5e36dbd34f2.jpg");
        if (bm5 != null) {
            QuanAo lp5 = new QuanAo("30", "LAoKaki", "Áo Khoác Kaki Waves Pattern"
                    , "470.000₫", 29, 0, changeType.checkByteInput(changeType.bitmapToByte(bm5)));
            quanAoDAO.insertQuanAo(lp5);
        }
    }

    public void addDemoAoThun() {
        Bitmap bm1 = changeType.urlToBitmap(context, "https://pos.nvncdn.net/778773-105877/ps/20221013_n6HKsuzizp6K2vDgrJLI4qA8.jpg");
        if (bm1 != null) {
            QuanAo lp1 = new QuanAo("16", "LAoThun", "ÁO PHÔNG - SIMON - SLIM FIT - TRẮNG"
                    , "149.000₫", 2, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            quanAoDAO.insertQuanAo(lp1);
        }

        Bitmap bm2 = changeType.urlToBitmap(context, "https://bizweb.dktcdn.net/100/415/697/products/1b-ff9ccc92-6e5d-42e5-b558-41348b582c20.jpg?v=1648869733877");
        if (bm2 != null) {
            QuanAo lp2 = new QuanAo("17", "LAoThun", "Áo Thun Teelab Local Brand Unisex Dreamland TS142"
                    , "185.000₫", 5, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            quanAoDAO.insertQuanAo(lp2);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://limeorange.vn/_next/image?url=https%3A%2F%2Fs3-ap-southeast-1.amazonaws.com%2Flo-image%2Fproduct%2Fcover%2Fpc-ao-thun-tron-nu-cao-cap-mau-xanh-la-large-1673321457-2682.jpg&w=750&q=75");
        if (bm4 != null) {
            QuanAo lp4 = new QuanAo("9", "LAoThun", "Áo Thun Trơn Nữ Cao Cấp - Basic Plus 3 T-shirt For Women"
                    , "209.000₫", 16, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            quanAoDAO.insertQuanAo(lp4);
        }
        Bitmap bm5 = changeType.urlToBitmap(context, "https://product.hstatic.net/1000088324/product/mock_up_ao_xe_dua_1127688c6b1f43a2ad656d64d3111328_master.png");
        if (bm5 != null) {
            QuanAo lp5 = new QuanAo("32", "LAoThun", "Áo thun Teeworld Thunder Car T-shirt"
                    , "229.000₫", 26, 0, changeType.checkByteInput(changeType.bitmapToByte(bm5)));
            quanAoDAO.insertQuanAo(lp5);
        }
    }

    public void addDemoAoGio() {
        Bitmap bm1 = changeType.urlToBitmap(context, "https://bizweb.dktcdn.net/100/415/697/products/m0w2iuvv-1-1hxj-hinh-mat-truoc-01-773b266a-c405-45d5-b508-87327f1f0062.jpg?v=1664011459027");
        if (bm1 != null) {
            QuanAo lp1 = new QuanAo("1", "LAoGio", "Áo Khoác Gió Teelab Local Brand"
                    , "299.000đ", 7, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            quanAoDAO.insertQuanAo(lp1);
        }

        Bitmap bm2 = changeType.urlToBitmap(context, "https://bizweb.dktcdn.net/100/415/697/products/m0w2iuvv-1-1hxj-hinh-mat-truoc-0as.jpg?v=1665212653290");
        if (bm2 != null) {
            QuanAo lp2 = new QuanAo("2", "LAoGio", "Áo Khoác Gió Teelab Local Brand Unisex"
                    , "275.000₫", 12, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            quanAoDAO.insertQuanAo(lp2);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://product.hstatic.net/1000026602/product/dsc06347_52d65afe91d14f51b44385c9bc71ab5b_master.jpg");
        if (bm4 != null) {
            QuanAo lp4 = new QuanAo("14", "LAoGio", "Áo Khoác Wind Easy Wear"
                    , "390.000₫", 18, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            quanAoDAO.insertQuanAo(lp4);
        }
        Bitmap bm5 = changeType.urlToBitmap(context, "https://bizweb.dktcdn.net/thumb/1024x1024/100/345/647/products/z3520329150571-8e91cd4f53a41b8d4d52a76612942d76.jpg");
        if (bm5 != null) {
            QuanAo lp5 = new QuanAo("35", "LAoGio", "ÁO KHOÁC DÙ SLC"
                    , "190.000₫", 18, 0, changeType.checkByteInput(changeType.bitmapToByte(bm5)));
            quanAoDAO.insertQuanAo(lp5);
        }
    }

    public void addDemoAoHoodie() {
        Bitmap bm1 = changeType.urlToBitmap(context, "https://pos.nvncdn.net/be3159-662/ps/20221102_KRxV7b5GBM0FUWxkw66Rk1Zr.jpg");
        if (bm1 != null) {
            QuanAo lp1 = new QuanAo("26", "LAoHoodie", "Áo Hoodie Classic 0390"
                    , "579.000₫", 15, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            quanAoDAO.insertQuanAo(lp1);
        }

        Bitmap bm2 = changeType.urlToBitmap(context, "https://bizweb.dktcdn.net/100/415/697/products/icon-back-1.jpg?v=1689669171490");
        if (bm2 != null) {
            QuanAo lp2 = new QuanAo("27", "LAoHoodie", "Áo Hoodie Teelab Local Brand Unisex Journey Icons"
                    , "349.000₫", 11, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            quanAoDAO.insertQuanAo(lp2);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://hudistore.com/wp-content/uploads/2021/06/ao-hoodie-den-trang-doc-la-vai-ni-sieu-ben-phong-cach.jpg");
        if (bm4 != null) {
            QuanAo lp4 = new QuanAo("19", "LAoHoodie", "Áo hoodie đen trắng độc lạ vải nỉ siêu bền"
                    , "158.000₫", 20, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            quanAoDAO.insertQuanAo(lp4);
        }
        Bitmap bm5 = changeType.urlToBitmap(context, "https://www.ebon.vn/data/product/91/62/54/medium/ao-hoodie-nam-champion-mau-xam-atd172-1633256288-429.jpg");
        if (bm5 != null) {
            QuanAo lp5 = new QuanAo("39", "LAoHoodie", "Áo hoodie nam Champion, màu xám ATD172"
                    , "180.000₫", 23, 0, changeType.checkByteInput(changeType.bitmapToByte(bm5)));
            quanAoDAO.insertQuanAo(lp5);
        }
    }

    public void addDemoQuanBo() {
        Bitmap bm1 = changeType.urlToBitmap(context, "https://pos.nvncdn.net/be3159-662/ps/20221205_V6G2YfE4zRO6weBXl9b2Ogby.jpg");
        if (bm1 != null) {
            QuanAo lp1 = new QuanAo("11", "LQuanBo", "Quần Jeans Slimfit 0464"
                    , "469.000₫", 16, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            quanAoDAO.insertQuanAo(lp1);
        }

        Bitmap bm2 = changeType.urlToBitmap(context, "https://product.hstatic.net/1000360022/product/dsc08837_f05f7932b6944adeab315c0f3d121222.jpg");
        if (bm2 != null) {
            QuanAo lp2 = new QuanAo("12", "LQuanBo", "Quần Jean Slim-fit ICONDENIM Light Blue"
                    , "385.000₫", 10, 0, changeType.checkByteInput(changeType.bitmapToByte(bm2)));
            quanAoDAO.insertQuanAo(lp2);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "http://www.gaugaushop.com/plugins/responsive_filemanager/source/san%20pham/Quan/QuanJeanDai/GQJ703%2C706%2C707%2C708%2C709/056ce4ebac4a56140f5b.jpg");
        if (bm4 != null) {
            QuanAo lp4 = new QuanAo("24", "LQuanBo", "\n" +
                    "Quần Baggy Jean Họa Tiết Hoạt Hình"
                    , "280.000₫", 25, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            quanAoDAO.insertQuanAo(lp4);
        }
        Bitmap bm5 = changeType.urlToBitmap(context, "https://cf.shopee.vn/file/4f8d33e3d3389038a2dc391ffdcde95d");
        if (bm5 != null) {
            QuanAo lp5 = new QuanAo("44", "LQuanBo", "\n" +
                    "QUẦN BÒ THỤNG"
                    , "320.000₫", 25, 0, changeType.checkByteInput(changeType.bitmapToByte(bm5)));
            quanAoDAO.insertQuanAo(lp5);
        }
    }

    public void addDemoQuanVai() {
        Bitmap bm0 = changeType.urlToBitmap(context, "https://product.hstatic.net/1000096703/product/2_2a97e6ad19684a8eb9e78890cb58821e_master.jpg");
        if (bm0 != null) {
            QuanAo lp0 = new QuanAo("5", "LQuanVai", "Quần Tây Nam Cạp Thun, Vải Co Giãn Thoáng Mát"
                    , "350.000₫", 19, 0, changeType.checkByteInput(changeType.bitmapToByte(bm0)));
            quanAoDAO.insertQuanAo(lp0);
        }

        Bitmap bm1 = changeType.urlToBitmap(context, "https://bizweb.dktcdn.net/100/345/548/products/untitled-1-01-60ef28f7-241d-4adf-a239-79fdfbac9351.jpg?v=1554173810040");
        if (bm1 != null) {
            QuanAo lp1 = new QuanAo("6", "LQuanVai", "Quần vải đen dày"
                    , "539.000₫", 12, 0, changeType.checkByteInput(changeType.bitmapToByte(bm1)));
            quanAoDAO.insertQuanAo(lp1);
        }

        Bitmap bm4 = changeType.urlToBitmap(context, "https://bizweb.dktcdn.net/thumb/large/100/176/815/products/74908190-2516228775128224-8058082186076618752-n-14e4a38a-9d42-46a4-92a9-50f10f18621f.jpg?v=1589545787750");
        if (bm4 != null) {
            QuanAo lp4 = new QuanAo("29", "LQuanVai", "Quần thể thao vải dù, kiểu ống bo"
                    , "180.000₫", 33, 0, changeType.checkByteInput(changeType.bitmapToByte(bm4)));
            quanAoDAO.insertQuanAo(lp4);
        }
        Bitmap bm5 = changeType.urlToBitmap(context, "https://filebroker-cdn.lazada.vn/kf/S2a9f565d165b40749bbc8c5166d04892h.jpg");
        if (bm5 != null) {
            QuanAo lp5 = new QuanAo("49", "LQuanVai", "Quần Tơ Lạnh Nam Mùa Hè"
                    , "400.000₫", 33, 0, changeType.checkByteInput(changeType.bitmapToByte(bm5)));
            quanAoDAO.insertQuanAo(lp5);
        }
    }

    private void textChange() {
        EditText editText = null;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
