package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.models.Admin;

public class ServiceTests {
    public static void adminServiceImplTest(){
        AdminService aService = new AdminServiceImpl();

            // creating new admin
//        Admin newAdmin = new Admin("veli soylu", "userveli", "pwveli");
//        aService.createAdmin(newAdmin);



            // updating admin
            // Not = Bir admini update edebilmek icin icinde o admine ait id bilinmeli


        // changing admin's full name
//        aService.changefullName(17, "1-birinci");

        // changing admin's username
//        aService.changeUsername(17, "2-ikinci");

        // changing admin's paswordd
//        aService.changePassword(17, "3-ucuncu");

        // validation of username and password
        aService.validateAdmin("dsaads", "musti123");
        aService.validateAdmin("musti user", "sdadsa");

    }
}
