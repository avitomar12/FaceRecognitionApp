package com.example.avi.faceauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {




    public static final int REQUEST_CAPTURE=1;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private JSONObject jsonObject;
    private ProgressDialog dialog = null;
    private TextView messageText;
    private Button click;
    private Button upload;

    public static String URL= "ec2_url/testingfc.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        click = (Button) findViewById(R.id.button3);
        imageView = (ImageView) findViewById(R.id.imageView2);
        upload = (Button) findViewById(R.id.but_upload);
        upload.setOnClickListener(this);
        click.setOnClickListener(this);
        jsonObject = new JSONObject();


    }
    public boolean hasCamera () {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    public void launchCamera(View v) {

        Intent i = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        startActivityForResult(i, REQUEST_CAPTURE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button3:
                Intent i= new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                startActivityForResult(i,REQUEST_CAPTURE);
                break;

            case R.id.but_upload:
              //  progressDialog = new ProgressDialog(this);
                //progressDialog.setMessage("Uploading, please wait...");
                //progressDialog.show();
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                //converting image to base64 string

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                String str;

                Toast.makeText(MainActivity.this,"encoded",Toast.LENGTH_SHORT).show();

                //sending image to server
                try {
                    jsonObject.put(Utils.imageName, "avi1");
                    Log.e("Image name", "avi");
                    Toast.makeText(getApplication(), "Inside try block", Toast.LENGTH_SHORT).show();
                    jsonObject.put(Utils.image, encodedImage);
                } catch (JSONException e) {
                    Log.e("JSONObject Here", e.toString());
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.e("Message from server", jsonObject.toString());
                  //              dialog.dismiss();

                                Toast.makeText(getApplication(), jsonObject.toString(), Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Message from server", volleyError.toString());
                //        dialog.dismiss();
                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(this).add(jsonObjectRequest);
                break;




        }
        }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

            Bundle extras = data.getExtras();
            Bitmap photo =(Bitmap)extras.get("data");
            imageView.setImageBitmap(photo);
           Toast.makeText(MainActivity.this,"In Activity",Toast.LENGTH_SHORT).show();

    }


}
