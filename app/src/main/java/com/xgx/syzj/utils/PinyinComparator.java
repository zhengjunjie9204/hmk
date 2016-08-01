//package com.xgx.syzj.utils;
//
//import com.xgx.syzj.bean.Member;
//
//import java.util.Comparator;
//
///**
// *
// * @author xiaanming
// *
// */
//public class PinyinComparator implements Comparator<Member> {
//
//	public int compare(Member o1, Member o2) {
//		if (o1.getSortLetters().equals("@")
//				|| o2.getSortLetters().equals("#")) {
//			return -1;
//		} else if (o1.getSortLetters().equals("#")
//				|| o2.getSortLetters().equals("@")) {
//			return 1;
//		} else {
//			return o1.getSortLetters().compareTo(o2.getSortLetters());
//		}
//	}
//
//}
