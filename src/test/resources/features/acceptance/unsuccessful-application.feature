Feature: Unsuccessful application
  In order to apply for the Apply for Healthy Start programme
  As a potential claimant
  If my claim is unsuccessful I want feedback about why my claim was unsuccessful

  @RequiresWiremock
  Scenario Outline: An application where the claimant's identity has not been matched fails
    Given I am on the first page of the application
    When I submit an application with details which fail due to <failureReason>
    Then I am shown an instant failure
    And the page content displays that my application was not successful
    And my claim is sent to the back end
    Examples:
      | failureReason |
      | identity      |
      | eligibility   |
