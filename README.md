# Data Strategy

1. PostgreSQL datastore. This was the obvious choice due to its tabular archectecture. Here meticulous management of financial data is important, and transactional data, user accounts, and historical records can be organized, queried, and updated with more accuracy.
2. PostgreSQL also supports JSONB data type which is ideal for storing flexible data like the "details" we need for investors.
3. Subscription management I felt needed its own entity, by linking subscription_id with investor_id and fund_id, we can manage multiple subscriptions for the same investor to different funds efficiently.
4. Subscription entity will include details such as completion status of tasks, responses to questions, and other time-stamped events to track progress through the onboarding flow.

## Schema Diagram

[![](https://mermaid.ink/img/pako:eNq9ls1ymzAQx19Fwzl5Ad-c2J66re3EH50ePMPISLY1BonqIylj-927CJBB0Jj2EE6wu0K__e-u4BxEgtBgEFA5YvggcbLlCK6J4QRdLo-P4owWfCewJIwfJrF4RwO0DY5YbYNa5Lm4z6_NZjpCezCGjKCXbzfHj-Hy-ctwiThO6M0638zGy-kzShhniUlCxt-o0gnlOsSJMFwXodctL248mBJxjdXJgkWCa8y4o_PCW5xg9DkbCUxqjjGwooJPyFBnaa80PH5LWlK_GohhgpfkSSqZog7dRvrAGozdwGUmdeD1-OcaEaoiydJ8Hw_Fbe9v8qt0dG5UEbQ20vS3vpmeFovv4-EcJZgTDHpl3ubTUsdKi5XZOU6rBwipGdY3PdwKH9eVxMO19WqW6etqMX8CSaBHYuURNQhqfbWkKhVcUUulJY5ODqmxxMdSNWenknXuSe8GVBpro2raT2fj1Xo4e0GRpKAXCbHu8sZY6dCkJA_p6EmXpdebjewljYQkjQ51fj99WTo6U_e1mfTpsnvpwwDFtC5Aq9P_D9ay1CNatPWBac0F5uqdyvZkON6uZIpFXbk0BudyhmfC3hgxOK48o6K9bcWK6d_Vp-hv4fZ9l9v77RD2WHfu2dbV-b9nEjqx-RWofLZJu11R_jGQWSj2eS0YoTyid6WBqmiTV6a3Oh-suCfQB0v_VaO8NzDP7kvBOExkKiTuON_vJgNjPmIw01UyFBpSZC4j5_PpSenonJYK_ROrHzwECZUJZgR-ZCwsHNRHCu8JbJWxPOU5XSEOGy1WGY-CgZaGPgRSmMMxGOxxrOCpOB7LH6HSev0DowXeaw?type=png)](https://mermaid.live/edit#pako:eNq9ls1ymzAQx19Fwzl5Ad-c2J66re3EH50ePMPISLY1BonqIylj-927CJBB0Jj2EE6wu0K__e-u4BxEgtBgEFA5YvggcbLlCK6J4QRdLo-P4owWfCewJIwfJrF4RwO0DY5YbYNa5Lm4z6_NZjpCezCGjKCXbzfHj-Hy-ctwiThO6M0638zGy-kzShhniUlCxt-o0gnlOsSJMFwXodctL248mBJxjdXJgkWCa8y4o_PCW5xg9DkbCUxqjjGwooJPyFBnaa80PH5LWlK_GohhgpfkSSqZog7dRvrAGozdwGUmdeD1-OcaEaoiydJ8Hw_Fbe9v8qt0dG5UEbQ20vS3vpmeFovv4-EcJZgTDHpl3ubTUsdKi5XZOU6rBwipGdY3PdwKH9eVxMO19WqW6etqMX8CSaBHYuURNQhqfbWkKhVcUUulJY5ODqmxxMdSNWenknXuSe8GVBpro2raT2fj1Xo4e0GRpKAXCbHu8sZY6dCkJA_p6EmXpdebjewljYQkjQ51fj99WTo6U_e1mfTpsnvpwwDFtC5Aq9P_D9ay1CNatPWBac0F5uqdyvZkON6uZIpFXbk0BudyhmfC3hgxOK48o6K9bcWK6d_Vp-hv4fZ9l9v77RD2WHfu2dbV-b9nEjqx-RWofLZJu11R_jGQWSj2eS0YoTyid6WBqmiTV6a3Oh-suCfQB0v_VaO8NzDP7kvBOExkKiTuON_vJgNjPmIw01UyFBpSZC4j5_PpSenonJYK_ROrHzwECZUJZgR-ZCwsHNRHCu8JbJWxPOU5XSEOGy1WGY-CgZaGPgRSmMMxGOxxrOCpOB7LH6HSev0DowXeaw)

## What are the distinct responsibilties of the subscription entity and the onboarding flow entity, and why do we need them both?

As the introduction of the Subscription entity is the most significant change I've done on the provided data model, I felt I should include this section to justify or at least elaborate on my thinking.

### Onboarding Flow Entity Responsibilities

**1. Workflow Template:** The `OnboardingFlow` entity essentially acts as a template for the set of tasks that need to be completed for a particular type of investment in a specific fund. Each `OnboardingFlow` is associated with one fund but can vary based on the investor type (e.g., individual vs. institutional).

**2. Task Organization:** It organizes the sequence and nature of tasks (each represented by a `Task` entity) that need to be executed to adhere to compliance, due diligence, and other requirements specific to a fund and investor type. This means all necessary information and actions are gathereed and completed respectively before an investment is finalized.

**3. Type-Specific Requirements:** By differentiating flows based on investor types, the system can tailor the onboarding experience and requirements according to the requirements of each investor categories.

**Why It’s Needed:**

- **Customization and Scalability:** Different funds may require different information and processes. `OnboardingFlow` allows the system to customize the investor's journey based on the fund and investor type, enhancing flexibility and scalability in offering various fund options.
- **Process Standardization:** It standardizes the process each investor goes through, ensuring consistency, completeness, and compliance across all subscriptions to a fund.

### Subscription Entity Responsibilities

**1. Record of Commitment:** The `Subscription` entity records an investor's commitment to invest in a specific fund. It includes references to both the investor and the fund, forming a concrete record of the subscription.

**2. Status Tracking:** It tracks the status of this investment process (e.g., active, completed, cancelled), providing real-time insights into the state of each investment from initiation to completion or cancellation.

**3. Historical and Audit Data:** Each `Subscription` maintains historical data for transactions, which is crucial for audits, investor relations, and compliance reporting.

**4. Link to Onboarding Execution:** While the `OnboardingFlow` defines what tasks need to be completed, the `Subscription` links these tasks to a specific investor and their actual progress through the onboarding flow. It often interacts with entities like `TaskResponse` and `QuestionResponse` to track more granular progress.

**Why It’s Needed:**

- **Investor Relationship Management:** Tracks each investor's engagement and commitment to specific funds, essential for managing investor relations and fund management.
- **Compliance and Reporting:** Ensures that all investments are tracked from a regulatory perspective, facilitating compliance and reporting.
- **Dynamic Interaction:** Enables the dynamic interaction between an investor's actions and the system's requirements, adjusting the flow as needed based on actual progress or changes in the investor's status or decisions.
