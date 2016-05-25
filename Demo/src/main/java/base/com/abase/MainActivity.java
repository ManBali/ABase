package base.com.abase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.core.activity.AbstractActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import base.com.activity.RecyclerViewActivity_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AbstractActivity{

    @ViewById(R.id.btn_pull2refresh)
    Button btn_pull2refresh;
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
    }
}
