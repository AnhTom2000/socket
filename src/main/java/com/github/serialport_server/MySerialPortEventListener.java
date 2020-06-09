package com.github.serialport_server;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

/**
 * @author guang19
 * @date 2020/6/8
 * @description 串口监听器
 * @since 1.0.0
 */
@Slf4j
public class MySerialPortEventListener implements SerialPortEventListener {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private InputStream inFromSerialPort; // 从串口来的输入流
    private OutputStream outToSerialPort;// 向串口输出的流
    private SerialPort serialPort; // 串口的引用
    private CommPortIdentifier portId;

    private GreetingServer greetingServer;

    //初始化串口，给串口添加监听器，获取串口的IO流
    public MySerialPortEventListener(GreetingServer greetingServer) {
        super();
        this.greetingServer = greetingServer;
        Enumeration portList = CommPortIdentifier.getPortIdentifiers(); //得到当前连接上的端口
        String name = "COM2";
        while (portList.hasMoreElements()) {
            CommPortIdentifier temp = (CommPortIdentifier) portList.nextElement();
            if (temp.getPortType() == CommPortIdentifier.PORT_SERIAL) {// 判断如果端口类型是串口
                if (temp.getName().equals(name)) { // 判断如果端口已经启动就连接
                    portId = temp;
                }
            }
        }
        try {
            serialPort = (SerialPort) portId.open("My" + name, 2000);

            inFromSerialPort = serialPort.getInputStream();
            outToSerialPort = serialPort.getOutputStream();

            serialPort.addEventListener(this); // 给当前串口天加一个监听器

            serialPort.notifyOnDataAvailable(true); // 当有数据时通知

            serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, // 设置串口读写参数
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //当串口有数据时，将数据发送给客户端
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;

            case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据,并且给串口返回数据
                sendToClient(greetingServer, inFromSerialPort);
                break;
        }
    }

    //将串口的数据发送给客户端
    private void sendToClient(GreetingServer greetingServer, InputStream inFromSerialPort) {
        byte[] readBuffer = new byte[1024];
        try {
            //将串口的数据发送给客户端
            if (inFromSerialPort.read(readBuffer) > 0) {
                String data = new String(readBuffer, StandardCharsets.UTF_8).trim();
                log.info("串口响应给客户端的数据是: " + data);
                greetingServer.sendToClient(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将数据发送给串口
    public void sendToSerialPort(String data) {
        try {
            outToSerialPort.write(data.getBytes(StandardCharsets.UTF_8));
            outToSerialPort.write("\r\n".getBytes(StandardCharsets.UTF_8));
            outToSerialPort.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
