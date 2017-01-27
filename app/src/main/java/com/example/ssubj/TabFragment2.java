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

public class TabFragment2 extends Fragment
{
    private ListView allListView;
    private LinearLayout all, allListSelect;
    private Button allBtnList, allBtnSelect, allBtnBack;
    String[] LIST_ALL = {"ALIST1", "ALIST2", "ALIST3", "ALIST4", "ALIST5", "ALIST6", "ALIST7", "ALIST8", "ALIST9", "ALIST10"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_fragment_2, null);

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, LIST_ALL);

        all = (LinearLayout) view.findViewById(R.id.all);
        allListSelect = (LinearLayout) view.findViewById(R.id.all_btns);

        allBtnList = (Button) view.findViewById(R.id.all_list);
        allBtnSelect = (Button) view.findViewById(R.id.all_select);
        allBtnBack = (Button) view.findViewById(R.id.all_back);

        allListView = (ListView) view.findViewById(R.id.all_listview);
        allListView.setAdapter(adapter);

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
}