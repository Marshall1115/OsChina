package net.oschina.app.improve.main.synthesize.top;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import net.oschina.app.R;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 头条数据
 * Created by huanghaibin on 2017/10/23.
 */

public class TopAdapter extends BaseRecyclerAdapter<Article> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {

    private static final int VIEW_TYPE_NOT_IMG = 1;
    private static final int VIEW_TYPE_ONE_IMG = 2;
    private static final int VIEW_TYPE_THREE_IMG = 3;
    private RequestManager mLoader;

    public TopAdapter(Context context, int mode) {
        super(context, mode);
        setOnLoadingHeaderCallBack(this);
        mLoader = Glide.with(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        Article article = getItem(position);
        if (article != null) {
            String imgs[] = article.getImgs();
            if (imgs == null || imgs.length == 0)
                return VIEW_TYPE_NOT_IMG;
            if (imgs.length < 3)
                return VIEW_TYPE_ONE_IMG;
            return VIEW_TYPE_THREE_IMG;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new HeaderHolder(mHeaderView);
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        if (type == VIEW_TYPE_NOT_IMG) {
            return new TextHolder(mInflater.inflate(R.layout.item_list_article_not_img, parent, false));
        } else if (type == VIEW_TYPE_ONE_IMG) {
            return new OneImgHolder(mInflater.inflate(R.layout.item_list_article_one_img, parent, false));
        } else {
            return new ThreeImgHolder(mInflater.inflate(R.layout.item_list_article_three_img, parent, false));
        }
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case VIEW_TYPE_NOT_IMG:
                TextHolder h = (TextHolder) holder;
                h.mTextTitle.setText(item.getTitle());
                h.mTextDesc.setText(item.getDesc());
                h.mTextTime.setText(parsePubDate(item.getPubDate()));
                h.mTextOrigin.setText(TextUtils.isEmpty(item.getAuthorName()) ? "匿名" : item.getAuthorName());
                break;
            case VIEW_TYPE_ONE_IMG:
                OneImgHolder h1 = (OneImgHolder) holder;
                h1.mTextTitle.setText(item.getTitle());
                h1.mTextTime.setText(parsePubDate(item.getPubDate()));
                h1.mTextOrigin.setText(item.getAuthorName());
                mLoader.load(item.getImgs()[0])
                        .fitCenter()
                        .error(R.mipmap.ic_split_graph)
                        .into(h1.mImageView);
                break;
            case VIEW_TYPE_THREE_IMG:
                ThreeImgHolder h2 = (ThreeImgHolder) holder;
                h2.mTextTitle.setText(item.getTitle());
                h2.mTextTime.setText(parsePubDate(item.getPubDate()));
                h2.mTextOrigin.setText(TextUtils.isEmpty(item.getAuthorName()) ? "匿名" : item.getAuthorName());
                mLoader.load(item.getImgs()[0])
                        .fitCenter()
                        .error(R.mipmap.ic_split_graph)
                        .into(h2.mImageOne);
                mLoader.load(item.getImgs()[1])
                        .fitCenter()
                        .into(h2.mImageTwo);
                mLoader.load(item.getImgs()[2])
                        .fitCenter()
                        .error(R.mipmap.ic_split_graph)
                        .into(h2.mImageThree);
                break;
        }

    }


    private static final class TextHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle,
                mTextDesc,
                mTextTime,
                mTextOrigin,
                mTextCommentCount;

        TextHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTextDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            mTextTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTextOrigin = (TextView) itemView.findViewById(R.id.tv_origin);
            mTextCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
        }
    }

    private static final class OneImgHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle,
                mTextTime,
                mTextOrigin,
                mTextCommentCount;
        ImageView mImageView;

        OneImgHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTextTime = (TextView) itemView.findViewById(R.id.tv_time);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_image);
            mTextOrigin = (TextView) itemView.findViewById(R.id.tv_origin);
            mTextCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
        }
    }

    private static final class ThreeImgHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle,
                mTextTime,
                mTextOrigin,
                mTextCommentCount;
        ImageView mImageOne, mImageTwo, mImageThree;

        ThreeImgHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTextTime = (TextView) itemView.findViewById(R.id.tv_time);
            mImageOne = (ImageView) itemView.findViewById(R.id.iv_img_1);
            mImageTwo = (ImageView) itemView.findViewById(R.id.iv_img_2);
            mImageThree = (ImageView) itemView.findViewById(R.id.iv_img_3);
            mTextOrigin = (TextView) itemView.findViewById(R.id.tv_origin);
            mTextCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
        }
    }


    private static final class HeaderHolder extends RecyclerView.ViewHolder {
        HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    private String parsePubDate(String pubDate) {
        if (TextUtils.isEmpty(pubDate) || pubDate.length() < 8)
            return pubDate;
        int year = parseInt(pubDate.substring(0, 4));
        int month = parseInt(pubDate.substring(4, 6));
        int day = parseInt(pubDate.substring(6, 8));
        String date = String.format("%s-%s-%s", year,
                month,
                day);
        if (isToday(date)) {
            return "今天";
        }
        if (isYesterday(year, month, day))
            return "昨天";
        return date;
    }

    private int parseInt(String intStr) {
        try {
            return Integer.parseInt(intStr);
        } catch (Exception e) {
            return 0;
        }
    }

    private boolean isToday(String pubDate) {
        String today = StringUtils.getCurrentTimeStr();
        return pubDate.equalsIgnoreCase(today);
    }

    private boolean isYesterday(int year, int month, int day) {
        Calendar mCurrentDate = Calendar.getInstance();
        mCurrentDate.set(year, month, day - 1, 0, 0, 0);
        Date date = new Date(mCurrentDate.getTimeInMillis());
        long delta = new Date().getTime() - date.getTime();
        return delta <= 48L * 3600000L;
    }
}
