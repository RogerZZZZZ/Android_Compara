package com.example.rogerzzzz.compara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rogerzzzz.compara.common.data.AbstractDataProvider;
import com.example.rogerzzzz.compara.common.fragment.CollectionDataProviderFragment;
import com.example.rogerzzzz.compara.common.fragment.ItemPinnedMessageDialogFragment;
import com.example.rogerzzzz.compara.fragment.CollectionFragment;
import com.example.rogerzzzz.compara.fragment.ProductListFragment;
import com.example.rogerzzzz.compara.models.Goods;

import java.io.Serializable;

/**
 * Created by rogerzzzz on 2016/12/6.
 */

public class CollectionActivity extends AppCompatActivity implements ItemPinnedMessageDialogFragment.EventListener{
    private static final String FRAGMENT_TAG_DATA_PROVIDER = "data provider";
    private static final String FRAGMENT_LIST_VIEW = "list view";
    private SharedPreferences        sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Collection List");
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new CollectionDataProviderFragment(), FRAGMENT_TAG_DATA_PROVIDER)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CollectionFragment(), FRAGMENT_LIST_VIEW)
                    .commit();
        }

        sharedPreferences = getSharedPreferences("compara", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * This method will be called when a list item is removed
     *
     * @param position The position of the item within data set
     */
    public void onItemRemoved(int position) {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.container),
                "Product has been successfully removed from collection list",
                Snackbar.LENGTH_LONG);


        snackbar.show();
    }

    /**
     * This method will be called when a list item is pinned
     *
     * @param position The position of the item within data set
     */
    public void onItemPinned(int position) {
        String name = getDataProvider().getItem(position).getText();
        Goods goods = new Goods(name, 1);
        Intent intent = new Intent(CollectionActivity.this, ProductListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods", (Serializable) goods);
        intent.putExtras(bundle);
        this.setResult(0, intent);
        finish();
    }

    /**
     * This method will be called when a list item is clicked
     *
     * @param position The position of the item within data set
     */
    public void onItemClicked(int position) {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST_VIEW);
        AbstractDataProvider.Data data = getDataProvider().getItem(position);

        if (data.isPinned()) {
            // unpin if tapped the pinned item
            data.setPinned(false);
            ((ProductListFragment) fragment).notifyItemChanged(position);
        }
    }

    // implements ItemPinnedMessageDialogFragment.EventListener
    @Override
    public void onNotifyItemPinnedDialogDismissed(int itemPosition, boolean ok) {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST_VIEW);

        getDataProvider().getItem(itemPosition).setPinned(ok);
        ((ProductListFragment) fragment).notifyItemChanged(itemPosition);
    }

    public AbstractDataProvider getDataProvider() {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_DATA_PROVIDER);
        return ((CollectionDataProviderFragment) fragment).getDataProvider();
    }
}
