package net.oschina.app.contract;

/**
 * Created by qiujuer
 * on 16/5/28.
 */

public interface BlogDetailContract {
    interface Operator {
        // 收藏
        void toFavorite();

        // 分享
        void toShare();

        // 关注
        void toFollow();

        // 举报
        void toReport();

        // 提交评价
        void toSendComment(long id, String comment);
    }

    interface View {
        void toFavoriteOk();

        void toShareOk();

        void toFollowOk();

        void toSendCommentOk();
    }
}