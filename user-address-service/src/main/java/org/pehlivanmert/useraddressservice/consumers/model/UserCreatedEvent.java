package org.pehlivanmert.useraddressservice.consumers.model;

import lombok.*;
import org.pehlivanmert.useraddressservice.entity.Address;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreatedEvent {
    private Long id;
    private String addressText;

    public static Address getAddressEntityFromEvent(UserCreatedEvent event) {
        return Address.builder()
                .userId(event.getId())
                .addressText(event.getAddressText())
                .build();
    }
}