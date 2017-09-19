package com.androidcat.fuelmore.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidcat.acnet.entity.GasStation;
import com.androidcat.fuelmore.R;

import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-4 18:35:19
 * add function description here...
 */
public class GasStationAdapter extends BaseAdapter{

    private Context context;
    private List<GasStation> stations;
    private LayoutInflater layoutInflater;

    public GasStationAdapter(Context context,List<GasStation> data){
        this.context = context;
        this.stations = data;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Object getItem(int position) {
        return stations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_gas_station, null);
            vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
            vh.addrTv = (TextView) convertView.findViewById(R.id.addrTv);
            vh.businessTimeTv = (TextView) convertView.findViewById(R.id.businessTimeTv);
            vh.disTv = (TextView) convertView.findViewById(R.id.disTv);
            vh.telTv = (TextView) convertView.findViewById(R.id.telTv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //set data
        final GasStation gasStation = stations.get(position);
        String name = gasStation.getPoint_name();
        String addr = gasStation.getPoint_adress();
        String businessTime = gasStation.getBusiness_time();
        String tel = gasStation.getTel();

        vh.nameTv.setText(name);
        vh.addrTv.setText(addr);
        vh.businessTimeTv.setText(businessTime);
        vh.telTv.setText(tel);
        vh.disTv.setText("700m");

        return convertView;
    }

    static class ViewHolder{
        TextView nameTv;
        TextView addrTv;
        TextView businessTimeTv;
        TextView disTv;
        TextView telTv;
    }
}
