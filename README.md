# Dimitar-Stefanov-employees

Create an application that identifies the pair of employees who have worked
together on common projects for the longest period of time.
# Input data:
A CSV file with data in the following format:
EmpID, ProjectID, DateFrom, DateTo
# Sample data:
143, 12, 2013-11-01, 2014-01-05 <br />
218, 10, 2012-05-16, NULL <br />
143, 10, 2009-01-01, 2011-04-27 <br />
...<br />
Sample output:<br />
143, 218, 8

# Specific requirements
1) DateTo can be NULL, equivalent to today <br />
2) The input data must be loaded to the program from a CSV file <br />

# Technologies
Spring Boot 2.7<br />
Swagger 3.0 <br />
Java 11 <br />

# Swagger url
http://{server address}:8080/swagger-ui

# Rest endpoint 
http://{server address}:8080/api/employees/upload <br />
Post <br />
Example curl <br />
curl -X POST "http://localhost:8080/api/employees/upload" -H "accept: application/json" -H "Content-Type: multipart/form-data" -F "file=@employees.csv;type=text/csv"
