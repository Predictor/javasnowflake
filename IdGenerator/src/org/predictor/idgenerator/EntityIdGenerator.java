package org.predictor.idgenerator;

/**
 * org.predictor.idgenerator.EntityIdGenerator
 *
 * @author Maxim Khodanovich
 * @version 21.01.13 17:18
 */
public interface EntityIdGenerator {
    String generateLongId() throws InvalidSystemClockException, GetHardwareIdFailedException;
}
