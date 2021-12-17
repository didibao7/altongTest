package com.altong.web.logic;

public class MessageVO {
	private int seq;
	private int writerSeq;
	private String contents;
	private String conDate;
	private String writerNic;
	private String writerPhoto;
	private int blockMemberSeq;
	private String is_read;

	private int begin;
	private int end;

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getWriterSeq() {
		return writerSeq;
	}
	public void setWriterSeq(int writerSeq) {
		this.writerSeq = writerSeq;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getConDate() {
		return conDate;
	}
	public void setConDate(String conDate) {
		this.conDate = conDate;
	}
	public String getWriterNic() {
		return writerNic;
	}
	public void setWriterNic(String writerNic) {
		this.writerNic = writerNic;
	}
	public String getWriterPhoto() {
		return writerPhoto;
	}
	public void setWriterPhoto(String writerPhoto) {
		this.writerPhoto = writerPhoto;
	}
	public int getBlockMemberSeq() {
		return blockMemberSeq;
	}
	public void setBlockMemberSeq(int blockMemberSeq) {
		this.blockMemberSeq = blockMemberSeq;
	}
	public String getIs_read() {
		return is_read;
	}
	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}


}
