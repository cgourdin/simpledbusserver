package com.inria.testserver.simpledbusserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.freedesktop.DBus;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;

/**
 *
 * @author cgourdin
 */
public class SimpleBusServer implements ISimpleBus , DBus.Properties{

    private DBusConnection dbusConnection;
    // private boolean stop = false;
    private String bufferSchema;
    
    public SimpleBusServer() {
        System.out.println("assign Schema");
        setSchema("schema.xml");
    }
    
    @Override
    public String testStringBuffer(String buffer) {
        // stop = true;
        return "Buffer received : " + buffer;
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    public void start() {
        try {
            dbusConnection = DBusConnection.getConnection(DBusConnection.SESSION);
            dbusConnection.requestBusName("com.inria.simplebus");
            dbusConnection.exportObject("/Main", this);
            System.out.println("Connected to : " + dbusConnection.getUniqueName());
            
//            while (!stop) {
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//                }
//            }
            // dbusConnection.disconnect();
        } catch (DBusException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SimpleBusServer().start();
    }
    
    @Override
    public <A> A Get(String interfaceName, String property) {
        
        if ("schema".equalsIgnoreCase(property)) {
            return (A) this.bufferSchema;
        } else {
            return null;
        }
    }

    @Override
    public <A> void Set(String string, String string1, A a) {
        // no op.
    }

    @Override
    public Map<String, Variant> GetAll(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param schemaFilename 
     */
    private void setSchema(String schemaFilename) {
        ByteArrayOutputStream os = null;
        
        String absPath = new File(".").getAbsolutePath();
        InputStream in = null;
        try {
            in = new FileInputStream(absPath + "/" + schemaFilename);
            os = new ByteArrayOutputStream();
            
            this.bufferSchema = Utils.copyStream(in, os);
            // logger.info("Schema returned: " + this.schema);
            
            // this.schema = os.toString("UTF-8");
        } catch (IOException e) {
            Utils.closeQuietly(in);
            Utils.closeQuietly(os);
            this.bufferSchema = null;
        }
    }
}
