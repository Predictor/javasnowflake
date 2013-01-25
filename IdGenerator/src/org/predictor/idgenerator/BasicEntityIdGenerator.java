package org.predictor.idgenerator;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;


import static java.lang.System.exit;

/**
 * org.predictor.idgenerator.BasicEntityIdGenerator
 *
 *
 *id is composed of:
 *time - 41 bits (millisecond precision w/ a custom epoch gives us 69 years)
 *configured machine id - 10 bits - gives us up to 1024 machines
 *sequence number - 12 bits - rolls over every 4096 per machine (with protection to avoid rollover in the same ms)
 *
 */
public class BasicEntityIdGenerator implements EntityIdGenerator {

    private final long datacenterIdBits = 10L;
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long timestampBits = 41L;

    private final long datacenterIdShift = 64L-datacenterIdBits;
    private final long timestampLeftShift = 64L-datacenterIdBits-timestampBits;
    private final long sequenceMax = 4096;
    private final long twepoch = 1288834974657L;
    private final long datacenterId;

    private  long lastTimestamp = -1L;
    private long sequence = 0L;


    public BasicEntityIdGenerator() throws GetHardwareIdFailed {
        datacenterId  = getDatacenterId();
        if (datacenterId > maxDatacenterId || datacenterId < 0){
            throw new GetHardwareIdFailed("datacenterId > maxDatacenterId");
        }
    }

    @Override
    public String generateLongId() throws InvalidSystemClock {
        long timestamp = System.currentTimeMillis();
        if(timestamp<lastTimestamp){
            throw new InvalidSystemClock("Clock moved backwards.  Refusing to generate id for "+ (
                    lastTimestamp - timestamp) +" milliseconds.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) % sequenceMax;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        Long id = ((timestamp - twepoch) << timestampLeftShift) |
                  (datacenterId << datacenterIdShift) |
                  sequence;
        return id.toString();
    }

    protected long tilNextMillis(long lastTimestamp){
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    protected long getDatacenterId() throws GetHardwareIdFailed {
        try{
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            long id = ((mac[mac.length-1] | (mac[mac.length-2]<<8))>>6);
            return id;
        } catch (SocketException e) {
            throw new GetHardwareIdFailed(e);
        } catch (UnknownHostException e) {
            throw new GetHardwareIdFailed(e);
        }
    }

    public static void main(String[] args) throws GetHardwareIdFailed, InvalidSystemClock {
        BasicEntityIdGenerator generator = new BasicEntityIdGenerator();
        int n = Integer.parseInt(args[0]);
        Set<String> ids =new HashSet<String>();
        for(int i=0; i<n; i++) {
           String id = generator.generateLongId();
           if(ids.contains(id)){
                System.out.println("Duplicate id:"+id);
                exit(1);
            }
            ids.add(id);
            System.out.println(id);
        }
    }

}
