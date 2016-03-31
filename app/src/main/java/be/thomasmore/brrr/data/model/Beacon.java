package be.thomasmore.brrr.data.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Beacon {
    private BeaconIds ids;
    private String pictureUrl;
    private String title = "";
    private String mainText = "";
    private String time = "";
    private boolean isLiked = false;

    public Beacon(BeaconIds ids, String title, String mainText, String pictureUrl, boolean isLiked) {
        this.ids = ids;
        this.title = title;
        this.mainText = mainText;
        this.pictureUrl = pictureUrl;
        this.time = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT, Locale.US).format(new Date());
        this.isLiked = isLiked;
    }

    public Beacon(NetworkBeacon networkBeacon) {
        ids = new BeaconIds(networkBeacon.getUuid(), networkBeacon.getMajor(), networkBeacon.getMinor());
        title = networkBeacon.getTitle();
        pictureUrl = networkBeacon.getAbsolutePicture();
        mainText = networkBeacon.getDescription();
//            link = beaconModel.getLink();
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getShowTime() {
        return time;
    }

    public String getMainText() {
        return mainText;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }

    public BeaconIds getIds() {
        return ids;
    }

    public static class BeaconIds {
        private String uuid;
        private String major;
        private String minor;

        public BeaconIds(String uuid, String major, String minor) {
            this.uuid = uuid;
            this.major = major;
            this.minor = minor;
        }

        public String getUuid() {
            return uuid;
        }

        public String getMajor() {
            return major;
        }

        public String getMinor() {
            return minor;
        }
    }
}