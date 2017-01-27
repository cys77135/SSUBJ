package com.example.ssubj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

public class TabFragment1 extends Fragment
{
    private Spinner menuSpinner;

    public class menuSpinnerable extends AppCompatActivity
    {

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.tab_fragment_1);

            menuSpinner = (Spinner) findViewById(R.id.menu_spinner);
            menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.tab_fragment_1, container, false);
    }
}