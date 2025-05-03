package org.example.Server.message.responseMessage;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Server.message.Message;


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class RequestResponseMessage extends Message {
    private boolean success;
    private String reason;

}
