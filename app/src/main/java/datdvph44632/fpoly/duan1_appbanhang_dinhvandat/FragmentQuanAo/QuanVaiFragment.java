package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.FragmentQuanAo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.Photo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Loader.KH_QuanAo_Loader;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support.SliderAdapter;

public class QuanVaiFragment extends Fragment {

    List<Photo> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanvai, container, false);
        SliderView sliderView = view.findViewById(R.id.sliderView);

        list = setListPhoto();
        SliderAdapter sliderAdapter = new SliderAdapter(list);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        KH_QuanAo_Loader kh_quanAo_loader = new KH_QuanAo_Loader(getContext(), view.findViewById(R.id.recyclerView_Laptop_Asus));
        kh_quanAo_loader.execute("LAoHoodie");

        return view;
    }

    private List<Photo> setListPhoto() {
        list.add(new Photo(R.drawable.ao_h_mot));
        list.add(new Photo(R.drawable.ao_h_hai));
        list.add(new Photo(R.drawable.ao_h_ba));
        list.add(new Photo(R.drawable.ao_h_bon));
        list.add(new Photo(R.drawable.ao_h_nam));
        return list;
    }
}