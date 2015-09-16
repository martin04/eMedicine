package com.diplomska.emed.martin.e_medicine.interfaces;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Martin on 9/16/2015.
 */
public interface InteractionsHandler {
    void onInteractionsResult(HashMap<String, String> contraDrugs);

    void onInteractionsError();
}
