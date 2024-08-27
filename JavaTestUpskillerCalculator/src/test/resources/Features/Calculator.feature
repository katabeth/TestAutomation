Feature: Calculator

  Simple calculator which takes two numbers

  Background:
    Given I have a calculator

  @HappyPath # Just a tag
  Scenario: Addition
    And I enter 5 and 2 into the calculator
    When I press add
    Then the result should be 7

  @HappyPath
  Scenario Outline: Subtract
    And I enter <input1> and <input2> into the calculator
    When I press subtract
    Then the result should be <result>

    Examples:
      |input1|input2|result|
      |1     |1     |0     |
      |0     |1     |-1    |
      |1000  |1     |999   |
