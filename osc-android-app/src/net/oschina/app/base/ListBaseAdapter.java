package net.oschina.app.base;

import java.util.ArrayList;
import java.util.List;

import net.oschina.app.R;
import net.oschina.app.util.TDevice;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListBaseAdapter extends BaseAdapter {
	public static final int STATE_EMPTY_ITEM = 0;
	public static final int STATE_LOAD_MORE = 1;
	public static final int STATE_NO_MORE = 2;
	public static final int STATE_NO_DATA = 3;
	public static final int STATE_LESS_ONE_PAGE = 4;
	public static final int STATE_NETWORK_ERROR = 5;

	protected int state = STATE_LESS_ONE_PAGE;

	protected int _loadmoreText;
	protected int _loadFinishText;
	protected int mScreenWidth;

	private LayoutInflater mInflater;

	private LinearLayout mFooterView;

	protected LayoutInflater getLayoutInflater(Context context) {
		if (mInflater == null) {
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return mInflater;
	}

	public View getFooterView() {
		return this.mFooterView;
	}

	public void setScreenWidth(int width) {
		mScreenWidth = width;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	@SuppressWarnings("rawtypes")
	protected ArrayList _data = new ArrayList();

	public ListBaseAdapter() {
		_loadmoreText = R.string.loading;
		_loadFinishText = R.string.loading_no_more;
	}

	@Override
	public int getCount() {
		switch (getState()) {
		case STATE_EMPTY_ITEM:
			return getDataSize() + 1;
		case STATE_NETWORK_ERROR:
		case STATE_LOAD_MORE:
			return getDataSize() + 1;
		case STATE_NO_DATA:
			return 0;
		case STATE_NO_MORE:
			return getDataSize() + 1;
		case STATE_LESS_ONE_PAGE:
			return getDataSize();
		default:
			break;
		}
		return getDataSize();
	}

	public int getDataSize() {
		return _data.size();
	}

	@Override
	public Object getItem(int arg0) {
		if (_data.size() > arg0) {
			return _data.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setData(ArrayList data) {
		_data = data;
		onRequestComplete(data);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getData() {
		return _data == null ? (_data = new ArrayList()) : _data;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addData(List data) {
		if (_data == null) {
			_data = new ArrayList();
		}
		_data.addAll(data);
		onRequestComplete(data);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addItem(Object obj) {
		if (_data == null) {
			_data = new ArrayList();
		}
		_data.add(obj);
		onRequestComplete(obj);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addItem(int pos, Object obj) {
		if (_data == null) {
			_data = new ArrayList();
		}
		_data.add(pos, obj);
		onRequestComplete(obj);
	}

	public void removeItem(Object obj) {
		_data.remove(obj);
		onRequestComplete(obj);
	}

	public void clear() {
		_data.clear();
		onRequestComplete();
	}

	public void setLoadmoreText(int loadmoreText) {
		_loadmoreText = loadmoreText;
	}

	public void setLoadFinishText(int loadFinishText) {
		_loadFinishText = loadFinishText;
	}

	protected boolean loadMoreHasBg() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == getCount() - 1) {// 最后一条
			if (getState() == STATE_LOAD_MORE || getState() == STATE_NO_MORE
					|| state == STATE_EMPTY_ITEM
					|| getState() == STATE_NETWORK_ERROR) {
				mFooterView = (LinearLayout) LayoutInflater.from(
						parent.getContext()).inflate(R.layout.list_cell_footer,
						null);
				if (!loadMoreHasBg()) {
					mFooterView.setBackgroundDrawable(null);
				}
				ProgressBar progress = (ProgressBar) mFooterView
						.findViewById(R.id.progressbar);
				TextView text = (TextView) mFooterView.findViewById(R.id.text);
				switch (getState()) {
				case STATE_LOAD_MORE:
					mFooterView.setVisibility(View.VISIBLE);
					progress.setVisibility(View.VISIBLE);
					text.setVisibility(View.VISIBLE);
					text.setText(_loadmoreText);
					break;
				case STATE_NO_MORE:
					mFooterView.setVisibility(View.VISIBLE);
					progress.setVisibility(View.GONE);
					text.setVisibility(View.VISIBLE);
					text.setText(_loadFinishText);
					break;
				case STATE_EMPTY_ITEM:
					progress.setVisibility(View.GONE);
					mFooterView.setVisibility(View.GONE);
					text.setVisibility(View.GONE);
					break;
				case STATE_NETWORK_ERROR:
					mFooterView.setVisibility(View.VISIBLE);
					progress.setVisibility(View.GONE);
					text.setVisibility(View.VISIBLE);
					if (TDevice.hasInternet()) {
						text.setText("加载出错了");
					} else {
						text.setText("没有可用的网络");
					}
					break;
				default:
					progress.setVisibility(View.GONE);
					mFooterView.setVisibility(View.GONE);
					text.setVisibility(View.GONE);
					break;
				}
				return mFooterView;
			}
		}
		return getRealView(position, convertView, parent);
	}

	protected View getRealView(int position, View convertView, ViewGroup parent) {
		return null;
	}

	private void onRequestComplete(final List<Object> result) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (result.size() == 0) { // no results

				} else {

					notifyDataSetChanged();
				}
			}
		}, 1);
	}

	private void onRequestComplete(final Object result) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (result != null) { // no results

				} else {

					notifyDataSetChanged();
				}
			}
		}, 1);
	}

	private void onRequestComplete() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				notifyDataSetChanged();
			}
		}, 1);
	}
}