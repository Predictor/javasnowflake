package org.predictor.idgenerator;


import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class BasicEntityIdGeneratorTest {

    @Test
    public void testForNegativeNumbers() throws GetHardwareIdFailedException, InvalidSystemClockException, SocketException {
        //sequential numbers
        Set<String> ids = new HashSet<String>();
        for(int i = 0; i< 100000; i++ ){
            String result = BasicEntityIdGenerator.getInstance().generateLongId();
            //ids should be unique
            assertTrue(!ids.contains(result));
            ids.add(result);
            assertTrue("should not start with - ", !result.startsWith("-"));
            System.out.println(result);
        }
    }
}
