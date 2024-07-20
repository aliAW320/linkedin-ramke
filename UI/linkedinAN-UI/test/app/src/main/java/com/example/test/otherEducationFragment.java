package com.example.test;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class otherEducationFragment extends Fragment {

    private TextView editTextNameOfEducationCenter;
    private TextView editTextFieldOfStudy;
    private TextView editTextStartDateOfEducation;
    private TextView editTextEndDateOfEducation;
    private TextView editTextGradeOfEducation;
    private TextView editTextDescriptionOfEducation;
    private TextView editTextOtherDescriptionOf;
    private TextView editTextSkill1;
    private TextView editTextSkill2;
    private TextView editTextSkill3;
    private TextView editTextSkill4;
    private TextView editTextSkill5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_other_education, container, false);

        // Initialize variables by finding the views from the inflated layout
        editTextNameOfEducationCenter = rootView.findViewById(R.id.editTextNameOfEducationCenter);
        editTextFieldOfStudy = rootView.findViewById(R.id.editTextFieldOfStudy);
        editTextStartDateOfEducation = rootView.findViewById(R.id.editTextStartDateOfEducation);
        editTextEndDateOfEducation = rootView.findViewById(R.id.editTextEndDateOfEducation);
        editTextGradeOfEducation = rootView.findViewById(R.id.editTextGradeOfEducation);
        editTextDescriptionOfEducation = rootView.findViewById(R.id.editTextDescriptionOfEducation);
        editTextOtherDescriptionOf = rootView.findViewById(R.id.editTextOtherDescriptionOf);
        editTextSkill1 = rootView.findViewById(R.id.editTextSkill1);
        editTextSkill2 = rootView.findViewById(R.id.editTextSkill2);
        editTextSkill3 = rootView.findViewById(R.id.editTextSkill3);
        editTextSkill4 = rootView.findViewById(R.id.editTextSkill4);
        editTextSkill5 = rootView.findViewById(R.id.editTextSkill5);

        // You can now use these TextView variables as needed in your fragment
        // For example, editTextNameOfEducationCenter.setText("Your Text");

        return rootView;
    }
}
