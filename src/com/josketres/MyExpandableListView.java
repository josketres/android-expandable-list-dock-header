package com.josketres;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

public class MyExpandableListView extends ExpandableListView {

	public interface OnScrollChangedListener {
		void onScrollChanged(ExpandableListView who, int t, int oldt,
				View firstChild);
	}

	private OnScrollChangedListener mOnScrollChangedListener;
	private int mOldTop;

	public MyExpandableListView(Context context) {
		super(context);
	}

	public MyExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyExpandableListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mOnScrollChangedListener != null) {
			View firstChild = getChildAt(0);
			int top = (firstChild == null) ? 0 : firstChild.getTop();
			mOnScrollChangedListener.onScrollChanged(this, top, mOldTop,
					firstChild);
			mOldTop = top;
		}
	}

	public void setOnScrollChangedListener(OnScrollChangedListener listener) {
		mOnScrollChangedListener = listener;
	}

}
