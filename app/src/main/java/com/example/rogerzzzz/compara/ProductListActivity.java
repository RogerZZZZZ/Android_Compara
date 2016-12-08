package com.example.rogerzzzz.compara;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.rogerzzzz.compara.common.data.AbstractDataProvider;
import com.example.rogerzzzz.compara.common.fragment.ExampleDataProviderFragment;
import com.example.rogerzzzz.compara.common.fragment.ItemPinnedMessageDialogFragment;
import com.example.rogerzzzz.compara.common.utils.SharedPerferenceUtils;
import com.example.rogerzzzz.compara.fragment.ProductListFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ItemPinnedMessageDialogFragment.EventListener, View.OnClickListener {
    private static final String FRAGMENT_TAG_DATA_PROVIDER = "data provider";
    private static final String FRAGMENT_LIST_VIEW = "list view";
    private SharedPreferences        sharedPreferences;
    private SharedPreferences.Editor editor;

    private Button findCheapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        findCheapBtn = (Button) findViewById(R.id.findCheap);
        findCheapBtn.setOnClickListener(this);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, ItemEditActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new ExampleDataProviderFragment(), FRAGMENT_TAG_DATA_PROVIDER)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ProductListFragment(), FRAGMENT_LIST_VIEW)
                    .commit();
        }

        sharedPreferences = getSharedPreferences("compara", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getDataProvider().getCount() == 0){
            Snackbar.make(getWindow().getDecorView(), "There is no products in the cart", Snackbar.LENGTH_LONG).show();
        }else{
            findCheapBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_collection:
                Intent intent = new Intent(ProductListActivity.this, CollectionActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.action_unit:
                Intent intent1 = new Intent(ProductListActivity.this, UnitPriceActivity.class);
                startActivity(intent1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0 && data != null){
            getSupportFragmentManager().findFragmentByTag("list view").onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * This method will be called when a list item is removed
     *
     * @param position The position of the item within data set
     */
    public void onItemRemoved(int position) {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.container),
                R.string.snack_bar_text_item_removed,
                Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.snack_bar_action_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemUndoActionClicked();
            }
        });
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.snackbar_action_color_done));
        snackbar.show();
    }

    /**
     * This method will be called when a list item is pinned
     *
     * @param position The position of the item within data set
     */
    public void onItemPinned(int position) {
        String productName = getDataProvider().getItem(position).getProductName();
        if(SharedPerferenceUtils.containValue(sharedPreferences, productName)){
            Snackbar.make(getWindow().getDecorView(), "Have already put into collection list", Snackbar.LENGTH_LONG).show();
        }else{
            SharedPerferenceUtils.saveItem(sharedPreferences, productName);
            Snackbar.make(getWindow().getDecorView(), "Successfully put it into collection list", Snackbar.LENGTH_LONG).show();
        }
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

    private void onItemUndoActionClicked() {
        int position = getDataProvider().undoLastRemoval();
        if (position >= 0) {
            final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST_VIEW);
            ((ProductListFragment) fragment).notifyItemInserted(position);
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
        return ((ExampleDataProviderFragment) fragment).getDataProvider();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.findCheap:
                Intent intent = new Intent(ProductListActivity.this, SupermarketActivity.class);
                List<String> productList = new ArrayList<>();
                int listSize = getDataProvider().getCount();
                for(int i = 0; i < listSize; i++){
                    productList.add(getDataProvider().getItem(i).getProductName());
                }
                intent.putExtra("product_list", (Serializable) productList);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
