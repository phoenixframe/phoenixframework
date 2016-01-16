package org.phoenix.api.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 对List进行拆分，用于分布式计算
 * 
 * @author mengfeiyang
 *
 */
public class ListSplit<T> {
	private List<List<T>> eList = new ArrayList<List<T>>();
	private List<T> gList;

	public List<List<T>> SplitList(List<T> sList, int num) {
		int size = (sList.size()) / num;
		int size2 = (sList.size()) % num;
		int j = 0;
		int xx = 0;
		for (int i = 0; i < num; i++) {
			gList = new ArrayList<T>();

			for (j = xx; j < (size + xx); j++) {
				gList.add(sList.get(j));
			}
			xx = j;
			eList.add(gList);
		}
		if (size2 != 0) {
			gList = new ArrayList<T>();
			for (int y = 1; y < size2 + 1; y++) {
				gList.add(sList.get(sList.size() - y));
			}
			eList.add(gList);
		}
		return eList;
	}
}