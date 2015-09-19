package com.diplomska.emed.martin.e_medicine.interfaces;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Martin on 9/19/2015.
 */
public interface CacheTaskHandler {
    void onCacheResult(LinkedHashMap<String,List<String>> description);
    void onCacheError();
}
