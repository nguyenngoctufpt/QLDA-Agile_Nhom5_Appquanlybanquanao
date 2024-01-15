package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.KH_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.GiaoDich;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;

public class KH_GiaoDich_Adapter extends RecyclerView.Adapter<KH_GiaoDich_Adapter.AuthorViewHolder> {

    Context context;
    ArrayList<GiaoDich> listGD;
    String TAG = "KH_ViTien_Adapter_____";

    public KH_GiaoDich_Adapter(ArrayList<GiaoDich> listGD, Context context) {
        this.listGD = listGD;
        this.context = context;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup vGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_giaodich, vGroup, false);
        return new AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder author, @SuppressLint("RecyclerView") final int pos) {
        setRow(pos, author);
    }

    @Override
    public int getItemCount() {
        return listGD.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView title, detail, date, soTien;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_Title);
            detail = itemView.findViewById(R.id.textView_ChiTiet);
            date = itemView.findViewById(R.id.textView_Date);
            soTien = itemView.findViewById(R.id.textView_SoTien);
        }
    }

    public void setRow(int pos, @NonNull AuthorViewHolder author) {
        Log.d(TAG, "setRow: " + pos);
        GiaoDich giaoDich = listGD.get(pos);
        author.title.setText(giaoDich.getTitle());
        author.detail.setText(giaoDich.getChiTiet());
        author.date.setText(giaoDich.getNgayGD());
        author.soTien.setText(giaoDich.getSoTien());
    }
}