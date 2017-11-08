package net.oschina.app.improve.main.synthesize.detail;

import net.oschina.app.improve.base.BaseListPresenter;
import net.oschina.app.improve.base.BaseListView;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.bean.comment.Comment;
import net.oschina.app.improve.detail.db.Behavior;

import java.util.List;

/**
 * 头条详情
 * Created by huanghaibin on 2017/10/23.
 */

interface ArticleDetailContract {

    interface EmptyView {

        void showCommentSuccess(Comment comment);

        void showCommentError(String message);

    }

    interface View extends BaseListView<Presenter,Article>{
        void showCommentSuccess(Comment comment);

        void showCommentError(String message);
    }

    interface Presenter extends BaseListPresenter {
        void putArticleComment(String content, long referId, long reAuthorId);

        void uploadBehaviors(List<Behavior> behaviors);

        void uploadBehaviors();
    }
}