package top.gtf35.controlcenter.common.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;

/*
* 本工具类部分copy自https://blog.csdn.net/tabactivity/article/details/9128271
* */

/*
 * 获取、设置控件信息
 */
public class WidgetController {

    public static int MATCH_PARENT = 1;
    public static int WRAP_CONTENT = 0;

    /*
     * 获取控件宽
     */
    public static int getWidth(View view)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return (view.getMeasuredWidth());
    }
    /*
     * 获取控件高
     */
    public static int getHeight(View view)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return (view.getMeasuredHeight());
    }

    /*
     * 设置控件所在的位置X，并且不改变宽高，
     * X为绝对位置，此时Y可能归0
     */
    public static void setLayoutX(View view, int x)
    {
        MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,margin.topMargin, x+margin.width, margin.bottomMargin);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
    /*
     * 设置控件所在的位置Y，并且不改变宽高，
     * Y为绝对位置，此时X可能归0
     */
    public static void setLayoutY(View view, int y)
    {
        MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(margin.leftMargin,y, margin.rightMargin, y+margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
    /*
     * 设置控件所在的位置YY，并且不改变宽高，
     * XY为绝对位置
     */
    public static void setLayout(View view, int x, int y)
    {
        MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,y, x+margin.width, y+margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    /*
     * 设置控件高
     */
    public static void setHeight(View view, int height) {
        if (height == MATCH_PARENT)
            view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        else if (height == WRAP_CONTENT)
            view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        else
            view.getLayoutParams().height = height;
        view.requestLayout();
    }

    /*
     * 设置控件宽
     */
    public static void setWidth(View view, int width) {
        if (width == MATCH_PARENT)
            view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        else if (width == WRAP_CONTENT)
            view.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        else
            view.getLayoutParams().width = width;
        view.requestLayout();
    }

    /*
     * 设置控件高宽
     */
    public static void setSize(View view, int size) {
        setWidth(view, size);
        setHeight(view, size);
    }


    /*
     * 设置控件高宽
     */
    public static void setSize(View view, int width, int height) {
        setWidth(view, width);
        setHeight(view, height);
    }

    public static void setPadding(View view, int size) {
        int padding = SizeUtils.dp2px(size);
        view.setPadding(padding, padding, padding, padding);
        view.requestLayout();
    }

    public static void setPadding(View view, int left, int top, int right, int bottom) {
        view.setPadding(SizeUtils.dp2px(left), SizeUtils.dp2px(top), SizeUtils.dp2px(right), SizeUtils.dp2px(bottom));
        view.requestLayout();
    }

    /*
     * 设置控件边距
     */
    public static void setMargin(View view, int margin) {
        int size = SizeUtils.dp2px(margin);
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(size, size, size, size);
        view.requestLayout();
    }


    /*
     * 设置控件下移
     */
    public static void setMarginTop(View view, int margin) {
        int size = SizeUtils.dp2px(margin);
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = size;
    }

    /*
     * 设置控件上移
     */
    public static void setMarginBottom(View view, int margin) {
        int size = SizeUtils.dp2px(margin);
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = size;
    }

    /*
     * 设置控件向右移动
     */
    public static void setMarginLeft(View view, int margin) {
        int size = SizeUtils.dp2px(margin);
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).leftMargin = size;
    }

    /*
     * 设置控件向左移动
     */
    public static void setMarginRight(View view, int margin) {
        int size = SizeUtils.dp2px(margin);
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).rightMargin = size;
    }

    /*
     * 设置控件边距
     */
    public static void setMargin(int margin, View... view) {
        int size = SizeUtils.dp2px(margin);
        if (view[0] == view[1]) {
            setMargin(view[0], size);
        } else {
            for (View aView : view) {
                setMargin(aView, size);
            }
        }
    }

    /*
     * 设置控件下移
     */
    public static void setMarginTop(int margin, View... view) {
        int size = SizeUtils.dp2px(margin);
        if (view[0] == view[1]) {
            setMarginTop(view[0], size);
        } else {
            for (View aView : view) {
                setMarginTop(aView, size);
            }
        }
    }

    /*
     * 设置控件上移
     */
    public static void setMarginBottom(int margin, View... view) {
        int size = SizeUtils.dp2px(margin);
        if (view[0] == view[1]) {
            setMarginBottom(view[0], size);
        } else {
            for (View aView : view) {
                setMarginBottom(aView, size);
            }
        }
    }

    /*
     * 设置控件向右移动
     */
    public static void setMarginLeft(int margin, View... view) {
        int size = SizeUtils.dp2px(margin);
        if (view[0] == view[1]) {
            setMarginLeft(view[0], size);
        } else {
            for (View aView : view) {
                setMarginLeft(aView, size);
            }
        }
    }

    /*
     * 设置控件向左移动
     */
    public static void setMarginRight(int margin, View... view) {
        int size = SizeUtils.dp2px(margin);
        if (view[0] == view[1]) {
            setMarginRight(view[0], size);
        } else {
            for (View aView : view) {
                setMarginRight(aView, size);
            }
        }
    }
}