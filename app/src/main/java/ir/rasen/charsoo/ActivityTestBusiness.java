package ir.rasen.charsoo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ir.rasen.charsoo.helper.BusinessListItem;
import ir.rasen.charsoo.helper.TestUnit;
import ir.rasen.charsoo.interfaces.ISelectBusiness;
import ir.rasen.charsoo.ui.DrawerLayoutBusiness;
import ir.rasen.charsoo.ui.DrawerLayoutUser;
import ir.rasen.charsoo.ui.GridViewBusiness;
import ir.rasen.charsoo.ui.GridViewHeader;
import ir.rasen.charsoo.ui.GridViewUser;


public class ActivityTestBusiness extends Activity implements ISelectBusiness {

    private DrawerLayout mDrawerLayout;


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        DrawerLayoutBusiness drawerLayoutBusiness = new DrawerLayoutBusiness();
        drawerLayoutBusiness.Initial(this, mDrawerLayout,TestUnit.getBusinessListItems(),this);
        final GridViewHeader gridView = (GridViewHeader) findViewById(R.id.gridView);
        GridViewBusiness.InitialGridViewBusiness(this,2022,TestUnit.getPostAdapterSharedListItems(),gridView,mDrawerLayout);
    }


    @Override
    public void notifySelectBusiness(int businessId) {
        Toast.makeText(ActivityTestBusiness.this,"Get selected business info:"+businessId,Toast.LENGTH_LONG).show();
    }
}
