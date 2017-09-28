package com.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * An example message:
 *
 * <notification>
 *     <email>example@gmail.com</email>
 *     <status>ORDER_DISPATCHED</status>
 * </notification>
 */

@Builder
@Data
@ToString
public class Notification {

    public String email;
    public String status;

}
