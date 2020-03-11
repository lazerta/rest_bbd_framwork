Feature:  validate todo Api
  Scenario:  Verify if place is being Successfully added using Add Place Api
    Given  path "/" with query param as following:
    | key |value
    | user|1
    | user|1



    When user make "GET" Request
    Then  status code is 200

