package com.androidcat.fuelmore.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.OrderInfo;
import com.androidcat.acnet.entity.response.OrderListResponse;
import com.androidcat.acnet.manager.OrderPayManager;
import com.androidcat.fuelmore.R;
import com.androidcat.fuelmore.ui.adapter.OrderAdapter;
import com.anroidcat.acwidgets.EmptyView;
import com.anroidcat.acwidgets.listview.XListView;

import java.util.ArrayList;

public class OrderListActivity extends BaseActivity implements OnClickListener, XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private XListView ordersLv;
    private View back;
    private EmptyView emptyView;

    private ArrayList<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
    private OrderAdapter adapter;
    private OrderPayManager orderPayManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
    }

    @Override
    protected void handleEventMsg(Message msg) {
        super.handleEventMsg(msg);
        switch (msg.what) {
            case OptMsgConst.MSG_GET_ORDER_LIST_START:
                if (!(boolean)msg.obj){
                    emptyView.loading();
                }
                break;
            case OptMsgConst.MSG_GET_ORDER_LIST_SUCCESS:
                OrderListResponse response = (OrderListResponse) msg.obj;
                if (response != null && response.getContent() != null && response.getContent().size() > 0){
                    orderInfos.clear();
                    orderInfos.addAll(response.getContent());
                }
                emptyView.success();
                setupView();
                break;
            case OptMsgConst.MSG_GET_ORDER_LIST_FAIL:
                String error = (String) msg.obj;
                showToast(error);
                emptyView.fail();
                break;
        }
    }

    private void initData() {
        ordersLv.setOnItemClickListener(this);
        orderPayManager = new OrderPayManager(this, baseHandler);
    }

    private void initUI() {
        setContentView(R.layout.activity_order_list);
        back = findViewById(R.id.back);
        ordersLv = (XListView) findViewById(R.id.order_list);
        emptyView = (EmptyView) findViewById(R.id.emptyView);
        emptyView.bindView(ordersLv);

        emptyView.viewOnClick(OrderListActivity.this,"loadOrders",false);
        back.setOnClickListener(this);
        ordersLv.setPullRefreshEnable(true);
        ordersLv.setPullLoadEnable(false);
        ordersLv.setXListViewListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders(false);
    }

    private void loadOrders(Boolean isPullRefreshing) {
        orderPayManager.getOrderList(user.userName,user.id,user.ciphertext,user.companyId,user.company,isPullRefreshing);
    }

	private void setupView() {
        ordersLv.stopRefresh();
        if (adapter == null) {
            adapter = new OrderAdapter(this, orderInfos);
            ordersLv.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadOrders(true);
    }

    @Override
    public void onLoadMore() {
        //do nothing...
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*GasStation gasStation = (GasStation) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this,GasStationActivity.class);
        intent.putExtra("GasStation",gasStation);
        startActivity(intent);*/
    }
}
