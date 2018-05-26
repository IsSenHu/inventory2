package com.husen.service;

import ecjtu.husen.pojo.DAO.Sport;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

public interface SportService {
    List<Sport> getAllSports() throws IOException;
}
