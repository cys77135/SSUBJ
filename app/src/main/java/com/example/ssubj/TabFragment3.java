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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TabFragment3 extends Fragment
{
    private Spinner locationSpinner;
    private String locationSelected = "위치 선택";
    private ListView locationListView;
    private TextView locationInfo;
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
        locationListSelect = (LinearLayout) view.findViewById(R.id.location_btns);

        locationBtnList = (Button) view.findViewById(R.id.location_list);
        locationBtnSelect = (Button) view.findViewById(R.id.location_select);
        locationBtnBack = (Button) view.findViewById(R.id.location_back);

        locationListView = (ListView) view.findViewById(R.id.location_listview);

        locationInfo = (TextView) view.findViewById(R.id.location_info);

        locationSpinner = (Spinner) view.findViewById(R.id.location_spinner);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
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
                        locationInfo.setVisibility(View.INVISIBLE);
                        locationBtnBack.setVisibility(View.VISIBLE);
                        location.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "위치를 선택하세요", Toast.LENGTH_LONG).show();
                    }
                }
                else if (v.getId() == R.id.location_select)
                {
                    if (!locationSelected.equals("위치 선택"))
                    {
                        locationInfo.setText(locationRandom());

                        locationListSelect.setVisibility(View.INVISIBLE);
                        location.setVisibility(View.INVISIBLE);
                        locationBtnBack.setVisibility(View.VISIBLE);
                        locationInfo.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "메뉴를 선택하세요", Toast.LENGTH_LONG).show();
                    }
                }
                else if (v.getId() == R.id.location_back)
                {
                    locationSetting();
                    locationAdapter.notifyDataSetChanged();

                    locationBtnBack.setVisibility(View.INVISIBLE);
                    locationInfo.setVisibility(View.INVISIBLE);
                    location.setVisibility(View.INVISIBLE);
                    locationListSelect.setVisibility(View.VISIBLE);
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

        for (int i = listLocation.size() - 1; i >= 0; i--)
        {
            listLocation.remove(i);
        }

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

    public String locationRandom()
    {
        int locationInt;
        String string;

        locationSetting();

        for (int i = listLocation.size() - 1; i >= 0; i--)
        {
            string = listLocation.get(i);
            if (locationSelected.charAt(0) != (string.charAt(0)))
            {
                listLocation.remove(i);
            }
        }

        while (true)
        {
            locationInt = (int) (Math.random() * 74) + 1;
            for (int i = 0; i < listLocation.size(); i++)
            {
                if (locationInt == Integer.parseInt(listLocation.get(i).substring(3, 5)))
                {
                    return listLocation.get(i).substring(5, listLocation.get(i).length());
                }
            }
        }
    }
}