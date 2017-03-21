package com.wellthy.www;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jimitpatel on 22/03/17.
 */

public class ApplicationLevelClass extends Application {


    private static ApplicationLevelClass _mInstance;
    public static synchronized ApplicationLevelClass getInstance() {
        return _mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _mInstance = this;
    }

    public Realm getRealm(){
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        return Realm.getInstance(realmConfiguration);
    }
}
