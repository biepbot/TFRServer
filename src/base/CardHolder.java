/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.util.List;

/**
 *
 * @author Rowan
 */
public class CardHolder
{
    public List<Card> cards;

    public List<Card> getCards()
    {
        return cards;
    }

    public void setCards(List<Card> cards)
    {
        this.cards = cards;
    }
    
    public CardHolder()
    {
        
    }

    public CardHolder(List<Card> cards)
    {
        this.cards = cards;
    }
}
