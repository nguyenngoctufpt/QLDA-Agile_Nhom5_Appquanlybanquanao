package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.NAV_Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentNV_Admin.NV_TTDonHang1_Fragment;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentNV_Admin.NV_TTDonHang2_Fragment;

public class NV_ViewDonHang_Adapter extends FragmentStatePagerAdapter {

    public NV_ViewDonHang_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                NV_TTDonHang1_Fragment kh_ttDonHang1_fragment = new NV_TTDonHang1_Fragment();
                return kh_ttDonHang1_fragment;
            case 1:
                NV_TTDonHang2_Fragment kh_ttDonHang2_fragment = new NV_TTDonHang2_Fragment();
                return kh_ttDonHang2_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {

            case 0:
                return "Đang giao hàng";
            case 1:
                return "Đã giao hàng";
            default:
                return null;
        }
    }
}
