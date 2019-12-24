// IFetchUpdatedListCallback.aidl
package com.radio.aidlandroidjava.callbacks;

import com.radio.aidlandroidjava.model.DataModel;

interface IFetchUpdatedListCallback {
    void onListUpdated(in DataModel dataModel);
}
