package com.example.ssubj;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    private Button btnBack;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        btnBack = (Button) findViewById(R.id.btn_back);

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
                    searchInfo.setVisibility(View.INVISIBLE);
                    btnBack.setVisibility(View.INVISIBLE);
                    editSearch.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    layoutTabPager.setVisibility(View.VISIBLE);
                }
                if (v.getId() == R.id.btn_search)
                {
                    toolbar.setVisibility(View.INVISIBLE);
                    editSearch.setVisibility(View.VISIBLE);
                    im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        };

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    if (search(editSearch.getText().toString()) == null)
                    {
                        im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        Toast.makeText(getApplicationContext(), "입력한 밥집이 목록에 없습니다", Toast.LENGTH_LONG).show();
                        editSearch.setText(null);
                    }
                    else
                    {
                        im.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

                        searchInfo.setText(search(editSearch.getText().toString()));

                        layoutTabPager.setVisibility(View.INVISIBLE);
                        editSearch.setVisibility(View.INVISIBLE);
                        searchInfo.setVisibility(View.VISIBLE);
                        btnBack.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);

                        editSearch.setText(null);
                        return true;
                    }
                }
                return false;
            }
        });

        btnSearch.setOnClickListener(listener);
        btnBack.setOnClickListener(listener);
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

    public String search(String str)
    {
        setting();
        String stringa = str + "\n\n", stringm = null, stringl = null;
        int ck = 0;

        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).substring(5, list.get(i).length()).contains(str))
            {
                str = list.get(i).substring(0, 5);
                stringa = list.get(i).substring(5, list.get(i).length()) + "\n\n";
                ck++;
                break;
            }
        }
        if (ck == 0)
        {
            return null;
        }
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
                        ck++;
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
                        ck++;
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

        if (ck == 3)
        {
            return stringa + stringm + stringl;
        }
        else
        {
            return null;
        }
    }
}