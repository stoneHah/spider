package com.youe.yc.spiderservice.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 数据处理器
 * @author ibm
 *
 */
public class DataProcessor {
	
	public static <T,R> List<R> applyList(List<T> list,Function<T, R> function){
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		
		List<R> result = new ArrayList<>();
		for (T t : list) {
			R value = function.apply(t);
			if(value != null){
				result.add(value);
			}
		}
		return result;
	}
}
