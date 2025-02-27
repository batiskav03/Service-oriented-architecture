package com.soa.ejb;

import com.soa.ejb.remotes.MyEjbRemote;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless(name = "MyEjb", mappedName = "java:global/worker-ejb/MyEjb!com.soa.ejb.MyEjb")
@Remote(MyEjbRemote.class)
public class MyEjb implements MyEjbRemote {
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}