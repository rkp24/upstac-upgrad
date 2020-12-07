package org.upgrad.upstac.testrequests.consultation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestQueryService;
import org.upgrad.upstac.testrequests.TestRequestUpdateService;
import org.upgrad.upstac.testrequests.flow.TestRequestFlowService;
import org.upgrad.upstac.testrequests.lab.LabResult;
import org.upgrad.upstac.testrequests.lab.LabResultRepository;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.roles.UserRole;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;


@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    Logger log = LoggerFactory.getLogger(ConsultationController.class);




    @Autowired
    private TestRequestUpdateService testRequestUpdateService;



    @Autowired
    private TestRequestQueryService testRequestQueryService;


    @Autowired
    TestRequestFlowService  testRequestFlowService;

    @Autowired
    private UserLoggedInService userLoggedInService;

    @Autowired
    private LabResultRepository labResultRepository;




    @GetMapping("/in-queue")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForConsultations()  {

        // Implement this method

        return testRequestQueryService.findBy(RequestStatus.LAB_TEST_COMPLETED);
        //Implement this method to get the list of test requests having status as 'LAB_TEST_COMPLETED'
        // make use of the findBy() method from testRequestQueryService class
        //return the result
        // For reference check the method getForTests() method from LabRequestController class

        // replace this line of code with your implementation
       // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");


    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForDoctor()  {

        //Implement this method

        // Create an object of User class and store the current logged in user first
        //Implement this method to return the list of test requests assigned to current doctor(make use of the above created User object)
        //Make use of the findByDoctor() method from testRequestQueryService class to get the list
        // For reference check the method getForTests() method from LabRequestController class

        // replace this line of code with your implementation
        //throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");
        User user = userLoggedInService.getLoggedInUser();
        return  testRequestQueryService.findByDoctor(user)
                .stream().collect(Collectors.toList());






    }



    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/assign/{id}")
    public TestRequest assignForConsultation(@PathVariable Long id) {

        // Implement this method

        // Implement this method to assign a particular test request to the current doctor(logged in user)
        //Create an object of User class and get the current logged in user
        //Create an object of TestRequest class and use the assignForConsultation() method of testRequestUpdateService to assign the particular id to the current user
        // return the above created object
        // For reference check the method assignForLabTest() method from LabRequestController class
        try {
            // replace this line of code with your implementation
            User doctor =userLoggedInService.getLoggedInUser();

            return   testRequestUpdateService.assignForConsultation(id,doctor);
            //throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");

        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }



    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/update/{id}")
    public TestRequest updateConsultation(@PathVariable Long id,@RequestBody CreateConsultationRequest creatconsultrequest) {

        // Implement this method

        // Implement this method to update the result of the current test request id with test doctor comments
        // Create an object of the User class to get the logged in user
        // Create an object of TestResult class and make use of updateConsultation() method from testRequestUpdateService class
        //to update the current test request id with the testResult details by the current user(object created)
        // For reference check the method updateLabTest() method from LabRequestController class

        try {
            // replace this line of code with your implementation
            User doctor =userLoggedInService.getLoggedInUser();
            //CreateConsultationRequest createconsultationrequest;
            //consultrequest.setSuggestion(consultrequest.getSuggestion());
            //consultrequest.setComments(consultrequest.getComments());

           // return testRequestUpdateService.updateLabTest(id,createLabResult,tester);
            return   testRequestUpdateService.updateConsultation(id,creatconsultrequest,doctor);

            //throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");


        } catch (ConstraintViolationException e) {
            throw asConstraintViolation(e);
        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }



}
