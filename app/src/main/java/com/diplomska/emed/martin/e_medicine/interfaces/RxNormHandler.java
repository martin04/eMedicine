package com.diplomska.emed.martin.e_medicine.interfaces;

import java.util.List;

/**
 * Created by Martin on 9/16/2015.
 */
public interface RxNormHandler {
    void onRxNormStarted();
    void onRxNormResult(List<String> rxcui);
    void onRxNormError();
}
