Feature: Basketball England Account Creation

  Scenario Outline: Create an account
    Given the user is using the browser "<browser>"
    Given the user is on the account creation page
    And the user enters "<dateOfBirth>", "<firstName>", "<lastName>", "<email>", "<password>", "<passwordConfirmation>", "<role>", "<comPrefs>", "<choice>"
    When the user clicks on the Confirm and Join-button
    Then the account is created

    Examples:
      | browser | dateOfBirth | firstName | lastName   | email             | password | passwordConfirmation | role                       | comPrefs             | choice  |
      | Chrome  | 01/01/1930  | Lennart   | Lindqvist  | t0303@example.com | test123  | test123              | Fan, Coach, Player         | Marketing            | Accept  |
      | Firefox | 02/12/2000  | Nils      | Östlund    | t0304@example.com | test123  | test123              | Coach, Sports science role | Partners             | Accept  |

  Scenario Outline: Create an account with invalid input
    Given the user is using the browser "<browser>"
    Given the user is on the account creation page
    And the user enters "<dateOfBirth>", "<firstName>", "<lastName>", "<email>", "<password>", "<passwordConfirmation>", "<role>", "<comPrefs>", "<choice>"
    When the user clicks on the Confirm and Join-button
    Then the user should see the error message "<errorMessage>"

    Examples:
      | browser | dateOfBirth | firstName | lastName   | email             | password | passwordConfirmation | role                               | comPrefs             | errorMessage               | choice  |
      | Chrome  | 02/12/2000  | Nils      |            | t0305@example.com | test123  | test123              | Coach, Sports science role         | Partners             | Last name is missing       | Accept  |
      | Chrome  | 29/02/2000  | Erik      | Henriksson | t0306@example.com | test123  | test234              | Player, Player's relative/guardian | Marketing & Partners | Passwords do not match     | Accept  |
      | Chrome  | 29/02/2000  | Jens      | Hansson    | t0307@example.com | test123  | test123              | Player                             | Marketing            | Code of Ethics and Conduct | Decline |

  Scenario Outline: Create an account with parental responsibility
    Given the user is using the browser "<browser>"
    Given the user is on the account creation page
    And the user enters "<dateOfBirth>", "<firstName>", "<lastName>", "<email>", "<password>", "<passwordConfirmation>", "<role>", "<comPrefs>", "<choice>"
    And the user enters "<parentFirstName>", "<parentLastName>", "<parentEmail>"
    When the user clicks on the Confirm and Join-button
    Then the account is created

    Examples:
      | browser | dateOfBirth | firstName | lastName   | email             | password | passwordConfirmation | role                       | comPrefs  | choice  | parentFirstName | parentLastName | parentEmail       |
      | Chrome  | 01/01/2012  | Lennart   | Lindqvist  | t0314@example.com | test123  | test123              | Fan, Coach, Player         | Marketing | Accept  | Agda            | Bengtsson      | t0312@example.com |
      | Firefox | 02/12/2012  | Nils      | Östlund    | t0315@example.com | test123  | test123              | Coach, Sports science role | Partners  | Accept  | Henrik          | Nilsson        | t0313@example.com |

