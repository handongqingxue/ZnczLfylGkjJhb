package com.znczLfylGkjJhb.entity;

public class GuoBangJiLu {

	public static final int RU_CHANG_GUO_BANG=1;
	public static final int CHU_CHANG_GUO_BANG=2;
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getGbzl() {
		return gbzl;
	}
	public void setGbzl(Float gbzl) {
		this.gbzl = gbzl;
	}
	public String getGbsj() {
		return gbsj;
	}
	public void setGbsj(String gbsj) {
		this.gbsj = gbsj;
	}
	public Integer getGblx() {
		return gblx;
	}
	public void setGblx(Integer gblx) {
		this.gblx = gblx;
	}
	public String getGblxName() {
		return gblxName;
	}
	public void setGblxName(String gblxName) {
		this.gblxName = gblxName;
	}
	public Integer getDdId() {
		return ddId;
	}
	public void setDdId(Integer ddId) {
		this.ddId = ddId;
	}
	public String getDdh() {
		return ddh;
	}
	public void setDdh(String ddh) {
		this.ddh = ddh;
	}
	public String getCph() {
		return cph;
	}
	public void setCph(String cph) {
		this.cph = cph;
	}
	public Integer getLxlx() {
		return lxlx;
	}
	public void setLxlx(Integer lxlx) {
		this.lxlx = lxlx;
	}
	public String getYssMc() {
		return yssMc;
	}
	public void setYssMc(String yssMc) {
		this.yssMc = yssMc;
	}
	public String getFhdwMc() {
		return fhdwMc;
	}
	public void setFhdwMc(String fhdwMc) {
		this.fhdwMc = fhdwMc;
	}
	public String getShbmMc() {
		return shbmMc;
	}
	public void setShbmMc(String shbmMc) {
		this.shbmMc = shbmMc;
	}
	private Float gbzl;
	private String gbsj;
	private Integer gblx;
	private String gblxName;
	private Integer ddId;
	private String ddh;
	private String cph;
	private Integer lxlx;//流向类型
	private String yssMc;
	private String fhdwMc;
	private String shbmMc;
}
