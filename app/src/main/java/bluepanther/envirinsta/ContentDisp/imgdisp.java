package bluepanther.envirinsta.ContentDisp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import bluepanther.envirinsta.FileUtils.ImgUri;
import bluepanther.envirinsta.R;

public class imgdisp extends AppCompatActivity {
    TextView tv;
    String value;
    ImageView imgv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.imgview);
        System.out.println("in imgdisp");
        imgv=(ImageView)findViewById(R.id.imageView3);

     //   Picasso.with(imgdisp.this).load(ImgUri.uri).fit().centerCrop().into(imgv);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(ImgUri.ref)
                .into(imgv);

    }
}
