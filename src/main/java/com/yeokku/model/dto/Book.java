package com.yeokku.model.dto;

public class Book {

	// 요청관련
//	private String srchTarget; // total or title or author or publisher, 생략시 전체검색
//	private String desc; // 오름차순 or 내림차순
//	private String apiType; // xml or json
//	private String searchType; // GB(그룹검색) or 생략
	// 응답관련
	private String kwd;
	private String category;
	private String pageNum;
	private String pageSize;
	private String sort;
	private String total;
	private String Id;
	private String kdc_code_1s;
	private String title_info;
	private String author_info;
	private String pub_info;
	private String exp_date;
	private String cover_yn;
	private String thumb_path;
	private String thumb_file_name;
	private String img_path;
	private String detail_path;
	private String price;
	private String currency_code;
	private String isbn;
	private String title_page_yn;
	private String copyright_page_yn;
	private String author_yn;
	private String preface_yn;
	private String contents_yn;
	private String abstract_yn;
	private String text_yn;
	private String etc_yn;
	private String publisher_key;
	private String ea_add_code;
	
	public Book() {}

//	public String getSrchTarget() {
//		return srchTarget;
//	}
//
//	public void setSrchTarget(String srchTarget) {
//		this.srchTarget = srchTarget;
//	}
//
//	public String getDesc() {
//		return desc;
//	}
//
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
//
//	public String getApiType() {
//		return apiType;
//	}
//
//	public void setApiType(String apiType) {
//		this.apiType = apiType;
//	}
//
//	public String getSearchType() {
//		return searchType;
//	}
//
//	public void setSearchType(String searchType) {
//		this.searchType = searchType;
//	}

	public String getKwd() {
		return kwd;
	}

	public void setKwd(String kwd) {
		this.kwd = kwd;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getKdc_code_1s() {
		return kdc_code_1s;
	}

	public void setKdc_code_1s(String kdc_code_1s) {
		this.kdc_code_1s = kdc_code_1s;
	}

	public String getTitle_info() {
		return title_info;
	}

	public void setTitle_info(String title_info) {
		this.title_info = title_info;
	}

	public String getAuthor_info() {
		return author_info;
	}

	public void setAuthor_info(String author_info) {
		this.author_info = author_info;
	}

	public String getPub_info() {
		return pub_info;
	}

	public void setPub_info(String pub_info) {
		this.pub_info = pub_info;
	}

	public String getExp_date() {
		return exp_date;
	}

	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}

	public String getCover_yn() {
		return cover_yn;
	}

	public void setCover_yn(String cover_yn) {
		this.cover_yn = cover_yn;
	}

	public String getThumb_path() {
		return thumb_path;
	}

	public void setThumb_path(String thumb_path) {
		this.thumb_path = thumb_path;
	}

	public String getThumb_file_name() {
		return thumb_file_name;
	}

	public void setThumb_file_name(String thumb_file_name) {
		this.thumb_file_name = thumb_file_name;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getDetail_path() {
		return detail_path;
	}

	public void setDetail_path(String detail_path) {
		this.detail_path = detail_path;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle_page_yn() {
		return title_page_yn;
	}

	public void setTitle_page_yn(String title_page_yn) {
		this.title_page_yn = title_page_yn;
	}

	public String getCopyright_page_yn() {
		return copyright_page_yn;
	}

	public void setCopyright_page_yn(String copyright_page_yn) {
		this.copyright_page_yn = copyright_page_yn;
	}

	public String getAuthor_yn() {
		return author_yn;
	}

	public void setAuthor_yn(String author_yn) {
		this.author_yn = author_yn;
	}

	public String getPreface_yn() {
		return preface_yn;
	}

	public void setPreface_yn(String preface_yn) {
		this.preface_yn = preface_yn;
	}

	public String getContents_yn() {
		return contents_yn;
	}

	public void setContents_yn(String contents_yn) {
		this.contents_yn = contents_yn;
	}

	public String getAbstract_yn() {
		return abstract_yn;
	}

	public void setAbstract_yn(String abstract_yn) {
		this.abstract_yn = abstract_yn;
	}

	public String getText_yn() {
		return text_yn;
	}

	public void setText_yn(String text_yn) {
		this.text_yn = text_yn;
	}

	public String getEtc_yn() {
		return etc_yn;
	}

	public void setEtc_yn(String etc_yn) {
		this.etc_yn = etc_yn;
	}

	public String getPublisher_key() {
		return publisher_key;
	}

	public void setPublisher_key(String publisher_key) {
		this.publisher_key = publisher_key;
	}

	public String getEa_add_code() {
		return ea_add_code;
	}

	public void setEa_add_code(String ea_add_code) {
		this.ea_add_code = ea_add_code;
	}
	
}
