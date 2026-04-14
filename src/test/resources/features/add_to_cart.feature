Feature: Add to cart feature (BDD examples)
  In order to verify add to cart functionality
  As a tester
  I want readable scenarios demonstrating positive and negative flows

  Scenario: Add a product to the cart successfully
    Given I am logged in as "standard_user"
    When I add "Sauce Labs Backpack" to my cart
    Then the cart badge should show "1"

  Scenario: Removing product empties the cart
    Given I am logged in as "standard_user"
    When I add "Sauce Labs Bike Light" to my cart
    And I open the cart and remove that product
    Then the cart should be empty

  Scenario: Attempting to checkout with empty cart
    Given I am logged in as "standard_user"
    When I open the cart
    Then the cart should be empty
