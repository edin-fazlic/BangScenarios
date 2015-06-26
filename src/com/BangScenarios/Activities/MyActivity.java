package com.BangScenarios.Activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.BangScenarios.R;
import com.BangScenarios.Services.SettingsService;
import com.BangScenarios.Services.TextService;
import com.BangScenarios.Utils.DialogBuilder;
import com.BangScenarios.Utils.SwipeCalculator;

public class MyActivity extends Activity {


    public static final int INVALID_ID = -1;
    private LinearLayout menuLayout;
    private FrameLayout startLayout;

    private LinearLayout mainLayout;
    private LinearLayout futureMainLayout;

    boolean menuOpen = false;

    private TextService textService;
    private SettingsService settingsService;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        initializeApp();
        setUpMenuMargin();
        settingsService = new SettingsService();
        textService = new TextService(this, mainLayout, futureMainLayout, settingsService);
    }

    private void initializeApp() {

        menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
        startLayout = (FrameLayout) findViewById(R.id.startLayout);

        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        futureMainLayout = (LinearLayout) findViewById(R.id.futureMainLayout);

        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAction();
            }
        });

        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBuilder.popExpansionDialog(MyActivity.this);
                toggleMenu(false);
            }
        });

        startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textService.getNext();
                startLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setUpMenuMargin() {
        menuLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) menuLayout.getLayoutParams();
                params.bottomMargin = -menuLayout.getHeight();
                menuLayout.setLayoutParams(params);
                menuLayout.setVisibility(View.VISIBLE);

                ViewTreeObserver obs = menuLayout.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            toggleMenu();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (menuOpen) {
                toggleMenu(false);
                return true;
            } else {
                DialogBuilder.getExitDialog(this);
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * Toggles menu view
     *
     * @param newValue If true, menu will become visible. If false, menu will hide.
     */
    private void toggleMenu(boolean newValue) {
        menuOpen = newValue;
        menuLayout.animate().translationY(menuOpen ? -menuLayout.getHeight() : 0);
    }

    private void toggleMenu() {
        toggleMenu(!menuOpen);
    }

    // The ‘active pointer’ is the one currently moving our object.
    private int activePointerId = INVALID_ID;
    private int activePointerIndex = INVALID_ID;
    private float lastTouchX;
    private float startTouchX;

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent) {
        /*if(activePointerId != INVALID_ID && activePointerId != touchEvent.getPointerId(activePointerIndex)) {
            return false;
        }
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                activePointerIndex = 0;
                activePointerId = touchEvent.findPointerIndex(activePointerIndex);
                startTouchX = touchEvent.getX();
                lastTouchX = startTouchX;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float x = touchEvent.getX();
                final float dx = x - lastTouchX;

                mainLayout.setX(x - startTouchX);
                lastTouchX = x;
                break;
            }
            case MotionEvent.ACTION_UP: {
                activePointerId = INVALID_ID;
                activePointerIndex = INVALID_ID;

                lastTouchX = touchEvent.getX();
                if (startTouchX + 80 < lastTouchX) {
                    textService.getPrevious();
                } else if (startTouchX - 80 > lastTouchX) {
                    textService.getNext();
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                activePointerId = INVALID_ID;
                activePointerIndex = INVALID_ID;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                lastTouchX = touchEvent.getX();
                break;
            }
        }
        return true;*/


        switch (SwipeCalculator.getSwipeMotion(touchEvent, 80)) {
            case LEFT:
                textService.getPrevious();
                break;
            case RIGHT:
                textService.getNext();
                break;
        }
        return false;
    }

    public void resetAction() {
        textService.resetAll();
        toggleMenu(false);
        startLayout.setVisibility(View.VISIBLE);
    }

    public void toggleFistful(boolean newValue) {
        settingsService.setFistfulIncluded(newValue);
    }

    public boolean isFistfulIncluded() {
        return settingsService.isFistfulIncluded();
    }
}
