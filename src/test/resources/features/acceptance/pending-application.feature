Feature: Pending application
  In order to apply for the Apply for Healthy Start programme
  As a potential claimant
  If my claim is not instantly successful I want feedback about what happens next

  @RequiresWiremock
  Scenario Outline: An application where the claimant is told we'll let you know
    Given I am on the first page of the application
    When I submit an application that doesn't get an instant answer due to: <reason>
    Then I am shown the decision page saying we'll let you know
    And the page content displays that my application is being considered
    And my claim is sent to the back end
    Examples:
      | reason                    |
      | postcode mismatch         |
      | address line one mismatch |
      | full address mismatch     |
