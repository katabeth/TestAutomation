Feature: Inventory
  Add a test for:
  The number of items on the inventory page is 6
  Adding an item to the basket increases the basket count by 1
  Refactor your step definitions and gherkin scripts to as to avoid repetition
  You can provide the trainees with the following:

  Background:
    Given I am on the inventory page
    And I am logged in as a standard user

  @Happy
  Scenario: Number of items on the inventory page is 6
    When I count the number of items
    Then I should see 6 items

  @Happy
  Scenario: Adding an item to the basket increases the basket count by 1
    When I add an item to basket
    Then the basket count should increase by 1
