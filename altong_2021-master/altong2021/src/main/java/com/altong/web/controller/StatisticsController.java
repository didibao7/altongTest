package com.altong.web.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.altong.web.logic.StatisticsSrchVO;
import com.altong.web.logic.StatisticsVO;
import com.altong.web.service.common.CommonService;

@Controller
@RequestMapping("statistics/*")
public class StatisticsController {
	@Autowired
	CommonService commonService;
	
	@RequestMapping("stat2")
	public ModelAndView stat2(HttpServletRequest request) throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView();
		
		String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String regDate = type.format(today.getTime());
		
		mav.addObject("now", regDate);
		
		return mav;
	}
	
	@RequestMapping("stat_real")
	public ModelAndView stat_real(HttpServletRequest request) throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView();
		
		String format = "yyyy-MM-dd";//yyyy-MM-dd aa hh:mm:ss(오전/오후 표시)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat type = new SimpleDateFormat(format);
		String regDate = type.format(today.getTime());
		
		mav.addObject("now", regDate);
		
		return mav;
	}
	
	@RequestMapping("almoney_stat")
	public ModelAndView almoney_stat(HttpServletRequest request) throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView();
		
		String ser_year = request.getParameter("ser_year");
		String ser_month = request.getParameter("ser_month");
		
		DecimalFormat df = new DecimalFormat("00");
		Calendar currentCalendar = Calendar.getInstance();
		
		String strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
		String strMonth = df.format(currentCalendar.get(Calendar.MONTH) + 1);
		String strDay = df.format(currentCalendar.get(Calendar.DATE));
		
		if(ser_year == null || ser_year.length() == 0 || ser_year.length() != 4) {
			ser_year = strYear;
		}
		
		if(ser_month == null || ser_month.length() == 0 || ser_month.length() != 2) {
			ser_month = strMonth;
		}
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("ser_year", ser_year);
		param.put("ser_month", ser_month);
		
		StatisticsSrchVO stat = commonService.getStatSearchData(param);
		
		int stat_cnt01 = 0;
		int stat_cnt02 = 0;
		int stat_cnt03 = 0;
		int stat_cnt04 = 0;
		int stat_cnt05 = 0;
		int stat_cnt06 = 0;
		int stat_cnt07 = 0;
		int stat_cnt08 = 0;
		int stat_cnt09 = 0;
		int stat_cnt10 = 0;
		int stat_cnt11 = 0;
		int stat_cnt12 = 0;
		
		BigDecimal stat_sum01 = BigDecimal.ZERO;
		BigDecimal stat_sum02 = BigDecimal.ZERO;
		BigDecimal stat_sum03 = BigDecimal.ZERO;
		BigDecimal stat_sum04 = BigDecimal.ZERO;
		BigDecimal stat_sum05 = BigDecimal.ZERO;
		BigDecimal stat_sum06 = BigDecimal.ZERO;
		BigDecimal stat_sum07 = BigDecimal.ZERO;
		BigDecimal stat_sum08 = BigDecimal.ZERO;
		BigDecimal stat_sum09 = BigDecimal.ZERO;
		BigDecimal stat_sum10 = BigDecimal.ZERO;
		BigDecimal stat_sum11 = BigDecimal.ZERO;
		BigDecimal stat_sum12 = BigDecimal.ZERO;
		
		if(stat != null) {
			stat_cnt01 = stat.getStat_cnt01();
			stat_cnt02 = stat.getStat_cnt02();
			stat_cnt03 = stat.getStat_cnt03();
			stat_cnt04 = stat.getStat_cnt04();
			stat_cnt05 = stat.getStat_cnt05();
			stat_cnt06 = stat.getStat_cnt06();
			stat_cnt07 = stat.getStat_cnt07();
			stat_cnt08 = stat.getStat_cnt08();
			stat_cnt09 = stat.getStat_cnt09();
			stat_cnt10 = stat.getStat_cnt10();
			stat_cnt11 = stat.getStat_cnt11();
			stat_cnt12 = stat.getStat_cnt12();
			
			stat_sum01 = stat.getStat_sum01();
			stat_sum02 = stat.getStat_sum02();
			stat_sum03 = stat.getStat_sum03();
			stat_sum04 = stat.getStat_sum04();
			stat_sum05 = stat.getStat_sum05();
			stat_sum06 = stat.getStat_sum06();
			stat_sum07 = stat.getStat_sum07();
			stat_sum08 = stat.getStat_sum08();
			stat_sum09 = stat.getStat_sum09();
			stat_sum10 = stat.getStat_sum10();
			stat_sum11 = stat.getStat_sum11();
			stat_sum12 = stat.getStat_sum12();
		}
		
		mav.addObject("nYear", Integer.parseInt(strYear));
		mav.addObject("nMonth", Integer.parseInt(strMonth));
		
		mav.addObject("stat_cnt01", stat_cnt01);
		mav.addObject("stat_cnt02", stat_cnt02);
		mav.addObject("stat_cnt03", stat_cnt03);
		mav.addObject("stat_cnt04", stat_cnt04);
		mav.addObject("stat_cnt05", stat_cnt05);
		mav.addObject("stat_cnt06", stat_cnt06);
		mav.addObject("stat_cnt07", stat_cnt07);
		mav.addObject("stat_cnt08", stat_cnt08);
		mav.addObject("stat_cnt09", stat_cnt09);
		mav.addObject("stat_cnt10", stat_cnt10);
		mav.addObject("stat_cnt11", stat_cnt11);
		mav.addObject("stat_cnt12", stat_cnt12);
		
		mav.addObject("stat_sum01", stat_sum01);
		mav.addObject("stat_sum02", stat_sum02);
		mav.addObject("stat_sum03", stat_sum03);
		mav.addObject("stat_sum04", stat_sum04);
		mav.addObject("stat_sum05", stat_sum05);
		mav.addObject("stat_sum06", stat_sum06);
		mav.addObject("stat_sum07", stat_sum07);
		mav.addObject("stat_sum08", stat_sum08);
		mav.addObject("stat_sum09", stat_sum09);
		mav.addObject("stat_sum10", stat_sum10);
		mav.addObject("stat_sum11", stat_sum11);
		mav.addObject("stat_sum12", stat_sum12);
		
		return mav;
	}
	
	@RequestMapping(value="getStatData", produces="application/json;charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getStatData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		StatisticsVO stat = commonService.getStatData();
		
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonString = mapper.writeValueAsString(stat);
		
		//System.out.println("stat : " + jsonString);
		return jsonString;
	}
}
