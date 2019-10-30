Feature: Privacy notice page
  In order to facilitate the beta Apply for Healthy Start programme
  As a potential claimant
  I want to see the details of how my details are used and retained

  Scenario: The back link on the Privacy notice page is not shown if we navigate to it directly
    When I navigate to the privacy notice page
    Then I am shown the privacy notice page
    And all page content is present on the privacy notice page
    And no back link is shown on the privacy notice page
