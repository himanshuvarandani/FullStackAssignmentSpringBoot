## FullStackAssignmentSpringBoot

This Assignment is to make the file explorer web application using java and spring boot.

It is divided into 2 parts:

1. Script to save drive details to database.
2. Web Application to show the drive details from database with drilling down to each folder.

### How to Run?

1. Clone the project in spring tool suite.
2. Create the table in the database with the following columns:
    - id -> integer
    - name -> string
    - type -> string ('drive', 'folder', 'file')
    - folders -> integer (showing number of folders in this specific folder)
    - files -> integer  (showing number of files in this specific folder)
    - parent_folder_id -> integer
    - path -> string

3. Change the database credentials and drive details in the DriveDetails project
4. Run the DriveDetails script.
5. Change the database credentials in the FileExplorer project application.properties file.
6. Start the web application and go to the url '/assignment/drives'.




