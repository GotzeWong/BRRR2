package be.thomasmore.brrr.common.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import be.thomasmore.brrr.Application;
import be.thomasmore.brrr.BuildConfig;
import be.thomasmore.brrr.R;
import be.thomasmore.brrr.data.model.Beacon;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityStreamRecyclerAdapter extends RecyclerView.Adapter<CityStreamRecyclerAdapter.ViewHolder> {

    private final LinkedList<Beacon> mItems;

    public CityStreamRecyclerAdapter() {
        this.mItems = new LinkedList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stream_card, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setData(i);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItem(Beacon beacon) {
        this.mItems.addFirst(beacon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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

            private boolean mainTextExpand = false;
            private Beacon beacon;
            private int pos;


        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Toast.makeText(Application.getAppContext(), " click View", Toast.LENGTH_SHORT).show();

                    ExampleFragment fragment = new ExampleFragment();
                    Bundle args = new Bundle();
                    args.putInt(ExampleFragment.ARG_POSITION, pos);
                    fragment.setArguments(args);

                    // Insert the fragment by replacing any existing fragment
                    FragmentTransaction transaction = Application.getFragmentManager().beginTransaction();
                    // Replace whatever is in thefragment_container view with this fragment,
                    // and add the transaction to the backstack
                    transaction.replace(R.id.container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        public void setData(int i) {
            pos = i;
            this.beacon = mItems.get(pos);
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
            actionButton1.setText("BTN1");
            actionButton2.setText("MORE");
            cardHeartButton.setSelected(beacon.isLiked());


            actionButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(Application.getAppContext(), " click more", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @OnClick(R.id.card_like_button)
        public void likeToggle(View button) {
            button.setSelected(!button.isSelected());
            if (button.isSelected()) {
                like();
            } else {
                unlike();
            }
        }

        private void like() {
            beacon.setLiked(true);
        }

        private void unlike() {
            beacon.setLiked(false);
        }

        @OnClick(R.id.card_main_text)
        public void expandMainTextClick(View v) {
            mainTextExpand = !mainTextExpand;
            if (mainTextExpand) {
                ((TextView) v).setSingleLine(false);
            } else {
                ((TextView) v).setLines(2);
            }
        }

    }

}