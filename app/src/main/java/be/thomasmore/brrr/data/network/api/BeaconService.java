package be.thomasmore.brrr.data.network.api;


import java.util.ArrayList;

import be.thomasmore.brrr.data.model.NetworkBeacon;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface BeaconService {
    @GET("/smart-city/beacon-data/")
    Observable<ArrayList<NetworkBeacon>> getBeacons(@Query(value = "beacons", encoded = true) String beaconsJson);
}