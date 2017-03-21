package com.wellthy.www;

import com.google.gson.annotations.SerializedName;
import com.wellthy.www.adapterdelegates.model.DisplayableItem;

/**
 * Created by jimitpatel on 22/03/17.
 */

public class PojoSqliteClass implements DisplayableItem {

    @SerializedName("name") public String userId;
    @SerializedName("mobileNumber") public String mobile;

    public PojoSqliteClass() {
    }
}
