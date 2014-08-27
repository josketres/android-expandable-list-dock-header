package com.josketres;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;

@SuppressLint("InflateParams")
public class MainActivity extends Activity {

	private ArrayList<String> parentItems = new ArrayList<String>();
	private ArrayList<Object> childItems = new ArrayList<Object>();
	private MyExpandableListView expandableList;
	private Drawable mHeaderIconDrawable;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// this is not really necessary as ExpandableListActivity contains an
		// ExpandableList
		setContentView(R.layout.activity_main);

		expandableList = (MyExpandableListView) findViewById(R.id.expandableListView);

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		setListHeader();
		setGroupParents();
		setChildData();

		MyExpandableListAdapter adapter = new MyExpandableListAdapter(
				parentItems, childItems);

		adapter.setInflater(
				(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
				this);
		expandableList.setAdapter(adapter);
		expandableList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				expandableList.expandGroup(groupPosition);
				return false;
			}
		});

		expandableList.setOnScrollChangedListener(mOnScrollChangedListener);

		headerDock = (LinearLayout) findViewById(R.id.header_dock);
		headerDock.setVisibility(View.INVISIBLE);

		mHeaderIconDrawable = ((ImageView) findViewById(R.id.headerIcon))
				.getDrawable();
	}

	private void setListHeader() {

		headerView = getLayoutInflater().inflate(R.layout.list_header, null);
		expandableList.addHeaderView(headerView);

	}

	public void setGroupParents() {
		parentItems.add("Android");
		parentItems.add("Core Java");
		parentItems.add("Desktop Java");
		parentItems.add("Enterprise Java");
	}

	public void setChildData() {

		// Android
		ArrayList<String> child = new ArrayList<String>();
		child.add("Core");
		child.add("Games");
		childItems.add(child);

		// Core Java
		child = new ArrayList<String>();
		child.add("Apache");
		child.add("Applet");
		child.add("AspectJ");
		child.add("Beans");
		child.add("Crypto");
		childItems.add(child);

		// Desktop Java
		child = new ArrayList<String>();
		child.add("Accessibility");
		child.add("AWT");
		child.add("ImageIO");
		child.add("Print");
		childItems.add(child);

		// Enterprise Java
		child = new ArrayList<String>();
		child.add("EJB3");
		child.add("GWT");
		child.add("Hibernate");
		child.add("JSP");
		childItems.add(child);
	}

	private MyExpandableListView.OnScrollChangedListener mOnScrollChangedListener = new MyExpandableListView.OnScrollChangedListener() {
		public void onScrollChanged(ExpandableListView who, int t, int oldt,
				View firstChild) {

			int offset = headerView.getHeight() - headerDock.getHeight();
			Log.i("test", "offset:" + offset);

			if (firstChild == headerView) {
				Log.i("test", "top:" + t);
				Log.i("test", "oldtop:" + oldt);
				if (t < -offset && oldt >= -offset) {
					headerDock.setVisibility(View.VISIBLE);
				} else if (t >= -offset && oldt < -offset || t == 0) {
					headerDock.setVisibility(View.INVISIBLE);
				}

				float ratio = Math.min(1.0f, (float) 1.0
						- ((float) t / -offset));
				int newAlpha = (int) (ratio * 255);
				Log.i("test", "newAlpha:" + newAlpha);
				mHeaderIconDrawable.setAlpha(newAlpha);
			}

			/*
			 * final int headerHeight = findViewById(R.id.image_header)
			 * .getHeight() - getActionBar().getHeight(); final float ratio =
			 * (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
			 * final int newAlpha = (int) (ratio * 255);
			 * mActionBarBackgroundDrawable.setAlpha(newAlpha);
			 */
		}
	};
	private LinearLayout headerDock;
	private View headerView;

}