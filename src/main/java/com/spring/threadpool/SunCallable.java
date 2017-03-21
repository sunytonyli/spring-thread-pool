package com.spring.threadpool;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

public class SunCallable implements Callable<Boolean> {

	Logger log = Logger.getLogger(SunCallable.class);
	
	/**当前是属于第几段线程**/
	private int pageIndex;
	
	private List<String> list;
	 
	public SunCallable(int pageIndex,List<String> list){
		this.pageIndex = pageIndex;
		this.list = list;
	}
	
	@Override
	public Boolean call() throws Exception {
		System.err.println(String.format("pageIndex:%s size:%s",pageIndex,list.size()));
		Boolean result = Boolean.TRUE;
		if(null != list && list.size() >0){
			for(String str: list){
				try {
					 //TODO 业务处理
					log.info("====" + str);
				} catch (Exception e) {
					result = Boolean.FALSE;;
					 
				}
			}
		}
		return result;
	}

}
