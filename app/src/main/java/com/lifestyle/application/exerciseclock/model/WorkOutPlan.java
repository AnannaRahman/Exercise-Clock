package com.lifestyle.application.exerciseclock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ananna on 11/10/2017.
 */

public class WorkOutPlan {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("sets")
    @Expose
    public int sets;

    @SerializedName("restInterval")
    @Expose
    public long restInterval;

    @SerializedName("workOutDuration")
    @Expose
    public long workOutDuration;

    public String code1;
    public String code2;

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public long getRestInterval() {
        return restInterval;
    }

    public void setRestInterval(long restInterval) {
        this.restInterval = restInterval;
    }

    public long getWorkOutDuration() {
        return workOutDuration;
    }

    public void setWorkOutDuration(long workOutDuration) {
        this.workOutDuration = workOutDuration;
    }
}
