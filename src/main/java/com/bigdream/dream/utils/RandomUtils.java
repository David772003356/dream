package com.bigdream.dream.utils;

import java.util.Random;

public class RandomUtils {
	
	private final static String[] CharacterPooL = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
													"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", 
													"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", 
													"u", "v", "w", "x", "y", "z", "A", "B", "C", "D", 
													"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", 
													"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private final static String[] NumberPooL = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	/**
	 * 生成digit位的数字字母随机数
	 * @return
	 */
	public static String create(int digit) {
		StringBuilder result = new StringBuilder();
		int length = CharacterPooL.length;
		Random random = new Random();
		for (int i=0; i<digit; i++) {
			String character = CharacterPooL[random.nextInt(length)];
			result.append(character);
		}
		return result.toString();
	}
	
	/**
	 * 生成digit位的纯数字随机数
	 * @param digit
	 * @return
	 */
	public static String createNumber(int digit) {
		StringBuilder result = new StringBuilder();
		int length = NumberPooL.length;
		Random random = new Random();
		for (int i=0; i<digit; i++) {
			String character = NumberPooL[random.nextInt(length)];
			result.append(character);
		}
		return result.toString();
	}

	/**
	 * 生成固定范围的随机数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int createNumber(int min,int max){
		Random random = new Random();
		return random.nextInt(max)%(max-min+1) + min;
	}
}
