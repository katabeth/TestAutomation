Feature: Calculator

  Simple calculator which takes two numbers


  @HappyPath
  Scenario: Addition
    Given I have a calculator
    And I enter 5 and 2 into the calculator
    When I press add
    Then the result should be should be 7
