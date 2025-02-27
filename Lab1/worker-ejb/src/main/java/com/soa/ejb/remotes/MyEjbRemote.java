package com.soa.ejb.remotes;

import javax.ejb.Remote;

@Remote
public interface MyEjbRemote {
    String sayHello(String name);
}
