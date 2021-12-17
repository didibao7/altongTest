package com.altong.web.rest.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestSirenService {
	Map<String, Object> restCommonSiren(Map<String, Object> param, Integer seq, HttpServletRequest request, HttpServletResponse response);
}
