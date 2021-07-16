package com.artemget.oil_service.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Record {
    private final long id;
    private final String userName;
    private final long inserted;
    private final long corrupted;
    private final LocalDateTime insertionDate;
}