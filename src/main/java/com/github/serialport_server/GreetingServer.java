package com.github.serialport_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author guang19
 * @date 2020/6/8
 * @description 串口程序服务端
 * @since 1.0.0
 */
public class GreetingServer {

    //串口程序服务器配置
    private final String localAddr = "127.0.0.1";
    private final int port = 9999;

    //存储已连接的客户端Socket
    private List<Socket> clientSockets = new ArrayList<>();

    //串口监听器
    private MySerialPortEventListener serialPortEventListener;

    //初始化
    public GreetingServer()
    {
        this.serialPortEventListener = new MySerialPortEventListener(this);
    }


    //启动串口程序服务器
    public void start()
    {
        try(ServerSocket serverSocket = new ServerSocket())
        {
            serverSocket.bind(new InetSocketAddress(localAddr,port));
            while (true)
            {
                try(Socket clientSocket = serverSocket.accept();
                    //当有客户端连接时，将客户端数据发送给串口
                    InputStream inFromClient = clientSocket.getInputStream())
                {
                    clientSockets.add(clientSocket);
                    //如果客户端有数据
                    if (inFromClient.available() > 0)
                    {
                        byte[] bytes = new byte[1024];
                        if(inFromClient.read(bytes) > 0)
                        {
                            String clientData = new String(bytes, StandardCharsets.UTF_8);
                            //将客户端请求的数据发送给串口
                            serialPortEventListener.sendToSerialPort(clientData);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //将串口的数据写入客户端socket
    public void sendToClient(String a) throws IOException
    {
        Iterator<Socket> clientSocketIterator = clientSockets.iterator();
        while (clientSocketIterator.hasNext())
        {
            try(OutputStream outToClientSocket = clientSocketIterator.next().getOutputStream())
            {
                outToClientSocket.write(a.getBytes(StandardCharsets.UTF_8));
                outToClientSocket.flush();
            }
            //写入完就移除，避免重复发送给一个客户端多个数据(如果只有一个客户端，那就不用移除了)
//            clientSocketIterator.remove();
        }
    }
}

