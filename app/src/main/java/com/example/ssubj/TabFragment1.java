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

public class TabFragment1 extends Fragment
{
    private Spinner menuSpinner;
    private String menuSelected = "메뉴 선택";
    private ListView menuListView;
    private LinearLayout menu, menuListSelect;
    private Button menuBtnList, menuBtnSelect, menuBtnBack;
    ArrayList<String> listMenu = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_fragment_1, null);
        menuSetting();

        final ArrayAdapter<String> menuAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listMenu);

        menu = (LinearLayout) view.findViewById(R.id.menu);
        menuListSelect = (LinearLayout) view.findViewById(R.id.menu_btns);

        menuBtnList = (Button) view.findViewById(R.id.menu_list);
        menuBtnSelect = (Button) view.findViewById(R.id.menu_select);
        menuBtnBack = (Button) view.findViewById(R.id.menu_back);

        menuListView = (ListView) view.findViewById(R.id.menu_listview);

        menuSpinner = (Spinner) view.findViewById(R.id.menu_spinner);

        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (!menuSelected.equals(menuSpinner.getSelectedItem().toString()))
                {
                    for (int i = listMenu.size() - 1; i >= 0; i--)
                    {
                        listMenu.remove(i);
                    }
                    menuSetting();
                    menuSelected = menuSpinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        View.OnClickListener menuListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v.getId() == R.id.menu_list)
                {
                    if (!menuSelected.equals("메뉴 선택"))
                    {
                        menuChange(menuSelected.substring(0, 2));
                        menuAdapter.notifyDataSetChanged();
                        menuListSelect.setVisibility(View.INVISIBLE);
                        menu.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "메뉴를 선택하세요", Toast.LENGTH_LONG).show();
                    }
                }
                else if (v.getId() == R.id.menu_select)
                {
                }
                else if (v.getId() == R.id.menu_back)
                {
                    menuListSelect.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.INVISIBLE);
                }
            }
        };

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
            }
        });

        menuBtnList.setOnClickListener(menuListener);
        menuBtnSelect.setOnClickListener(menuListener);
        menuBtnBack.setOnClickListener(menuListener);

        menuListView.setAdapter(menuAdapter);

        return view;
    }

    public void menuSetting()
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
                    listMenu.add(string);
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

    public void menuChange(String str)
    {
        String string;
        for (int i = listMenu.size() - 1; i >= 0; i--)
        {
            string = listMenu.get(i);
            if (str.equals(string.substring(1, 3)))
            {
                listMenu.set(i, string.substring(5, string.length()));
            }
            else
            {
                listMenu.remove(i);
            }
        }
    }
}