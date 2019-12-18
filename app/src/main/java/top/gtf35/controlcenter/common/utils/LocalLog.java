package top.gtf35.controlcenter.common.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import top.gtf35.controlcenter.common.settings.Settings;

/*
* 写入本地日志
* 调用方法：
* LocalLog log = new LocalLog();
* log.writeLocalLog("金额格式化失败");
* */

public class LocalLog
{
	private String filePath = Settings.INSTANCE.getLOCAL_LOG_PATH();
	private String fileName = "##notset";
	
	public LocalLog(){
        if (fileName.startsWith("##")) fileName = getStringDateShort() + ".txt";
	}
	
	public void setFilePath(String p){
		filePath = p;
	}

	public void setFileName(String n){
		fileName = n;
	}
	
	public void writeLocalLog(String txt){
		txt = getStringDate() + " =====> " + txt;
        writeTxtToFile(txt, filePath, fileName);
	}
	
	
	
	/**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    private static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
	
	
	/**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式 yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
	
	
	
	// 将字符串写入到文本文件中
    private void writeTxtToFile(String strcontent, String filePath, String fileName) {
        if (!Settings.INSTANCE.getENABLE_LOCAL_LOG()) return;
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
           // strContent = getFileContent(file) + strContent;
			raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }


    //生成文件
	private File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //生成文件夹
    private static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

	
	//读取指定目录下的所有TXT文件的文件内容
    private String getFileContent(File file) {
        String content = "";
        if (!file.isDirectory()) {  //检查此路径名的文件是否是一个目录(文件夹)
            if (file.getName().endsWith("txt")) {//文件格式为""文件
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader
							= new InputStreamReader(instream, "UTF-8");
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line = "";
                        //分行读取
                        while ((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                        instream.close();//关闭输入流
                    }
                } catch (FileNotFoundException e) {
                    Log.d("TestFile", "The File doesn't not exist.");
                } catch (IOException e) {
                    Log.d("TestFile", e.getMessage());
                }
            }
        }
        return content;
    }
	
	
	/**
     * 判断文件及目录是否存在，若不存在则创建文件及目录
     * @param filepath
     * @return
     * @throws Exception
     */
	public static File checkExist(String filepath) throws Exception {

		File file=new File(filepath);

		if (file.exists()) {//判断文件目录的存在
			System.out.println("文件夹存在！");
			if(file.isDirectory()){//判断文件的存在性
				System.out.println("文件存在！");
			}else{
				file.createNewFile();//创建文件
				System.out.println("文件不存在，创建文件成功！"   );
			}
		}else {
			System.out.println("文件夹不存在！");
			File file2=new File(file.getParent());
			file2.mkdirs();
			System.out.println("创建文件夹成功！");
			if(file.isDirectory()){
				System.out.println("文件存在！");
			}else{
				file.createNewFile();//创建文件
				System.out.println("文件不存在，创建文件成功！"   );
			}
		}
		return file;
    }
	
}
