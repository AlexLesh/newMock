package com.example.newMock1.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RequestDTO {

    private String rqUID;
    private String clientId;
    private String account;
    private String openDate;
    private String CloseDate;

    /*private String currency;
    private String balance;
    private String maxLimit;*/

}
