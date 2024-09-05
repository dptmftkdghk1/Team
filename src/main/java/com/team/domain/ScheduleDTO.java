package com.team.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO {
    private Integer scheduleNo;
    private String scheduleTitle;
    private String scheduleStartDate;
    private String scheduleEndDate;
    private String scheduleLocation;
    private String scheduleGuests;
    private String scheduleRemarks;
}
