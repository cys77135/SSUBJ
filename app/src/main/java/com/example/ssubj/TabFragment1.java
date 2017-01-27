package com.example.ssubj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class TabFragment1 extends Fragment
{
    String[] LIST_MENU = {"LIST1", "LIST2", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3"};
    private Spinner menuSpinner;
    private String menuSelect;
    private ListView menuListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_fragment_1, null);

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, LIST_MENU);

        menuListView = (ListView) view.findViewById(R.id.menu_listview);
        menuListView.setAdapter(adapter);

        menuSpinner = (Spinner) view.findViewById(R.id.menu_spinner);
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                menuSelect = menuSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                // TODO : use strText
            }
        });

        return view;
    }
}