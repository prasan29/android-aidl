// IMyAidlInterface.aidl
package com.radio.aidlandroidjava;

import com.radio.aidlandroidjava.model.DataModel;
import com.radio.aidlandroidjava.callbacks.IFetchUpdatedListCallback;

interface IMyAidlInterface {
    String getMessage();
    void setDataModel(in DataModel dataModel);
    void fetchUpdatedList(in DataModel dataModel, in IFetchUpdatedListCallback iFetchUpdatedListCallback);
}
