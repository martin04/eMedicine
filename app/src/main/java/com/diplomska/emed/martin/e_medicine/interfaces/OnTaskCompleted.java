package com.diplomska.emed.martin.e_medicine.interfaces;

import com.diplomska.emed.martin.e_medicine.models.Drug;

import java.util.List;

/**
 * Created by Martin on 30-Jun-15.
 */
public interface OnTaskCompleted {

    //Function for signaling that the task has been completed.
    void onTaskCompleted(List<Drug> drugs);

    //Function for signaling that an error occured during database loading.
    void onTaskNotCompleted();
}
