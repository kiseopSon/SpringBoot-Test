package com.excel.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ExcelService {
	
	/**
    *	엑셀 파일 다운로드
    * @param
    * @throws IOException, NullPointerException, Exception
    */
	public void downExcel(String tableNm,HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException, Exception;

	public String uploadExcel(String tableNm,MultipartHttpServletRequest uploadFile, HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException, Exception;

	/**
    *	엑셀 파일 다운로드
	 * @param response 
	 * @param  
    * @param 예시) c:/window/download/{filename}.xlsx
    * @throws IOException, NullPointerException, Exception
    */
	public void downPathExcel(String fullPath, HttpServletResponse response) throws IOException;
	
	/**
    *	엑셀 파일 다운로드
    * @param 예시) c:/window/download/{filename}, .xlsx or .xls
    * @throws IOException, NullPointerException, Exception
    */
	public void downPathExcel(String path, String extension, HttpServletResponse response) throws IOException;
}
