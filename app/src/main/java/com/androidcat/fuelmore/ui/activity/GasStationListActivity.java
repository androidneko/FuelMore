package com.androidcat.fuelmore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.GasStation;
import com.androidcat.acnet.entity.response.StationListResponse;
import com.androidcat.acnet.manager.GasStationManager;
import com.androidcat.fuelmore.R;
import com.androidcat.fuelmore.ui.adapter.GasStationAdapter;
import com.anroidcat.acwidgets.EmptyView;
import com.anroidcat.acwidgets.listview.XListView;

import java.util.ArrayList;

public class GasStationListActivity extends BaseActivity implements OnClickListener, XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private XListView stationLv;
    private View back;
    private View mapModeView;
    private EmptyView emptyView;

    private ArrayList<GasStation> stationDatas = new ArrayList<GasStation>();
    private GasStationAdapter adapter;
    private GasStationManager stationManager;

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
            case OptMsgConst.MSG_GET_STATION_LIST_START:
                if (!(boolean)msg.obj){
                    emptyView.loading();
                }
                break;
            case OptMsgConst.MSG_GET_STATION_LIST_SUCCESS:
                StationListResponse response = (StationListResponse) msg.obj;
                if (response != null && response.getContent() != null && response.getContent().size() > 0){
                    stationDatas.clear();
                    stationDatas.addAll(response.getContent());
                }
                emptyView.success();
                setupView();
                break;
            case OptMsgConst.MSG_GET_STATION_LIST_FAIL:
                String error = (String) msg.obj;
                showToast(error);
                emptyView.fail();
                break;
        }
    }

    private void initData() {
        stationLv.setOnItemClickListener(this);
        stationManager = new GasStationManager(this, baseHandler);
    }

    private void initUI() {
        setContentView(R.layout.activity_gasstation_list);
        back = findViewById(R.id.back);
        mapModeView = findViewById(R.id.mapModeView);
        stationLv = (XListView) findViewById(R.id.review_list);
        emptyView = (EmptyView) findViewById(R.id.empty);
        stationLv.setEmptyView(emptyView);

        emptyView.viewOnClick(this,"loadStations",false);
        back.setOnClickListener(this);
        mapModeView.setOnClickListener(this);
        stationLv.setPullRefreshEnable(true);
        stationLv.setPullLoadEnable(false);
        stationLv.setXListViewListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStations(false);
    }

    private void loadStations(Boolean isPullRefreshing) {
        stationManager.queryGasStations(isPullRefreshing);
    }

	private void setupView() {
        stationLv.stopRefresh();
        if (adapter == null) {
            adapter = new GasStationAdapter(this, stationDatas);
            stationLv.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mapModeView:
                gotoMapView();
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadStations(true);
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

    private void gotoMapView(){
        Intent intent = new Intent(this,GasStationMapActivity.class);
        intent.putParcelableArrayListExtra("gasStations",stationDatas);
        startActivity(intent);
    }
}
