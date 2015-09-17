package com.diplomska.emed.martin.e_medicine.interfaces;

import java.util.LinkedHashMap;

/**
 * Created by Martin on 9/16/2015.
 */
public interface InteractionsHandler {
    void onInteractionsResult(LinkedHashMap<String, String> contraDrugs);

    void onInteractionsError();
}
