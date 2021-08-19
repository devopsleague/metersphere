package io.metersphere.notice.sender.impl;

import io.metersphere.base.domain.Notification;
import io.metersphere.commons.constants.NotificationConstants;
import io.metersphere.commons.utils.LogUtil;
import io.metersphere.notice.domain.MessageDetail;
import io.metersphere.notice.domain.Receiver;
import io.metersphere.notice.sender.AbstractNoticeSender;
import io.metersphere.notice.sender.NoticeModel;
import io.metersphere.notice.service.NotificationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class InSiteNoticeSender extends AbstractNoticeSender {

    @Resource
    private NotificationService notificationService;

    public void sendAnnouncement(MessageDetail messageDetail, NoticeModel noticeModel, String context) {
        List<Receiver> receivers = noticeModel.getReceivers();
        if (CollectionUtils.isEmpty(receivers)) {
            return;
        }

        receivers.forEach(receiver -> {
            LogUtil.debug("发送站内通知: {}, 内容: {}", receiver, context);
            Notification notification = new Notification();
            notification.setTitle(noticeModel.getSubject());
            notification.setContent(context);
            notification.setOperator(noticeModel.getOperator());
            notification.setOperation(noticeModel.getEvent());
            notification.setResourceId((String) noticeModel.getParamMap().get("id"));
            notification.setResourceType(messageDetail.getTaskType());
            //
            if (noticeModel.getParamMap().get("name") != null) {
                notification.setResourceName((String) noticeModel.getParamMap().get("name"));
            }

            if (noticeModel.getParamMap().get("title") != null) {
                notification.setResourceName((String) noticeModel.getParamMap().get("title"));
            }

            notification.setType(receiver.getType());
            notification.setStatus(NotificationConstants.Status.UNREAD.name());
            notification.setCreateTime(System.currentTimeMillis());
            notification.setReceiver(receiver.getUserId());
            notificationService.sendAnnouncement(notification);
        });
    }

    @Override
    public void send(MessageDetail messageDetail, NoticeModel noticeModel) {
        String context = super.getContext(messageDetail, noticeModel);
        sendAnnouncement(messageDetail, noticeModel, context);
    }
}
