package com.example.rogerzzzz.compara.common.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.rogerzzzz.compara.CollectionActivity;
import com.example.rogerzzzz.compara.common.data.AbstractDataProvider;
import com.example.rogerzzzz.compara.common.data.CollectionDataProvider;

/**
 * Created by rogerzzzz on 2016/12/6.
 */

public class CollectionDataProviderFragment extends Fragment {
    private AbstractDataProvider     mDataProvider;
    private SharedPreferences        sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("compara", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        setRetainInstance(true);  // keep the mDataProvider instance
        mDataProvider = new CollectionDataProvider(sharedPreferences, (CollectionActivity) getActivity());
    }

    public AbstractDataProvider getDataProvider() {
        return mDataProvider;
    }
}
