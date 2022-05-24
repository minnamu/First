package com.example.first;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class LetterAdapter extends ArrayAdapter<Letters> {
    public LetterAdapter(Context context, int textViewResouceId, List<Letters> objects){
        super(context,textViewResouceId,objects);


    }

}
