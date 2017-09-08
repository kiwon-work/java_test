package com.io.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ServerProc extends Thread {

	private Socket socket = null;
	private String threadId = null;
	
	public ServerProc(Socket socket) {
		this.socket = socket;
		this.threadId = UUID.randomUUID().toString();
	}
	
	
	public void run() {
		try {
			InetAddress clientAddress = socket.getInetAddress(); // 클라이언트의 주소를 가져온다.
			log(clientAddress + " 에서 클라이언트가 접속했습니다.");

			OutputStream out = socket.getOutputStream(); //클라이언트 소켓의 바이트 스트림을 가져온다.
			InputStream in =socket.getInputStream(); //클라이언트 소켓의 바이트 스트림을 입력한다.
			 
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			 
			String recvStr = null; //받은 문자열
			while((recvStr = br.readLine()) != null){
			    log("[" + threadId + "] 클라이언트로부터 받은 문자열 : " + recvStr);
			    pw.println(recvStr);    //메시지를 클라이언트에게 전송
			    pw.flush(); //버퍼를 비움
			    
			    if (recvStr.equals("/quit")) {
			    	log("Thread[" + threadId + "] interrupted.");
			    	this.join(200); // 200 millisecond wait
			    }
			}
			 
			pw.close(); //스트림 닫기
			br.close(); //버퍼 닫기
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close(); //소켓 닫기
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void log(String message) {
		SimpleDateFormat f = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]"); //날짜 출력
		String currentDT = f.format(new Date());
		System.out.println(currentDT + " " + message);
	}
}
