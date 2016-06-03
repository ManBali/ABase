package base.com.activity;

import android.os.Bundle;
import android.widget.Button;

import com.core.activity.AbstractActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import base.com.abase.R;

/**
 * Created by sufun_job on 2016/6/2.
 *
 */
@EActivity(R.layout.intent_acitivity)
public class IntentActivity extends AbstractActivity{

    @Extra
    String data="";

    @ViewById(R.id.btn_extra)
    Button btn_extra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init()
    {
            //接收从那边传过来的数据
            btn_extra.setText(data);
    }
}
