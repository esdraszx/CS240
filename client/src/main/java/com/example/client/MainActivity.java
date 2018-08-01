package com.example.client;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import layout.LoginRegisterFragment;
import layout.MyMapFragment;

public class MainActivity extends AppCompatActivity {

    String maintsBirthID = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Iconify.with(new FontAwesomeModule());

        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFrag = fm.findFragmentById(R.id.firstLayout);
        Fragment mapFrag = fm.findFragmentById(R.id.firstLayout);

        if (!ClientModel.getInstance().isLoggedIn()){
            if (loginFrag == null) {
                loginFrag = LoginRegisterFragment.newInstance(this);
                fm.beginTransaction()
                        .add(R.id.firstLayout, loginFrag)
                        .commit();
            }
        } else {
            if(mapFrag==null) {
                mapFrag=new MyMapFragment();
                fm.beginTransaction()
                        .replace(R.id.firstLayout, mapFrag)
                        .commit();
            }
        }
    }

    public void onLoginRegisterSuccess(){
        ClientModel clientModel = ClientModel.getInstance();
        clientModel.setLoggedIn(true);


        String birthIDOfUser = clientModel.getPeopleEventMap().get(clientModel.getUser().getPersonID()).get(0).getEventID();

        maintsBirthID = birthIDOfUser;

        FragmentManager fm = getSupportFragmentManager();
        MyMapFragment mapFrag = (MyMapFragment) fm.findFragmentById(R.id.myMapFragmentDifferent);


        if(mapFrag==null) {
            mapFrag=new MyMapFragment();
            mapFrag.setEventId(birthIDOfUser);
            mapFrag.setContext(this);
            fm.beginTransaction()
                    .replace(R.id.firstLayout, mapFrag)
                    .commit();


        }
    }

}
