package com.douzone.jblog.vo;

public class CategoryVo {

	private Long no;
	private String name;
	private String desc;
	private String blogId;
	private Long postCNT;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getBlogId() {
		return blogId;
	}
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
	
	public Long getPostCNT() {
		return postCNT;
	}
	public void setPostCNT(Long postCNT) {
		this.postCNT = postCNT;
	}
	
	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", desc=" + desc + ", blogId=" + blogId + ", postCNT="
				+ postCNT + "]";
	}
	
	
}
