package com.sl.api.salon.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zeasn.common.model.result.ApiResult;

public abstract class BaseFilter implements Filter {
	private XmlMapper xmlMapper = new XmlMapper();
	
	protected void error(ApiResult data, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(data == null){
			this.error(null, response);
			return;
		}
		
		String accept = request.getHeader("Accept");
		String msg;
		
		if(StringUtils.isEmpty(accept)){
			msg = JSON.toJSONString(data);
			response.setContentType("application/json; charset=UTF-8");
			
		}else{
			accept = accept.toLowerCase();
			if(accept.indexOf("xml") >= 0){
				msg = this.xmlMapper.writeValueAsString(data);
				response.setContentType("application/xhtml+xml;charset=UTF-8");
				
			}else{
				msg = JSON.toJSONString(data);
				response.setContentType("application/json; charset=UTF-8");
			}
		}
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(HttpStatus.SC_OK);
		
		try(PrintWriter pw = response.getWriter()){
			pw.write(msg);
			pw.flush();
		}
	}
	
	protected void error(String message, HttpServletResponse response) throws IOException{
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.sendError(HttpStatus.SC_UNAUTHORIZED, message);
	}
}
