package com.avinash.BusNama.serviceImpl;

import com.avinash.BusNama.exception.BusException;
import com.avinash.BusNama.exception.FeedBackException;
import com.avinash.BusNama.exception.UserException;
import com.avinash.BusNama.model.Bus;
import com.avinash.BusNama.model.CurrentUserSession;
import com.avinash.BusNama.model.Feedback;
import com.avinash.BusNama.model.User;
import com.avinash.BusNama.repository.*;
import com.avinash.BusNama.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusRepository busRepo;

    @Autowired
    private CurrentUserSessionRepository userSessionDao;

    @Override
    public Feedback addFeedBack(Feedback feedback, Integer busId, String key) throws BusException, UserException {
        CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);
        if (loggedInUser == null){
            throw new UserException("Please provide a valid key to give Feedback!");
        }
        User user = userRepository.findById(loggedInUser.getUserID()).orElseThrow(() -> new UserException("User not found!"));
        Optional<Bus> busOptional = busRepo.findById(busId);
        if (busOptional.isEmpty()){
            throw new BusException("Bus is not present with id: "+busId);
        }
        feedback.setBus(busOptional.get());
        feedback.setUser(user);
        feedback.setFeedbackDateTime(LocalDateTime.now());
        Feedback savedFeedBack = feedbackRepository.save(feedback);

        return savedFeedBack;
    }

    @Override
    public Feedback updateFeedBack(Feedback feedback, String key) throws FeedBackException, UserException {
        CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);
        if (loggedInUser == null){
            throw new UserException("Please provide a valid key to update Feedback!");
        }
        User user = userRepository.findById(loggedInUser.getUserID()).orElseThrow(() -> new UserException("User not found!"));
        Optional<Feedback> opt = feedbackRepository.findById(feedback.getFeedBackId());
        if(opt.isPresent()){
            Feedback feedback1 = opt.get();
            Optional<Bus> busOptional = busRepo.findById(feedback1.getBus().getBusId());
            if(!busOptional.isPresent()) throw new FeedBackException("Invalid bus details!");
            feedback.setBus(busOptional.get());
            feedback.setUser(user);
            user.getFeedbackList().add(feedback);
            feedback.setFeedbackDateTime(LocalDateTime.now());
            return feedbackRepository.save(feedback);
        }else
            throw new FeedBackException("No feedback found!");
    }

    @Override
    public Feedback deleteFeedBack(Integer id,String key) throws FeedBackException ,UserException{
        CurrentUserSession loggedInUser = userSessionDao.findByUuid(key);
        if (loggedInUser == null){
            throw new UserException("Please provide a valid key to update Feedback!");
        }
        User user = userRepository.findById(loggedInUser.getUserID()).orElseThrow(() -> new UserException("User not found!"));
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isPresent()){
            Feedback existingFeedback = feedbackOptional.get();
            feedbackRepository.delete(existingFeedback);
            return existingFeedback;
        }
        throw new FeedBackException("No feedback found!");
    }

    @Override
    public Feedback viewFeedBack(Integer id) throws FeedBackException {
        Optional<Feedback> optional = feedbackRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new FeedBackException(("No Feedback found!"));
    }

    @Override
    public List<Feedback> viewFeedbackAll() throws FeedBackException {
       Optional<List<Feedback>> feedbackList = Optional.of(feedbackRepository.findAll());
       if (feedbackList.isPresent()){
           return feedbackList.get();
       }
       throw new FeedBackException("No feedbacks found!");
    }
}
