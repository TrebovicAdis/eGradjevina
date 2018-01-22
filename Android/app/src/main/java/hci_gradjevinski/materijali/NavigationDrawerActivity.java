package hci_gradjevinski.materijali;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import hci_gradjevinski.materijali.Activitys.KorpaActivity;
import hci_gradjevinski.materijali.Activitys.NarudzbeActivity;
import hci_gradjevinski.materijali.Fragments.ProfileFragment;
import hci_gradjevinski.materijali.Fragments.ProizvodiViewPagerFragment;
import hci_gradjevinski.materijali.Helper.Sesija;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Sesija.GetUser()!=null){
            setContentView(hci_gradjevinski.materijali.R.layout.activity_nav_drawer_logined);}
        else{
            setContentView(hci_gradjevinski.materijali.R.layout.activity_nav_drawer);}



       mDrawer= (DrawerLayout) findViewById(hci_gradjevinski.materijali.R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, null, hci_gradjevinski.materijali.R.string.navigation_drawer_open, hci_gradjevinski.materijali.R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(hci_gradjevinski.materijali.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ProizvodiViewPagerFragment fragment=new ProizvodiViewPagerFragment();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(hci_gradjevinski.materijali.R.id.content,fragment).commit();
       View mHeaderNav= navigationView.getHeaderView(0);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(hci_gradjevinski.materijali.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


         if (id == hci_gradjevinski.materijali.R.id.nav_proizvodi) {


             ProizvodiViewPagerFragment fragment=new ProizvodiViewPagerFragment();
             FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
             ft.replace(hci_gradjevinski.materijali.R.id.content,fragment).commit();





         }



        if (id == hci_gradjevinski.materijali.R.id.nav_korpa) {

           Intent intent=new Intent(NavigationDrawerActivity.this, KorpaActivity.class);
            startActivity(intent);




        }
        if (id == hci_gradjevinski.materijali.R.id.nav_narudzbe) {

            Intent intent=new Intent(NavigationDrawerActivity.this, NarudzbeActivity.class);
            startActivity(intent);




        }
        if (id == hci_gradjevinski.materijali.R.id.nav_logout) {

            Sesija.SaveUser(null);
            Intent intent=new Intent(NavigationDrawerActivity.this, NavigationDrawerActivity.class);
            startActivity(intent);
            finish();

        }
            if (id == hci_gradjevinski.materijali.R.id.nav_login) {


                Intent intent=new Intent(NavigationDrawerActivity.this, LoginActivity.class);
                startActivity(intent);





        }
        if (id == hci_gradjevinski.materijali.R.id.nav_profile) {

            ProfileFragment fragment=new ProfileFragment();
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(hci_gradjevinski.materijali.R.id.content,fragment).commit();



        }



        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void OpenDrawer(){

        mDrawer.openDrawer(GravityCompat.START);
    }
}
