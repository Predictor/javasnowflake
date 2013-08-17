package org.predictor.idgenerator;

/**
 * Created with IntelliJ IDEA.
 * User: predictor
 * Date: 25.01.13
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class InvalidSystemClockException extends Exception {
    public InvalidSystemClockException(String message){
        super(message);
    }
}
