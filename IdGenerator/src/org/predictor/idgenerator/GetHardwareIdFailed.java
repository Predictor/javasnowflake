package org.predictor.idgenerator;

/**
 * Created with IntelliJ IDEA.
 * User: predictor
 * Date: 25.01.13
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
public class GetHardwareIdFailed extends Exception {
    GetHardwareIdFailed(String reason){
        super(reason);
    }
    GetHardwareIdFailed(Exception e){
        super(e);
    }
}
