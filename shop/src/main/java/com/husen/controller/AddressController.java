package com.husen.controller;

import com.husen.dao.AddressDao;
import com.husen.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 11785
 */
@Controller
public class AddressController {
    @Autowired
    private AddressDao addressDao;

    @RequestMapping("/addressById.do")
    private @ResponseBody Address addressById(Integer addressId){
        return addressDao.findById(addressId).get();
    }

}
