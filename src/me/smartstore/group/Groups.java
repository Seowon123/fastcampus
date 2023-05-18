package me.smartstore.group;

import me.smartstore.arrays.DArray;
import me.smartstore.customer.Customer;
import me.smartstore.util.UtilMethod;

public class Groups extends DArray<Group> { // singleton

    /////////////////////////////////////////
    ////////////// singleton ////////////////
    private static Groups allGroups;

    public static Groups getInstance() {
        if (allGroups == null) {
            allGroups = new Groups();
        }
        return allGroups;
    }
    /////////////////////////////////////////
    /////////////////////////////////////////

    public Groups() {
        arrays = new Group[] {
                new Group( GroupType.NONE, null ),
                new Group( GroupType.GENERAL, null ),
                new Group( GroupType.VIP, null),
                new Group( GroupType.VVIP, null)};
        size = arrays.length;
        capacity = arrays.length;
    }

    public void setGroups(Group[] groups) {
        this.arrays = groups;
        capacity = groups.length;
        size = groups.length;
    }

    public Group[] getGroups() {
        return arrays;
    }


    public void update(Group group) {
        Group grp = find(group.getGroupType());
        if (grp != null) {
            grp.setParameter(group.getParameter());
        }

    }

    public void print() {
        for (int i = 0; i < size; i++) {
            if (arrays[i] != null) {
                System.out.println(arrays[i]);
            }
        }
    }

    public Group find(GroupType groupType) {
        for (int i = 0; i < size; i++) {
            if (arrays[i].getGroupType() == groupType)
                return arrays[i];
        }

        return null;
    }

    public Group findGroupFor(Customer customer) {
        if (arrays == null) return null;
        if (UtilMethod.isAnyNUll(customer, customer.getSpentTime(), customer.getTotalPay())) return null;

        for (int i = size - 1; i >= 0; i--) {
            if (UtilMethod.isAnyNUll(arrays[i], arrays[i].getParameter())) continue;

            Parameter param = arrays[i].getParameter();
            if (UtilMethod.isAnyNUll(param, param.getMinTime(), param.getMinPay())) continue;

            if (customer.getSpentTime() >= param.getMinTime()
                    && customer.getTotalPay() >= param.getMinPay()) {
                return arrays[i];
            }
        }
        return null;
    }


    @Override
    public String toString() {
        String str = "";

        for(int i = 0; i < size; ++i) {
            str = str + " " + arrays[i] + "\n";
        }

        return str;
    }
}