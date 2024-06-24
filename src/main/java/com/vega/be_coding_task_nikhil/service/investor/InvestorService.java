package com.vega.be_coding_task_nikhil.service.investor;

import com.vega.be_coding_task_nikhil.model.dto.IndividualInvestorDetailsDTO;
import com.vega.be_coding_task_nikhil.model.dto.InstitutionalInvestorDetailsDTO;
import com.vega.be_coding_task_nikhil.model.dto.InvestorDTO;

public interface InvestorService {
    InvestorDTO createIndividualInvestor(IndividualInvestorDetailsDTO individualInvestorDetailsDTO);
    InvestorDTO createInstitutionalInvestor(InstitutionalInvestorDetailsDTO institutionalInvestorDetailsDTO);
}

