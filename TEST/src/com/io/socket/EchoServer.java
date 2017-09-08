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
		ServerSocket serverSocket = null; //서버의 소켓
		int port = 10001; //포트 번호
		
		try {
			serverSocket = new ServerSocket(port); //포트에 서버소켓을 붙인다(Bind)
			log("서버가 준비되었습니다.");
			
			while (true) {
				Socket socket = serverSocket.accept(); // 클라이언트의 접속을 허가한다.(Accept)
				Thread thread = new ServerProc(socket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void log(String message) {
		SimpleDateFormat f = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]"); //날짜 출력
		String currentDT = f.format(new Date());
		System.out.println(currentDT + " " + message);
	}
	
	
	public static void main(String[] args) {
		new EchoServer();
	}

}
