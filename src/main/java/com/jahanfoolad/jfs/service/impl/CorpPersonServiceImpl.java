package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.dto.CorpPersonDto;
import com.jahanfoolad.jfs.jpaRepository.CorpPersonRepository;
import com.jahanfoolad.jfs.service.CorpPersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorpPersonServiceImpl implements CorpPersonService {

    @Autowired
    CorpPersonRepository corpPersonRepository;

    @Override
    public List<CorpPerson> getCorpPeople() {
        return corpPersonRepository.findAll();
    }

    @Override
    public CorpPerson getCorpPersonByUserId(Long id) throws Exception {
        return corpPersonRepository.findById(id).orElseThrow(() -> new Exception("CorpNot Found"));
    }

    @Override
    public CorpPerson createCorpPerson(CorpPersonDto corpPersonDto) {
        ModelMapper modelMapper = new ModelMapper();
        CorpPerson corpPerson = modelMapper.map(corpPersonDto, CorpPerson.class);
        return modelMapper.map(corpPersonRepository.save(corpPerson), CorpPerson.class);
    }

    @Override
    public void deleteCorpPerson(Long id) {
        corpPersonRepository.deleteById(id);
    }
}
