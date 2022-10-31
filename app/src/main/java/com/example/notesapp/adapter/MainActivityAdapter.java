package com.example.notesapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notesapp.R;
import com.example.notesapp.model.MainActivityModel;

import java.util.ArrayList;

public class MainActivityAdapter extends ArrayAdapter<MainActivityModel> {

    public MainActivityAdapter(Activity context, ArrayList<MainActivityModel> notification) {
        super(context, 0, notification);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_booking, parent, false);
        }

        MainActivityModel current = getItem(position);

        TextView tv_judul = listItemView.findViewById(R.id.list_tv_judul);
        TextView tv_isi = listItemView.findViewById(R.id.list_tv_isi);
        TextView tv_tanggal = listItemView.findViewById(R.id.list_tv_tanggal);

        tv_judul.setText(current.getMjudul());
        tv_isi.setText(current.getMisi());
        tv_tanggal.setText(current.getMtanggal());

//        TextView idBook = listItemView.findViewById(R.id.id_booking);
//        idBook.setText("ID : " + current.getIdBook());
//
//        TextView tanggal = listItemView.findViewById(R.id.tanggal);
//        tanggal.setText(current.getTanggal());
//
//        TextView riwayat = listItemView.findViewById(R.id.riwayat);
//        riwayat.setText(current.getRiwayat());
//
//        TextView tvTotal = listItemView.findViewById(R.id.tv_total);
//        tvTotal.setText("Total :");
//
//        TextView total = listItemView.findViewById(R.id.total);
//        total.setText("Rp. " + current.getTotal());



        return listItemView;
    }
}