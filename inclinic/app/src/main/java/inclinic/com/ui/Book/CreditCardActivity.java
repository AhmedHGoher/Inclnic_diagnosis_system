package inclinic.com.ui.Book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.braintreepayments.cardform.view.CardForm;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.models.doctor.Book_doctor_appointment_model;

public class CreditCardActivity extends AppCompatActivity {

    Button paycash_btn;
    CardForm cardForm;
    ImageButton backimgbtn;
    TextView book_txt ;
    ProgressBar progressBar;
    BookModel bookModel = new BookModel();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    public static SharedPreferences SP ;
    public static String filename = "Keys";

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private IOSDialog iosDialog;

    public CreditCardActivity(String day_book, String time_book) {
        this.day_book = day_book;
        this.time_book = time_book;
    }

    public CreditCardActivity() {

    }
    String day_book;
    String time_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

//        SP = getSharedPreferences(filename, MODE_PRIVATE);
//        String pos_day = SP.getString("pos_day","");
//        String pos_time = SP.getString("pos_time","");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String pos_day = sharedPreferences.getString("pos_day", day_book);
        String pos_time =sharedPreferences.getString("pos_time", time_book);


        paycash_btn = findViewById(R.id.creditactivity_paycash);
        cardForm = findViewById(R.id.creditcard);
        backimgbtn = findViewById(R.id.creditactivity_backimgbtn);
        book_txt = findViewById(R.id.creditactivity_booktxt);
        progressBar = findViewById(R.id.creditactivity_progressbar);
        bookModel = (BookModel) getIntent().getSerializableExtra("bookmodel");

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .setup(CreditCardActivity.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        paycash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iosDialog = new IOSDialog.Builder(CreditCardActivity.this)
                        .setDimAmount(3)
                        .setTitle("Booking...")
                        .setTitleColorRes(R.color.blackTextColor)
                        .setSpinnerColorRes(R.color.blackTextColor)
                        .setSpinnerColorRes(R.color.blackTextColor)
                        .setMessageColorRes(R.color.blackTextColor)
                        .setCancelable(false)
                        .build();
                iosDialog.show();
                String key = reference.push().getKey();
                bookModel.setKey(key);
                bookModel.setCredit(false);


                reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization())
                        .child(bookModel.getDoctorid()).child("Appointments")
                        .child(String.valueOf(getIntent().getIntExtra("day_bookk",Integer.valueOf(pos_day))))
                        .child("times_appointments")
                        .child(String.valueOf(getIntent().getIntExtra("time_bookk",Integer.valueOf(pos_time))))
                        .child("is_booked").setValue(true);



                reference.child("users").child(user.getUid()).child("Booking").child(key).setValue(bookModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization()).child(bookModel.getDoctorid())
                                    .child("Booking").child(bookModel.getBookday()).child(key).setValue(bookModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        TastyToast.makeText(getApplicationContext(), "The appointment was booked successfully with your doctor in ("+bookModel.getBookday()+") at "+bookModel.getBooktime(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        Intent intent = new Intent(CreditCardActivity.this,HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                        iosDialog.dismiss();
                                        Animatoo.animateFade(CreditCardActivity.this);

                                    }
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
                                            iosDialog.dismiss();
                                        }
                                    });
                        }
                        else{

                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
                                iosDialog.dismiss();
                            }
                        });
            }
        });

        book_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (cardForm.isValid()){
                    String key = reference.push().getKey();
                    bookModel.setCardnumber(cardForm.getCardNumber());
                    bookModel.setCvv(cardForm.getCvv());
                    bookModel.setExpirationdate(cardForm.getExpirationMonth()+"/"+cardForm.getExpirationYear());
                    bookModel.setPostalcode(cardForm.getPostalCode());
                    bookModel.setKey(key);
                    bookModel.setCredit(true);


                    reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization())
                            .child(bookModel.getDoctorid()).child("Appointments")
                            .child(String.valueOf(getIntent().getIntExtra("day_bookk",Integer.valueOf(pos_day))))
                            .child("times_appointments")
                            .child(String.valueOf(getIntent().getIntExtra("time_bookk",Integer.valueOf(pos_time))))
                            .child("is_booked").setValue(true);


                    reference.child("users").child(user.getUid()).child("Booking").child(key).setValue(bookModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization()).child(bookModel.getDoctorid())
                                        .child("Booking").child(bookModel.getBookday()).child(key).setValue(bookModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            progressBar.setVisibility(View.GONE);
                                            Intent intent = new Intent(CreditCardActivity.this,HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                            Animatoo.animateFade(CreditCardActivity.this);
                                        }
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressBar.setVisibility(View.GONE);
                                                Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
                                            }
                                        });
                            }
                            else{

                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
                                }
                            });
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(view , "CardForm must be valid",Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Animatoo.animateSwipeRight(this);
    }
}