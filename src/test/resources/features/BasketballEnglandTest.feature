Feature: Basketball England Account Creation

  Scenario Outline: Create an account
    Given the user is using the browser "<browser>"
    Given the user is on the account creation page
    And the user enters "<firstName>" in the First Name field
    * the user enters "<lastName>" in the Last Name field
    * the user enters "<dateOfBirth>" in the Date of Birth field
    * the user enters "<email>" in the Email fields
    * the user enters "<password>" and "<passwordConfirmation>" in the Password Fields
    * the user selects their "<role>" in the Role form
    * the user accepts the Terms and Conditions
    * the user confirms age over 18
    * the user checks their communication preferences "<communicationPreferences>"
    * the user "<choice>" the Code of Ethics and Conduct
    When the user clicks on the Confirm and Join-button
    Then "<expectedOutcome>" should occur
    And if invalid the user should see the error message "<errorMessage>"


    Examples:
      | browser | dateOfBirth | firstName | lastName   | email          | password | passwordConfirmation | role                               | communicationPreferences | expectedOutcome | errorMessage               | choice  |
      | Chrome  | 01/01/1930  | Lennart   | Lindqvist  | test1@test.com | test123  | test123              | Fan, Coach, Player                 | Marketing                | Success         |                            | Accept  |
      | Chrome  | 02/12/2000  | Nils      |            | test2@test.com | test123  | test123              | Coach, Sports science role         | Partners                 | Failure         | Last Name is required      | Accept  |
      | Chrome  | 29/02/2000  | Erik      | Henriksson | test3@test.com | test123  | test234              | Player, Player's relative/guardian | Marketing & Partners     | Failure         | Password did not match     | Accept  |
      | Chrome  | 29/02/2000  | Jens      | Hansson    | test4@test.com | test123  | test123              | Player                             | Marketing                | Failure         | Code of Ethics and Conduct | Decline |


