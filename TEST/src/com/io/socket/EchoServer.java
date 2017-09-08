package com.io.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoServer {

	public EchoServer() {
		invoke();
	}
	
	private void invoke() {
		ServerSocket serverSocket = null; //������ ����
		int port = 10001; //��Ʈ ��ȣ
		
		try {
			serverSocket = new ServerSocket(port); //��Ʈ�� ���������� ���δ�(Bind)
			log("������ �غ�Ǿ����ϴ�.");
			
			while (true) {
				Socket socket = serverSocket.accept(); // Ŭ���̾�Ʈ�� ������ �㰡�Ѵ�.(Accept)
				Thread thread = new ServerProc(socket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void log(String message) {
		SimpleDateFormat f = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]"); //��¥ ���
		String currentDT = f.format(new Date());
		System.out.println(currentDT + " " + message);
	}
	
	
	public static void main(String[] args) {
		new EchoServer();
	}

}
