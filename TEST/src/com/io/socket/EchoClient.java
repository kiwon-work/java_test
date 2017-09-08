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
			String ip = "localhost"; //Ŭ���̾�Ʈ�� �ӽ� ���� �ּ�
			int port = 10001; //������ ���� ��Ʈ
			boolean keepConnect = false;
			socket = new Socket(ip, port); //Ŭ���̾�Ʈ�� ���� ����
			
			if (socket != null) {
				keepConnect = true;
			}
       
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
 
			OutputStream out = socket.getOutputStream(); //������ �������κ��� ����� ����
			InputStream in = socket.getInputStream(); //������ �������κ��� �Է��� ����
			 
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out)); //��� ��Ʈ���� ��ȯ
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); //�Է� ��Ʈ���� ��ȯ
			 
			String myMsg = null; //���� �޽���
			String echo = null; //�޴� �޽���
   
			while((myMsg = input.readLine()) != null && keepConnect){
			    if(myMsg.equals("/quit")){
			    	keepConnect = false;
			        break; //���� ����
			    }
			     
			    pw.println(myMsg); //PrintWriter�� �̿��Ͽ� �������� ����
			    pw.flush(); //���� ����
			     
			    echo = br.readLine(); //������ ���۷� �޽����� �����ϸ� �̸� ����
			    System.out.println("����: " + echo);
			}
			 
			pw.close();
			br.close();
			socket.close();
			System.out.println("�������� ������ �����߽��ϴ�.");
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
