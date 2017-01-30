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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TabFragment2 extends Fragment
{
    private ListView allListView;
    private LinearLayout all, allListSelect;
    private Button allBtnList, allBtnSelect, allBtnBack;
    ArrayList<String> listAll = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_fragment_2, null);

        loadTxt();

        ArrayAdapter<String> allAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listAll);

        all = (LinearLayout) view.findViewById(R.id.all);
        allListSelect = (LinearLayout) view.findViewById(R.id.all_btns);

        allBtnList = (Button) view.findViewById(R.id.all_list);
        allBtnSelect = (Button) view.findViewById(R.id.all_select);
        allBtnBack = (Button) view.findViewById(R.id.all_back);

        allListView = (ListView) view.findViewById(R.id.all_listview);
        allListView.setAdapter(allAdapter);

        View.OnClickListener allListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v.getId() == R.id.all_list)
                {
                    allListSelect.setVisibility(View.INVISIBLE);
                    all.setVisibility(View.VISIBLE);
                }

                else if (v.getId() == R.id.all_select)
                {
                }

                else if (v.getId() == R.id.all_back)
                {
                    allListSelect.setVisibility(View.VISIBLE);
                    all.setVisibility(View.INVISIBLE);
                }
            }
        };

        allListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
            }
        });

        allBtnList.setOnClickListener(allListener);
        allBtnSelect.setOnClickListener(allListener);
        allBtnBack.setOnClickListener(allListener);

        return view;
    }

    public void loadTxt()
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
                    string = string.substring(5, string.length());
                    listAll.add(string);
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
}