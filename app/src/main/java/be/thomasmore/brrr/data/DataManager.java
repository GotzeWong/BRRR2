package be.thomasmore.brrr.data;


import be.thomasmore.brrr.data.model.Beacon;
import be.thomasmore.brrr.data.network.NetworkHelper;
import rx.Observable;

public class DataManager {
    private static DataManager mInstance = null;

    private final NetworkHelper networkHelper = new NetworkHelper();

    private DataManager() {
    }

    public static synchronized DataManager getInstance() {
        if (mInstance == null)
            mInstance = new DataManager();
        return mInstance;
    }

    public Observable<Beacon> getDefaultBeacons() {
        return Observable.just(
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "2"), "Truck 10", "Hi, This is Michael from China.", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "8"), "Truck 9", "Long description Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", true),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "12"), "Truck 8", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", true),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "43"), "Truck 7", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "44"), "Truck 6", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "45"), "Truck 5", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "46"), "Truck 4", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "47"), "Truck 3", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "48"), "Truck 2", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "49"), "Truck 1", "Year:   2016                               Manufacture:   TM                               Model:   A1000                                       Asset Type:   Heavey Haul Truck", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", true)
        );
    }

    public Observable<Beacon> loadNewBeacons() {
        return networkHelper.getBeacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "12", "42"));
    }
}

