package wawa.labwawa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    //Data Member, Field, Global Variable
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signupButton;
    private Button loginButton;

    //Member Function, Method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        signupButton = (Button) findViewById(R.id.signupButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == signupButton)
        {
            Intent i = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(i);
        }
        else if (v == loginButton)
        {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            boolean isOk = db.authenUser(username, password);

            if (isOk)
            {
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
