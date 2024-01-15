package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentKH.KH_TTDonHang1_Fragment;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentKH.KH_TTDonHang2_Fragment;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentKH.KH_TTDonHang3_Fragment;

public class KH_ViewDonHang_Adapter extends FragmentStatePagerAdapter {

    public KH_ViewDonHang_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                KH_TTDonHang1_Fragment kh_ttDonHang1_fragment = new KH_TTDonHang1_Fragment();
                return kh_ttDonHang1_fragment;
            case 1:
                KH_TTDonHang2_Fragment kh_ttDonHang2_fragment = new KH_TTDonHang2_Fragment();
                return kh_ttDonHang2_fragment;
            case 2:
                KH_TTDonHang3_Fragment kh_ttDonHang3_fragment = new KH_TTDonHang3_Fragment();
                return kh_ttDonHang3_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {

            case 0:
                return "Chờ xác nhận";
            case 1:
                return "Đang giao hàng";
            case 2:
                return "Đã giao hàng";
            default:
                return null;
        }
    }
}
