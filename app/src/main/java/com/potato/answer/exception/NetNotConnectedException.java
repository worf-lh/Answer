package com.potato.answer.exception;

/**
 * @author lihao
 * @date 2019/7/10 14:44
 * @desc 网络未连接且继续做请求网络操作时抛出
 */
public class NetNotConnectedException extends RuntimeException {

    public NetNotConnectedException() {
        super("The network is not connected and can not access the network");
    }

}
