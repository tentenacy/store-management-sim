package com.tenutz.storemngsim;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@EnableJpaAuditing(dateTimeProviderRef = "localDateTimeProvider")
@SpringBootApplication
@RequiredArgsConstructor
public class StoreManagementSimApplication implements ApplicationListener {

	private final ApplicationContext applicationContext;

	public int port;
	public String ip;

	public static void main(String[] args) {
		SpringApplication.run(StoreManagementSimApplication.class, args);
	}

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		try {
			this.ip = InetAddress.getLocalHost().getHostAddress();
			this.port = applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class, 8080);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public String hostAddress() {
		return "http://" + ip + ":" + port;
	}

}
