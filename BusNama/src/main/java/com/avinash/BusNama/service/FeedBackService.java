package com.avinash.BusNama.service;

import com.avinash.BusNama.exception.BusException;
import com.avinash.BusNama.exception.FeedBackException;
import com.avinash.BusNama.exception.UserException;
import com.avinash.BusNama.model.Feedback;
import com.avinash.BusNama.model.User;

import java.util.List;

public interface FeedBackService {
    Feedback addFeedBack(Feedback feedback,Integer busId,String key)throws BusException, UserException;
    Feedback updateFeedBack(Feedback feedback,String key)throws FeedBackException,UserException;
    Feedback deleteFeedBack(Integer id,String key)throws FeedBackException,UserException;
    Feedback viewFeedBack(Integer id)throws FeedBackException;
    List<Feedback> viewFeedbackAll()throws FeedBackException;
}
