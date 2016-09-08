package com.enterprises.wayne.yugicards.event;

import com.enterprises.wayne.yugicards.entity.Card;

/**
 * Created by ahmed on 9/8/2016.
 */
public class CardAddedEvent
{
    public Card card;

    public CardAddedEvent(Card card)
    {
        this.card  = card;
    }
}
