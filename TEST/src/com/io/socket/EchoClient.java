package com.io.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	public EchoClient() {
		invoke();
	}
	
	private void invoke() {
		Socket socket = null;
		
        try {
			String ip = "localhost"; //클라이언트의 임시 접속 주소
			int port = 10001; //접속할 서버 포트
			boolean keepConnect = false;
			socket = new Socket(ip, port); //클라이언트의 소켓 생성
			
			if (socket != null) {
				keepConnect = true;
			}
       
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
 
			OutputStream out = socket.getOutputStream(); //서버의 소켓으로부터 출력을 받음
			InputStream in = socket.getInputStream(); //서버의 소켓으로부터 입력을 받음
			 
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out)); //출력 스트림을 변환
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); //입력 스트림을 변환
			 
			String myMsg = null; //전달 메시지
			String echo = null; //받는 메시지
   
			while((myMsg = input.readLine()) != null && keepConnect){
			    if(myMsg.equals("/quit")){
			    	keepConnect = false;
			        break; //연결 해제
			    }
			     
			    pw.println(myMsg); //PrintWriter를 이용하여 서버에게 전달
			    pw.flush(); //버퍼 비우기
			     
			    echo = br.readLine(); //서버가 버퍼로 메시지를 전달하면 이를 읽음
			    System.out.println("서버: " + echo);
			}
			 
			pw.close();
			br.close();
			socket.close();
			System.out.println("서버와의 연결을 종료했습니다.");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoClient();
	}

}
