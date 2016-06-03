package base.com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.core.activity.AbstractActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import base.com.abase.R;

/**
 * Created by sufun_job on 2016/6/2.
 */
@EActivity(R.layout.take_photo_activity)
public class TakePhotoActivity  extends AbstractActivity{
    @ViewById(R.id.id_take_pick_photo)
    Button id_take_pick_photo;
    @ViewById(R.id.id_take_photo)
    Button id_take_photo;

    @ViewById(R.id.id_txt_view)
    TextView id_txt_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init()
    {
        setPhotoUrl(new ITakePhotoUrl() {
            @Override
            public void getUploadPhotoPath(String uploadPhotoPath) {
                id_txt_view.setText(" 你所选择的照片地址是："+uploadPhotoPath);
            }
        });
        id_take_pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getPhoto();
            }
        });
        id_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
    }
}
