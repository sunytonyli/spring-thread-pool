package com.spring.threadpool;


import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class ReadListTest {

	@Test
	public void test() throws InterruptedException, ExecutionException {
		ReadList.doReadList();
	}

}
