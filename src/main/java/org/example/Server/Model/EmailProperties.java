package org.example.Server.Model;

import lombok.Data;

@Data
public class EmailProperties {
    public String user;
    public String code;
    public String host;
    private boolean auth = true;
}
