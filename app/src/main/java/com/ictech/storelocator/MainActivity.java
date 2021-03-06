package com.ictech.storelocator;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.ictech.storelocator.loginfragment.Form_fragment;

public class MainActivity extends Activity {

    private static final String SESSTAG = "session";
    private static final String CURRENTITEM = "currentitem";
    private static final String GMAPSFRAG = "googlemapsfragment";
    private static final String ELENCO = "fragment_RecycleView";
    private static final String PROFILO = "profilo utente";
    private FragmentManager fragmentManager = getFragmentManager();
    private GoogleFragment googleFragment;
    private StoreList storeList;
    private UserAccount userAccount;
    private String session;
    private String dataLogin;
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SESSTAG, session);
        outState.putInt(CURRENTITEM, bottomNavigation.getCurrentItem());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            session = bundle.getString(SESSTAG);
            dataLogin = bundle.getString(Form_fragment.DATALOGIN);
        }

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem home = new AHBottomNavigationItem("List", R.drawable.list);
        AHBottomNavigationItem map = new AHBottomNavigationItem("Map", R.drawable.compass);
        AHBottomNavigationItem user = new AHBottomNavigationItem("Profile", R.drawable.user);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.addItem(home);
        bottomNavigation.addItem(map);
        bottomNavigation.addItem(user);



        googleFragment = GoogleFragment.newInstance(session);
        storeList = StoreList.newInstance(session);
        userAccount = UserAccount.newInstance(session, dataLogin);
        //Bundle bundle = new Bundle();
        //bundle.putString(SESSTAG, session);
//        storeList.setArguments(bundle);
//        googleFragment.setArguments(bundle);
//        userAccount.setArguments(bundle);

        if(savedInstanceState != null) {
            session = savedInstanceState.getString(SESSTAG);
            bottomNavigation.setCurrentItem(savedInstanceState.getInt(CURRENTITEM));
        }else{
            fragmentManager.beginTransaction()
                    .add(R.id.frame, storeList, ELENCO)
                    .commit();
            bottomNavigation.setCurrentItem(0);
        }





        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                if (position == 0) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, storeList, ELENCO)
                            .commit();
                }
                if (position == 1) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, googleFragment, GMAPSFRAG)
                            .commit();
                }
                if (position == 2) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, userAccount, PROFILO)
                            .commit();
                }
            }
        });




    }
}
