package com.example.cis385finalproject;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.cis385finalproject.models.CardSearchUtil;
import com.example.cis385finalproject.models.FetchInfo;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FetchInfoTests {
    @Test
    public void cardsInBudget_isCorrect() {
        boolean resultCardsInBudget = FetchInfo.cardsInBudget(5.99, 10.00);
        assertTrue(resultCardsInBudget);
    }

    @Test
    public void cardIsSpell_isCorrect() {
        boolean resultCardsInBudget = FetchInfo.cardIsSpell("Spell Card");
        assertTrue(resultCardsInBudget);
    }

    @Test
    public void cardsLevelInRange_isCorrect() {
        boolean resultCardsInBudget = FetchInfo.cardsLevelInRange(6, 8,5);
        assertTrue(resultCardsInBudget);
    }

}