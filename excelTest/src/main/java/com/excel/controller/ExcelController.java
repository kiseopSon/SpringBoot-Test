package com.excel.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.excel.service.ExcelService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * 메인 컨트롤러
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *  수정일                   수정자                        수정내용
 *  ------------    --------    ---------------------------
 *  2019. 10. 25.    허성훈         		  엑셀 다운로드
 *  </pre>
 */
@Controller
@RequestMapping(value = "/excel")
public class ExcelController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);
	
	@Resource(name="ExcelService")
	private ExcelService excelService;
	
	/**
     * excelMove - Mapping
     * @param paramMap
     * @return excel/testUploadDomain
     */
	@GetMapping(path = "/{tableNm}")
	public String testUploadDomain(@PathVariable String tableNm) throws NullPointerException, Exception {
		//테스트경로 /excel/testUploadDomain
		return tableNm;
	}
	
    /**
     * excelDownByTable - get
     * @param tableNm
     * @return ExampleExcelFile
     */
    @GetMapping(path = "/download/{tableNm}")//테스트용프로젝트엑셀
    public void excelDownByTable(@PathVariable String tableNm,HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException, Exception{
    	org.springframework.core.io.Resource resource = new ClassPathResource(tableNm+".xlsx");
		excelService.downPathExcel(resource.getFile().getCanonicalPath().toString(), response);//excelService.downExcel(tableNm,request, response);
    }
    /**
     * excelUploadByTable - post
     * @param tableNm
     * @param uploadFile
     * @return S or F
     */
    @PostMapping(path = "/upload/{tableNm}")
    public String excelUploadByTable(@PathVariable String tableNm,MultipartHttpServletRequest uploadFile, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException, Exception{
    	model.addAttribute("result", excelService.uploadExcel(tableNm,uploadFile,request, response));
    	return tableNm;
    }
    

}