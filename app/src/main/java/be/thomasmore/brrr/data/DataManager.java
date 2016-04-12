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
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "8"), "Truck 9", "Long description Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "12"), "Truck 8", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "43"), "Truck 7", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "44"), "Truck 6", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "45"), "Truck 5", "Some text", "http://www.afetrucks.com/media/dealer_921/storage/images/keys/inv_worktuck.png", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "49"), "CHEVROLET C6500", "Year:\t2005\n" +"Axles:\tSingle\n" +
                        "Front Axle:\t8000\n" +
                        "Rear Axle:\t17200\n" +
                        "Wheel Base:\t149\n" +
                        "Engine:\tCaterpillar C-7\n" +
                        "Transmission:\t6 SPD\n" +
                        "GVWR:\t25200\n" +
                        "Fuel Type:\tDIESEL\n" +
                        "VIN:\t1GBJ6C1C45F530817", "http://www.itrucksale.com/image/40999_w400_h300_2.jpg", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "46"), "INTERNATIONAL PROSTAR + EAGLE", "\n" +
                        "Body style\tHEAVY DUTY TRUCKS - CONVENTIONAL TRUCKS W/ SLEEPER\tAxles\tTandem\n" +
                        "Suspension\tAir Ride\tFuel Type\tDIESEL\n" +
                        "Wheel Base\t244\tVIN\t3HSDJAPR3GN379030\n" +
                        "Engine\tCummins ISX 475\tOdometer\t2,400 mi\n" +
                        "Transmission\tULTRASHIFT\tStock#\t379030\n" +
                        "Ratio\t3.36", "http://www.itrucksale.com/image/35945_w800_h600_2.jpg", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "47"), "INTERNATIONAL PROSTAR EAGLE", "DOUBLE BUNK SKYRISE, MONSOON PREMIUM STEREO PKG W SLEEPER CONTROLS, REFRIGERATOR, DSL BUNK HEATER, SHORE POWER 110V SOCKET, AUTO TRACTION CONTROL, LED HEADLIGHTS, SMARTWAY CERTIFIED LOADED TRUCK MUST SEE! CALL TO SCHEDULE A TEST DRIVE TODAY. 125,500 + FET. Disclaimer:Price listed does not expire. Prices do not include additional fees and costs of closing, including government fees and taxes, any finance charges, any dealer documentation fees, any emissions testing fees or other fees. All prices, specifications and availability subject to change without notice. Contact dealer for most current information. Plus sales tax, title and license (if applicable). A documentary service fee up to $150 may be added. The documentary service fee is a negotiable fee. Please Call or email for complete, specific vehicle information. ", "http://www.itrucksale.com/image/35944_w800_h600_2.jpg", false),
                new Beacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "1", "48"), "INTERNATIONAL 4300", "DOUBLE BUNK SLEEPER, DUAL 100 GAL FUEL TANKS, STATIONARY 5 TH WHEEL, ENGINE-TRANSMISSION-REAREND TEMP GAUGES, INVERTER, APU. Disclaimer:Price listed does not expire. Prices do not include additional fees and costs of closing, including government fees and taxes, any finance charges, any dealer documentation fees, any emissions testing fees or other fees. All prices, specifications and availability subject to change without notice. Contact dealer for most current information. Plus sales tax, title and license (if applicable). A documentary service fee up to $150 may be added. The documentary service fee is a negotiable fee. Please Call or email for complete, specific vehicle information. ", "http://www.itrucksale.com/image/17131_w800_h600_2.jpg", false)

        );
    }

    public Observable<Beacon> loadNewBeacons() {
        return networkHelper.getBeacon(new Beacon.BeaconIds("ebefd083-70a2-47c8-9837-e7b5634df524", "12", "42"));
    }
}

