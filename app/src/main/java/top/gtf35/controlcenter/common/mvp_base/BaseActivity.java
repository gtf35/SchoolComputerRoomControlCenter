package top.gtf35.controlcenter.common.mvp_base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import top.gtf35.controlcenter.R;
import top.gtf35.controlcenter.common.utils.LogUtils;
import top.gtf35.controlcenter.common.utils.StatusBarUtil;

/*
* mvp 基类系列_V 层
* 主要就是便于关联和释放相对的资源
* 2019/04/29
* gtf35
* gtfdeyouxiang@gmail.com
* */

public abstract class BaseActivity<P> extends AppCompatActivity {

    private Reference<P> mPresenterRef;  // 弱引用保存 P 实例

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealSetP();
        setContentView(setLayout());
        StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark);
        main();
    }

    // 装入 布局ID
    public abstract int setLayout();

    // 装入 主函数
    public abstract void main();

    //装入 p 层
    public abstract P setP();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        goDie();
    }


    /*关联 P 层*/
    private void dealSetP(){
        mPresenterRef = new WeakReference<P>(setP());
    }

    /*获取P 层实例*/
    public P getP(){
        if (!isPresenterAttached()) {
            LogUtils.w("P层尚未绑定就被调用");
            dealSetP();
            return getP();
        }
        return mPresenterRef.get();
    }

    /*已经有关联的 P 层？*/
    public boolean isPresenterAttached(){
        return mPresenterRef != null && mPresenterRef.get() != null;
    }

    /*销毁V层*/
    public void goDie() {
        BasePresenter bp = (BasePresenter)getP();
        if (bp != null && bp.isViewAttached()) bp.goDie();
        if (mPresenterRef != null) {
            mPresenterRef.clear();
            mPresenterRef = null;
        }
    }
}
