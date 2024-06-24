package com.vega.be_coding_task_nikhil.service.investor;

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
import org.springframework.transaction.annotation.Transactional;

public class InvestorServiceImpl implements InvestorService {

    private final InvestorRepository investorRepository;
    private final IndividualInvestorDetailsRepository individualInvestorDetailsRepository;
    private final InstitutionalInvestorDetailsRepository institutionalInvestorDetailsRepository;

    @Autowired
    public InvestorServiceImpl(InvestorRepository investorRepository,
                               IndividualInvestorDetailsRepository individualInvestorDetailsRepository,
                               InstitutionalInvestorDetailsRepository institutionalInvestorDetailsRepository) {
        this.investorRepository = investorRepository;
        this.individualInvestorDetailsRepository = individualInvestorDetailsRepository;
        this.institutionalInvestorDetailsRepository = institutionalInvestorDetailsRepository;
    }

    @Transactional
    @Override
    public InvestorDTO createIndividualInvestor(IndividualInvestorDetailsDTO individualInvestorDetailsDTO) {
        Investor investor = new Investor();
        investor.setType(InvestorType.HIGH_NET_WORTH_INDIVIDUAL);
        investor.setDetails(individualInvestorDetailsDTO.toString()); // Simplification for example

        Investor savedInvestor = investorRepository.save(investor);

        IndividualInvestorDetails individualInvestorDetails = new IndividualInvestorDetails();
        individualInvestorDetails.setInvestor(savedInvestor);
        individualInvestorDetails.setFirstName(individualInvestorDetailsDTO.firstName());
        individualInvestorDetails.setLastName(individualInvestorDetailsDTO.lastName());
        individualInvestorDetails.setCountryOfResidence(individualInvestorDetailsDTO.countryOfResidence());

        individualInvestorDetailsRepository.save(individualInvestorDetails);

        return new InvestorDTO(savedInvestor.getInvestorId(), savedInvestor.getType(), savedInvestor.getDetails());
    }

    @Transactional
    @Override
    public InvestorDTO createInstitutionalInvestor(InstitutionalInvestorDetailsDTO institutionalInvestorDetailsDTO) {
        Investor investor = new Investor();
        investor.setType(InvestorType.INSTITUTIONAL);
        investor.setDetails(institutionalInvestorDetailsDTO.toString()); // Simplification for example

        Investor savedInvestor = investorRepository.save(investor);

        InstitutionalInvestorDetails institutionalInvestorDetails = new InstitutionalInvestorDetails();
        institutionalInvestorDetails.setInvestor(savedInvestor);
        institutionalInvestorDetails.setCompanyName(institutionalInvestorDetailsDTO.companyName());
        institutionalInvestorDetails.setCountryOfIncorporation(institutionalInvestorDetailsDTO.countryOfIncorporation());

        institutionalInvestorDetailsRepository.save(institutionalInvestorDetails);

        return new InvestorDTO(savedInvestor.getInvestorId(), savedInvestor.getType(), savedInvestor.getDetails());
    }





}
