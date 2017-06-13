package com.hsh.baselib.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.hsh.baselib.R;
import com.hsh.baselib.adapter.ImageGridAdapter;
import com.hsh.baselib.model.ImageItem;
import com.hsh.baselib.widget.mutiPicturePicker.CustomConstants;
import com.hsh.baselib.widget.mutiPicturePicker.IntentConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 图片选择
 *
 */
public class ImageChooseActivity extends BaseNoPresenterActivity
{
	public static String ACTION_IMG_UPDATE="action_img_update";
	private List<ImageItem> mDataList = new ArrayList<ImageItem>();
	private String mBucketName;
	private int availableSize;
	private GridView mGridView;
	private ImageGridAdapter mAdapter;
	private Button mFinishBtn;
	private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();


	@Override
	protected int getLayoutResId() {
		return R.layout.act_image_choose;
	}

	@Override
	protected void initialize() {

		mDataList = (List<ImageItem>) getIntent().getSerializableExtra(
				IntentConstants.EXTRA_IMAGE_LIST);
		if (mDataList == null) mDataList = new ArrayList<ImageItem>();
		mBucketName = getIntent().getStringExtra(
				IntentConstants.EXTRA_BUCKET_NAME);

		if (TextUtils.isEmpty(mBucketName))
		{
			mBucketName = "请选择";
		}
		availableSize = getIntent().getIntExtra(
				IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
				CustomConstants.MAX_IMAGE_SIZE);

		initView();
		initListener();

	}


	private void initView()
	{
		setToolbarTitle(mBucketName,true);

		mGridView = (GridView) findViewById(R.id.gridview);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mAdapter = new ImageGridAdapter(ImageChooseActivity.this, mDataList);
		mGridView.setAdapter(mAdapter);
		mFinishBtn = (Button) findViewById(R.id.finish_btn);
		mRightTitle.setText("取消");


		mFinishBtn.setText("确定" + "(" + selectedImgs.size() + "/"
				+ availableSize + ")");
		mAdapter.notifyDataSetChanged();
	}

	private void initListener()
	{
		mFinishBtn.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setAction(ACTION_IMG_UPDATE);
				intent.putExtra(
						IntentConstants.EXTRA_IMAGE_LIST,
						(Serializable) new ArrayList<ImageItem>(selectedImgs
								.values()));
				sendBroadcast(intent);
				//startActivity(new Intent(ImageChooseActivity.this,cls));
				finish();

			}

		});

		mGridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{

				ImageItem item = mDataList.get(position);
				if (item.isSelected)
				{
					item.isSelected = false;
					selectedImgs.remove(item.imageId);
				}
				else
				{
					if (selectedImgs.size() >= availableSize)
					{
						Toast.makeText(ImageChooseActivity.this,
								"不能超过"+availableSize+"张图片",
								Toast.LENGTH_SHORT).show();
						return;
					}
					item.isSelected = true;
					selectedImgs.put(item.imageId, item);
				}

				mFinishBtn.setText("确定"+ "(" + selectedImgs.size() + "/"
						+ availableSize + ")");
				mAdapter.notifyDataSetChanged();
			}

		});

		mRightTitle.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
//				Intent intent = new Intent(ImageChooseActivity.this,
//						WriteMessageActivity.class);
//				startActivity(intent);
				finish();
			}
		});

	}
}