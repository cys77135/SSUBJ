package com.example.ssubj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

public class TabFragment3 extends Fragment
{
    private Spinner locationSpinner;
    private String locationSelected;
    private ListView locationListView;
    private LinearLayout location, locationListSelect;
    private Button locationBtnList, locationBtnSelect, locationBtnBack;
    String[] LIST_LOCATION = {"LLIST1", "LLIST2", "LLIST3", "LLIST4", "LLIST5", "LLIST6", "LLIST7", "LLIST8", "LLIST9", "LLIST10"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_fragment_3, null);

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, LIST_LOCATION);

        location = (LinearLayout) view.findViewById(R.id.location);
        locationListSelect = (LinearLayout) view.findViewById(R.id.location_list_select);

        locationBtnList = (Button) view.findViewById(R.id.location_list);
        locationBtnSelect = (Button) view.findViewById(R.id.location_select);
        locationBtnBack = (Button) view.findViewById(R.id.location_back);

        locationListView = (ListView) view.findViewById(R.id.location_listview);
        locationListView.setAdapter(adapter);

        locationSpinner = (Spinner) view.findViewById(R.id.location_spinner);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                locationSelected = locationSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        View.OnClickListener locationListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v.getId() == R.id.location_list)
                {
                    locationListSelect.setVisibility(View.INVISIBLE);
                    location.setVisibility(View.VISIBLE);
                }
                else if (v.getId() == R.id.location_select)
                {
                }
                else if (v.getId() == R.id.location_back)
                {
                    locationListSelect.setVisibility(View.VISIBLE);
                    location.setVisibility(View.INVISIBLE);
                }
            }
        };

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
            }
        });

        locationBtnList.setOnClickListener(locationListener);
        locationBtnSelect.setOnClickListener(locationListener);
        locationBtnBack.setOnClickListener(locationListener);

        return view;
    }
}