package hci_gradjevinski.materijali.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hci_gradjevinski.materijali.Activitys.DetaljiProizvodaActivity;
import hci_gradjevinski.materijali.Helper.GsonConverter;
import hci_gradjevinski.materijali.Models.ProizvodiVM;
import hci_gradjevinski.materijali.R;
import hci_gradjevinski.materijali.Utils.NetworkUtils;

public class BetonFragment extends Fragment {
    private TextView mErrorMessage;
    private ProgressBar mProggresBar;
    private ListView mListView;
    private List<ProizvodiVM> mProizvodi;
    private String mSearchQuery="";

    private TextView mSearchNotFound;
    private List<String> listDataHeader;
    List<ProizvodiVM> p;
    HashMap<String, List<ProizvodiVM>> listDataChild;
    ExpandableListView expListView;
    public BetonFragment() {

    }

    public static BetonFragment newInstance() {
        BetonFragment fragment = new BetonFragment();

        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_proizvodi,container,false);



    //    mListView = (ListView) view.findViewById(R.id.list_view_proizvodi);
        mErrorMessage = (TextView) view.findViewById(R.id.textviev_error_message);
        mProggresBar = (ProgressBar) view.findViewById(R.id.proggres_bar);
        mSearchNotFound= (TextView) view.findViewById(R.id.textviev_search_not_found_message);

        listDataHeader = new ArrayList<String>();
        doGetData();

        listDataHeader.add("Beton");
        listDataHeader.add("Cement");
        listDataHeader.add("Kreč");

        listDataChild= new HashMap<String, List<ProizvodiVM>>();
        expListView=(ExpandableListView)view.findViewById(R.id.list_view_proizvodi);



