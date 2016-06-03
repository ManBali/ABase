package base.com.abase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.core.activity.AbstractActivity;
import com.core.util.IntentUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import base.com.activity.DialogActivity_;
import base.com.activity.IntentActivity_;
import base.com.activity.PhotoBrowserImageActivity_;
import base.com.activity.RecyclerViewActivity_;
import base.com.activity.TakePhotoActivity_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AbstractActivity{

    @ViewById(R.id.btn_pull2refresh)
    Button btn_pull2refresh;

    @ViewById(R.id.btn_activity_intent)
    Button btn_activity_intent;

    @ViewById(R.id.btn_activity_take_photo)
    Button btn_activity_take_photo;


    @ViewById(R.id.btn_activity_dialog)
    Button btn_activity_dialog;

    @ViewById(R.id.btn_browser_image)
    Button btn_browser_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.isTemplate=false;
        super.onCreate(savedInstanceState);
    }
    @AfterViews
    void init() {
        //开始执行测试
        hiddenTitleLeftButton();
        initEvent();
    }
    void initEvent()
    {
        btn_pull2refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开第二个界面
                RecyclerViewActivity_.intent(MainActivity.this).start();
            }
        });
        btn_activity_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent   intent= IntentActivity_.intent(MainActivity.this).data("This is the data I want to Post").get();
                IntentUtil.intentForward(MainActivity.this, intent);
            }
        });
        btn_activity_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进行拍照界面
                TakePhotoActivity_.intent(MainActivity.this).start();
            }
        });

        //跳转到对话框面
        btn_activity_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogActivity_.intent(MainActivity.this).start();
            }
        });
        //查看图片的浏览
        btn_browser_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String>  datas=new ArrayList<String>();
                datas.add("http://s18.mogucdn.com/p1/160411/130504643_ie4teobqgbtdazdeg4zdambqgqyde_640x960.jpg_220x330.jpg");//
                datas.add("http://s18.mogucdn.com/p1/160510/105880603_ifrdcyrtgzrtambxhazdambqmeyde_640x960.jpg_220x330.jpg");
                datas.add("http://s10.mogucdn.com/p1/160512/p6_ifqtkyzygu2teyrxhazdambqgyyde_300x300.jpg");
                datas.add("http://s18.mogucdn.com/p1/160414/63508466_ie4tgmlfg5sdeytfg4zdambqgqyde_640x960.jpg_220x330.jpg");
                datas.add("http://s16.mogucdn.com/p1/160522/87906359_ie4weodcgzqtoztbhazdambqgqyde_640x960.jpg_220x330.jpg");
                datas.add("http://d05.res.meilishuo.net/pic/_o/c2/8d/f1d26a716fbef332f6817e1f2493_1242_582.c1.jpg_756dba9e_s3_330_230.jpg");
                datas.add("http://s18.mogucdn.com/p1/160515/63909790_ifqtkmbwgq4tkyryhazdambqgyyde_640x961.jpg_220x330.jpg");
                datas.add("http://s8.mogucdn.com/p1/160517/sy_ie4dmnjymrqtcnbzhazdimbqgiyde_183x213.jpg");
                PhotoBrowserImageActivity_.intent(MainActivity.this).imagesUrl(datas).start();
            }
        });
    }
}
