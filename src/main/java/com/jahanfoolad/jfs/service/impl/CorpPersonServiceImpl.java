package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.dto.CorpPersonDto;
import com.jahanfoolad.jfs.jpaRepository.CorpPersonRepository;
import com.jahanfoolad.jfs.service.CorpPersonService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CorpPersonServiceImpl implements CorpPersonService {

    @Autowired
    CorpPersonRepository corpPersonRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Override
    public List<CorpPerson> getCorpPeople() {
        return corpPersonRepository.findAll();
    }

    @Override
    public CorpPerson getCorpPersonByUserId(Long id) throws Exception {
        return corpPersonRepository.findById(id).orElseThrow(() -> new Exception(enMessageSource.getMessage("item_not_found_message", null, Locale.ENGLISH)));
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
