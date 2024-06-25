package com.vega.be_coding_task_nikhil.repository;

import java.util.List;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.dto.FundSubscriptionDTO;
import com.vega.be_coding_task_nikhil.model.dto.SubscriptionInvestmentDTO;
import com.vega.be_coding_task_nikhil.model.entity.Fund;
import com.vega.be_coding_task_nikhil.model.entity.Investor;
import com.vega.be_coding_task_nikhil.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    boolean existsByInvestorAndFund(Investor investor, Fund fund);

    /**
     * Finds the state of active subscriptions with the total intended investment volume per fund.
     *
     * This method executes a JPQL query that selects new instances of the {@link FundSubscriptionDTO}
     * class, containing the fund ID, fund name, total investment volume, and count of active subscriptions.
     * It joins the {@link Subscription} entity with the {@link Fund} entity and filters the subscriptions
     * to include only those with an {@code ACTIVE} status. The results are grouped by fund ID and fund name.
     *
     *
     * @return a list of {@link FundSubscriptionDTO} instances containing the fund ID, fund name,
     *         total investment volume, and count of active subscriptions.
     */
    @Query("SELECT new com.vega.be_coding_task_nikhil.model.dto.FundSubscriptionDTO(f.fundId, f.name, SUM(s.minimumInvestmentAmount), COUNT(s)) " +
            "FROM Subscription s JOIN s.fund f " +
            "WHERE s.status = com.vega.be_coding_task_nikhil.model.enums.SubscriptionStatus.ACTIVE " +
            "GROUP BY f.fundId, f.name")
    List<FundSubscriptionDTO> findActiveSubscriptionsWithTotalInvestmentVolume();


    /**
     * Finds the state of active subscriptions with the total intended investment volume per subscription.
     *
     * <p>This method executes a JPQL query that selects new instances of the {@link SubscriptionInvestmentDTO}
     * class, containing the subscription ID, investor ID, fund ID, fund name, total investment volume, and subscription status.
     * It filters the subscriptions to include only those with an {@code ACTIVE} status and groups the results by subscription ID,
     * investor ID, fund ID, fund name, and status.</p>
     *
     *
     * @return a list of {@link SubscriptionInvestmentDTO} instances containing the subscription ID, investor ID,
     *         fund ID, fund name, total investment volume, and subscription status.
     */
    @Query("SELECT new com.vega.be_coding_task_nikhil.model.dto.SubscriptionInvestmentDTO(s.subscriptionId, s.investor.investorId, s.fund.fundId, s.fund.name, SUM(s.minimumInvestmentAmount), s.status) " +
            "FROM Subscription s " +
            "WHERE s.status = com.vega.be_coding_task_nikhil.model.enums.SubscriptionStatus.ACTIVE " +
            "GROUP BY s.subscriptionId, s.investor.investorId, s.fund.fundId, s.fund.name, s.status")
    List<SubscriptionInvestmentDTO> findActiveSubscriptionsWithTotalInvestmentVolumePerSubscription();
}

