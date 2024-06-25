package com.vega.be_coding_task_nikhil.service.investor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vega.be_coding_task_nikhil.model.dto.IndividualInvestorDetailsDTO;
import com.vega.be_coding_task_nikhil.model.dto.InstitutionalInvestorDetailsDTO;
import com.vega.be_coding_task_nikhil.model.dto.InvestorDTO;
import com.vega.be_coding_task_nikhil.model.entity.IndividualInvestorDetails;
import com.vega.be_coding_task_nikhil.model.entity.InstitutionalInvestorDetails;
import com.vega.be_coding_task_nikhil.model.entity.Investor;
import com.vega.be_coding_task_nikhil.model.enums.InvestorType;
import com.vega.be_coding_task_nikhil.repository.IndividualInvestorDetailsRepository;
import com.vega.be_coding_task_nikhil.repository.InstitutionalInvestorDetailsRepository;
import com.vega.be_coding_task_nikhil.repository.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvestorServiceImpl implements InvestorService {

    private final InvestorRepository investorRepository;
    private final IndividualInvestorDetailsRepository individualInvestorDetailsRepository;
    private final InstitutionalInvestorDetailsRepository institutionalInvestorDetailsRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public InvestorServiceImpl(InvestorRepository investorRepository,
                               IndividualInvestorDetailsRepository individualInvestorDetailsRepository,
                               InstitutionalInvestorDetailsRepository institutionalInvestorDetailsRepository, ObjectMapper objectMapper) {
        this.investorRepository = investorRepository;
        this.individualInvestorDetailsRepository = individualInvestorDetailsRepository;
        this.institutionalInvestorDetailsRepository = institutionalInvestorDetailsRepository;
        this.objectMapper = objectMapper;
    }


    @Transactional
    @Override
    public InvestorDTO createIndividualInvestor(IndividualInvestorDetailsDTO dto) {
        Investor investor = convertToInvestor(dto);
        Investor savedInvestor = investorRepository.save(investor);
        IndividualInvestorDetails details = convertToIndividualDetails(dto, savedInvestor);
        individualInvestorDetailsRepository.save(details);
        return convertToInvestorDTO(savedInvestor);
    }

    @Transactional
    @Override
    public InvestorDTO createInstitutionalInvestor(InstitutionalInvestorDetailsDTO dto) {
        Investor investor = convertToInvestor(dto);
        Investor savedInvestor = investorRepository.save(investor);
        InstitutionalInvestorDetails details = convertToInstitutionalDetails(dto, savedInvestor);
        institutionalInvestorDetailsRepository.save(details);
        return convertToInvestorDTO(savedInvestor);
    }

    private Investor convertToInvestor(IndividualInvestorDetailsDTO dto) {
        Investor investor = new Investor();
        investor.setType(InvestorType.HIGH_NET_WORTH_INDIVIDUAL);
        investor.setDetails(convertDtoToJson(dto)); // Simplification for example
        return investor;
    }

    private Investor convertToInvestor(InstitutionalInvestorDetailsDTO dto) {
        Investor investor = new Investor();
        investor.setType(InvestorType.INSTITUTIONAL);
        investor.setDetails(convertDtoToJson(dto)); // Simplification for example
        return investor;
    }

    private IndividualInvestorDetails convertToIndividualDetails(IndividualInvestorDetailsDTO dto, Investor investor) {
        IndividualInvestorDetails details = new IndividualInvestorDetails();
        details.setInvestor(investor);
        details.setFirstName(dto.firstName());
        details.setLastName(dto.lastName());
        details.setCountryOfResidence(dto.countryOfResidence());
        return details;
    }

    private InstitutionalInvestorDetails convertToInstitutionalDetails(InstitutionalInvestorDetailsDTO dto, Investor investor) {
        InstitutionalInvestorDetails details = new InstitutionalInvestorDetails();
        details.setInvestor(investor);
        details.setCompanyName(dto.companyName());
        details.setCountryOfIncorporation(dto.countryOfIncorporation());
        return details;
    }

    private InvestorDTO convertToInvestorDTO(Investor investor) {
        return new InvestorDTO(investor.getInvestorId(), investor.getType(), investor.getDetails());
    }

    private String convertDtoToJson(Object dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert DTO to JSON", e);
        }
    }


}
