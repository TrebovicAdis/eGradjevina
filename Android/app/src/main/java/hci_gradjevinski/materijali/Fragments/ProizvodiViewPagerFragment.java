package hci_gradjevinski.materijali.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import hci_gradjevinski.materijali.R;

import java.util.ArrayList;
import java.util.List;

import hci_gradjevinski.materijali.NavigationDrawerActivity;


public class ProizvodiViewPagerFragment extends Fragment {



    private EditText mSearchQuery;


    private SimpleAdapter mPagerAdapter;
    private View view;
    private NavigationDrawerActivity activity;

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_prevozi_view_pager, container, false);
        activity = ((NavigationDrawerActivity) getActivity());
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_proizvodi);
        activity.setSupportActionBar(toolbar);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburgere_black_24dp);


        mViewPager = (ViewPager) view.findViewById(R.id.container);

        mPagerAdapter = new SimpleAdapter(getActivity().getSupportFragmentManager());
        mPagerAdapter.addFragment(MaterijalFragment.newInstance(), "Materijal");
        mPagerAdapter.addFragment(BetonFragment.newInstance(), "Beton i cement");
        mPagerAdapter.addFragment(OplatniSistemi.newInstance(), "Oplatni sistem");
      


        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.instantiateItem(mViewPager, 0);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



        mSearchQuery = (EditText) toolbar.findViewById(R.id.search_view);

mSearchQuery.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {
        mPagerAdapter.notifyDataSetChanged();
    }
});

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        super.onCreateOptionsMenu(menu, inflater);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) { // This is the home/back button
            activity.OpenDrawer();
        }


        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.section_view_pager, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }


        private void doListenOnSearch() {


        }


    }

    public class SimpleAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SimpleAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        @Override
        public int getItemPosition(Object object) {

            if (object instanceof MaterijalFragment) {

                MaterijalFragment fragment = (MaterijalFragment) object;
                fragment.doSetSearchQuery(mSearchQuery.getText().toString());

            }

            if (object instanceof BetonFragment) {

                BetonFragment fragment = (BetonFragment) object;
                fragment.doSetSearchQuery(mSearchQuery.getText().toString());

            }

            if (object instanceof OplatniSistemi) {

                OplatniSistemi fragment = (OplatniSistemi) object;
                fragment.doSetSearchQuery(mSearchQuery.getText().toString());

            }
            return super.getItemPosition(object);
        }
    }

}