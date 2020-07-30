/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.bigdream.dream.utils;

import java.util.UUID;

/**
 * Created by ace on 2017/9/27.
 */
public class UUIDUtils {

    private static SnowflakeIdWorker snowflakeIdWorker=new SnowflakeIdWorker(getWorkerId());


    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    
    public static String generateUuidOf8() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
    /**
     * 主键统一用它生成16位id
     * @return
     */
    public static String generateUuid() {
        return generateShortUuid();
    }
    
    /**
     *  主键统一用它生成32位id
     * @return
     */
    public static String generateUuid32() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
    /**
     * 返回16位
     * @return
     */
    public static String generateShortUuid() {
    	StringBuffer shortBuffer = new StringBuffer();
    	shortBuffer.append(generateUuidOf8());
    	shortBuffer.append(generateUuidOf8());
         return shortBuffer.toString();
    }

    public static String getSnowflakeId(){
        return String.valueOf(snowflakeIdWorker.nextId());
    }
    
    public static void main(String[] args) {
		System.out.println(getSnowflakeId());
	}

    private static long getWorkerId() {
        long workerId= IpUtils.getLastIpNum();
        if(workerId==-1l){
            /**
             * 本系统SnowflakeIdWorker的workerId范围为0-1023，ip最后一段数字最大为255
             * 一旦获取本机ip失败，就取300-1023之间的随机数做为workerId
             */
            workerId= RandomUtils.createNumber(300,1023);
        }
        return workerId;
    }
}
