package com.sensorsdata.analytics.android.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

/*public*/ class WrapperWindowCallback implements Window.Callback {
    private Window.Callback callback;
    private Activity activity;

    WrapperWindowCallback(Activity activity, Window.Callback callback) {
        this.callback = callback;
        this.activity = activity;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return this.callback.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return this.callback.dispatchKeyShortcutEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        TouchEventHandler.dispatchTouchEvent(activity, event);
        return this.callback.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return this.callback.dispatchTrackballEvent(event);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return this.callback.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return this.callback.dispatchPopulateAccessibilityEvent(event);
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return this.callback.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return this.callback.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return this.callback.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return this.callback.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return this.callback.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        this.callback.onWindowAttributesChanged(attrs);
    }

    @Override
    public void onContentChanged() {
        this.callback.onContentChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        this.callback.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        this.callback.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        this.callback.onDetachedFromWindow();
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        this.callback.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onSearchRequested() {
        return this.callback.onSearchRequested();
    }

    @Override
    @TargetApi(23)
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return this.callback.onSearchRequested(searchEvent);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return this.callback.onWindowStartingActionMode(callback);
    }

    @Nullable
    @TargetApi(23)
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return this.callback.onWindowStartingActionMode(callback, type);
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        this.callback.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        this.callback.onActionModeFinished(mode);
    }
}
