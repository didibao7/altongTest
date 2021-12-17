package com.altong.web.logic.common;

import java.util.HashMap;

public class PageUtilBean {
	/* total : 총 페이지
	 * pgSize : 한개 페이지에서 보여질 갯수, 리스트의 숫자
	 * ROWSIZE : 하단에 보여질 페이징 갯수, 클릭해서 이동할 페이지숫자
	 * curPage : 현재 페이지
	 * SQL_Query_Page 함수의 대체용 (SQL_cmn_cls.vbs)
	*/
	public HashMap<Object,Object> makePageData(float total, float pgSize, int ROWSIZE, int curPage){
		HashMap<Object,Object> param = new HashMap<Object, Object>();
		float totCnt = (float)total;  	
		float pageSize = (float)pgSize;    
		int pgCnt = (int) Math.ceil(totCnt / pageSize);    
		if(ROWSIZE > pgCnt){  //총 갯수 오버 방지
			ROWSIZE = pgCnt;
		}
		int devide = (int) Math.ceil(((float)(curPage+1)/(float)ROWSIZE));  //기준체크		
		int start = ((devide)*ROWSIZE) - (ROWSIZE-1); // 해당페이지에서 시작번호(step2) 
		int end = ((devide)*ROWSIZE); // 해당페이지에서 끝번호(step2)		
		if(end > pgCnt){  //최종 넘어가는 갯수 오버 방지
			end = pgCnt;
		}
		
		param.put("firstPage", (int)(curPage * pageSize)); //첫번째 페이지수(0으로 리턴되면 1을더해서 쓰면 된다)
		param.put("pgCnt", pgCnt);  //기준값으로 나눈 페이지 갯수
		param.put("curPage", curPage); //현재 페이지
		param.put("start",start);  //시작가능 페이지
		param.put("end", end); // 끝 페이지
		param.put("pageSize", (int)pageSize);  //페이지 사이즈
		//start값과 end값이 하단에 보여질 페이지수이다. 해당 데이터를 for문으로 돌리면 된다. 
		return param;
	}
}
