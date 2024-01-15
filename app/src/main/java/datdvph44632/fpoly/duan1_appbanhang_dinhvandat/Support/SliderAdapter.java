package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.Photo;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {
    List<Photo> listImages;

    public SliderAdapter(List<Photo> listImages) {
        this.listImages = listImages;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_image, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        Photo photo = listImages.get(position);
        viewHolder.imageView.setImageResource(photo.getResurceId());
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_QuanAo);
        }
    }
}
