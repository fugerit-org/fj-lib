package org.fugerit.java.core.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ObjectIO {

    public static Object fullSerializationTest( Object value ) throws IOException {
        byte[] data = serialize(value);
        return deserialize(data);
    }

    public static Object deserialize( byte[] data ) throws IOException {
        Object res = null;
        try ( ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( data ) ) ) {
            res = ois.readObject();
            log.info( "deserializeTest, read object : {}", res );
        } catch (ClassNotFoundException e) {
            throw new IOException( e.toString(), e );
        }
        return res;
    }

    public static byte[] serialize( Object value ) throws IOException {
        byte[] res = null;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream( os ) ) {
            oos.writeObject( value );
            res = os.toByteArray();
            log.info( "serializeTest, total byte written : {}", res.length );
        }
        return res;
    }

}
