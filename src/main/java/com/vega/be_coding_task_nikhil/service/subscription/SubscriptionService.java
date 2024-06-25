package com.vega.be_coding_task_nikhil.service.subscription;

import java.util.List;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.dto.FundSubscriptionDTO;
import com.vega.be_coding_task_nikhil.model.dto.SubscriptionDTO;
import com.vega.be_coding_task_nikhil.model.dto.SubscriptionInvestmentDTO;

public interface SubscriptionService {
    SubscriptionDTO subscribeToFund(UUID investorId, UUID fundId);

    List<FundSubscriptionDTO> getActiveSubscriptionsWithTotalInvestmentVolume();

    List<SubscriptionInvestmentDTO> getActiveSubscriptionsWithTotalInvestmentVolumePerSubscription();

}
