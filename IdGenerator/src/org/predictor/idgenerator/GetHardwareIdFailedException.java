package org.predictor.idgenerator;

/**
 * Created with IntelliJ IDEA.
 * User: predictor
 * Date: 25.01.13
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
public class GetHardwareIdFailedException extends Exception {
    GetHardwareIdFailedException(String reason){
        super(reason);
    }
    GetHardwareIdFailedException(Exception e){
        super(e);
    }
}
