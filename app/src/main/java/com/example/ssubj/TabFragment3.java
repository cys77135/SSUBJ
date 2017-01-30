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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TabFragment3 extends Fragment
{
    private Spinner locationSpinner;
    private String locationSelected;
    private ListView locationListView;
    private LinearLayout location, locationListSelect;
    private Button locationBtnList, locationBtnSelect, locationBtnBack;
    ArrayList<String> listLocation = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_fragment_3, null);
        locationSetting();

        final ArrayAdapter<String> locationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listLocation);

        location = (LinearLayout) view.findViewById(R.id.location);
        locationListSelect = (LinearLayout) view.findViewById(R.id.location_list_select);

        locationBtnList = (Button) view.findViewById(R.id.location_list);
        locationBtnSelect = (Button) view.findViewById(R.id.location_select);
        locationBtnBack = (Button) view.findViewById(R.id.location_back);

        locationListView = (ListView) view.findViewById(R.id.location_listview);

        locationSpinner = (Spinner) view.findViewById(R.id.location_spinner);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                for (int i = listLocation.size() - 1; i >= 0; i--)
                {
                    listLocation.remove(i);
                }
                locationSetting();
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
                    if (!locationSelected.equals("위치 선택"))
                    {
                        locationChange(locationSelected.charAt(0));
                        locationAdapter.notifyDataSetChanged();
                        locationListSelect.setVisibility(View.INVISIBLE);
                        location.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "위치를 선택하세요", Toast.LENGTH_LONG).show();
                    }
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

        locationListView.setAdapter(locationAdapter);

        return view;
    }

    public void locationSetting()
    {
        InputStream inputData = getResources().openRawResource(R.raw.ssubjlist);

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputData, "EUC_KR"));
            while (true)
            {
                String string = bufferedReader.readLine();

                if (string != null)
                {
                    listLocation.add(string);
                }
                else
                {
                    break;
                }
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void locationChange(char ch)
    {
        String string;
        for (int i = listLocation.size() - 1; i >= 0; i--)
        {
            string = listLocation.get(i);
            if (string.charAt(0) == ch)
            {
                listLocation.set(i, string.substring(5, string.length()));
            }
            else
            {
                listLocation.remove(i);
            }
        }
    }
}