package com.inria.testserver.simpledbusserver;

import org.freedesktop.DBus;
import org.freedesktop.dbus.DBusInterface;

/**
 *
 * @author Christophe Gourdin - INRIA
 */
public interface ISimpleBus extends DBusInterface, DBus.Properties {
    
    @DBus.Description("Petit message de HelloWorld avec un nom en paramètre")
    public String testStringBuffer(String buffer);
    
}
