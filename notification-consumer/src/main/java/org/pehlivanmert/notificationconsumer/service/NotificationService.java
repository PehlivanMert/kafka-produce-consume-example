package org.pehlivanmert.notificationconsumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pehlivanmert.notificationconsumer.entity.Notification;
import org.pehlivanmert.notificationconsumer.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void save(Notification notification) {
        Notification savedEntity = notificationRepository.save(notification);
        log.info("Notification saved with id : {}", savedEntity.getId());
    }
}
