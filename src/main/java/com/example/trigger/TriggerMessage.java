package com.example.trigger;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@AllArgsConstructor
@Getter
public class TriggerMessage {

    private Date date;
    private String status;
}
