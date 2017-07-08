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

import static com.example.ssubj.MainActivity.editSearch;

public class TabFragment1 extends Fragment
{
    private Spinner menuSpinner;
    private String menuSelected = "메뉴 선택";
    private ListView menuListView;
    private TextView menuInfo;
    private LinearLayout menu, menuListSelect,tab;
    private Button menuBtnList, menuBtnSelect, menuBtnBack, menuBtnListBack;
    private ImageButton menuBtnReplay;
    private InputMethodManager im;
    ArrayList<String> listMenu = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_fragment_1, null);
        menuSetting();
        final ArrayAdapter<String> menuAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listMenu);

        im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        tab=(LinearLayout) view.findViewById(R.id.tab);
        menu = (LinearLayout) view.findViewById(R.id.menu);
        menuListSelect = (LinearLayout) view.findViewById(R.id.menu_btns);
        menuBtnList = (Button) view.findViewById(R.id.menu_list);
        menuBtnSelect = (Button) view.findViewById(R.id.menu_select);
        menuBtnBack = (Button) view.findViewById(R.id.menu_back);
        menuBtnListBack = (Button) view.findViewById(R.id.menu_list_back);
        menuBtnReplay = (ImageButton) view.findViewById(R.id.menu_replay);
        menuListView = (ListView) view.findViewById(R.id.menu_listview);
        menuInfo = (TextView) view.findViewById(R.id.menu_info);
        menuSpinner = (Spinner) view.findViewById(R.id.menu_spinner);

        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                editSearch.setVisibility(View.INVISIBLE);
                MainActivity.toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(menuSpinner.getWindowToken(), 0);
                menuSetting();
                menuSelected = menuSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                editSearch.setVisibility(View.INVISIBLE);
                MainActivity.toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(menuSpinner.getWindowToken(), 0);
            }
        });

        View.OnClickListener menuListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editSearch.setVisibility(View.INVISIBLE);
                MainActivity.toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                if (v.getId() == R.id.menu_list)
                {
                    if (!menuSelected.equals("메뉴 선택"))
                    {
                        menuChange(menuSelected.substring(0, 2));
                        menuAdapter.notifyDataSetChanged();

                        menuListSelect.setVisibility(View.INVISIBLE);
                        menuInfo.setVisibility(View.INVISIBLE);
                        menuBtnReplay.setVisibility(View.INVISIBLE);
                        menuBtnBack.setVisibility(View.VISIBLE);
                        menu.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "메뉴를 선택하세요", Toast.LENGTH_LONG).show();
                    }
                }

                else if (v.getId() == R.id.menu_select)
                {
                    if (!menuSelected.equals("메뉴 선택"))
                    {
                        menuInfo.setText(menuRandom());

                        menuListSelect.setVisibility(View.INVISIBLE);
                        menu.setVisibility(View.INVISIBLE);
                        menuBtnBack.setVisibility(View.VISIBLE);
                        menuBtnReplay.setVisibility(View.VISIBLE);
                        menuInfo.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "메뉴를 선택하세요", Toast.LENGTH_LONG).show();
                    }
                }

                else if (v.getId() == R.id.menu_back)
                {
                    menuSetting();
                    menuAdapter.notifyDataSetChanged();

                    menuBtnBack.setVisibility(View.INVISIBLE);
                    menuInfo.setVisibility(View.INVISIBLE);
                    menu.setVisibility(View.INVISIBLE);
                    menuBtnReplay.setVisibility(View.INVISIBLE);
                    menuListSelect.setVisibility(View.VISIBLE);
                }

                else if (v.getId() == R.id.menu_replay)
                {
                    menuInfo.setText(menuRandom());

                    menuListSelect.setVisibility(View.INVISIBLE);
                    menu.setVisibility(View.INVISIBLE);
                    menuBtnBack.setVisibility(View.VISIBLE);
                    menuBtnReplay.setVisibility(View.VISIBLE);
                    menuInfo.setVisibility(View.VISIBLE);
                }

                else if (v.getId() == R.id.menu_list_back)
                {
                    menuChange(menuSelected.substring(0, 2));
                    menuAdapter.notifyDataSetChanged();

                    menuListSelect.setVisibility(View.INVISIBLE);
                    menuInfo.setVisibility(View.INVISIBLE);
                    menuBtnReplay.setVisibility(View.INVISIBLE);
                    menuBtnListBack.setVisibility(View.INVISIBLE);
                    menuBtnBack.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.VISIBLE);
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

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                im.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                String string = (String) parent.getItemAtPosition(position);
                menuSetting();
                for (int i = 0; i < listMenu.size(); i++)
                {
                    if (listMenu.get(i).substring(5, listMenu.get(i).length()).equals(string))
                    {
                        string = listMenu.get(i).substring(5, listMenu.get(i).length()) + "\n\n";
                        string += "메뉴 : " + menuSelected.substring(4, menuSelected.length()) + "\n\n";
                        string += "위치 : " + menuText(listMenu.get(i).charAt(0));
                        break;
                    }
                }
                menuInfo.setText(string);

                menuListSelect.setVisibility(View.INVISIBLE);
                menu.setVisibility(View.INVISIBLE);
                menuBtnReplay.setVisibility(View.INVISIBLE);
                menuBtnListBack.setVisibility(View.VISIBLE);
                menuBtnBack.setVisibility(View.VISIBLE);
                menuInfo.setVisibility(View.VISIBLE);
            }
        });

        menuBtnList.setOnClickListener(menuListener);
        menuBtnSelect.setOnClickListener(menuListener);
        menuBtnBack.setOnClickListener(menuListener);
        menuBtnReplay.setOnClickListener(menuListener);
        menuBtnListBack.setOnClickListener(menuListener);
        menuListView.setAdapter(menuAdapter);

        return view;
    }


    public void menuSetting()
    {
        InputStream inputData = getResources().openRawResource(R.raw.ssubjlist);

        for (int i = listMenu.size() - 1; i >= 0; i--)
        {
            listMenu.remove(i);
        }

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

    public String menuRandom()
    {
        int menuInt;
        String string;
        menuSetting();

        for (int i = listMenu.size() - 1; i >= 0; i--)
        {
            string = listMenu.get(i);
            if (!menuSelected.substring(0, 2).equals(string.substring(1, 3)))
            {
                listMenu.remove(i);
            }
        }

        while (true)
        {
            menuInt = (int) (Math.random() * 74) + 1;
            for (int i = 0; i < listMenu.size(); i++)
            {
                if (menuInt == Integer.parseInt(listMenu.get(i).substring(3, 5)))
                {
                    string = listMenu.get(i).substring(5, listMenu.get(i).length()) + "\n\n";
                    string += "메뉴 : " + menuSelected.substring(4, menuSelected.length()) + "\n\n";
                    string += "위치 : " + menuText(listMenu.get(i).charAt(0));

                    return string;
                }
            }
        }
    }

    public String menuText(char ch)
    {
        InputStream inputData = getResources().openRawResource(R.raw.ssubjllist);

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputData, "EUC_KR"));
            while (true)
            {
                String string = bufferedReader.readLine();

                if (string != null)
                {
                    if (string.charAt(0) == ch)
                    {
                        return string.substring(1, string.length());
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