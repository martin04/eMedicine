package com.diplomska.emed.martin.e_medicine.interfaces;

import com.diplomska.emed.martin.e_medicine.models.PillModel;

import java.util.List;

/**
 * Created by Martin on 8/28/2015.
 */
public interface onPillIdTaskHandler {
    void onPillIdStarted();
    void onPillIdResult(List<PillModel> pills);
    void onPillIdNoResults();
    void onPillIdError(String errMsg);
}
