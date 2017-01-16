package com.koalitech.bsmart.domain.enity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoson on 15/12/20.
 */
public class Speciality {
    public List<String> skills;
    public List<String> comments;

    public Speciality(){
        skills = new ArrayList<String>();
        comments = new ArrayList<String>();
    }

    public List<String> getSkills(){
        return skills;
    }

    public List<String> getComments(){
        return comments;
    }

    public void addSkill(String skill){
        skills.add(skill);
    }

    public void addComment(String comment){
        comments.add(comment);
    }

    public void changeSkill(int index,String skill){

    }
}
