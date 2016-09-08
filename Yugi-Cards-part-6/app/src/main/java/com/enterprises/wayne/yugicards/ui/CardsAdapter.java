package com.enterprises.wayne.yugicards.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enterprises.wayne.yugicards.R;
import com.enterprises.wayne.yugicards.entity.Card;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 8/23/2016.
 */
public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>
{
    private Context mContext;
    List<Card> mCardsList;
    private Listener mListener;

    public CardsAdapter(Context context)
    {
        this.mContext = context;
        this.mCardsList = new ArrayList<>();
    }

    public void setData(List<Card> data)
    {
        mCardsList.clear();
        mCardsList.addAll(data);
        notifyDataSetChanged();
    }

    public void setListenr(Listener listenr)
    {
        this.mListener = listenr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.row_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Card card = mCardsList.get(position);
        Picasso
                .with(mContext)
                .load(card.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageViewCard);
    }

    @Override
    public int getItemCount()
    {
        return mCardsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ScaledImageView imageViewCard;

        public ViewHolder(View itemView)
        {
            super(itemView);

            // reference views
            imageViewCard = (ScaledImageView) itemView.findViewById(R.id.image_view_card);

            // add listener
            imageViewCard.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (mListener != null)
                        mListener.onCardClicked(mCardsList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface Listener
    {
        void onCardClicked(Card card);
    }
}
