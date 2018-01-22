package hci_gradjevinski.materijali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import hci_gradjevinski.materijali.Fragments.RegistracijaFragment;
import hci_gradjevinski.materijali.Helper.GsonConverter;
import hci_gradjevinski.materijali.Helper.MyRunnable;
import hci_gradjevinski.materijali.Models.KorisniciVM;
import hci_gradjevinski.materijali.Utils.NetworkUtils;
import hci_gradjevinski.materijali.Helper.Sesija;


public class LoginActivity extends AppCompatActivity {


    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(hci_gradjevinski.materijali.R.layout.activity_login);

        mUsernameView = (EditText) findViewById(hci_gradjevinski.materijali.R.id.tv_username);


        mPasswordView = (EditText) findViewById(hci_gradjevinski.materijali.R.id.tv_password);


        Button mSignInButton = (Button) findViewById(hci_gradjevinski.materijali.R.id.sign_in_button);

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });

        TextView mRegistracijaButton;
        mRegistracijaButton = (TextView) findViewById(R.id.register_button);

        mRegistracijaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegistracija();
            }
        });


        mProgressView = findViewById(hci_gradjevinski.materijali.R.id.login_progress);
    }


    private void doLogin() {


        mUsernameView.setError(null);
        mPasswordView.setError(null);


        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

if(!username.equals("")||!password.equals("")){
        doAutentifikacija(username, password);

}
else{

    mPasswordView.setError("Korisnicko ime ili lozinka ne mogu biti prazna polja");
    mPasswordView.requestFocus();
}

    }


    private void doAutentifikacija(String username, String password) {
        new AsyncTask<URL, Void, String>() {

            @Override
            protected String doInBackground(URL... params) {

                String responseFromHttpUrl;


                try {
                    responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(params[0]);
                } catch (IOException e) {
                    return null;
                }


                return responseFromHttpUrl;
            }

            @Override
            protected void onPostExecute(final String response) {

                mProgressView.setVisibility(View.GONE);



                KorisniciVM korisnik = GsonConverter.JsonToObject(response, KorisniciVM.class);

                if (korisnik != null) {
                    Sesija.SaveUser(korisnik);

                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    mPasswordView.setError("Korisničko ime ili lozinka nisu validni");
                    mPasswordView.requestFocus();
                }





            }
        }.execute(NetworkUtils.buildAutentifikacijaURL(username, password));

    }



    private void doRegistracija() {

        RegistracijaFragment.openFragmentAsDialog(this, new MyRunnable<KorisniciVM>() {
            @Override
            public void run(KorisniciVM result) {

                mUsernameView.setText(result.Username);
                mPasswordView.setText(result.Password);
              doLogin();
            }
        });

    }


}


