package com.example.rogerzzzz.compara.common.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.rogerzzzz.compara.common.data.AbstractDataProvider;
import com.example.rogerzzzz.compara.common.data.ExampleDataProvider;

public class ExampleDataProviderFragment extends Fragment {
    private AbstractDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);  // keep the mDataProvider instance
        mDataProvider = new ExampleDataProvider();
    }

    public AbstractDataProvider getDataProvider() {
        return mDataProvider;
    }
}
