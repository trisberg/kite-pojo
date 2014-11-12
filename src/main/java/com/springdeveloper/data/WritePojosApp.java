package com.springdeveloper.data;

import org.kitesdk.data.Dataset;
import org.kitesdk.data.DatasetDescriptor;
import org.kitesdk.data.DatasetWriter;
//import org.kitesdk.data.DatasetRepositories;
//import org.kitesdk.data.DatasetRepository;
import org.kitesdk.data.spi.DatasetRepositories;
import org.kitesdk.data.spi.DatasetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.util.Date;

/**
 * Created by trisberg on 11/12/14.
 */
public class WritePojosApp implements CommandLineRunner {

	private DatasetRepository repo;
	private DatasetDescriptor descriptor;
	private Dataset<TestPojo> pojos;
	private DatasetWriter<TestPojo> writer;

	public static void main(String[] args) {
		SpringApplication.run(WritePojosApp.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		init();

		//writer.open();

		TestPojo t1 = new TestPojo();
		t1.setId(1);
		t1.setTimestamp(new Date().getTime());
		t1.setDescription("test");

		writer.write(t1);

		writer.flush();
		writer.close();
		System.out.println("Done!");
	}

	private void init() {
		this.repo = DatasetRepositories.repositoryFor("repo:hdfs://localhost:8020/data");
//		this.repo = DatasetRepositories.open("repo:hdfs://localhost:8020/data");
		this.descriptor = new DatasetDescriptor.Builder()
				.schema(TestPojo.class)
				.build();
		this.pojos = repo.create("test", "pojo", descriptor);
//		this.pojos = repo.create("pojo", descriptor);
		this.writer = this.pojos.newWriter();
	}
}
