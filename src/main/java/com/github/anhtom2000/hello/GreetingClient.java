package com.github.anhtom2000.hello;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//串口程序客户端

@Component
@Slf4j
public class GreetingClient {


    //串口程序服务器配置
    private final String localAddr = "127.0.0.1";
    private final int port = 9999;

    private Socket clientSocket;

    //初始化GreetingClient,连接到串口程序服务端
    public GreetingClient() throws IOException
    {
        start();
    }

    //启动串口程序客户端，连接到串口程序服务端
    public void start() throws IOException
    {
        try
        {
            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(localAddr,port));
            while (clientSocket.isConnected())
            {
                log.info("connect successful");
            }
            //开启一个线程 不断的接受串口程序服务端的的响应
            new Thread(()->
            {
                receiveFromSerialPortServer(clientSocket);
            }).start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //从串口程序服务端接收的数据
    private void receiveFromSerialPortServer(Socket clientSocket)
    {
        try(InputStream inFromClient = clientSocket.getInputStream())
        {
            while (true)
            {
                //如果串口服务器有响应的数据
                if (inFromClient.available() > 0)
                {
                    byte[] bytes = new byte[1024];
                    if (inFromClient.read(bytes) > 0)
                    {
                        String receiveData = new String(bytes, StandardCharsets.UTF_8);
                        log.info("receive data={}", receiveData);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //向串口服务器发送请求
    public void requestToSerialPortServer(String data)
    {
        try(OutputStream outToClientSocket = clientSocket.getOutputStream())
        {
            outToClientSocket.write(data.getBytes(StandardCharsets.UTF_8));
            outToClientSocket.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}