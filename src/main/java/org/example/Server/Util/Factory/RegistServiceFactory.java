package org.example.Server.Util.Factory;

import lombok.Getter;
import org.example.Server.Service.Impl.RegistServiceImpl;
import org.example.Server.Service.RegistService;

public abstract class RegistServiceFactory {

    @Getter
    private static RegistService registService = new RegistServiceImpl();
}
