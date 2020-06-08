package com.github.serialport_server;

/**
 * @author guang19
 * @date 2020/6/8
 * @description 单片机串口程序启动
 * @since 1.0.0
 */
public class Bootstrap
{
    public static void main(String[] args)
    {
        GreetingServer greetingServer = new GreetingServer();
        greetingServer.start();
    }
}
