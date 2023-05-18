package me.smartstore.menu;

import me.smartstore.customer.Customers;
import me.smartstore.exception.InputEmptyException;
import me.smartstore.exception.InputEndException;
import me.smartstore.exception.InputFormatException;
import me.smartstore.exception.InputRangeException;
import me.smartstore.group.Group;
import me.smartstore.group.GroupType;
import me.smartstore.group.Groups;
import me.smartstore.group.Parameter;
import me.smartstore.util.Message;
import me.smartstore.util.UtilMethod;
public class GroupMenu implements Menu {

    /////////////////////////////////////////
    ////////////// singleton ////////////////
    private static GroupMenu groupMenu;

    public static GroupMenu getInstance() {
        if (groupMenu == null) {
            groupMenu = new GroupMenu();
        }
        return groupMenu;
    }
    /////////////////////////////////////////
    /////////////////////////////////////////


    Groups allGroups = Groups.getInstance();
    Customers allCustomers = Customers.getInstance();


    public String chooseGroup() {
        while ( true ) {
            try {
                System.out.print("Which group (GENERAL (G), VIP (V), VVIP (VV))? ");
                String choice = nextLine(Message.END_MSG);

                if (choice.equals("")) throw new InputEmptyException();
                return choice;

            } catch (InputEmptyException e) {
                System.out.println(Message.ERR_MSG_INVALID_INPUT_NULL);
            } catch (IllegalArgumentException e) {
                System.out.println(Message.ERR_MSG_INVALID_INPUT_RANGE);
            } catch (InputEndException e) {
                System.out.println(Message.ERR_MSG_INPUT_END);
                return null;
            }
        }
    }

    @Override
    public void manage() {
        while ( true ) {
            int choice = chooseMenu(new String[]{
                    "Set Parameter",
                    "View Parameter",
                    "Update Parameter",
                    "Back"});

            if (choice == 1) {
                setParameter();
            } else if (choice == 2) {
                viewParameter();
            } else if (choice == 3) {
                updateParameter();
            } else if (choice == 4) {
                break;
            }
        }
    }

    public void setParameter() {
        while ( true ) {
            String strGroup = chooseGroup(); // "V", "VIP" (string) => GroupType.VIP
            if (strGroup == null) return;

            GroupType groupType;
            try {
                groupType = GroupType.valueOf(strGroup).replaceFullName();
            } catch (IllegalArgumentException e) {
                System.out.println("\n" + Message.ERR_MSG_INVALID_INPUT_RANGE);
                continue;
            }

            Group grp = allGroups.find(groupType);
            if (grp != null && grp.getParameter() != null) {
                System.out.println("\n" + grp.getGroupType() + " group already exists.");
                System.out.println("\n" + grp);
            } else {
                Parameter param = new Parameter();

                while ( true ) {
                    int choice = chooseMenu(new String[]{
                            "Minimum Spent Time",
                            "Minimum Total Pay",
                            "Back"});
                    if (choice == 1) {
                        setParameterMinimumSpentTime(param);
                    } else if (choice == 2) {
                        setParameterMinimumTotalPay(param);
                    } else if (choice == 3) break;
//                    else System.out.println("\n" + Message.ERR_MSG_INVALID_INPUT_RANGE);
                }

                if (UtilMethod.isAllNonNUll(param, param.getMinTime(), param.getMinPay())) {
                    Group newGrp = allGroups.find(groupType);
                    newGrp.setParameter(param);
                    allCustomers.refresh(allGroups);
                    System.out.println("\n" + newGrp);
                } else {
                    System.out.println("No parameter is added. Please fill out all information.");
                }
            }
        }
    }

    public void viewParameter() {
        while ( true ) {
            String strGroup = chooseGroup();
            if (strGroup == null) return;

            GroupType groupType;
            try {
                groupType = GroupType.valueOf(strGroup).replaceFullName();
            } catch (IllegalArgumentException e) {
                System.out.println("\n" + Message.ERR_MSG_INVALID_INPUT_TYPE);
                continue;
            }

            Group grp = allGroups.find(groupType);
            System.out.println();
            System.out.println(grp);
        }
    }

    public void updateParameter() {
        while ( true ) {
            String strGroup = chooseGroup();
            if (strGroup == null) return;

            GroupType groupType;
            try {
                groupType = GroupType.valueOf(strGroup).replaceFullName();
            } catch (IllegalArgumentException e) {
                System.out.println("\n" + Message.ERR_MSG_INVALID_INPUT_RANGE);
                return;
            }

            Group grp = allGroups.find(groupType);
            if (grp == null || grp.getParameter() == null) {
                System.out.println("\nNo parameter. Set the parameter first.");
                return;
            }

            System.out.println("\n" + grp);
            Parameter param = grp.getParameter();

            while ( true ) {
                int choice = chooseMenu(new String[]{
                        "Minimum Spent Time",
                        "Minimum Total Pay",
                        "Back"});
                if (choice == 1) {
                    setParameterMinimumSpentTime(param);
                } else if (choice == 2) {
                    setParameterMinimumTotalPay(param);
                } else if (choice == 3) {
                    break;
                }
            }

            allGroups.update(new Group(groupType, param));
            allCustomers.refresh(allGroups);
            System.out.println("\n" + grp);

        }
    }

    public void setParameterMinimumSpentTime(Parameter param) {
        while ( true ) {
            try {
                System.out.print("\nInput Minimum Spent Time: ");
                int minimumSpentTime = nextInt(Message.END_MSG);
                if (minimumSpentTime < 0) throw new InputRangeException();

                param.setMinTime(minimumSpentTime);
                return;
            } catch (InputFormatException e) {
                System.out.println(Message.ERR_MSG_INVALID_INPUT_TYPE);
            } catch (InputRangeException e) {
                System.out.println(Message.ERR_MSG_INVALID_INPUT_RANGE);
            } catch (InputEndException e) {
                System.out.println(Message.ERR_MSG_INPUT_END);
                return;
            }
        }
    }


    public void setParameterMinimumTotalPay(Parameter param) {

        while ( true ) {
            try {
                System.out.print("\nInput Minimum Total Pay: ");
                int minimumTotalPay = nextInt(Message.END_MSG);
                if (minimumTotalPay < 0) throw new InputRangeException();
                param.setMinPay(minimumTotalPay);
                return;
            } catch (InputFormatException e) {
                System.out.println(Message.ERR_MSG_INVALID_INPUT_TYPE);
            } catch (InputRangeException e) {
                System.out.println(Message.ERR_MSG_INVALID_INPUT_RANGE);
            } catch (InputEndException e) {
                System.out.println(Message.ERR_MSG_INPUT_END);
                return;
            }
        }
    }
}