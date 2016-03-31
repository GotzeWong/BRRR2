package be.thomasmore.brrr;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import java.util.LinkedList;

import be.thomasmore.brrr.data.DataManager;
import be.thomasmore.brrr.data.model.Beacon;

public class Application extends android.app.Application {
    private static Context context;

    private static FragmentManager fragmentManager;

    private static LinkedList<Beacon> beaconList = new LinkedList<>();

    public static Context getAppContext() {
        return Application.context;
    }
    public static FragmentManager getFragmentManager() {
        return Application.fragmentManager;
    }

    public static void setFragmentManager(FragmentManager fragmentManager) {
        Application.fragmentManager = fragmentManager;
    }

    public static LinkedList<Beacon> getBeaconList() {
        return Application.beaconList;
    }

    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();
    }

    public static void addBeacon(Beacon beacon) {
        Application.beaconList.addFirst(beacon);
    }
}