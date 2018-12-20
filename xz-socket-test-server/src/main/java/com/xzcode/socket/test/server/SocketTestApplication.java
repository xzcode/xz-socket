package com.xzcode.socket.test.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.xzcode.socket.core.starter.SocketServerStarter;

/**
 * 测试服务器启动类
 * 
 * 
 * @author zai
 * 2018-06-16 19:57:43
 */
@SpringBootApplication
public class SocketTestApplication implements CommandLineRunner{
	
	public static void main(String[] args) { 
		SpringApplication.run(SocketTestApplication.class, args);
	}

	@Autowired
	private SocketServerStarter serverStarter;
	
	@Override
	public void run(String... args) throws Exception {
		this.serverStarter.run();
	}

	
}

