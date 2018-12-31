<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <title></title>
  </head>
  <body>
    <div id="personInformationPanel">
      <form id="personForm">
        <div>
          <label id="personId" class="form-elements">Id</label>
          <input type="text" id="personIdDetail" class="form-elements" required />
        </div>
        <div>
          <label id="firstName" class="form-elements">First Name</label>
          <input type="text" id="personFirstNameDetail" class="form-elements" required />
        </div>
        <div>
          <label id="lastName" class="form-elements">Last Name</label>
          <input type="text" id="personLastNameDetail" class="form-elements" required />
        </div>
        <div>
          <label id="personMail" class="form-elements">E-mail</label>
          <input type="text" id="personMailDetail" class="form-elements" required />
        </div>
        <div>
         <label id="personBirthDate" class="form-elements">Birth Date</label>
          <input type="text" id="personBirthDateDetail" class="form-elements" required />
        </div>
      </form>
      <button id="submit" type="submit" >Submit</button>
      <button id="reset" type="reset" >Reset</button>
    </div>
    <script src="../scripts/personInformationPanel.js"> </script>
  </body>
</html>