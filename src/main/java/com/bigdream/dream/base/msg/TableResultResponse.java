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

package com.bigdream.dream.base.msg;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-14 22:40
 */
public class TableResultResponse<T> extends BaseResponse {

    TableData<T> data;

    public TableResultResponse(long total, List<T> rows) {
        this.data = new TableData<T>(total, rows);
    }

    public TableResultResponse(long total, Object object, List<T> rows) {
        this.data = new TableData<T>(total, object, rows);
    }

    public TableResultResponse() {
        this.data = new TableData<T>();
    }

    @Override
    public TableResultResponse message(String message){
        this.setMessage(message);
        return this;
    }

    @Override
    public TableResultResponse status(int status){
        this.setStatus(status);
        return this;
    }

    TableResultResponse<T> total(int total) {
        this.data.setTotal(total);
        return this;
    }

    TableResultResponse<T> total(List<T> rows) {
        this.data.setRows(rows);
        return this;
    }

    public TableData<T> getData() {
        return data;
    }

    public void setData(TableData<T> data) {
        this.data = data;
    }

    public class TableData<T> {
        long total;
        Object object;
        List<T> rows;

        public TableData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }

        public TableData(long total, Object object, List<T> rows) {
            this.total = total;
            this.object = object;
            this.rows = rows;
        }

        public TableData() {
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }
}
