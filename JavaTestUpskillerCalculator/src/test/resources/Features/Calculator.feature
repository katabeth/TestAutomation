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

  @HappyPath
  Scenario Outline: Multiply
    Given I have a calculator
    And I enter <input1> and <input2> into the calculator
    When I press multiply
    Then the result should be <result>

    Examples:
      | input1 | input2 | result |
      | 1      | 1      | 1      |
      | 2      | 3      | 6      |
      | 9      | 9      | 81     |
      | 5      | -17    | -85    |

  @HappyPath
  Scenario: Divide
    Given I have a calculator
    And I enter 6 and 2 into the calculator
    When I press divide
    Then the result should be 3

  @SadPath
  Scenario Outline: Divide By Zero
    Given I have a calculator
    And I enter <input1> and 0 into the calculator
    When I press divide
    Then a DivideByZeroException should be thrown
    And the exception should have the message "Cannot Divide By Zero"
    Examples:
      | input1 |
      | 1      |
      | 6      |

#  @HappyPath
#  Scenario: SumOfNumbersDivisibleBy2
#    Given I have a calculator
#    And I enter the numbers below to a list
#      | 1    |
#      | 2    |
#      | 3    |
#      | 4    |
#      | 5    |
#    When I iterate through the list to add all the even numbers
#    Then the result should be 6
