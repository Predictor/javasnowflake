package org.predictor.idgenerator;

/**
 * Created with IntelliJ IDEA.
 * User: predictor
 * Date: 25.01.13
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class InvalidSystemClock extends Exception {
    public InvalidSystemClock(String message){
        super(message);
    }
}
