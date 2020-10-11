package inclinic.com.OCR;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import inclinic.com.R;
import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.app.Activity.RESULT_OK;

public class OCR extends Fragment {

    private static final int PERMISSION_CODE = 1000 ;
    private static final int IMAGE_CAPTURE_CODE = 1001 ;
    Button MCapture;
    Button MDetect;
    TextView IText;
    ImageView MView;
    Bitmap imageBitmap;

    Uri image_uri;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.ocr, container, false);

        MCapture = v.findViewById(R.id.capture_image_btn);
        MDetect = v.findViewById(R.id.Detect_image_btn);
        IText = v.findViewById(R.id.ItextView);
        MView = v.findViewById(R.id.image_view);

        MCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IText.setText("");
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ){
                        String[] Permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(Permission, PERMISSION_CODE);
                    }
                    else {
                        openCamera();
                    }
                }
                else {
                    openCamera();
                }
            }
        });

        MDetect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    DetectTextFromImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getActivity().getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else {
                    Toast.makeText(getContext(), "permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            MView.setImageURI(image_uri);
        }
    }

    private void DetectTextFromImage() throws IOException {
        FirebaseVisionImage FBVI = FirebaseVisionImage.fromFilePath(getContext(),image_uri);
        FirebaseVisionTextRecognizer FBVR = FirebaseVision.getInstance().getCloudTextRecognizer();
        FBVR.processImage(FBVI).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                displayTextFromImage(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error "+e.getMessage(),Toast.LENGTH_SHORT ).show();
                Log.d("Error ",e.getMessage());
            }
        });
    }

    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.TextBlock> blockList = firebaseVisionText.getTextBlocks();
        if(blockList.size() == 0){
            Toast.makeText(getContext(), "No Text Found in image.", Toast.LENGTH_SHORT).show();
        }
        else {
            for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()){
                String text = block.getText();
                IText.setText(text);
            }
        }
    }


}
