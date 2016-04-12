package be.thomasmore.brrr.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;

import be.thomasmore.brrr.Application;
import be.thomasmore.brrr.R;
import be.thomasmore.brrr.common.adapter.TabPagerFragmentAdapter;
import be.thomasmore.brrr.data.model.Beacon;
import be.thomasmore.brrr.utils.DBHelper;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailFragment extends TitledFragment {
    private static final int LAYOUT = R.layout.fragment_detail;
    public static final String ARG_POSITION = "POSITION";

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Bind(R.id.card_title_image)
    ImageView titleImage;
    @Bind(R.id.card_title_text)
    TextView titleText;
    @Bind(R.id.card_time_text)
    TextView timeText;
    @Bind(R.id.card_main_text)
    TextView mainText;
    @Bind(R.id.card_action_button1)
    Button actionButton1;
    @Bind(R.id.card_action_button2)
    Button actionButton2;
    @Bind(R.id.card_like_button)
    ImageButton cardHeartButton;

    Beacon beacon;

    int position;

    private boolean mainTextExpand = false;


    private DBHelper mydb ;

    public static DetailFragment getInstance(Context context, int pos) {
        Bundle args = new Bundle();

        DetailFragment fragment = new DetailFragment();
        fragment.setTitle("ExampleFragment");

        args.putInt(DetailFragment.ARG_POSITION, pos);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(LAYOUT, container, false);
        position = this.getArguments().getInt(ARG_POSITION, 0);

        mydb = new DBHelper(Application.getAppContext());
//        ButterKnife.bind(this, v);

        TextView titleText = ButterKnife.findById(v, R.id.card_title_text);
        TextView timeText = ButterKnife.findById(v, R.id.card_time_text);
        TextView mainText = ButterKnife.findById(v, R.id.card_main_text);

        ImageView titleImage = ButterKnife.findById(v, R.id.card_title_image);

        Button actionButton1 = ButterKnife.findById(v, R.id.card_action_button1);

        Button actionButton2 = ButterKnife.findById(v, R.id.card_action_button2);
        ImageButton cardHeartButton = ButterKnife.findById(v, R.id.card_like_button);

        Bundle args = this.getArguments();
        int pos = args.getInt(ARG_POSITION, 0);

        this.beacon = Application.getBeaconList().get(position);
        Picasso picasso = Picasso.with(Application.getAppContext());
        //            if (BuildConfig.DEBUG) {
        //                picasso.setIndicatorsEnabled(true);
        //            }
        picasso.load(beacon.getPictureUrl())
                .error(R.drawable.no_image)
                .into(titleImage);

        titleText.setText(beacon.getTitle());
        timeText.setText(beacon.getShowTime());
        mainText.setText(beacon.getMainText());
        actionButton1.setText("CALL");
        actionButton2.setText("MORE");

        beacon.setLiked(mydb.get(beacon));
        cardHeartButton.setSelected(beacon.isLiked());

        actionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Application.getAppContext(), " click more", Toast.LENGTH_SHORT).show();
            }
        });

        mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainTextExpand = !mainTextExpand;
                if (mainTextExpand) {
                    ((TextView) v).setSingleLine(false);
                } else {
                    ((TextView) v).setLines(2);
                }
            }
        });

        cardHeartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    like();

                } else {
                    unlike();

                }            }
            });

            return v;
        }

    private void like() {

        beacon.setLiked(true);

        //add to database
        mydb.insert(beacon);
    }

    private void unlike() {

        beacon.setLiked(false);

        // delete record
        mydb.delete(beacon);
    }

}
