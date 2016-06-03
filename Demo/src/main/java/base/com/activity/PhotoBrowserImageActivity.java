package base.com.activity;

import android.support.v4.view.ViewPager;

import com.core.activity.AbstractActivity;
import com.core.adapter.ImageBrowserAdapter;
import com.core.widget.ScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import base.com.abase.R;

/**
 * @author SUSUN.WU
 * @Created by sufun_job on 2016/6/3.
 * @email wsfjlagr@qq.com
 * @Descriptin 用于图片的浏览
 */
@EActivity(R.layout.photo_browser_image_activity)
public class PhotoBrowserImageActivity extends AbstractActivity implements ViewPager.OnPageChangeListener {

    @Extra
    List<String> imagesUrl = new ArrayList<String>();

    @ViewById(R.id.id_pager)
    ScrollViewPager id_pager;

    private ImageBrowserAdapter mAdapter;

    @AfterViews
    void init() {
        id_pager.setOnPageChangeListener(this);
        if (imagesUrl.size() > 0) {
            mAdapter=new ImageBrowserAdapter(PhotoBrowserImageActivity.this,imagesUrl);
            id_pager.setAdapter(mAdapter);
        }
        else
        {
            finish();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
