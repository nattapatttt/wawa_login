package wawa.labwawa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    EditText nameEditText;
    EditText surnameEdittext;
    EditText telephoneEditText;
    EditText emailEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEdittext = (EditText) findViewById(R.id.surnameEditText);
        telephoneEditText = (EditText) findViewById(R.id.telephoneEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        okButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancleButton);

        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == okButton) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to cancle?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    Customer customer = new Customer();
                    customer.Name = nameEditText.getText().toString();
                    customer.surname = surnameEdittext.getText().toString();
                    customer.Telephone = telephoneEditText.getText().toString();
                    customer.Email = nameEditText.getText().toString();
                    customer.Name = nameEditText.getText().toString();
                    customer.Name = nameEditText.getText().toString();

                    //   long id = db.insertCustomer(customer);

                    //   if(id>0)
                    // {
                    //      Toast.makeText(getApplicationContext() , "Insert Completed ID =" +id, Toast.LENGTH_SHORT).show();

                    //     finish();

                    //  }

                    new PostData().execute(customer);
                }
            });

            builder.setNegativeButton("Cancle", null);
            builder.show();

        } else if (view == cancelButton) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to cancle?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onBackPressed();
                }
            });

            builder.setNegativeButton("Cancle", null);
            builder.show();

        }

    }

    class PostData extends AsyncTask<Customer, Void, String> {

        @Override
        protected String doInBackground(Customer... customers) {

            RequestBody body = new FormBody
                    .Builder()
                    .add("name", customers[0].surname)
                    .add("surname", customers[0].surname)
                    .add("telephone", customers[0].Telephone)
                    .add("email", customers[0].Email)
                    .add("username", customers[0].Username)
                    .add("password", customers[0].Password)
                    .add("photoUrl", customers[0].PhotoUrl)
                    .build();

            Request request = new Request
                    .Builder()
                    .url("http://wawawa.azurewebsites.net/tables/customer/get")
                    .build();
            OkHttpClient client = new OkHttpClient();
            Call call = client.newCall(request);
            try {
                Response response = call.execute();
                String json = response.body().string();
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(SignupActivity.this, "Insert Completed", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
