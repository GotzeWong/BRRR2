package be.thomasmore.brrr.common.fragment;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import org.altbeacon.beacon.BeaconManager;

import be.thomasmore.brrr.Application;
import be.thomasmore.brrr.BuildConfig;
import be.thomasmore.brrr.R;
import be.thomasmore.brrr.common.mvp.CityStreamMvpView;
import be.thomasmore.brrr.common.mvp.CityStreamPresenter;
import be.thomasmore.brrr.data.model.Beacon;
import be.thomasmore.brrr.dialogs.DialogHelper;
import butterknife.Bind;
import butterknife.ButterKnife;

public class CityStreamFragment extends TitledFragment implements CityStreamMvpView {
    private static final int BT_REQUEST = 102;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 101;

    private static final int LAYOUT = R.layout.fragment_city_stream;
    @Bind(R.id.city_stream_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.city_stream_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private CityStreamRecyclerAdapter cityStreamRecyclerAdapter;
    private CityStreamPresenter cityStreamPresenter = new CityStreamPresenter();

    public static CityStreamFragment getInstance(Context context) {
        Bundle args = new Bundle();
        CityStreamFragment fragment = new CityStreamFragment();
//        fragment.setTitle(context.getString(R.string.tab_title_city_stream));
        fragment.setTitle("Hi");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void addCard(Beacon beacon) {
        Application.addBeacon(beacon);
        cityStreamRecyclerAdapter.addItem(beacon);
        cityStreamRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void stopLoadIndicator() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BT_REQUEST) {
            if (resultCode != Activity.RESULT_OK) {
                showBTNotEnabledMessage();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                checkGrantedCoarseLocationPermission(grantResults[0]);
            }
        }
    }

    private void checkGrantedCoarseLocationPermission(int grantResult) {
        if (grantResult == PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "coarse location permission granted");
        } else {
            showLocationPermissionLimitedMessage();
        }
    }

    private void showLocationPermissionLimitedMessage() {
        DialogHelper.showOkDialog(getActivity(), R.string.location_permission_limited_title, R.string.location_permission_limited_message);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, v);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cityStreamRecyclerAdapter = new CityStreamRecyclerAdapter();
        mRecyclerView.setAdapter(cityStreamRecyclerAdapter);

        cityStreamPresenter.attachView(this);

        if (BuildConfig.DEBUG) {
            cityStreamPresenter.loadCard();
        }

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestCoarseLocationPermission();
                verifyBluetooth();
                cityStreamPresenter.loadNewCard();
            }
        });

        return v;
    }

    private void requestCoarseLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                DialogHelper.showOkDialog(
                        getActivity(),
                        R.string.location_permission_predialog_title,
                        R.string.location_permission_predialog_message,
                        new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                            }
                        });
            }
        }
    }

    private void verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(getActivity()).checkAvailability()) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, BT_REQUEST);
                }
            }
        } catch (RuntimeException e) {
            showBTLENotSupportedMessage();
        }
    }

    private void showBTLENotSupportedMessage() {
        DialogHelper.showOkDialog(getActivity(), R.string.bluetooth_activation_request_title, R.string.bluetooth_activation_request_message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cityStreamPresenter.detachView();
    }

    private void showBTNotEnabledMessage() {
        DialogHelper.showOkDialog(getActivity(), R.string.bluetooth_not_activated_title, R.string.bluetooth_not_activated_message);
    }
}
