package kmitl.project.bosstanayot.runranrun.Control;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import kmitl.project.bosstanayot.runranrun.R;
import kmitl.project.bosstanayot.runranrun.Fragment.Tab1run;
import kmitl.project.bosstanayot.runranrun.Fragment.Tab2profile;
import kmitl.project.bosstanayot.runranrun.View.MyViewPageAdapter;

public class MainActivity extends AppCompatActivity {
    TabLayout MyTabs;
    ViewPager MyPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MyTabs = findViewById(R.id.tabs);
        MyPage = findViewById(R.id.container);
        MyTabs.setupWithViewPager(MyPage);
        SetUpViewPager(MyPage);
    }

    public void SetUpViewPager (ViewPager viewpage){
        MyViewPageAdapter Adapter = new MyViewPageAdapter(getSupportFragmentManager());
        Adapter.AddFragmentPage(new Tab1run(), "run");
        Adapter.AddFragmentPage(new Tab2profile(), "profile");
        viewpage.setAdapter(Adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}


