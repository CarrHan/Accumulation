package com.hsh.baselib.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.hsh.baselib.R;
import com.hsh.baselib.adapter.ImageBucketAdapter;
import com.hsh.baselib.model.ImageBucket;
import com.hsh.baselib.model.ImageItem;
import com.hsh.baselib.utils.AppUtil;
import com.hsh.baselib.utils.BitMapUtil;
import com.hsh.baselib.utils.LogUtil;
import com.hsh.baselib.widget.mutiPicturePicker.CustomConstants;
import com.hsh.baselib.widget.mutiPicturePicker.ImageFetcher;
import com.hsh.baselib.widget.mutiPicturePicker.ImageTemp;
import com.hsh.baselib.widget.mutiPicturePicker.IntentConstants;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择相册
 *
 */

public class ImageBucketChooseActivity extends BaseNoPresenterActivity
{
	private ImageFetcher mHelper;
	private List<ImageBucket> mDataList = new ArrayList<>();
	private ListView mListView;
	private ImageBucketAdapter mAdapter;
	private int availableSize;

	MyBroadCast myBroadCast;
	class MyBroadCast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.act_image_bucket_choose;
	}

	@Override
	protected void initialize() {

		IntentFilter intentFilter=new IntentFilter(ImageChooseActivity.ACTION_IMG_UPDATE);
		myBroadCast=new MyBroadCast();
		registerReceiver(myBroadCast, intentFilter);

		mHelper = ImageFetcher.getInstance(getApplicationContext());
		initData();
		initView();
	}

	private void initData()
	{
		mDataList = mHelper.getImagesBucketList(false);
		availableSize = getIntent().getIntExtra(
				IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
				CustomConstants.MAX_IMAGE_SIZE);
	}

	private void initView()
	{
		setToolbarTitle("相册",true);
		mListView = (ListView) findViewById(R.id.listview);
		mAdapter = new ImageBucketAdapter(this, mDataList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{

				selectOne(position);

				Intent intent = new Intent(ImageBucketChooseActivity.this,
						ImageChooseActivity.class);
				intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST,
						(Serializable) mDataList.get(position).imageList);
				intent.putExtra(IntentConstants.EXTRA_BUCKET_NAME,
						mDataList.get(position).bucketName);
				intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
						availableSize);

				startActivity(intent);
			}
		});

		mRightTitle.setText("取消");
		mRightTitle.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
//				Intent intent = new Intent(ImageBucketChooseActivity.this,
//						WriteMessageActivity.class);
//				startActivity(intent);
				finish();
			}
		});
	}

	private void selectOne(int position)
	{
		int size = mDataList.size();
		for (int i = 0; i != size; i++)
		{
			if (i == position) mDataList.get(i).selected = true;
			else
			{
				mDataList.get(i).selected = false;
			}
		}
		mAdapter.notifyDataSetChanged();
	}

}
