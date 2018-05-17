package com.zhlzzz.together.article.reply;

import com.google.common.base.Strings;
import com.zhlzzz.together.article.ArticleEntity;
import com.zhlzzz.together.article.ArticleNotFoundException;
import com.zhlzzz.together.article.ArticleRepository;
import com.zhlzzz.together.article.discuss.DiscussEntity;
import com.zhlzzz.together.article.discuss.DiscussNotFoundException;
import com.zhlzzz.together.article.discuss.DiscussRepository;
import com.zhlzzz.together.user.UserNotFoundException;
import com.zhlzzz.together.user.user_label.UserLabelNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
public class ReplyServiceImpl implements ReplyService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final ReplyRepository replyRepository;
    private final ArticleRepository articleRepository;
    private final DiscussRepository discussRepository;

    @Override
    public ReplyEntity addReply(Long articleId,Long discussId,ReplyParam replyParam) {
        ArticleEntity articleEntity = articleRepository.findById(articleId).orElseThrow(()->new ArticleNotFoundException(articleId));
        DiscussEntity discussEntity = discussRepository.findById(discussId).orElseThrow(() -> new DiscussNotFoundException(discussId));
        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setArticleId(articleEntity.getId());
        replyEntity.setDiscussId(discussEntity.getId());
        if (StringUtils.isNotEmpty(replyParam.getContent()))
            replyEntity.setContent(replyParam.getContent());
        replyEntity.setCreateTime(LocalDateTime.now());
        return replyRepository.save(replyEntity);
    }

    @Override
    public ReplyEntity updateReply(Long articleId,Long discussId,Long id,ReplyParam replyParam) {
        ReplyEntity reply = replyRepository.findById(id).orElseThrow(()->new ReplyNotFoundException(id));
        if (StringUtils.isNotEmpty(replyParam.getContent()))
            reply.setContent(replyParam.getContent());
        return replyRepository.save(reply);
    }

    @Override
    public Optional<? extends ReplyEntity> getReplyById(Long articleId,Long discussId,Long id) {
        return replyRepository.findById(id);
    }

    @Override
    public void deleteReply(Long articleId,Long discussId,Long id) {
        ReplyEntity replyEntity = replyRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        tt.execute((s)-> {
            em.createQuery("DELETE FROM ReplyEntity u WHERE u.id = :id")
                    .setParameter("id", replyEntity.getId())
                    .executeUpdate();
            return true;
        });
    }

    @Override
    public Set<ReplyEntity> findByArticleIdAndDiscussId(Long articleId,Long discussId){
        return replyRepository.findByArticleIdAndDiscussId(articleId,discussId);
    }
}
