package com.diplomska.emed.martin.e_medicine.interfaces;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Martin on 9/17/2015.
 */
public interface OpenFDAHandler {
    void openFdaResult(LinkedHashMap<String,List<String>> fda);

    void openFdaError();
}
