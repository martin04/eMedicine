package com.diplomska.emed.martin.e_medicine.interfaces;

import com.diplomska.emed.martin.e_medicine.models.Drug;

import java.util.List;

/**
 * Created by Martin on 30-Jun-15.
 */
public interface OnTaskCompleted {
    void onTaskCompleted(List<Drug> drugs);
    void onTaskNotCompleted();
}
