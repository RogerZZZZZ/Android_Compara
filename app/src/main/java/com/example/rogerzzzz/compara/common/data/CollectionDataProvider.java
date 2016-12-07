package com.example.rogerzzzz.compara.common.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.rogerzzzz.compara.CollectionActivity;
import com.example.rogerzzzz.compara.common.utils.SharedPerferenceUtils;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.example.rogerzzzz.compara.R.id.sum;

/**
 * Created by rogerzzzz on 2016/12/6.
 */

public class CollectionDataProvider extends AbstractDataProvider{
    private List<ConcreteData> mData;
    private ConcreteData       mLastRemovedData;
    private int mLastRemovedPosition = -1;
    private CollectionActivity activity;
    private SharedPreferences        sharedPreferences;
    private SharedPreferences.Editor editor;

    public CollectionDataProvider(SharedPreferences sp, CollectionActivity activity) {
        mData = new LinkedList<>();
        mData = SharedPerferenceUtils.toList(sp);
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("compara", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public AbstractDataProvider.Data getItem(int index) {
        if (index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }

        return mData.get(index);
    }

    @Override
    public int undoLastRemoval() {
        if (mLastRemovedData != null) {
            int insertedPosition;
            if (mLastRemovedPosition >= 0 && mLastRemovedPosition < mData.size()) {
                insertedPosition = mLastRemovedPosition;
            } else {
                insertedPosition = mData.size();
            }

            mData.add(insertedPosition, mLastRemovedData);

            mLastRemovedData = null;
            mLastRemovedPosition = -1;

            return insertedPosition;
        } else {
            return -1;
        }
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        final ConcreteData item = mData.remove(fromPosition);

        mData.add(toPosition, item);
        mLastRemovedPosition = -1;
    }

    @Override
    public void swapItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        Collections.swap(mData, toPosition, fromPosition);
        mLastRemovedPosition = -1;
    }

    @Override
    public void addItem(String text, int sum) {
        int length = mData.size();
        final int viewType = 0;
        final int swipeReaction = RecyclerViewSwipeManager.REACTION_CAN_SWIPE_UP | RecyclerViewSwipeManager.REACTION_CAN_SWIPE_DOWN;
        mData.add(new ConcreteData(sum, viewType, text, swipeReaction));
    }

    @Override
    public void clear() {
        mData.clear();
    }

    @Override
    public void removeItem(int position) {
        //noinspection UnnecessaryLocalVariable

        String target = activity.getDataProvider().getItem(position).getProductName();
        SharedPerferenceUtils.removeItem(sharedPreferences, target);
        final ConcreteData removedItem = mData.remove(position);

        mLastRemovedData = removedItem;
        mLastRemovedPosition = position;
    }

    public static final class ConcreteData extends AbstractDataProvider.Data {

        private final long    mId;
        private final String  mText;
        private final int     mViewType;
        private       boolean mPinned;
        private String productName;

        public ConcreteData(int id, int viewType, String text, int swipeReaction) {
            mId = id;
            mViewType = viewType;
            productName = text;
            mText = makeText(text, swipeReaction);
        }

        private static String makeText(String text, int swipeReaction) {
            final StringBuilder sb = new StringBuilder();

            sb.append(text);

            return sb.toString();
        }

        @Override
        public boolean isSectionHeader() {
            return false;
        }

        @Override
        public int getViewType() {
            return mViewType;
        }

        @Override
        public long getId() {
            return mId;
        }

        @Override
        public String toString() {
            return mText;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public int getNum() {
            return sum;
        }

        @Override
        public String getProductName() {
            return productName;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        @Override
        public void setPinned(boolean pinned) {
            mPinned = pinned;
        }

    }
}
