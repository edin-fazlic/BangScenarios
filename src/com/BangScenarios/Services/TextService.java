package com.BangScenarios.Services;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.BangScenarios.R;
import com.BangScenarios.Utils.Animations;

import java.util.ArrayList;
import java.util.List;

import static com.BangScenarios.Utils.Constants.SCENARIO_CARDS_FISTFUL_OF_CARDS;
import static com.BangScenarios.Utils.Constants.SCENARIO_CARDS_HIGH_NOON;

/**
 * Created by Edin on 21.6.2015.
 */
public class TextService {

    private final Activity activity;
    private LinearLayout layout1;
    private LinearLayout layout2;

    private TextView text1;
    private TextView title1;
    private TextView text2;
    private TextView title2;

    private List<Integer> unselectedRandomNumbers;
    private List<Integer> selectedRandomNumbers;

    private int index;
    private int screenWidth;

    private String[] scenarioCards;

    private SettingsService settingsService;

    public TextService(Activity activity, LinearLayout layout1, LinearLayout layout2, SettingsService settingsService) {
        this.activity = activity;
        this.layout1 = layout1;
        this.layout2 = layout2;
        this.settingsService = settingsService;

        initViews();
        setFonts();
        initScreenWidth();
        resetAll();
    }

    //<editor-fold desc="Init">
    private void initViews() {
        text1 = (TextView) activity.findViewById(R.id.textDescription);
        title1 = (TextView) activity.findViewById(R.id.textDescriptionTitle);
        text2 = (TextView) activity.findViewById(R.id.futureTextDescription);
        title2 = (TextView) activity.findViewById(R.id.futureTextDescriptionTitle);
    }

    private void setFonts() {
        Typeface face = Typeface.createFromAsset(activity.getAssets(), "fonts/Perdido.ttf");
        title1.setTypeface(face);
        title2.setTypeface(face);
        face = Typeface.createFromAsset(activity.getAssets(), "fonts/BookAntiqua.ttf");
        text1.setTypeface(face);
        text2.setTypeface(face);
    }

    private void initScreenWidth() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    public void resetAll() {
        populateScenarioCards();
        generateUnselectedNumbersList();
        index = -1;
        selectedRandomNumbers = new ArrayList<>(scenarioCards.length);
        text1.setText("");
        title1.setText("");
        text2.setText("");
        title2.setText("");
    }

    private void populateScenarioCards() {
        int size = SCENARIO_CARDS_HIGH_NOON.length;
        if(settingsService.isFistfulIncluded()) {
            size += SCENARIO_CARDS_FISTFUL_OF_CARDS.length;
        }
        scenarioCards = new String[size];
        int i = 0;
        for(; i < SCENARIO_CARDS_HIGH_NOON.length; i++) {
            scenarioCards[i] = SCENARIO_CARDS_HIGH_NOON[i];
        }
        if(settingsService.isFistfulIncluded()) {
            System.arraycopy(SCENARIO_CARDS_FISTFUL_OF_CARDS, 0, scenarioCards, i, SCENARIO_CARDS_FISTFUL_OF_CARDS.length);
        }
    }

    public void generateUnselectedNumbersList() {
        unselectedRandomNumbers = new ArrayList<>(scenarioCards.length);
        for (int i = 0; i < scenarioCards.length; i++) {
            unselectedRandomNumbers.add(i);
        }
    }
    //</editor-fold>

    public void generateNextRandomNumber() {
        int random = (int) (Math.random() * unselectedRandomNumbers.size());
        selectedRandomNumbers.add(unselectedRandomNumbers.remove(random));
    }

    private boolean nextIsTwo = true;

    public void animateSwipeLeft() {
        LinearLayout nextLayout;
        LinearLayout currentLayout;
        TextView nextTitle;
        TextView nextText;
        if(nextIsTwo) {
            nextLayout = layout2;
            currentLayout = layout1;
            nextText = text2;
            nextTitle = title2;
        } else {
            currentLayout = layout2;
            nextLayout = layout1;
            nextText = text1;
            nextTitle = title1;
        }

        nextIsTwo = !nextIsTwo;

        setText(nextTitle, nextText);

        currentLayout.startAnimation(Animations.getToLeft(screenWidth));
        nextLayout.startAnimation(Animations.getFromRight(screenWidth));
    }

    public void animateSwipeRight() {
        LinearLayout nextLayout;
        final LinearLayout currentLayout;
        TextView nextTitle;
        TextView nextText;
        if(nextIsTwo) {
            nextLayout = layout2;
            currentLayout = layout1;
            nextText = text2;
            nextTitle = title2;
        } else {
            currentLayout = layout2;
            nextLayout = layout1;
            nextText = text1;
            nextTitle = title1;
        }

        nextIsTwo = !nextIsTwo;

        setText(nextTitle, nextText);

        currentLayout.startAnimation(Animations.getToRight(screenWidth));
        nextLayout.startAnimation(Animations.getFromLeft(screenWidth));
    }

    private void setText(TextView nextTitle, TextView nextText) {
        String description = scenarioCards[selectedRandomNumbers.get(index)];
        nextTitle.setText(description.substring(0, description.indexOf(":")).toUpperCase());
        nextText.setText(description.substring(description.indexOf(":") + 2));
    }

    public void getPrevious() {
        if (index > 0) {
            index--;
            animateSwipeRight();
        }
    }

    public void getNext() {
        if (index < scenarioCards.length - 1) {
            index++;
            if (index >= selectedRandomNumbers.size()) {
                generateNextRandomNumber();
            }
            animateSwipeLeft();
        }
    }
}
