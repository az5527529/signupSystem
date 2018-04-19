package com.entity;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by victor on 2018/4/2.
 */
@Entity
public class Activity implements Serializable {
    private Long activityId;
    private String startTime;
    private String endTime;
    private String topic;
    private String content;
    private String backgroundUrl;
    private Double money;

    @Id
    @Column(name = "activity_id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    @Basic
    @Column(name = "start_time", nullable = false, length = 20)
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = false, length = 20)
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "topic", nullable = false, length = 64)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Basic
    @Column(name = "content", nullable = false, length = 512)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "background_url", nullable = false, length = 128)
    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    @Basic
    @Column(name = "money", nullable = false, precision = 0)
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (activityId != null ? !activityId.equals(activity.activityId) : activity.activityId != null) return false;
        if (startTime != null ? !startTime.equals(activity.startTime) : activity.startTime != null) return false;
        if (endTime != null ? !endTime.equals(activity.endTime) : activity.endTime != null) return false;
        if (topic != null ? !topic.equals(activity.topic) : activity.topic != null) return false;
        if (content != null ? !content.equals(activity.content) : activity.content != null) return false;
        if (backgroundUrl != null ? !backgroundUrl.equals(activity.backgroundUrl) : activity.backgroundUrl != null)
            return false;
        if (money != null ? !money.equals(activity.money) : activity.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = activityId != null ? activityId.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (backgroundUrl != null ? backgroundUrl.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
