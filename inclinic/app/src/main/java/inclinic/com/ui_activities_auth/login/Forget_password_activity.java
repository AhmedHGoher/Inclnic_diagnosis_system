package inclinic.com.ui_activities_auth.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inclinic.com.R;

public class Forget_password_activity extends AppCompatActivity
{
    FirebaseAuth auth ;


    EditText email_forget;
    Button send_email_code;
    ProgressBar progressBar ;


    public static boolean isConnected;
    public static SharedPreferences SP ;
    public static String filename = "Keys";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_activity);

        email_forget=findViewById(R.id.ui_EmailAddress_EditText_forget_password_activity);
        send_email_code=findViewById(R.id.ui_forgetpassword_btn);
        progressBar=findViewById(R.id.main_progress_forgetpassword);


        SP = getSharedPreferences(filename, MODE_PRIVATE);
        SharedPreferences preferences =getSharedPreferences(filename,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String getname = SP.getString("UserName_saved_key1","");
        editor.commit();

        if (getname!=null)
        {
            email_forget.setText(getname);
        }


        auth= FirebaseAuth.getInstance();

        send_email_code.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                preventTwoClick(v);

                progressBar.setVisibility(View.VISIBLE);
                new SginIn.InternetDialog(Forget_password_activity.this).getInternetStatus();

                if (isBlank(email_forget.getText().toString())||email_forget==null)
                {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Email Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if(!isEmailValid(email_forget.getText().toString()))
                {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Email isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    return;
                }

                auth.sendPasswordResetEmail(email_forget.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            SP = getSharedPreferences(filename, MODE_PRIVATE);
                            SharedPreferences preferences =getSharedPreferences(filename,MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("UserName_saved_key1", email_forget.getText().toString());
                            editor.commit();

                            progressBar.setVisibility(View.VISIBLE);
                            TastyToast.makeText(getApplicationContext(), "Password Send Successful To Email .. Check Your Mailbox", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            final Intent e = new Intent(Forget_password_activity.this, SginIn.class);
                            startActivity(e);
                            finish();
                            progressBar.setVisibility(View.GONE);

                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            TastyToast.makeText(getApplicationContext(), "Please check your correct email address or contact our support", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            return;
                        }
                    }
                });



            }
        });

    }


    public static class InternetDialog
    {
        private Context context;

        public InternetDialog()
        {

        }

        public InternetDialog(Context context)
        {
            this.context = context;
        }

        public void showNoInternetDialog()
        {
            final Dialog dialog1 = new Dialog(context, R.style.df_dialog);
            dialog1.setContentView(R.layout.dialog_no_internet);
            dialog1.setCancelable(true);
            dialog1.setCanceledOnTouchOutside(true);
            dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(!isConnected)
                    {
                        dialog1.dismiss();
                        getInternetStatus();
                    }
                    else
                    {
                        dialog1.dismiss();
                    }
                }
            });
            dialog1.show();
        }



        public  boolean getInternetStatus()
        {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if(!isConnected)
            {
                //show no internet dialog
                showNoInternetDialog();
            }
            else
            {

            }
            return isConnected;
        }
    }



    public static boolean isBlank(String value)
    {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static void preventTwoClick(final View view){
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            public void run() {
                view.setEnabled(true);
            }
        }, 1000);
    }

}
