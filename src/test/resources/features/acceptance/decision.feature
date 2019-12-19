Feature: Confirm application
  In order to apply for the Apply for Healthy Start programme
  As a potential claimant
  I want to be shown the decision after submitting my application details

  @RequiresWiremock
  Scenario: Valid application progresses to the confirmation page
    Given I am on the first page of the application
    When I submit an application with valid details
    Then I am shown the decision page
    And all page content is present on the decision details page
    And my entitlement is £9.30 per week with a first payment of £37.20
    And my claim is sent to the back end

  @RequiresWiremock
  Scenario Outline: An application where the claimant is told we'll let you know due to <reason>
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
      | email mismatch            |
      | phone mismatch            |
      | email and phone mismatch  |

  @RequiresWiremock
  Scenario: An application where a non-pregnant claimant is told we'll let you know due to an address mismatch
    Given I am on the first page of the application
    When I submit an application for a non-pregnant claimant that doesn't get an instant answer due to an address mismatch
    Then I am shown the decision page saying we'll let you know
    And the page content displays that my application is being considered
    And my claim is sent to the back end

  @RequiresWiremock
  Scenario Outline: An instant fail application due to <failureReason>
    Given I am on the first page of the application
    When I submit an application with details which fail due to <failureReason>
    Then I am shown an instant failure
    And the page content displays that my application was not successful
    And my claim is sent to the back end
    Examples:
      | failureReason             |
      | identity not matched      |
      | eligibility not confirmed |
      | duplicate claim           |
      | child dob mismatch        |
