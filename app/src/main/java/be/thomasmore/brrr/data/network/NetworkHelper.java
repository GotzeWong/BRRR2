package be.thomasmore.brrr.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import be.thomasmore.brrr.Application;
import be.thomasmore.brrr.data.model.Beacon;
import be.thomasmore.brrr.data.model.NetworkBeacon;
import be.thomasmore.brrr.data.network.api.BeaconService;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NetworkHelper {
    private static final String SERVER_API_URL = "http://labo-pbei.no-ip.org:10001";

    public Observable<Beacon> getBeacon(Beacon.BeaconIds beaconIds) {
        if (isOnline()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            BeaconService service = retrofit.create(BeaconService.class);

            Observable<Beacon> downloadedBeacons =
                    service.getBeacons(encodeQueryPart(generateJsonFromBeaconIds(beaconIds)))
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .observeOn(Schedulers.io())
                            .flatMap(new Func1<ArrayList<NetworkBeacon>, Observable<NetworkBeacon>>() {
                                @Override
                                public Observable<NetworkBeacon> call(ArrayList<NetworkBeacon> beaconModels) {
                                    return Observable.from(beaconModels);
                                }
                            })
                            .filter(new Func1<NetworkBeacon, Boolean>() {
                                @Override
                                public Boolean call(NetworkBeacon networkBeacon) {
                                    return networkBeacon.getStatus().equals("200");
                                }
                            })
                            .map(new Func1<NetworkBeacon, Beacon>() {
                                @Override
                                public Beacon call(NetworkBeacon networkBeacon) {
                                    return new Beacon(networkBeacon);
                                }
                            });

            return downloadedBeacons;

        } else {
            Log.d("NETWORK", "There is no internet connection");
            return Observable.empty();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) Application.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private String encodeQueryPart(String queryPart) {
        String query = "";
        try {
            query = URLEncoder.encode(queryPart, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("NETWORK", "Encoding error", e);
        }
        return query;
    }

    private String generateJsonFromBeaconIds(Beacon.BeaconIds beaconIds) {
        StringBuilder json = new StringBuilder();
        json.append('[');

        json.append('{')
                .append("\"uuid\":\"")
                .append(beaconIds.getUuid())
                .append("\",\"major\":\"")
                .append(beaconIds.getMajor())
                .append("\",\"minor\":\"").append(beaconIds.getMinor()).append("\"}");
        json.append(']');

        Log.d("NETWORK", "Json generated : " + json.toString());
        return json.toString();
    }

}
