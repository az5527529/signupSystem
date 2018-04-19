package com.service.impl.common;

import com.Constant.DocumentNoEnum;
import com.entity.NoRobot;
import com.service.common.NoRobotService;
import com.util.Tools;
import org.hibernate.LockMode;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by victor on 2018/4/3.
 */
@Service("noRobotService")
public class NoRobotServiceImpl  extends BaseServiceImpl<NoRobot> implements NoRobotService{
    @Override
    public String getCurrentNo(DocumentNoEnum noType, int length) {
        return getCurrentNos(noType.getNoPrefix(), noType.getBit(), 1).get(0);
    }

    @Override
    public synchronized List<String> getCurrentNos(String prefix, int bit, int length) {

        if(!prefix.startsWith("W")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            Date date = new Date();
            prefix += sdf.format(date);
        }
        List<String> result = new ArrayList<String>();
        String hql = "select robot from NoRobot robot where noRobotId=:id";
        List<NoRobot> list = getSession().createQuery(hql).setLockMode("robot", LockMode.UPGRADE)
                .setString("id", prefix).list();
        long currentVal = length;
        if (list.isEmpty()) {
            NoRobot robot = new NoRobot();
            robot.setNoRobotId(prefix);
            robot.setCurrentVal(currentVal);
            robot.setBit(bit);
            super.getSession().save(robot);
            super.getSession().flush();
            super.getSession().clear();
        } else {
            NoRobot robot = list.get(0);
            currentVal = robot.getCurrentVal() + length;
            robot.setCurrentVal(currentVal);
            super.update(robot);
            super.getSession().flush();
            super.getSession().clear();
        }
        for (int i = length - 1; i >= 0; i--) {
            String cv = String.valueOf(currentVal - i);
            if (cv.length() >= bit) {
                result.add(prefix + cv);
            } else {
                result.add(prefix + Tools.getLengthChar(bit - cv.length(), '0') + cv);
            }
        }
        return result;
    }
}
