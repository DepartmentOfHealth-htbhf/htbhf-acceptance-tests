Feature: Enter NHS number
  In order to apply for the Apply for Healthy Start programme
  As a potential claimant
  I want to enter my NHS number

  Background:
    Given I navigate to the nhs number page

  Scenario: Enter in a valid nhs number
    When I enter a valid nhs number
    Then I am shown the test name page

  Scenario: Do not enter in a "nhs number"
    When I do not enter a nhs number
    Then I am informed that the nhs number is blank

  Scenario Outline: Fill "nhs number" with invalid format
    When I enter <invalidnhsno> as my nhs number
    Then I am informed that the nhs number is in the wrong format
    Then I see the value <invalidnhsno> in the input in the same format as I entered it in

    Examples:
      | invalidnhsno       |
      | 123456             |
      | ABCDEF             |


