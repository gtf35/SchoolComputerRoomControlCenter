package top.gtf35.controlcenter.common.mvp_base;

import android.util.Log;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import top.gtf35.controlcenter.common.utils.LogUtils;

/*
 * mvp 基类系列_P 层
 * 主要就是便于关联和释放相对的资源
 * 2019/04/29
 * gtf35
 * gtfdeyouxiang@gmail.com
 * */

public abstract class BasePresenter<V> {
    private Reference<V> mViewRef;  // 弱引用保存 V 实例

    //装入 V 层
    public abstract V setV();

    /*关联 V 层*/
    private void dealSetV(){
        mViewRef = new WeakReference<V>(setV());
    }

    /*获取V层实例*/
    public V getV(){
        if (!isViewAttached()){
            LogUtils.w("V层在尚未关联的时候被调用");
            dealSetV();
            return getV();
        }
        return mViewRef.get();
    }

    /*已经有关联的V层？*/
    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    /*销毁V层*/
    public void goDie() {
        Log.w("gtf", "go die");
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
