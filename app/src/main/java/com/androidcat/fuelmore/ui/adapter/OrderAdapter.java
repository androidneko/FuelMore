package com.androidcat.fuelmore.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidcat.acnet.entity.OrderInfo;
import com.androidcat.fuelmore.R;

import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-4 18:35:19
 * add function description here...
 */
public class OrderAdapter extends BaseAdapter{

    private Context context;
    private List<OrderInfo> stations;
    private LayoutInflater layoutInflater;

    public OrderAdapter(Context context, List<OrderInfo> data){
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
            convertView = layoutInflater.inflate(R.layout.item_order, null);
            vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
            vh.orderNoTv = (TextView) convertView.findViewById(R.id.orderNoTv);
            vh.statusTv = (TextView) convertView.findViewById(R.id.statusTv);
            vh.amountTv = (TextView) convertView.findViewById(R.id.amountTv);
            vh.typeTv = (TextView) convertView.findViewById(R.id.typeTv);
            vh.litreTv = (TextView) convertView.findViewById(R.id.litreTv);
            vh.orderTimeTv = (TextView) convertView.findViewById(R.id.orderTimeTv);
            vh.paidTv = (TextView) convertView.findViewById(R.id.paidTv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //set data
        final OrderInfo orderInfo = stations.get(position);
        String name = orderInfo.pointName;
        String orderNo = orderInfo.orderNo;
        String status = orderInfo.status;
        String amount = orderInfo.amountMoney;
        String type = orderInfo.pointType;
        String litre = orderInfo.consumerUnit;
        String orderTime = orderInfo.createTime;
        String paid = orderInfo.paid;

        vh.nameTv.setText(name);
        vh.orderNoTv.setText(orderNo);
        vh.statusTv.setText(status);
        vh.amountTv.setText("￥"+amount);
        vh.typeTv.setText(type);
        vh.litreTv.setText(litre+"升");
        vh.orderTimeTv.setText(orderTime);
        vh.paidTv.setText("实付:￥"+paid);

        return convertView;
    }

    static class ViewHolder{
        TextView orderNoTv;
        TextView statusTv;
        TextView nameTv;
        TextView amountTv;
        TextView typeTv;
        TextView litreTv;
        TextView orderTimeTv;
        TextView paidTv;
    }
}
