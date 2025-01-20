package com.mustafa.hotelreservationsystem.dao;


import com.mustafa.hotelreservationsystem.models.Feature;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.List;

public class DaoTests {
    public static void featureDaoImplUnitTest(){

        FeatureDao fdao = new FeatureDaoImpl();

        // creating feature
//        fdao.save(new Feature("feature 1", 1000));
//        fdao.save(new Feature("feature 2", 2000));
//        fdao.save(new Feature("feature 3", 3000));

        // retrieving all features
//        List<Feature> features = fdao.retrieveAllFeatures();
//        for (Feature feature : features) {
//            System.out.println(feature);
//        }

        // updating feature
//        fdao.update(new Feature(5, "updatedFeature1", 2999));


        // updating specified attribute with specified value
        fdao.updateSpecifiedFeatureField(9, "featureName", "new f1");


        // retrieving feature
//        Feature f1 = fdao.retrieve(2);
//        System.out.println(f1);

        // deleting feature
//        fdao.delete(2);
    }


    public static void adminDaoImplUnitTest(){

        AdminDao aDao = new AdminDaoImpl();

        // creating admin
//        aDao.save(new Admin("mustafa gunaltay", "user1", "pw1"));
//        aDao.save(new Admin("ali kilic", "user2", "pw2"));
//        aDao.save(new Admin("mehmet soylu", "user3", "pw3"));

        // retrieving all admins
//        List<Admin> adminList = aDao.retrieveAllAdmins();
//        for (Admin admin : adminList) {
//            System.out.println(admin);
//        }

        // updating admin
//        aDao.update(new Admin(2, "ali kilicoglu", "userr2", "pww2"));

        // updating specified attribute with specified value
//        aDao.updateSpecifiedAdminField(16, "passwordd", "now passwordd");

        // retrieve admin
//        Admin ra = aDao.retrieve(2);
//        System.out.println("Retrieve admin whose id is 2: \n" + ra);

        // delete admin
//        Admin da = aDao.delete(2);
//        System.out.println("Delete admin whose id is 2: \n" + da);

    }


    public static void receptionistDaoImplUnitTest(){

        ReceptionistDao rDao = new ReceptionistDaoImpl();

        // creating receptionist
//        Receptionist r1 = new Receptionist("must gun", "user must", "must123");
//        Receptionist r2 = new Receptionist("ali kilic", "user ali", "ali123");
//        Receptionist r3 = new Receptionist("mehmet soylu", "user mehmet", "mehmet123");
//        rDao.save(r1);
//        rDao.save(r2);
//        rDao.save(r3);

        // retrieving receptionists
//        List<Receptionist> allReceptionist = rDao.retrieveAllReceptionists();
//        for (Receptionist r : allReceptionist){
//            System.out.println(r);
//        }

        // updatig receptionist
//        Receptionist rToBeUpdated = new Receptionist(2, "new ali kilic", "new user ali", "newali123");
//        rDao.update(rToBeUpdated);

        // updating specified attribute with specified value
        rDao.updateSpecifiedReceptionistField(3, "dsadsa", "updated user mehmetov");

        // retrieve receptionist
//        Receptionist rRetrieve = rDao.retrieve(2);
//        System.out.println(rRetrieve);

        // delete receptionist
//        Receptionist rDeleted = rDao.delete(2);
//        System.out.println(rDeleted);

    }


}
