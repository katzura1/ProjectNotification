package com.example.acer.projectnotification.classPackage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.acer.projectnotification.R;
import com.example.acer.projectnotification.dbPackage.HistoryPengingat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<HistoryPengingat> historyPengingats;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<HistoryPengingat> historyPengingats) {
        this.historyPengingats = historyPengingats;
        this.layoutInflater = layoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        //This method returns the total number of row counts for the listview.
        // Typically this contains the size of the list you passing as input.
        return historyPengingats.size();
    }

    @Override
    public Object getItem(int position) {
        //Returns object representing data for each row
        return historyPengingats.get(position);
    }

    @Override
    public long getItemId(int position) {
        //This returns the unique integer id that represents each row item.
        // Let us return the integer position value.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //The getView() method returns a view instance that represents a single row in ListView item.
        // Here you can inflate your own layout and update values on list row.
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.tglView = convertView.findViewById(R.id.tgl);
            holder.namaView = convertView.findViewById(R.id.nama_obat);
            holder.hariView = convertView.findViewById(R.id.hari);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(historyPengingats.get(position).getmDate());
        } catch (ParseException e) {
            Log.e("TANGGAL !!!!", e.getMessage());
        }
        Log.e("TANGGAL", String.valueOf(date));
        String namaHari = new SimpleDateFormat("EEEE").format(date);

        holder.tglView.setText(historyPengingats.get(position).getmDate()+" "+historyPengingats.get(position).getmTime());
        holder.namaView.setText(historyPengingats.get(position).getObat1()+", "+historyPengingats.get(position).getObat2());
        holder.hariView.setText(namaHari);
        return convertView;
    }

    static class ViewHolder {
        TextView tglView;
        TextView namaView;
        TextView hariView;
    }
}
