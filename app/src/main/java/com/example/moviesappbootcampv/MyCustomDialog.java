package com.example.moviesappbootcampv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class MyCustomDialog extends DialogFragment {
    TextView tvHeading;
    Button btnClose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_my_custom, container, false);

        tvHeading = view.findViewById(R.id.tvHeading);
        btnClose = view.findViewById(R.id.btnClose);
        MainActivity.fragmentManager.beginTransaction()
                .hide(Objects.requireNonNull(MainActivity.fragmentManager.findFragmentById(R.id.listFrag)))
                .commit();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });
        return view;
    }
}
