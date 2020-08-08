package com.luxiu.spring.conf.page;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Description: 自定义分页类
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName Pagination
 * @date 2020/8/1 23:04
 * @company https://www.beyond.com/
 */
public class Pagination<T> implements Serializable {

	private static final long serialVersionUID = -5626846339655628738L;

	// 总条数
	private Integer totalCount;

	// 总页数
	private Integer totalPages;

	// 当前页码从1开始
	private Integer pageNumber;

	// 每页条数
	private Integer pageSize;

	// oracle分页起始行
	private Integer begin;

	// oracle分页结束行
	private Integer end;

	// 前一页
	private Integer prePage;

	// 是否首页
	private Boolean firstPage;

	// 是否末页
	private Boolean lastPage;

	// 下一页
	private Integer nextPage;

	// 偏移量
	private Integer offset;

	// 是否有下页
	private Boolean hasNextPage;

	// 是否有上页
	private Boolean hasPrePage;

	// 当前页从第几条开始查
	private Integer startRow;

	// 返回数据
	private List<T> rows;

	public Pagination() {
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPrePage() {
		return prePage;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}

	public Boolean getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(Boolean firstPage) {
		this.firstPage = firstPage;
	}

	public Boolean getLastPage() {
		return lastPage;
	}

	public void setLastPage(Boolean lastPage) {
		this.lastPage = lastPage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Boolean getHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(Boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public Boolean getHasPrePage() {
		return hasPrePage;
	}

	public void setHasPrePage(Boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
