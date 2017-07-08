package com.example.ssubj;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
    private LinearLayout location, locationListSelect,tab;
    private Button locationBtnList, locationBtnSelect, locationBtnBack, locationBtnListBack;
    private ImageButton locationBtnReplay;
    private InputMethodManager im;
    ArrayList<String> listLocation = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_fragment_3, null);
        locationSetting();
        final ArrayAdapter<String> locationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listLocation);

        im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        tab=(LinearLayout) view.findViewById(R.id.tab);
        location = (LinearLayout) view.findViewById(R.id.location);
        locationListSelect = (LinearLayout) view.findViewById(R.id.location_btns);
        locationBtnList = (Button) view.findViewById(R.id.location_list);
        locationBtnSelect = (Button) view.findViewById(R.id.location_select);
        locationBtnBack = (Button) view.findViewById(R.id.location_back);
        locationBtnListBack = (Button) view.findViewById(R.id.location_list_back);
        locationBtnReplay = (ImageButton) view.findViewById(R.id.location_replay);
        locationListView = (ListView) view.findViewById(R.id.location_listview);
        locationInfo = (TextView) view.findViewById(R.id.location_info);
        locationSpinner = (Spinner) view.findViewById(R.id.location_spinner);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                MainActivity.editSearch.setVisibility(View.INVISIBLE);
                MainActivity.toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(locationSpinner.getWindowToken(), 0);
                locationSetting();
                locationSelected = locationSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                MainActivity.editSearch.setVisibility(View.INVISIBLE);
                MainActivity.toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(locationSpinner.getWindowToken(), 0);
            }
        });

        View.OnClickListener locationListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.editSearch.setVisibility(View.INVISIBLE);
                MainActivity.toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                if (v.getId() == R.id.location_list)
                {
                    if (!locationSelected.equals("위치 선택"))
                    {
                        locationChange(locationSelected.charAt(0));
                        locationAdapter.notifyDataSetChanged();

                        locationListSelect.setVisibility(View.INVISIBLE);
                        locationInfo.setVisibility(View.INVISIBLE);
                        locationBtnReplay.setVisibility(View.INVISIBLE);
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
                        locationBtnReplay.setVisibility(View.VISIBLE);
                        locationInfo.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "위치를 선택하세요", Toast.LENGTH_LONG).show();
                    }
                }

                else if (v.getId() == R.id.location_back)
                {
                    locationSetting();
                    locationAdapter.notifyDataSetChanged();

                    locationBtnBack.setVisibility(View.INVISIBLE);
                    locationInfo.setVisibility(View.INVISIBLE);
                    location.setVisibility(View.INVISIBLE);
                    locationBtnReplay.setVisibility(View.INVISIBLE);
                    locationListSelect.setVisibility(View.VISIBLE);
                }

                else if (v.getId() == R.id.location_replay)
                {
                    locationInfo.setText(locationRandom());

                    locationListSelect.setVisibility(View.INVISIBLE);
                    location.setVisibility(View.INVISIBLE);
                    locationBtnBack.setVisibility(View.VISIBLE);
                    locationBtnReplay.setVisibility(View.VISIBLE);
                    locationInfo.setVisibility(View.VISIBLE);
                }

                else if (v.getId() == R.id.location_list_back)
                {
                    locationChange(locationSelected.charAt(0));
                    locationAdapter.notifyDataSetChanged();

                    locationListSelect.setVisibility(View.INVISIBLE);
                    locationInfo.setVisibility(View.INVISIBLE);
                    locationBtnReplay.setVisibility(View.INVISIBLE);
                    locationBtnListBack.setVisibility(View.INVISIBLE);
                    locationBtnBack.setVisibility(View.VISIBLE);
                    location.setVisibility(View.VISIBLE);
                }
            }
        };

        tab.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                MainActivity.editSearch.setVisibility(View.INVISIBLE);
                MainActivity.toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                return false;
            }
        });

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                im.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                String string = (String) parent.getItemAtPosition(position);
                locationSetting();
                for (int i = 0; i < listLocation.size(); i++)
                {
                    if (listLocation.get(i).substring(5, listLocation.get(i).length()).equals(string))
                    {
                        string = listLocation.get(i).substring(5, listLocation.get(i).length()) + "\n\n";
                        string += "메뉴 : " + locationText(listLocation.get(i).substring(1, 3)) + "\n\n";
                        string += "위치 : " + locationSelected.substring(3, locationSelected.length());
                        break;
                    }
                }
                locationInfo.setText(string);

                locationListSelect.setVisibility(View.INVISIBLE);
                location.setVisibility(View.INVISIBLE);
                locationBtnReplay.setVisibility(View.INVISIBLE);
                locationBtnBack.setVisibility(View.VISIBLE);
                locationInfo.setVisibility(View.VISIBLE);
                locationBtnListBack.setVisibility(View.VISIBLE);
            }
        });

        locationBtnList.setOnClickListener(locationListener);
        locationBtnSelect.setOnClickListener(locationListener);
        locationBtnBack.setOnClickListener(locationListener);
        locationBtnReplay.setOnClickListener(locationListener);
        locationBtnListBack.setOnClickListener(locationListener);
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
                    string = listLocation.get(i).substring(5, listLocation.get(i).length()) + "\n\n";
                    string += "메뉴 : " + locationText(listLocation.get(i).substring(1, 3)) + "\n\n";
                    string += "위치 : " + locationSelected.substring(3, locationSelected.length());

                    return string;
                }
            }
        }
    }

    public String locationText(String str)
    {
        InputStream inputData = getResources().openRawResource(R.raw.ssubjmlist);

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputData, "EUC_KR"));
            while (true)
            {
                String string = bufferedReader.readLine();

                if (string != null)
                {
                    if (str.equals(string.substring(0, 2)))
                    {
                        return string.substring(2, string.length());
                    }
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

        return null;
    }
}