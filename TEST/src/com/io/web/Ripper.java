package com.io.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.print.Doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Ripper {

	public Ripper() {
		invoke();
	}
	
	private void invoke() {
		String web_addr = "https://seowoochi.tumblr.com";
		boolean exists = false;
		
		try {
			//String web address를 URL로 바꾼뒤 Connection 생성
			URL url = new URL(web_addr);
			URLConnection conn = url.openConnection();
			conn.connect();
			
			if ((conn instanceof HttpURLConnection)) {
				HttpURLConnection httpConnection = (HttpURLConnection) conn;
				
				if (httpConnection.getResponseCode() == 200) {
					exists = true;
				} else {
					System.out.println("HTTP=" + httpConnection.getResponseCode());
				}
			}
			
			if (exists) {
				Document doc = Jsoup.connect(web_addr).get();
				System.out.println(">>> make a connection.");
				System.out.println(doc.toString());
				getContentsFromIframe(doc);
			}
			
//			System.out.println("###");
//			System.out.println(doc.toString());
			System.out.println("### FINISHED");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void getContentsFromIframe(Document doc) throws IOException {
		Elements link = doc.select("iframe");
		int elems = link.size();
		
		for (int j = 0; j < elems; j++) {
			String id = link.get(j).attr("src");
//			System.out.println("### getContentsFromIframe().id="+id);
			
			if (id.contains("video")) {
				getContent(id);
			}
		}
	}//getContentsFromIframe( )
	
	
	private void getContent(String uri) {
		String dest_dir = "C:/sourceforge/down";
		
		String[] split_url = uri.split("/");
		dest_dir = dest_dir + "/" + split_url[4];
//		System.out.println("### dest_dir=" + dest_dir);
		System.out.println("### dest_file=" + split_url[5]+".mp4");
		
		InputStream is = null;
		OutputStream os = null;
		try {
			//저장대상 디렉토리 없으면 만들기
			File dest_folder = new File(dest_dir);
			if (!dest_folder.exists()) {
				dest_folder.mkdirs();
			}
			
			is = new URL(uri).openStream();
			os = new FileOutputStream(dest_dir + "/" + split_url[5]+".mp4");
			
			byte[] buffer = new byte[4096];
			int read_length = 0;
			while ((read_length = is.read(buffer)) > -1) {
				os.write(buffer, 0, read_length);
				System.out.print("-");
			}
			is.close();
			os.close();
			System.out.println("");
			System.out.println("> saved " + dest_dir + "/" + split_url[5]+".mp4");
			System.out.println("");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	} //getContent()
	
	
	public static void main(String[] args) {
		new Ripper();
	}
}
