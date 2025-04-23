package org.pehlivanmert.kafkaexample.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.pehlivanmert.kafkaexample.entity.User;

@Data
@Getter
@Setter
@Builder
public class UserResponse extends BaseResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private AddressResponseDto address;

    public static UserResponse getUserResponseWithAddress(User user, AddressResponseDto address) {
        UserResponse userResponse = UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(address)
                .build();

        userResponse.setId(user.getId());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }
}
