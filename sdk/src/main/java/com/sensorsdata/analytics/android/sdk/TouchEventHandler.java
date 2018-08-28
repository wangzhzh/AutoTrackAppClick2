package com.sensorsdata.analytics.android.sdk;

import android.app.Activity;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;

/*public*/ class TouchEventHandler {

    @SuppressWarnings("all")
    public static void dispatchTouchEvent(Activity activity, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
            ArrayList<View> targetVies = getTargetViews(rootView, event);
            if (targetVies == null) {
                return;
            }
            for (View view : targetVies) {
                if (view == null) {
                    continue;
                }
                if (view instanceof AdapterView) {
                    if (view instanceof Spinner) {
                        SensorsDataPrivate.trackViewOnClick(view);
                        AdapterView.OnItemSelectedListener onItemSelectedListener =
                                ((Spinner) view).getOnItemSelectedListener();
                        if (onItemSelectedListener != null &&
                                !(onItemSelectedListener instanceof WrapperAdapterViewOnItemSelectedListener)) {
                            ((Spinner) view).setOnItemSelectedListener(
                                    new WrapperAdapterViewOnItemSelectedListener(onItemSelectedListener));
                        }
                    } else if (view instanceof ExpandableListView) {
                        try {
                            Class viewClazz = Class.forName("android.widget.ExpandableListView");
                            //Child
                            Field mOnChildClickListenerField = viewClazz.getDeclaredField("mOnChildClickListener");
                            if (!mOnChildClickListenerField.isAccessible()) {
                                mOnChildClickListenerField.setAccessible(true);
                            }
                            ExpandableListView.OnChildClickListener onChildClickListener =
                                    (ExpandableListView.OnChildClickListener) mOnChildClickListenerField.get(view);
                            if (onChildClickListener != null &&
                                    !(onChildClickListener instanceof WrapperOnChildClickListener)) {
                                ((ExpandableListView) view).setOnChildClickListener(
                                        new WrapperOnChildClickListener(onChildClickListener));
                            }

                            //Group
                            Field mOnGroupClickListenerField = viewClazz.getDeclaredField("mOnGroupClickListener");
                            if (!mOnGroupClickListenerField.isAccessible()) {
                                mOnGroupClickListenerField.setAccessible(true);
                            }
                            ExpandableListView.OnGroupClickListener onGroupClickListener =
                                    (ExpandableListView.OnGroupClickListener) mOnGroupClickListenerField.get(view);
                            if (onGroupClickListener != null &&
                                    !(onGroupClickListener instanceof WrapperOnGroupClickListener)) {
                                ((ExpandableListView) view).setOnGroupClickListener(
                                        new WrapperOnGroupClickListener(onGroupClickListener));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (view instanceof ListView ||
                            view instanceof GridView) {
                        SensorsDataPrivate.trackAdapterViewOnClick((AdapterView) view, event);
                    }
                } else {
                    SensorsDataPrivate.trackViewOnClick(view);
                }
            }
        }
    }

    private static void getTargetViewsInGroup(ViewGroup parent, MotionEvent event, ArrayList<View> hitViews) {
        try {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                ArrayList<View> hitChildren = getTargetViews(child, event);
                if (!hitChildren.isEmpty()) {
                    hitViews.addAll(hitChildren);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    private static ArrayList<View> getTargetViews(View parent, MotionEvent event) {
        ArrayList<View> targetViews = new ArrayList<>();
        try {
            if (isVisible(parent) && isContainView(parent, event)) {
                if (parent instanceof AdapterView) {
                    targetViews.add(parent);
                    getTargetViewsInGroup((ViewGroup) parent, event, targetViews);
                } else if (parent.isClickable() ||
                        parent instanceof SeekBar ||
                        parent instanceof RatingBar) {
                    targetViews.add(parent);
                } else if (parent instanceof ViewGroup) {
                    getTargetViewsInGroup((ViewGroup) parent, event, targetViews);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetViews;
    }

    public static boolean isContainView(View view, MotionEvent event) {
        double x = event.getRawX();
        double y = event.getRawY();
        Rect outRect = new Rect();
        view.getGlobalVisibleRect(outRect);
        return outRect.contains((int) x, (int) y);
    }

}