        return view;








    }

    private void doSetAdapter() {





        for(int i=0;i<listDataHeader.size(); i++){
            List< ProizvodiVM> p= new ArrayList<>();
            List< ProizvodiVM>k= new ArrayList<>();
            List< ProizvodiVM> v= new ArrayList<>();
            for(int j=0;j<mProizvodi.size();j++){

                if(Integer.parseInt(mProizvodi.get(j).Sifra)==4)

                {

                    p.add(mProizvodi.get(j));

                }
                if(Integer.parseInt(mProizvodi.get(j).Sifra)==5)

                {

                    k.add(mProizvodi.get(j));

                }
                if(Integer.parseInt(mProizvodi.get(j).Sifra)==6)

                {

                    v.add(mProizvodi.get(j));

                }

            }
            Log.e("----Eroor","velicinna pkv ---"+v.size());
            if(p.size()!=0){

                listDataChild.put(listDataHeader.get(0),p);

            }if(k.size()!=0){

                listDataChild.put(listDataHeader.get(1),k);

            }if(v.size()!=0){

                listDataChild.put(listDataHeader.get(2),v);

            }
        }



        Log.e("Neki error", "Velicini data hedera:"+listDataHeader.size());
        expListView.setAdapter(new BaseExpandableListAdapter() {

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                Log.e("Neki error", "-----1-----4--derror u adapteru");
                return listDataChild.get(listDataHeader.get(groupPosition))
                        .get(childPosition);
            }
            @Override
            public long getChildId(int groupPosition, int childPosition) {

                Log.e("Neki error", "------2----4--derror u adapteru");return childPosition;
            }
            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                Log.e("Neki error", "----------56------");
                final ProizvodiVM p= (ProizvodiVM) getChild(groupPosition,childPosition);
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) getActivity().getApplicationContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.proizvodi_list_view, null);
                }
                Log.e("Neki error", "----------57------");
                final TextView naziv = (TextView) convertView.findViewById(R.id.tvNaziv);

                final TextView cijena = (TextView) convertView.findViewById(R.id.tvCijena);

                ImageView imageProizvod = (ImageView) convertView.findViewById(R.id.imageProizvod);







                naziv.setText(p.Naziv);
                cijena.setText(String.valueOf(p.Cijena)+" KM");


                if(p.Slika!=null) {
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(p.Slika, 0, p.Slika.length);
                    imageProizvod.setImageBitmap(decodedByte);
                }

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        do_GetDetalji(p);
                    }
                });


                return convertView;
            }


            @Override
            public int getChildrenCount(int groupPosition) {
                Log.e("Neki error", "----------4--derror u adapteru");
                return listDataChild.get(listDataHeader.get(groupPosition))
                        .size();
            }

            @Override
            public Object getGroup(int groupPosition) {Log.e("Neki error", "--1--------5--Get group----");
                return listDataHeader.get(groupPosition);
            }
            @Override
            public int getGroupCount() {
                Log.e("Neki error", "--Unutar gertGroupCount-----"+listDataHeader.size());

                return listDataHeader.size();
            }


            @Override
            public long getGroupId(int groupPosition) {   Log.e("Neki error", "--11--------5------");
                return groupPosition;
            }



            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                Log.e("Neki error", "----------5------");
                String headerTitle = (String) getGroup(groupPosition);
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    convertView = infalInflater.inflate(R.layout.list_header, null);
                }

                TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
                lblListHeader.setTypeface(null, Typeface.BOLD);
                lblListHeader.setText(headerTitle);

                return convertView;
            }



            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        });


        expListView.expandGroup(0);
    }
    private void doGetData() {
        mProggresBar.setVisibility(View.VISIBLE);
        new AsyncTask<URL, Void, String>() {

            @Override

            protected String doInBackground(URL... params) {
                String result=null;
                try {
                    result =NetworkUtils.getResponseFromHttpUrl(params[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);
                mProggresBar.setVisibility(View.INVISIBLE);
                if (data != null) {
                    Type listType = new TypeToken<ArrayList<ProizvodiVM>>() {
                    }.getType();

                    mProizvodi = GsonConverter.JsonToListArray(data, listType);

                    doSetAdapter();
                    if (mProizvodi.size() == 0) {
                        doShowSearchNotFound();
                    } else {
                        doShowData();

                    }

                }
                else{
                    doShowErrorMessage();
                }

            }






        }.execute(NetworkUtils.buildSearchURL(mSearchQuery,2));



    }
  /*  private void doSetAdapter() {

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mProizvodi.size();
            }

            @Override
            public Object getItem(int position) {
                return mProizvodi.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                final ProizvodiVM proizvod = mProizvodi.get(position);


                if(view==null){
                    final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view=inflater.inflate(R.layout.proizvodi_list_view,parent,false);
                }
                final TextView naziv = (TextView) view.findViewById(R.id.tvNaziv);

                final TextView cijena = (TextView) view.findViewById(R.id.tvCijena);

                ImageView imageProizvod = (ImageView) view.findViewById(R.id.imageProizvod);








               naziv.setText(proizvod.Naziv);

                cijena.setText(String.valueOf(proizvod.Cijena)+" KM");


                if(proizvod.Slika!=null) {
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(proizvod.Slika, 0, proizvod.Slika.length);

                    imageProizvod.setImageBitmap(decodedByte);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        do_GetDetalji(proizvod);
                    }
                });
                return view;

            }
        });
    }

*/
    private void do_GetDetalji(ProizvodiVM proizvod) {

        Intent intent =new Intent(getActivity(), DetaljiProizvodaActivity.class);
        Bundle arg=new Bundle();
        arg.putSerializable("proizvod_key",proizvod);
        intent.putExtras(arg);
        startActivity(intent);
    }

    public void doSetSearchQuery(String s) {
        mSearchQuery=s;
        doGetData();
    }




    public void doShowErrorMessage() {
        mSearchNotFound.setVisibility(View.INVISIBLE);
        expListView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }
    public void doShowSearchNotFound() {

        mSearchNotFound.setVisibility(View.VISIBLE);
    }
    public void doShowData() {
        mSearchNotFound.setVisibility(View.INVISIBLE);
        expListView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
    }
}
