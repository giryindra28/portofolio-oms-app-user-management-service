package org.portfolio.giry.oms.helper;

import jakarta.enterprise.context.ApplicationScoped;
import org.portfolio.giry.oms.Exceptions.ProcessExceptions;

@ApplicationScoped
public class HelperService {
    public static void doCheckAdmin(String roleName){
        if(!roleName.equals("admin")){
            throw new ProcessExceptions("you do not have permission to perform this action");
        }
    }
}
