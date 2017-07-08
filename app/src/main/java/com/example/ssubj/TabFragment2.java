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
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TabFragment2 extends Fragment
{
    private ListView allListView;
    private TextView allInfo;
    private LinearLayout all, allListSelect,tab;
    private Button allBtnList, allBtnSelect, allBtnBack, allBtnListBack;
    private ImageButton allBtnReplay;
    private InputMethodManager im;
    ArrayList<String> listAll = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_fragment_2, null);
        allSetting();
        final ArrayAdapter<String> allAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listAll);

        im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        tab=(LinearLayout) view.findViewById(R.id.tab);
        all = (LinearLayout) view.findViewById(R.id.all);
        allListSelect = (LinearLayout) view.findViewById(R.id.all_btns);
        allBtnList = (Button) view.findViewById(R.id.all_list);
        allBtnSelect = (Button) view.findViewById(R.id.all_select);
        allBtnBack = (Button) view.findViewById(R.id.all_back);
        allBtnListBack = (Button) view.findViewById(R.id.all_list_back);
        allBtnReplay = (ImageButton) view.findViewById(R.id.all_replay);
        allListView = (ListView) view.findViewById(R.id.all_listview);
        allInfo = (TextView) view.findViewById(R.id.all_info);

        View.OnClickListener allListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.editSearch.setVisibility(View.INVISIBLE);
                MainActivity.toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                if (v.getId() == R.id.all_list)
                {
                    allSetting();
                    allChange();
                    allAdapter.notifyDataSetChanged();

                    allListSelect.setVisibility(View.INVISIBLE);
                    allInfo.setVisibility(View.INVISIBLE);
                    allBtnReplay.setVisibility(View.INVISIBLE);
                    allBtnBack.setVisibility(View.VISIBLE);
                    all.setVisibility(View.VISIBLE);
                }

                else if (v.getId() == R.id.all_select)
                {
                    allInfo.setText(allRandom((int) (Math.random() * 74) + 1) + "\n");

                    allListSelect.setVisibility(View.INVISIBLE);
                    all.setVisibility(View.INVISIBLE);
                    allBtnBack.setVisibility(View.VISIBLE);
                    allBtnReplay.setVisibility(View.VISIBLE);
                    allInfo.setVisibility(View.VISIBLE);
                }

                else if (v.getId() == R.id.all_back)
                {
                    allSetting();
                    allChange();
                    allAdapter.notifyDataSetChanged();

                    allBtnBack.setVisibility(View.INVISIBLE);
                    allInfo.setVisibility(View.INVISIBLE);
                    allListSelect.setVisibility(View.VISIBLE);
                    allBtnReplay.setVisibility(View.INVISIBLE);
                    all.setVisibility(View.INVISIBLE);
                }

                else if (v.getId() == R.id.all_replay)
                {
                    allInfo.setText(allRandom((int) (Math.random() * 74) + 1) + "\n");

                    allListSelect.setVisibility(View.INVISIBLE);
                    all.setVisibility(View.INVISIBLE);
                    allBtnBack.setVisibility(View.VISIBLE);
                    allBtnReplay.setVisibility(View.VISIBLE);
                    allInfo.setVisibility(View.VISIBLE);
                }

                if (v.getId() == R.id.all_list_back)
                {
                    allSetting();
                    allChange();
                    allAdapter.notifyDataSetChanged();

                    allListSelect.setVisibility(View.INVISIBLE);
                    allInfo.setVisibility(View.INVISIBLE);
                    allBtnReplay.setVisibility(View.INVISIBLE);
                    allBtnListBack.setVisibility(View.INVISIBLE);
                    allBtnBack.setVisibility(View.VISIBLE);
                    all.setVisibility(View.VISIBLE);
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

        allListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                im.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                String string = (String) parent.getItemAtPosition(position);
                allSetting();
                for (int i = 0; i < listAll.size(); i++)
                {
                    if (listAll.get(i).substring(5, listAll.get(i).length()).equals(string))
                    {
                        string += "\n\n" + allText(listAll.get(i).substring(0, 5));
                        break;
                    }
                }
                allInfo.setText(string);

                allListSelect.setVisibility(View.INVISIBLE);
                all.setVisibility(View.INVISIBLE);
                allBtnReplay.setVisibility(View.INVISIBLE);
                allInfo.setVisibility(View.VISIBLE);
                allBtnBack.setVisibility(View.VISIBLE);
                allBtnListBack.setVisibility(View.VISIBLE);
            }
        });

        allBtnList.setOnClickListener(allListener);
        allBtnSelect.setOnClickListener(allListener);
        allBtnBack.setOnClickListener(allListener);
        allBtnReplay.setOnClickListener(allListener);
        allBtnListBack.setOnClickListener(allListener);
        allListView.setAdapter(allAdapter);

        return view;
    }

    public void allSetting()
    {
        InputStream inputData = getResources().openRawResource(R.raw.ssubjlist);

        for (int i = listAll.size() - 1; i >= 0; i--)
        {
            listAll.remove(i);
        }

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputData, "EUC_KR"));
            while (true)
            {
                String string = bufferedReader.readLine();

                if (string != null)
                {
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

    public void allChange()
    {
        for (int i = 0; i < listAll.size(); i++)
        {
            listAll.set(i, listAll.get(i).substring(5, listAll.get(i).length()));
        }
    }

    public String allRandom(int allInt)
    {
        String string;
        allSetting();

        for (int i = 0; i < listAll.size(); i++)
        {
            if (allInt == Integer.parseInt(listAll.get(i).substring(3, 5)))
            {
                string = listAll.get(i).substring(5, listAll.get(i).length()) + "\n\n";
                string += allText(listAll.get(i).substring(0, 5));

                return string;
            }
        }
        return null;
    }

    public String allText(String str)
    {
        String stringm = null, stringl = null;

        InputStream inputMData = getResources().openRawResource(R.raw.ssubjmlist);
        InputStream inputLData = getResources().openRawResource(R.raw.ssubjllist);

        try
        {
            BufferedReader bufferedMReader = new BufferedReader(new InputStreamReader(inputMData, "EUC_KR"));
            BufferedReader bufferedLReader = new BufferedReader(new InputStreamReader(inputLData, "EUC_KR"));

            while (true)
            {
                String Mstring = bufferedMReader.readLine();

                if (Mstring != null)
                {
                    if (str.substring(1, 3).equals(Mstring.substring(0, 2)))
                    {
                        stringm = "메뉴 : " + Mstring.substring(2, Mstring.length()) + "\n\n";
                        break;
                    }
                }
                else
                {
                    break;
                }
            }

            while (true)
            {
                String Lstring = bufferedLReader.readLine();

                if (Lstring != null)
                {
                    if (str.charAt(0) == Lstring.charAt(0))
                    {
                        stringl = "위치 : " + Lstring.substring(1, Lstring.length());
                        break;
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

        return stringm + stringl;
    }
}