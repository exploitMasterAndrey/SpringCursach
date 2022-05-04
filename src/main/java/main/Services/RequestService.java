package main.Services;

import main.model.Request;
import main.model.User;
import main.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    public void saveRequest(Request request){
        requestRepository.save(request);
    }

    public List<Request> getAllRequestsByUser(User user){
        List<Request> requestList = requestRepository.findAllByUser(user);
        return requestList;
    }

    public List<Request> getAllRequests(){
        List<Request> requestList = requestRepository.findAll();
        return requestList;
    }

    public Request findById(Long id){
        return requestRepository.findById(id).get();
    }
}
