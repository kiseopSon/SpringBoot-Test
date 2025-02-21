package com.example.readdetectedfiles.API.Modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class dagCreate {
    private File file;
    private String name;
    private String path;
    private List<String> names;
    LocalDate date = LocalDate.now();
    public dagCreate(String name , String path){
        this.name = name;
        this.path = path;
        String directoryPath = this.path;
        String fileName = this.name+".py";
        String filePath = directoryPath + File.separator + fileName;

        // 파이썬 파일에 작성할 내용
        String pythonCode1 = "" +
                "from airflow import DAG\n" +
                "from airflow.operators.bash_operator import BashOperator\n" +
                "from datetime import datetime, timedelta\n" +
                "# DAG 기본 옵션 설정\n" +
                "default_args = {\n" +
                "    'owner': 'airflow',  # 소유자 이름을 지정합니다.\n" +
                "    'depends_on_past': False,  # 이전 태스크의 성공 여부에 따라 현재 태스크가 실행될지 여부를 설정합니다.\n" +
                "    'email': ['ks.son@nuriggum.com'],  # 알림을 받을 이메일 주소를 지정합니다.\n" +
                "    'email_on_failure': True,  # 태스크 실패 시 이메일 알림 여부를 설정합니다.\n" +
                "    'email_on_retry': True,  # 태스크 재시도 시 이메일 알림 여부를 설정합니다.\n" +
                "    'retries': 1,  # 태스크 실패 시 재시도 횟수를 지정합니다.\n" +
                "    'retry_delay': timedelta(minutes=5),  # 재시도 간격을 지정합니다.\n" +
                "    'start_date': datetime("+ date.getYear() +"," + date.getMonthValue()+"," + "1" +"),  # DAG 시작 날짜를 지정합니다.\n" +
                "}";

        String pythonCode2 = "" +
                "# DAG 인스턴스 생성 및 정의\n" +
                "dag = DAG(\n" +
                "    '" + name + "',  # DAG의 이름\n" +
                "    default_args=default_args,  # 기본 인수\n" +
                "    description='test',  # DAG 설명\n" +
                "    schedule_interval=timedelta(days=1),  # DAG의 실행 주기 (여기서는 하루에 한 번)\n" +
                "    catchup=False  # catchup을 비활성화하여 과거의 태스크를 실행하지 않음\n" +
                ")\n";

        //별도의 정의기능으로 활용 예정
        String pythonCode3 = "" +
                "";

        String pythonCode4 = "" +
                "# PythonOperator를 사용하여 태스크 생성\n" +
                "task = BashOperator(\n" +
                "    task_id='"+"run_"+this.name+"_script',  # 태스크 ID\n" +
                "    bash_command='sh "+this.path + File.separator + this.name+".sh',  # 실행할 파일 경로\n" +
                "    dag=dag  # 이 태스크가 속한 DAG 인스턴스를 지정\n" +
                ")\n" +
                "task";

        // 디렉토리 존재 여부 확인 및 생성
        file = new File(directoryPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 파일 생성 및 내용 작성
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(pythonCode1);
            writer.write(pythonCode2);
            writer.write(pythonCode3);
            writer.write(pythonCode4);
            System.out.println("Python file created successfully at " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while creating the Python file: " + e.getMessage());
        }
    }
    public dagCreate(List<String> names, String name, String path){
        this.name = name;
        this.names = names;
        this.path = path;
        String directoryPath = this.path;
        String fileName = name+".py";
        String filePath = directoryPath + File.separator + fileName;

        // 파이썬 파일에 작성할 내용
        String pythonCode1 = "" +
                "from airflow import DAG\n" +
                "from airflow.operators.bash_operator import BashOperator\n" +
                "from datetime import datetime, timedelta\n" +
            "# DAG 기본 옵션 설정\n" +
            "default_args = {\n" +
                "    'owner': 'airflow',  # 소유자 이름을 지정합니다.\n" +
                "    'depends_on_past': False,  # 이전 태스크의 성공 여부에 따라 현재 태스크가 실행될지 여부를 설정합니다.\n" +
                "    'email': ['ks.son@nuriggum.com'],  # 알림을 받을 이메일 주소를 지정합니다.\n" +
                "    'email_on_failure': True,  # 태스크 실패 시 이메일 알림 여부를 설정합니다.\n" +
                "    'email_on_retry': True,  # 태스크 재시도 시 이메일 알림 여부를 설정합니다.\n" +
                "    'retries': 1,  # 태스크 실패 시 재시도 횟수를 지정합니다.\n" +
                "    'retry_delay': timedelta(minutes=5),  # 재시도 간격을 지정합니다.\n" +
                "    'start_date': datetime("+ date.getYear() +"," + date.getMonthValue()+"," + "1" +"),  # DAG 시작 날짜를 지정합니다.\n" +
            "}";

        String pythonCode2 = "" +
            "# DAG 인스턴스 생성 및 정의\n" +
            "dag = DAG(\n" +
                "    '" + name + "',  # DAG의 이름\n" +
                "    default_args=default_args,  # 기본 인수\n" +
                "    description='test',  # DAG 설명\n" +
                "    schedule_interval=timedelta(days=1),  # DAG의 실행 주기 (여기서는 하루에 한 번)\n" +
                "    catchup=False  # catchup을 비활성화하여 과거의 태스크를 실행하지 않음\n" +
            ")\n";

        //별도의 정의기능으로 활용 예정
        String pythonCode3 = "" +
            "";

        String pythonCode4 = "" +
                "# PythonOperator(x) BashOperator(o)를 사용하여 태스크 생성\n" +
                name + " = BashOperator(\n" +
                "    task_id='"+"run_"+this.name+"_script',  # 태스크 ID\n" +
                "    bash_command='sh "+this.path + File.separator + this.name+".sh',  # 실행할 파일 경로\n" +
                "    dag=dag  # 이 태스크가 속한 DAG 인스턴스를 지정\n" +
                ")\n" +
                "task";

        // 디렉토리 존재 여부 확인 및 생성
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 생성 및 내용 작성
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(pythonCode1);
            writer.write(pythonCode2);
            writer.write(pythonCode3);
            writer.write(pythonCode4);
            System.out.println("Python file created successfully at " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while creating the Python file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
