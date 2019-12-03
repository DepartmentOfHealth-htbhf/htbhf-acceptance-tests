Feature: Confirm application
  In order to apply for the Apply for Healthy Start programme
  As a potential claimant
  I want to be shown the decision after submitting my application details

  @RequiresWiremock
  Scenario: Valid application progresses to the confirmation page
    Given I am on the first page of the application
    When I submit an application with valid details
    Then I am shown the decision page
    And all page content is present on the confirm details page
    And my entitlement is 12.40 per week with a first payment of 49.60
    And my claim is sent to the back end
