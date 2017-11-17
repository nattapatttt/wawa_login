package wawa.labwawa;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


import android.content.Intent;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //=========================================================

    class LoadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

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

                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {


            super.onPostExecute(s);

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Customer>>() {
            }.getType();
            ArrayList<Customer> customer = gson.fromJson(s, type);

            CustomerAdapter adapter = new CustomerAdapter(customer);
            recyclerView.setAdapter(adapter);

        }

    }


    //==========================================================


    class CustomerViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView nameTextView;
        TextView telephoneTextView;
        TextView emailTextView;
        TextView usernameTextView;

        public CustomerViewHolder(View itemView) {
            super(itemView);

            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            telephoneTextView = (TextView) itemView.findViewById(R.id.telephoneTextView);
            emailTextView = (TextView) itemView.findViewById(R.id.emailTextView);
            usernameTextView = (TextView) itemView.findViewById(R.id.usernameTextView);
        }
    }

    class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {
        List<Customer> _customers;

        public CustomerAdapter(List<Customer> customers) {
            _customers = customers;
        }


        @Override
        public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_customer, parent, false);

            return new CustomerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomerViewHolder holder, int position) {
            Customer customer = _customers.get(position);
            holder.nameTextView.setText(customer.Name + " " + customer.surname);
            holder.telephoneTextView.setText("Tel: " + customer.Telephone);
            holder.emailTextView.setText("Email: " + customer.Email);
            holder.usernameTextView.setText("Username: " + customer.Username);
            Glide.with(getApplicationContext())
                    .load(customer.PhotoUrl)
                    .into(holder.photoImageView);
        }

        @Override
        public int getItemCount() {
            return _customers.size();
        }
    }



}


