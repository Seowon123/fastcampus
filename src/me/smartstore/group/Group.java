package me.smartstore.group;

import me.smartstore.customer.Customers;
import java.util.Objects;

public class Group {
    private GroupType type;
    private Parameter param;

    public Group() {
        this(null, null);
    }

    public Group( GroupType type, Parameter param ) {
        this.type = type;
        this.param = param;
    }

    public GroupType getGroupType() {
        return type;
    }

    public void setGroupType(GroupType type) {
        this.type = type;
    }

    public Parameter getParameter() {
        return param;
    }

    public void setParameter(Parameter param) {
        this.param = param;
    }

    public Customers getCustomers(Customers allCustomers) {
        return allCustomers.findCustomers(this);
    }

    public void update(GroupType type, Parameter param) {
        this.type = type;
        this.param = param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return type == group.type && Objects.equals(param, group.param);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, param);
    }

    @Override
    public String toString() {
        if (type == null) {
            return "No group.";
        } else if (param == null) {
            return "GroupType: " + type + "\nParameter: null";
        } else {
            return "GroupType: " + type + "\nParameter: " + param;
        }
    }
}