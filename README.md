# straltoglobal
projects

1. To Run this project do maven clean install then run spring boot app
2. To get Contact object detail, use '/contacts' 
3. To add Contact object record use '/addContact'
4. To delete Contact object record use '/deleteContact/{id}'
JSON object

 {"LastName" : "Tiwari",
    "FirstName" : "Mithilesh",
    "salutation" : "Mr."
    }
4. update Contact Object
use :- /updateContact/{id}
Request Payload:-

  {"lastname" : "Tiwarii",
    "firstname" : "Mithilesh",
    "salutation" : "Mr."
    }
    
 5. Delete /deleteContact/{ID}
    
// BULK API
//Required csv file I have attached in class path of the project if you want to change just put in same folder and update name in constant file. 'file_dir'

5./bulkapi/job/create/{action}   -- update,insert
6. /bulkapi/batch/status/{jobID}  -- check the status of the job. put job_id which got from pervious call.
7./bulkapi/batch/result/{job_id}  -- Check the successfull added record.put job_id which got from pervious call.
