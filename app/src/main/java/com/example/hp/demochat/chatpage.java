package com.example.hp.demochat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class chatpage extends AppCompatActivity {
    private Toolbar tb1;
    private TabLayout tablyot;
    private ViewPager vp;
    private myadpter mtadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);


        tb1 = (Toolbar) findViewById(R.id.toolbar);
        tablyot = (TabLayout) findViewById(R.id.tablayout);
        vp = (ViewPager) findViewById(R.id.view);

        setSupportActionBar(tb1);
        //Adding Tabs inside tablayout
        tablyot.addTab(tablyot.newTab().setText("(MY FRIENDLIST)"));
        tablyot.addTab(tablyot.newTab().setText("(CHAT)"));
        tablyot.addTab(tablyot.newTab().setText("(FRIENDS)"));

        mtadpt = new myadpter(getSupportFragmentManager(), tablyot.getTabCount());  //3 parameters take
        vp.setAdapter(mtadpt);
        //Event handling
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablyot));
        tablyot.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //MENU OPTIONS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item1:
                Toast.makeText(chatpage.this, "Item 1 is clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Toast.makeText(chatpage.this, "Item 2 is clicked", Toast.LENGTH_LONG).show();
                Intent i=new Intent(chatpage.this,settings.class);
                startActivity(i);
                return true;
            case R.id.item3:
                Toast.makeText(chatpage.this, "Item 3 is clicked", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
