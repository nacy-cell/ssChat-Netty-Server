package org.example.Server.Service;

public interface RegistService {
    Integer regist(String code,String email, String password);

    String Code(String email);
}
