package com.example.ssubj;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    static EditText editSearch;
    static Toolbar toolbar;
    private InputMethodManager im;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton btnSearch;
    private LinearLayout layoutTabPager;
    private TextView searchInfo;
    private Button btnBack, btnsearchBack;
    private ListView searchListView;
    private String strSearched=null;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting();
        final ArrayAdapter<String> searchAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);

        searchListView = (ListView) findViewById(R.id.search_listview);
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        btnBack = (Button) findViewById(R.id.btn_back);
        btnsearchBack = (Button) findViewById(R.id.search_list_back);

        btnSearch = (ImageButton) findViewById(R.id.btn_search);

        editSearch = (EditText) findViewById(R.id.edit_search);

        layoutTabPager = (LinearLayout) findViewById(R.id.layout_tab_pager);

        searchInfo = (TextView) findViewById(R.id.search_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.menu));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.restaurant));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.location));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                editSearch.setText(null);
                editSearch.setVisibility(View.INVISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
            }
        });

        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v.getId() == R.id.btn_back)
                {
                    im.hideSoftInputFromWindow(searchListView.getWindowToken(), 0);

                    btnBack.setVisibility(View.INVISIBLE);
                    searchInfo.setVisibility(View.INVISIBLE);
                    editSearch.setVisibility(View.INVISIBLE);
                    btnsearchBack.setVisibility(View.INVISIBLE);
                    searchListView.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    layoutTabPager.setVisibility(View.VISIBLE);
                }

                else if (v.getId() == R.id.btn_search)
                {
                    toolbar.setVisibility(View.INVISIBLE);
                    editSearch.setVisibility(View.VISIBLE);
                    editSearch.requestFocus();
                    im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }

                else if (v.getId() == R.id.search_list_back)
                {
                    im.hideSoftInputFromWindow(searchListView.getWindowToken(), 0);

                    search(strSearched);
                    searchAdapter.notifyDataSetChanged();

                    searchInfo.setVisibility(View.INVISIBLE);
                    editSearch.setVisibility(View.INVISIBLE);
                    btnsearchBack.setVisibility(View.INVISIBLE);
                    layoutTabPager.setVisibility(View.INVISIBLE);
                    btnBack.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    searchListView.setVisibility(View.VISIBLE);
                }
            }
        };

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                im.hideSoftInputFromWindow(searchListView.getWindowToken(), 0);

                String string = (String) parent.getItemAtPosition(position);
                setting();
                for (int i = 0; i < list.size(); i++)
                {
                    if (list.get(i).substring(5, list.get(i).length()).equals(string))
                    {
                        string = list.get(i).substring(5, list.get(i).length()) + "\n\n";
                        string += mlsearch(list.get(i));
                        break;
                    }
                }
                searchInfo.setText(string);

                editSearch.setVisibility(View.INVISIBLE);
                searchListView.setVisibility(View.INVISIBLE);
                btnBack.setVisibility(View.INVISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                searchInfo.setVisibility(View.VISIBLE);
                btnsearchBack.setVisibility(View.VISIBLE);
            }
        });

        searchListView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                MainActivity.editSearch.setVisibility(View.INVISIBLE);
                MainActivity.toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(searchListView.getWindowToken(), 0);
                return false;
            }
        });

        searchInfo.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                editSearch.setVisibility(View.INVISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                im.hideSoftInputFromWindow(searchInfo.getWindowToken(), 0);
                return false;
            }
        });

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    strSearched=editSearch.getText().toString();
                    if (search(strSearched) == false)
                    {
                        im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        Toast.makeText(getApplicationContext(), "입력한 밥집이 목록에 없습니다", Toast.LENGTH_LONG).show();
                        editSearch.setText(null);
                    }
                    else
                    {
                        searchAdapter.notifyDataSetChanged();
                        im.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

                        searchListView.setVisibility(View.VISIBLE);
                        layoutTabPager.setVisibility(View.INVISIBLE);
                        editSearch.setVisibility(View.INVISIBLE);
                        searchInfo.setVisibility(View.INVISIBLE);
                        btnBack.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        btnsearchBack.setVisibility(View.INVISIBLE);

                        editSearch.setText(null);
                        return true;
                    }
                }
                return false;
            }
        });

        btnsearchBack.setOnClickListener(listener);
        btnSearch.setOnClickListener(listener);
        btnBack.setOnClickListener(listener);
        searchListView.setAdapter(searchAdapter);
    }

    public void setting()
    {
        InputStream inputData = getResources().openRawResource(R.raw.ssubjlist);

        for (int i = list.size() - 1; i >= 0; i--)
        {
            list.remove(i);
        }

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputData, "EUC_KR"));
            while (true)
            {
                String string = bufferedReader.readLine();

                if (string != null)
                {
                    list.add(string);
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

    public boolean search(String str)
    {
        setting();
        String string;
        int ck = 0;

        for (int i = list.size() - 1; i >= 0; i--)
        {
            string = list.get(i);
            if (string.substring(5, list.get(i).length()).contains(str))
            {
                list.set(i, string.substring(5, string.length()));
                ck++;
            }
            else
            {
                list.remove(i);
            }
        }
        if (ck == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public String mlsearch(String str)
    {
        String mlstring = "메뉴 : ";
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
                        mlstring += Mstring.substring(2, Mstring.length()) + "\n\n";
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
                        mlstring += "위치 : " + Lstring.substring(1, Lstring.length());
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
        return mlstring;
    }
}