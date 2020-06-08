package com.github.anhtom2000.hello;


// ÎÄ¼þÃû GreetingServer.java

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


@Component
@Slf4j
public class GreetingClient {


    Socket client;
    DataOutputStream out;
    DataInputStream in;
    private String receiveData;

    @PostConstruct
    public void finish() throws IOException {
        log.info("connect to server..");
        int PORT = 9999;
        String ADDR = "127.0.0.1";
        client = new Socket(ADDR, PORT);
        if (client.isConnected()) log.info("connect successful");
        out = new DataOutputStream(client.getOutputStream());
        new Thread(this::receive).start();
    }

    public void receive()  {

            while(true)
                if(client!=null)
                {
                    try {
                        in = new DataInputStream(client.getInputStream());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }

            while (true) {
                try {
                    byte[] data = new byte[1024];
                    int len = -1;
                    while((len = in.read(data))!=-1){
                        receiveData = new String(data,0,len);
                        log.info("receive data={}", receiveData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    public DataOutputStream getOut() {
        return out;
    }

    public String getReceiveData() {
        return receiveData;
    }
}