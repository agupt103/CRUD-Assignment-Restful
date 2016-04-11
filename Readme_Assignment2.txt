I am submitting two maven projects.
1. CRUD-Gradebook-Client-1207301364-agupt103-Eclipse
2. CRUD-Gradebook-Server-1207301364-agupt103-Eclipse

First import the above two projects seperately as existing maven projects and do a maven build in Eclipse. I have used java compiler 1.7.
Its a dynamic web module and configured on Apache tomcat 8.0.

Run both the client and server applications on tomcat 8.0.
Index page of client will be opened up. Click on one of the CRUD services.

1.Create - Enter the id, grading item, feedback and the grades of the student and submit it. It will create a new gradebook each and every time by erasing all 
the previous information. Even if you leave all the fields blank, it will create a new gradebook with only the elements with blank entries.

2.Read - Enter both the id and grading item of the student to retrieve the grades and feedback of the student. Both the fields are mandatory.

3.Update - Enter the student id, grading item, grades and feedback to update the information of the student or to add a new student in the xml. Student id and item is 
mandatory. If you will enter the existing information, it won't update the xml, otherwise it will update it. If you will enter the information of the new student, it will add that into xml.

4. Delete - Enter both the id and grading item of the student to delete the information of the student. Both the fields are mandatory.

Structure of my xml:

<Gradebook>
    <Student id="12">
        <GradingItems>test</GradingItems>
        <Grades/>
        <Feedback/>
    <GradingItems>test1</GradingItems>
        <Grades>78</Grades>
        <Feedback>good</Feedback>
    <GradingItems>test2</GradingItems>
        <Grades/>
        <Feedback/>
    </Student>
</Gradebook>

where Gradebook is a root element, Student id is unique and GradingItems, Grades and feedback are its child elements. I have used a Jersey framework.