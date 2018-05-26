package com.husen.service.impl;

import com.alibaba.fastjson.JSON;
import com.husen.service.SportService;
import ecjtu.husen.pojo.DAO.Sport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
@Transactional
public class SportServiceImpl implements SportService {

    /**
     * 调用http接口获取所有的运动
     * @return
     * @throws IOException
     */
    @Override
    public List<Sport> getAllSports() throws IOException {
        URL url = new URL("http://localhost:8080/ws/rest/sports");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Charset", "utf-8");
        httpURLConnection.setDoOutput(true);
        InputStream inputStream = httpURLConnection.getInputStream();
        byte[] b = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        while (true){
            len = inputStream.read(b);
            if(len == -1){
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        String response = byteArrayOutputStream.toString("utf-8");
        List<Sport> sports = JSON.parseArray(response, Sport.class);
        return sports;
    }
}
