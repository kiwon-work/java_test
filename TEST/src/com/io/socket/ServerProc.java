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
			InetAddress clientAddress = socket.getInetAddress(); // Ŭ���̾�Ʈ�� �ּҸ� �����´�.
			log(clientAddress + " ���� Ŭ���̾�Ʈ�� �����߽��ϴ�.");

			OutputStream out = socket.getOutputStream(); //Ŭ���̾�Ʈ ������ ����Ʈ ��Ʈ���� �����´�.
			InputStream in =socket.getInputStream(); //Ŭ���̾�Ʈ ������ ����Ʈ ��Ʈ���� �Է��Ѵ�.
			 
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			 
			String recvStr = null; //���� ���ڿ�
			while((recvStr = br.readLine()) != null){
			    log("[" + threadId + "] Ŭ���̾�Ʈ�κ��� ���� ���ڿ� : " + recvStr);
			    pw.println(recvStr);    //�޽����� Ŭ���̾�Ʈ���� ����
			    pw.flush(); //���۸� ���
			    
			    if (recvStr.equals("/quit")) {
			    	log("Thread[" + threadId + "] interrupted.");
			    	this.join(200); // 200 millisecond wait
			    }
			}
			 
			pw.close(); //��Ʈ�� �ݱ�
			br.close(); //���� �ݱ�
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close(); //���� �ݱ�
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void log(String message) {
		SimpleDateFormat f = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]"); //��¥ ���
		String currentDT = f.format(new Date());
		System.out.println(currentDT + " " + message);
	}
}
