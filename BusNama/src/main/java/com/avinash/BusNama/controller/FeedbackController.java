package com.avinash.BusNama.controller;

import com.avinash.BusNama.exception.BusException;
import com.avinash.BusNama.exception.FeedBackException;
import com.avinash.BusNama.exception.UserException;
import com.avinash.BusNama.model.Feedback;
import com.avinash.BusNama.service.FeedBackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/busnama")
public class FeedbackController {

    @Autowired
    private FeedBackService feedBackService;

    @PostMapping("/user/feedback/add/{busId}")
    public ResponseEntity<Feedback> addFeedback(@Valid @RequestBody Feedback feedback, @PathVariable("busId")Integer busId, @RequestParam(required = false)String key)throws UserException, BusException{
        Feedback feedback1 = feedBackService.addFeedBack(feedback,busId,key);
        return new ResponseEntity<Feedback>(feedback1, HttpStatus.ACCEPTED);
    }

    @PutMapping("/user/feedback/update")
    public ResponseEntity<Feedback> updateFeedback(@Valid @RequestBody Feedback feedback,@RequestBody(required = false)String key)throws FeedBackException,UserException{
        Feedback feedback1 = feedBackService.updateFeedBack(feedback,key);
        return new ResponseEntity<Feedback>(feedback1,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user/feedback/delete/{id}")
    public ResponseEntity<Feedback> deleteFeedback(@PathVariable("id")Integer feedbackId,@RequestParam(required = false)String key)throws FeedBackException,UserException{

        Feedback feedback = feedBackService.deleteFeedBack(feedbackId,key);
        return new ResponseEntity<Feedback>(feedback,HttpStatus.ACCEPTED);
    }


    @GetMapping("/feedback/{id}")
    public ResponseEntity<Feedback> viewFeedback(@PathVariable("id")Integer ID)throws FeedBackException{
        Feedback feedback = feedBackService.viewFeedBack(ID);
        return new ResponseEntity<Feedback>(feedback,HttpStatus.ACCEPTED);
    }

    @GetMapping("feedback/all")
    public ResponseEntity<List<Feedback>> viewFeedbackAll() throws FeedBackException{
        List<Feedback> feedbackList = feedBackService.viewFeedbackAll();
        return new ResponseEntity<List<Feedback>>(feedbackList,HttpStatus.ACCEPTED);
    }
}
