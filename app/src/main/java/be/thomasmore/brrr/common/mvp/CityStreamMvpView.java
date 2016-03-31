package be.thomasmore.brrr.common.mvp;


import be.thomasmore.brrr.common.base.MvpView;
import be.thomasmore.brrr.data.model.Beacon;

public interface CityStreamMvpView extends MvpView {
    void addCard(Beacon beacon);

    void showError(String s);

    void stopLoadIndicator();
}
