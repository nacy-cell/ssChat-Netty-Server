package org.example.Server.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private Integer userID;
    private String password;
    private Integer lastMessageId;

}
