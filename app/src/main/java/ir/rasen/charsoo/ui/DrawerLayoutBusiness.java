package ir.rasen.charsoo.ui;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

import ir.rasen.charsoo.R;
import ir.rasen.charsoo.dialog.PopupEditDeleteBusiness;
import ir.rasen.charsoo.helper.BusinessListItem;
import ir.rasen.charsoo.helper.Image_M;
import ir.rasen.charsoo.helper.LoginInfo;
import ir.rasen.charsoo.interfaces.IDeleteBusiness;
import ir.rasen.charsoo.interfaces.ISelectBusiness;
import ir.rasen.charsoo.webservices.DownloadImages;

/**
 * Created by android on 3/15/2015.
 */
public class DrawerLayoutBusiness implements IDeleteBusiness {

    public static int selectedBusinessId;
    ArrayAdapter<String> adapter;
    ListView listView;
    ArrayList<BusinessListItem> items;
    Context mContext;
    ArrayList<String> businessNamesList;
    IDeleteBusiness iDeleteBusiness;
    TextViewFont textViewIdentifier;

    public DrawerLayoutBusiness() {
        iDeleteBusiness = this;
    }

    public void Initial(final Context context, final DrawerLayout drawerLayout, ArrayList<BusinessListItem> businessListItems, final ISelectBusiness iSelectBusiness) {
        //businessHashTable: <businessIdentifier,businessId>
        items = businessListItems;
        selectedBusinessId = businessListItems.get(0).id;
        mContext = context;
        iDeleteBusiness = this;
        ( drawerLayout.findViewById(R.id.imageView_drawer_edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupEditDeleteBusiness p = new PopupEditDeleteBusiness(context, selectedBusinessId, iDeleteBusiness);
                p.show();
            }
        });

        textViewIdentifier = (TextViewFont) drawerLayout.findViewById(R.id.textView_drawer_identifier);
        textViewIdentifier.setText(businessListItems.get(0).businessIdentifier);

        ( drawerLayout.findViewById(R.id.ll_drawer_new_business)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Open new business page", Toast.LENGTH_LONG).show();
            }
        });

        businessNamesList = new ArrayList<>();
        for (BusinessListItem item : businessListItems) {
            businessNamesList.add(item.businessIdentifier);
        }
        listView = (ListView) drawerLayout.findViewById(R.id.listView_drawer);
        adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, businessNamesList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                iSelectBusiness.notifySelectBusiness(items.get(i).id);
                selectedBusinessId = items.get(i).id;
                drawerLayout.closeDrawer(Gravity.RIGHT);
                ((TextViewFont) drawerLayout.findViewById(R.id.textView_drawer_identifier)).setText(items.get(i).businessIdentifier);
            }
        });
    }


    @Override
    public void notifyDeleteBusiness(int businessId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).id == businessId) {
                items.remove(i);
                businessNamesList.remove(i);
            }
        }
        adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, android.R.id.text1, businessNamesList);
        listView.setAdapter(adapter);
        textViewIdentifier.setText(businessNamesList.get(0));
        Toast.makeText(mContext,"Get another business home info:"+items.get(0).id,Toast.LENGTH_LONG).show();
    }
}
