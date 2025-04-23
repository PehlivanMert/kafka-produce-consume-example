package org.pehlivanmert.notificationconsumer.repository;

import org.pehlivanmert.notificationconsumer.entity.Notification;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CouchbaseRepository<Notification, Long> {
}
