package com.excel.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.excel.service.ExcelService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 이슈 1: maven종속성을 가지고, org.apache.poi 라이브러리를 import 시킨것은, jar라이브러리를 외부에서 import시켰을때, 자바에는 있으나, 서버에서 인식을 못하기에 다음과 같이 설정하게 되었다.
 * */

@Service("ExcelService")
public class ExcelServiceImpl implements ExcelService{

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelServiceImpl.class);
	

	//현재 dao없음
	
	@Override
	public synchronized void downExcel(String tableNm,HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException, Exception {
		//List<String> headStr = dao.selectList("Excel.downExcel",tableNm );
		List<String> headStr = new ArrayList();

    	//타이틀
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_hhmmss");
        String title= sdf.format(new Date()).toString()+"_"+tableNm+".xlsx";
       
    	//문자열
		int result = 0;
        Workbook xlsxWb = new HSSFWorkbook(); // Excel 2007 미만/ xls , 현재 라이브럴 버전이 안맞아서 poi만 버전맞춤.
        // *** Sheet-------------------------------------------------
        // Sheet 생성
        Sheet sheet1 = xlsxWb.createSheet(tableNm);

        // *** Style--------------------------------------------------
        // Cell 스타일 생성
        CellStyle cellStyle = xlsxWb.createCellStyle();
        
        // 줄 바꿈
        cellStyle.setWrapText(true);
        
        //가운데 정렬 설정
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        
        //중앙 정렬 설정
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row row = null;
        Cell cell = null;
        //----------------------------------------------------------

        //첫줄 문자열넣기
        row = sheet1.createRow(0);

    	for(int i = 0 ; i<headStr.size();i++) {
    		cell = row.createCell(i);
    		cell.setCellValue(headStr.get(i));
    		sheet1.setColumnWidth(i, headStr.get(i).length() *1000);
    	}

        // excel 파일 저장
        //파일 다운로드

        if(headStr.size() > 0) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Transfer-Encoding", "binary");
            try {
            	response.setHeader("Content-Disposition", "attachment;filename=" + title);
            	xlsxWb.write(response.getOutputStream());
                response.setHeader("Set-Cookie", "fileDownload=true; path=/");

            } catch (IOException ex) {
                response.setHeader("Set-Cookie", "fileDownload=false; path=/");
                throw new IOException("파일을 다운로드 에러 발생");
            } finally {
            	xlsxWb.close();
            }
        } else {
            throw new Exception("테이블이 존재하지 않습니다.");
        }

	}


	@Override
	public String uploadExcel(String tableNm, MultipartHttpServletRequest uploadFile, HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException, Exception {
		List<String> headStr = null;
		try {
			if (tableNm.equals("code_domain_fmt")) {//code_domain_fmt으로 테스트 파라미터
				//headStr = dao.selectList("Excel.downExcel",tableNm );
				headStr = new ArrayList();
				MultipartFile fileInfo = uploadFile.getFile("file");
				if (fileInfo.getSize() != 0) {
					try {
						Workbook xlsxWb = null;
						try {
							xlsxWb = new HSSFWorkbook(fileInfo.getInputStream());// Excel 2007 미만/ xls , 현재 라이브럴 버전이 안맞아서 poi만 버전맞춤.
							
						} catch (Exception e) {
							LOGGER.error("uploadExcel workbook Exception : " + e.getMessage());
						}
						for (Sheet sheet : xlsxWb) {
							for (Row r : sheet) {
								//처음부터 검사
								for (Cell cell : r) {
									if(! headStr.get(cell.getColumnIndex()).equals(cell) ) System.out.println(cell.getColumnIndex()+1 + "번째 값이 틀립니다.");
									System.out.println("-------------------- "+cell.getColumnIndex()+1+"번 : "+cell);
								}
							} // Row
						} // Workbook
					} catch (Exception e) {
						LOGGER.error("Exception : " + e.getMessage());
						// TODO: handle exception
					} 
				};
			} else throw new SQLException("존재하지 않는 양식입니다. 다시 확인해 주세요.");
			return "S";
		} catch (SQLException e) {
			LOGGER.error("uploadExcel.SQLException - " + e.getMessage());
			return "F";
		} finally {
		}
	}


	@Override
	public void downPathExcel(String fullPath, HttpServletResponse response) throws IOException {
        //파일 다운로드
        File xlsxFile = new File(fullPath);
		xlsxFile.setReadable(true);
		xlsxFile.setWritable(true);
		
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(xlsxFile.getName(), "UTF-8"));
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        FileInputStream fis=null;
        try {
        	fis=new FileInputStream(xlsxFile);
            in = new BufferedInputStream(fis);
            out = new BufferedOutputStream(response.getOutputStream());
            FileCopyUtils.copy(in, out);
            out.flush();
            response.setHeader("Set-Cookie", "fileDownload=true; path=/");

        } catch (IOException ex) {
            response.setHeader("Set-Cookie", "fileDownload=false; path=/");
            throw new FileNotFoundException("파일을 다운로드 에러 발생");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch(IOException e){
                	String errMsg = "IOException error";
    				LOGGER.error(errMsg);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch(IOException ne){
                	String errMsg = "IOException error";
    				LOGGER.error(errMsg);
    				throw new IOException("파일을 다운로드 에러 발생");
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch(IOException ne){
                	String errMsg = "IOException error";
    				LOGGER.error(errMsg);
                }
            }
        }
	}


	@Override
	public void downPathExcel(String path, String extension, HttpServletResponse response) throws IOException {
		//파일 다운로드
        File xlsxFile = new File((path+extension));/*기존 Override 변경사항*/
		xlsxFile.setReadable(true);
		xlsxFile.setWritable(true);
		
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(xlsxFile.getName(), "UTF-8"));

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        FileInputStream fis=null;
        try {
        	fis=new FileInputStream(xlsxFile);
            in = new BufferedInputStream(fis);
            out = new BufferedOutputStream(response.getOutputStream());
            FileCopyUtils.copy(in, out);
            out.flush();
            response.setHeader("Set-Cookie", "fileDownload=true; path=/");

        } catch (IOException ex) {
            response.setHeader("Set-Cookie", "fileDownload=false; path=/");
            throw new IOException("파일을 다운로드 에러 발생");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch(IOException e){
                	String errMsg = "IOException error";
    				LOGGER.error(errMsg);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch(IOException ne){
                	String errMsg = "IOException error";
    				LOGGER.error(errMsg);
                } catch (Exception e) {
                	String errMsg = "IOException error";
    				LOGGER.error(errMsg);
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch(IOException ne){
                	String errMsg = "IOException error";
    				LOGGER.error(errMsg);
                } catch (Exception e) {
                	String errMsg = "IOException error";
    				LOGGER.error(errMsg);
                }
            }
        }
	}


}
