package be.thomasmore.brrr.common.mvp;


import be.thomasmore.brrr.Application;
import be.thomasmore.brrr.R;
import be.thomasmore.brrr.common.base.BasePresenter;
import be.thomasmore.brrr.data.DataManager;
import be.thomasmore.brrr.data.model.Beacon;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CityStreamPresenter extends BasePresenter<CityStreamMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    public CityStreamPresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public void attachView(CityStreamMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadCard() {
        checkViewAttached();
        mSubscription = mDataManager.getDefaultBeacons()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Beacon>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(Application.getAppContext().getString(R.string.city_stream_load_failed));
                    }

                    @Override
                    public void onNext(Beacon beacon) {
                        getMvpView().addCard(beacon);
                    }
                });
    }

    public void loadNewCard() {
        checkViewAttached();
        mSubscription = mDataManager.loadNewBeacons()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Beacon>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().stopLoadIndicator();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(Application.getAppContext().getString(R.string.city_stream_load_failed));
                        getMvpView().stopLoadIndicator();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Beacon beacon) {
                        getMvpView().addCard(beacon);
                    }
                });
    }
}
