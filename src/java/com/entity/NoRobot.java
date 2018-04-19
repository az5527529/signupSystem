package com.entity;

import javax.persistence.*;

/**
 * Created by victor on 2018/4/3.
 */
@Entity
@Table(name = "no_robot", schema = "activitymanage", catalog = "")
public class NoRobot {
    private String noRobotId;
    private Long currentVal;
    private Integer bit;

    @Id
    @Column(name = "no_robot_id", nullable = false, length = 16)
    public String getNoRobotId() {
        return noRobotId;
    }

    public void setNoRobotId(String noRobotId) {
        this.noRobotId = noRobotId;
    }

    @Basic
    @Column(name = "current_val", nullable = false)
    public Long getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(Long currentVal) {
        this.currentVal = currentVal;
    }

    @Basic
    @Column(name = "bit", nullable = false)
    public Integer getBit() {
        return bit;
    }

    public void setBit(Integer bit) {
        this.bit = bit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoRobot noRobot = (NoRobot) o;

        if (noRobotId != null ? !noRobotId.equals(noRobot.noRobotId) : noRobot.noRobotId != null) return false;
        if (currentVal != null ? !currentVal.equals(noRobot.currentVal) : noRobot.currentVal != null) return false;
        if (bit != null ? !bit.equals(noRobot.bit) : noRobot.bit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = noRobotId != null ? noRobotId.hashCode() : 0;
        result = 31 * result + (currentVal != null ? currentVal.hashCode() : 0);
        result = 31 * result + (bit != null ? bit.hashCode() : 0);
        return result;
    }
}
