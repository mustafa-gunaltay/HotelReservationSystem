package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.InvalidAdminException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidReceptionistException;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.List;

public class ServiceTests {
    public static void adminServiceImplUnitTest(){
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

        // validation of username and password of admin
        try {
            aService.validateAdmin("dsaads", "musti123");
        } catch (InvalidAdminException e) {
            System.out.println(e.getMessage());
        }

        try {
            aService.validateAdmin("musti user", "sdadsa");;
        } catch (InvalidAdminException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void ReceptionistServiceImplUnitTest(){

        ReceptionistService rService = new ReceptionistServiceImpl();

        // creating new receptionist
//        Receptionist r1 = new Receptionist("must gun", "user must", "must123");
//        Receptionist r2 = new Receptionist("ali kilic", "user ali", "ali123");
//        Receptionist r3 = new Receptionist("mehmet soylu", "user mehmet", "mehmet123");
//        rService.createReceptionist(r1);
//        rService.createReceptionist(r2);
//        rService.createReceptionist(r3);

        // get all receptionists
//        List<Receptionist> allReceptionists = rService.getAllReceptionists();
//        for (Receptionist r : allReceptionists){
//            System.out.println(r);
//        }


            // updating admin
            // Not = Bir resepsiyonisti update edebilmek icin icinde o resepsiyoniste ait id bilinmeli

        //changing receptionist's full name
//        rService.changefullName(5, "new ali kilic");

        //changing receptionist's username
//        rService.changeUsername(6, "new user mehmet");

        //changing receptionist's passwordd
//        rService.changePassword(6, "newmehmet123");

        // validation of username and password of receptionist
        try {
            rService.validateReceptionist("dsasad", "dsasad");
        }
        catch (InvalidReceptionistException e){
            System.out.println(e);
        }

        try {
            rService.validateReceptionist("new user mehmet", "Mehmet123");
        }
        catch (InvalidReceptionistException e){
            System.out.println(e);
        }

        try {
            rService.validateReceptionist("new user mehmet", "newmehmet123");
        }
        catch (InvalidReceptionistException e){
            System.out.println(e);
        }




    }
}
