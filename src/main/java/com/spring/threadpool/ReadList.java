package com.spring.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.spring.threadpool.util.ThreadPoolTaskExecutorUtil;

public class ReadList {

	public static void doReadList() throws InterruptedException, ExecutionException{
		
		ThreadPoolTaskExecutor threadPoolTaskExecutor = ThreadPoolTaskExecutorUtil.initThreadPool();
		
		/**初始化集合**/
		List<String> list = new ArrayList<String>();
		for(int i=0;i<100;i++){
			list.add("test--"+i);
		}
		
		/**接收集合各段的 执行的返回结果**/
		List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>();
		
		/**集合总条数**/
		int size = list.size();
		/**将集合切分的段数**/
		int sunSum = 10;
		int listStart,listEnd;
		/***当总条数不足10条时 用总条数 当做线程切分值**/
		if(sunSum > size){
			sunSum = size;
		}
		/**定义子线程**/
		SunCallable sunCallable ;
		/**将list 切分10份 多线程执行**/
		for (int i = 0; i < sunSum; i++) {
			/***计算切割  开始和结束**/
			listStart = size / sunSum * i ;
			listEnd = size / sunSum * ( i + 1 );
			/**最后一段线程会 出现与其他线程不等的情况**/
			if(i == sunSum - 1){
				listEnd = size;
			}
			/**线程切断**/
			List<String> sunList = list.subList(listStart,listEnd); 
			/**子线程初始化**/
			sunCallable = new SunCallable(i,sunList);
			/***多线程执行***/
			futureList.add(threadPoolTaskExecutor.submit(sunCallable));
		}
		/**对各个线程段结果进行解析**/
		for(Future<Boolean> future : futureList){
			if(null != future && future.get()){
				System.err.println("成功");
			}else{
				System.err.println("失败");
			}
		}
	}

}
