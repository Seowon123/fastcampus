package me.smartstore;

import me.smartstore.customer.Customer;
import me.smartstore.customer.Customers;
import me.smartstore.group.Group;
import me.smartstore.group.GroupType;
import me.smartstore.group.Groups;
import me.smartstore.group.Parameter;
import me.smartstore.menu.*;

public class SmartStoreApp {

    /////////////////////////////////////////
    ////////////// singleton ////////////////
    private static SmartStoreApp smartStoreApp;

    public static SmartStoreApp getInstance() {
        if (smartStoreApp == null) {
            smartStoreApp = new SmartStoreApp();
        }
        return smartStoreApp;
    }
    /////////////////////////////////////////
    /////////////////////////////////////////

    private final Groups allGroups = Groups.getInstance();
    private final Customers allCustomers = Customers.getInstance();
    private final MainMenu mainMenu = MainMenu.getInstance();


    public SmartStoreApp test() {
        for (int i = 1; i < allGroups.size(); i++) {
            allGroups.get(i).setParameter(new Parameter((i+1)*10, (i+1)*100000));
        }

        // allGroups.add(new Group( GroupType.GENERAL, new Parameter(10, 100000)) );
        // allGroups.add(new Group( GroupType.VIP, new Parameter(20, 200000)) );
        // allGroups.add(new Group( GroupType.VVIP, new Parameter(30, 300000)) );

        for (int i = 0; i < 20; i++) {
            allCustomers.add(new Customer(
                    Character.toString(
                            (char) ('a' + i)),
                    (char) ('a' + i) + "123",
                    ((int) (Math.random() * 5) + 1) * 10,
                    ((int) (Math.random() * 5) + 1) * 100000));
        }

        allCustomers.refresh(allGroups); // Customer 들의 Group 지정 (자동 배정)
        return this;
    }

    private void details() {
        System.out.println("\n\n===========================================");
        System.out.println(" Title : SmartStore Customer Segmentation");
        System.out.println(" Release Date : 23.05.16");
        System.out.println(" Copyright 2023 Eunbin All rights reserved.");
        System.out.println("===========================================\n");
    }

    public void run() {
        details();

        mainMenu.manage();
    }
}