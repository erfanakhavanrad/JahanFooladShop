package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.domain.dto.CorpPersonDto;
import com.jahanfoolad.jfs.service.CorpPersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/corpperson")
@RestController
public class CorpPersonController {

    @Autowired
    CorpPersonService corpPersonService;

    @Autowired
    ResponseModel responseModel;


    @GetMapping(path = "/login")
    public ResponseModel login(@RequestBody CorpPerson corpPerson, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        responseModel.clear();
        try {
            CorpPerson user = corpPersonService.login(corpPerson);
            responseModel.setContent(user);
            responseModel.setResult(1);
            responseModel.setRecordCount(1);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;

    }


    @GetMapping("/getall")
    public ResponseModel getCorpPersons(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            List<CorpPerson> corpPeople = corpPersonService.getCorpPeople();
            responseModel.setContents(corpPeople);
            responseModel.setResult(1);
            responseModel.setRecordCount(corpPeople.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }


    @GetMapping(path = "/getbyid")
    public ResponseModel getCorpPersonById(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            responseModel.setContent(corpPersonService.getCorpPersonById(id));
            responseModel.setResult(1);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @GetMapping(path = "/findbycontact")
    public ResponseModel findByContact(ContactDto contactDto, @RequestParam Integer pageNo, Integer perPage, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        try {
            responseModel.clear();
            Page<CorpPerson> corpPeople = corpPersonService.findByContact(contactDto, pageNo, perPage);
            responseModel.setContent(corpPeople);
            responseModel.setResult(1);
            responseModel.setRecordCount((int) corpPeople.getTotalElements());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }


    @PostMapping("/save")
    public ResponseModel createCorpPerson(@RequestBody CorpPersonDto corpPersonDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            responseModel.clear();
            responseModel.setContent(corpPersonService.createCorpPerson(corpPersonDto));
            responseModel.setResult(1);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @PutMapping(path = "/update")
    public ResponseModel updateCorpPerson(@RequestBody CorpPersonDto corpPersonDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            responseModel.setContent(corpPersonService.updateCorpPerson(corpPersonDto));
            responseModel.setResult(1);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
            responseModel.setResult(0);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deleteCorpPerson(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            corpPersonService.deleteCorpPerson(id);
            responseModel.clear();
            responseModel.setResult(1);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }


}
