package com.wellthy.www;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.wellthy.www.adapterdelegates.model.DisplayableItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jimitpatel on 22/03/17.
 */

public class PojoRealmClass extends RealmObject implements DisplayableItem {

    @PrimaryKey @SerializedName("name") public String userId;
    @SerializedName("mobileNumber") public String mobile;

    public PojoRealmClass() {
    }

    public static void writeToRealm(@NonNull final List<PojoRealmClass> list) {
        ApplicationLevelClass.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ApplicationLevelClass.getInstance().getRealm().deleteAll();

                boolean isInTransaction = false;
                if (!ApplicationLevelClass.getInstance().getRealm().isInTransaction() ||
                        ApplicationLevelClass.getInstance().getRealm().isClosed()) {
                    ApplicationLevelClass.getInstance().getRealm().beginTransaction();
                    isInTransaction = true;
                }

                ApplicationLevelClass.getInstance().getRealm().copyToRealm(list);

                if (isInTransaction)
                    ApplicationLevelClass.getInstance().getRealm().commitTransaction();
            }
        });
    }

    public static List<PojoRealmClass> getListFromRealm() {
        return ApplicationLevelClass.getInstance().getRealm().where(PojoRealmClass.class).findAll();
    }
}
